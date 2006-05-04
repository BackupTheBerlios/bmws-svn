package de.mbws.server;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import de.mbws.common.EventQueue;
import de.mbws.common.data.AbstractPlayerData;
import de.mbws.common.events.AbstractGameEvent;
import de.mbws.common.events.EventTypes;
import de.mbws.common.events.MessageEvent;
import de.mbws.common.events.data.generated.SystemInformationData;
import de.mbws.server.controller.AbstractEventController;
import de.mbws.server.controller.AbstractEventTransformer;

/**
 * Description:
 * 
 * @author Azarai
 */
public abstract class AbstractTcpServer extends Thread {
    private ServerSocketChannel sSockChan;

    private boolean running;

    protected RequestDispatcher dispatcher;

    protected Selector selector;

    protected HashMap<Integer, AbstractEventController> eventReader = new HashMap<Integer, AbstractEventController>();

    protected List<AbstractEventTransformer> eventTransformer = new ArrayList<AbstractEventTransformer>();

    private EventQueue outgoingEventQueue = new EventQueue("GameEvents-out");

    private HashMap<Integer, AbstractPlayerData> clients = new HashMap<Integer, AbstractPlayerData>();

    private EventQueue incomingEventQueue = new EventQueue("GameEvents-in");

    private EventDispatcher eventController = null;

    private EventWriter eventWriter = null;

    protected ServerConfig config;

    public AbstractTcpServer(ServerConfig config) {
        super();
        try {
            getLogger().info("Server initializing");

            this.config = config;
            initServerSocket(config.getC2sport());
            registerEventController();
            eventController = new EventDispatcher(this, incomingEventQueue, config.getQueueWorkerSize());
            eventWriter = new EventWriter(this, outgoingEventQueue, config.getQueueWorkerSize());
            dispatcher = new RequestDispatcher(this);
            dispatcher.start();
            getLogger().info("Server initializing ... done");
        } catch (Exception e) {
            getLogger().error("Error during Server initializing", e);
        }
    }

    public void run() {
        getLogger().info("******** Server running ********");
        running = true;

        while (running) {
            try {
                selector.select();
                Set readyKeys = selector.selectedKeys();
                // run through the keys and process
                Iterator i = readyKeys.iterator();
                while (i.hasNext()) {
                    SelectionKey key = (SelectionKey) i.next();
                    i.remove();
                    ServerSocketChannel ssChannel = (ServerSocketChannel) key.channel();
                    SocketChannel clientChannel = ssChannel.accept();
                    dispatcher.addNewClient(clientChannel);

                    getLogger().info("got connection from: " + clientChannel.socket().getRemoteSocketAddress());
                }
            } catch (IOException ioe) {
                getLogger().warn("error during serverSocket select(): " + ioe.getMessage());
            } catch (Exception e) {
                getLogger().error("exception in run()", e);
            }
        }
    }

    private void initServerSocket(int port) {
        try {
            // open a non-blocking server socket channel
            sSockChan = ServerSocketChannel.open();
            sSockChan.configureBlocking(false);

            // bind to localhost on designated port
            InetAddress addr = InetAddress.getLocalHost();
            getLogger().info("binding to address: " + addr.getHostAddress() + " and port: " + port);
            sSockChan.socket().bind(new InetSocketAddress(port));

            // get a selector
            selector = Selector.open();

            // register the channel with the selector to handle accepts
            SelectionKey acceptKey = sSockChan.register(selector, SelectionKey.OP_ACCEPT);
        } catch (Exception e) {
            getLogger().error("error initializing ServerSocket", e);
            System.exit(1);
        }
    }

    public AbstractEventController getEventController(int eventType) {
        if (eventReader.containsKey(new Integer(eventType))) {
            return eventReader.get(new Integer(eventType));
        }
        return null;
    }

    public Map<Integer, AbstractPlayerData> getAllPlayers() {
        return clients;
    }

    public void addPlayer(Integer sessionId, AbstractPlayerData p) {
        synchronized (clients) {
            clients.put(sessionId, p);
        }
    }

    public void removePlayer(AbstractPlayerData p) {
        removePlayer(p.getSessionId());
    }

