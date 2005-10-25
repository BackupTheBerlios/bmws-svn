package de.mbws.server.account;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import de.mbws.common.EventQueue;
import de.mbws.common.Player;
import de.mbws.common.events.AbstractGameEvent;
import de.mbws.common.events.EventTypes;
import de.mbws.server.EventWriter;
import de.mbws.server.RequestDispatcher;
import de.mbws.server.controller.AccountEventController;
import de.mbws.server.controller.EventController;
import de.mbws.server.controller.EventDispatcher;
import de.mbws.server.controller.LoginEventController;

public class AccountServer extends Thread {

    private static Logger logger = Logger.getLogger(AccountServer.class);

    private ServerSocketChannel sSockChan;

    private boolean running;

    private RequestDispatcher dispatcher;

    private Selector selector;

    private HashMap eventReader = new HashMap();

    private HashMap<Integer, Player> clients = new HashMap<Integer, Player>() ;

    private static EventQueue outgoingEventQueue = new EventQueue("GameEvents-out");
   
    private static EventQueue incomingEventQueue = new EventQueue("GameEvents-in");
   
    private static int nextSessionId = 0;

    private EventDispatcher eventController = null;
    private EventWriter eventWriter = null;
    /**
     * 
     */
    public AccountServer(int c2cport, int s2sport, int queueWorkerSize) {
        super();
        try {
            logger.info("AccountServer initializing");
            initServerSocket(c2cport);
            registerEventController();
            eventController = new EventDispatcher(this,incomingEventQueue, queueWorkerSize);
            eventWriter = new EventWriter(this,outgoingEventQueue, queueWorkerSize);
            dispatcher = new RequestDispatcher(this);
            dispatcher.start();
            logger.info("AccountServer initializing ... done");
        } catch (Exception e) {
            logger.error("Error during AccountServer initializing", e);
        }
    }

    public void run() {
        logger.info("******** AccountServer running ********");
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

                    logger.info("got connection from: " + clientChannel.socket().getRemoteSocketAddress());
                }
            } catch (IOException ioe) {
                logger.warn("error during serverSocket select(): " + ioe.getMessage());
            } catch (Exception e) {
                logger.error("exception in run()", e);
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
            logger.info("binding to address: " + addr.getHostAddress() + " and port: " + port);
            sSockChan.socket().bind(new InetSocketAddress(port));

            // get a selector
            selector = Selector.open();

            // register the channel with the selector to handle accepts
            SelectionKey acceptKey = sSockChan.register(selector, SelectionKey.OP_ACCEPT);
        } catch (Exception e) {
            logger.error("error initializing ServerSocket", e);
            System.exit(1);
        }
    }

    private void registerEventController() {
        eventReader.put(new Integer(EventTypes.LOGIN), new LoginEventController(this, EventTypes.LOGIN));
        eventReader.put(new Integer(EventTypes.LOGOUT), new LoginEventController(this, EventTypes.LOGOUT));
        eventReader.put(new Integer(EventTypes.ACCOUNT_CREATE), new AccountEventController(this, EventTypes.ACCOUNT_CREATE));
    }

    public EventController getEventController(int eventType) {
        if (eventReader.containsKey(new Integer(eventType))) {
            return (EventController) eventReader.get(new Integer(eventType));
        }
        return null;
    }

    /**
     * fetches the Player for a given sessionId
     */
    public Player getPlayerBySessionId(Integer id) {
        return clients.get(id);
    }

    public void addPlayer(Integer sessionId, Player p) {
        clients.put(sessionId, p);
    }
    public void removePlayer(Player p) {
        clients.remove(p.getSessionId());
    }
    
    /**
     * Return the next available sessionId
     */
    public synchronized Integer nextSessionId() {
        return new Integer(nextSessionId++);
    }
    
    public void handleIncomingEvent(AbstractGameEvent event){
        incomingEventQueue.enQueue(event);
    }
    
    public void handleOutgoingEvent(AbstractGameEvent event){
        outgoingEventQueue.enQueue(event);
    }
}