    public void removePlayer(Integer sessionId) {
        synchronized (clients) {
            clients.remove(sessionId);
        }
        if (getLogger().isDebugEnabled()) {
            getLogger().debug("Session removed id=" + sessionId);
        }
    }

    protected void registerEventController() {
        if (getEventControllerIdentifier() == null) {
            getLogger().error("No Eventcontroller specified. eventControllerIdentifier is null");
        }

        IExtensionRegistry registry = Platform.getExtensionRegistry();
        IExtensionPoint extensionPoint = registry.getExtensionPoint("de.mbws.servers.core.eventcontrollers");
        IConfigurationElement[] controllers = extensionPoint.getConfigurationElements();
        for (int m = 0; m < controllers.length; m++) {
            IConfigurationElement elem = controllers[m];
            if (getEventControllerIdentifier().equals(elem.getName())) {
                IConfigurationElement[] currControllers = elem.getChildren();
                for (int i = 0; i < currControllers.length; i++) {
                    Object arglist[] = new Object[1];
                    arglist[0] = this;
                    try {
                        Class cls = Class.forName(currControllers[i].getAttribute("controllerClass"));
                        Class partypes[] = new Class[1];
                        partypes[0] = AbstractTcpServer.class;
                        Constructor ct = cls.getConstructor(partypes);
                        Object retobj = ct.newInstance(arglist);
                        Integer[] types = ((AbstractEventController) retobj).getSupportedEventTypes();
                        for (int j = 0; j < types.length; j++) {
                            eventReader.put(types[j], (AbstractEventController) retobj);
                            eventTransformer.add((AbstractEventTransformer) currControllers[i].createExecutableExtension("transformerClass"));
                            StringBuilder sb = new StringBuilder();
                            sb.append("register for eventtype ");
                            sb.append(types[j]);
                            sb.append(" controller ");
                            sb.append(retobj.getClass().getName());
                            getLogger().info(sb.toString());
                        }

                    } catch (Exception e) {
                        getLogger().error("Cant init EventController", e);
                        System.exit(1);
                    }
                }
            }
        }
    }

    protected abstract Logger getLogger();

    protected abstract String getEventControllerIdentifier();

    /**
     * fetches the Player for a given sessionId
     */
    public AbstractPlayerData getPlayerBySessionId(Integer id) {
        return clients.get(id);
    }

    public void handleIncomingEvent(AbstractGameEvent event) {
        incomingEventQueue.enQueue(event);
    }

    public void handleOutgoingEvent(AbstractGameEvent event) {
        outgoingEventQueue.enQueue(event);
    }

    public abstract void handleClientConnectionLost(SocketChannel channel);

    protected abstract void processShutdown();

    public void shutdown() {
        Map<Integer, AbstractPlayerData> players = getAllPlayers();
        Set keys = players.keySet();
        SystemInformationData sid = new SystemInformationData();
        sid.setSeverity(Byte.parseByte("1"));
        sid.setMessageCodeKey("system.shutdown");
        sid.setAdditionalInfo("Server shutdown. Please be patient.");
        MessageEvent me = new MessageEvent(sid);
        me.setEventType(EventTypes.S2C_SYSTEM_MESSAGE);

        ArrayList<Integer> allPlayer = new ArrayList<Integer>();
        for (Iterator iter = keys.iterator(); iter.hasNext();) {
            Integer key = (Integer) iter.next();
            AbstractPlayerData element = players.get(key);
            allPlayer.add(element.getSessionId());
        }
        me.setRecipients(allPlayer.toArray(new Integer[allPlayer.size()]));
        handleOutgoingEvent(me);
        processShutdown();
    }

    public AbstractGameEvent getEvent(ByteBuffer payload, AbstractPlayerData p) {

        for (Iterator iter = eventTransformer.iterator(); iter.hasNext();) {
            AbstractEventTransformer transformer = (AbstractEventTransformer) iter.next();
            AbstractGameEvent event = transformer.getGameEvent(payload, p);
            if (event != null) {
                return event;
            }
        }
        return null;
    }
}
