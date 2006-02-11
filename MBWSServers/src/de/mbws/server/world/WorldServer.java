package de.mbws.server.world;

import java.lang.reflect.Constructor;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.*;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.apache.log4j.Logger;

import de.mbws.common.data.AbstractPlayerData;
import de.mbws.common.events.EventTypes;
import de.mbws.common.events.LoginEvent;
import de.mbws.common.events.data.generated.ServerLoginData;
import de.mbws.common.events.data.generated.ServerRedirectData;
import de.mbws.common.exceptions.InitializationException;
import de.mbws.server.AbstractTcpServer;
import de.mbws.server.ServerConfig;
import de.mbws.server.configuration.EventController;
import de.mbws.server.controller.AbstractEventController;
import de.mbws.server.data.ServerCommunicationData;
import de.mbws.server.data.ServerPlayerData;
import de.mbws.server.management.MBeanHelper;
import de.mbws.server.management.statistic.Statatistic;

public class WorldServer extends AbstractTcpServer {

    private static Logger logger = Logger.getLogger(WorldServer.class);

    SocketChannel accountServerChannel;

    protected HashMap<Integer, AbstractPlayerData> servers = new HashMap<Integer, AbstractPlayerData>();

    /**
     * @param config
     */
    public WorldServer(ServerConfig config) {
        super(config);
        try {
            MBeanServer mbs = MBeanHelper.getMBeanServer();
            ObjectName name = new ObjectName(this.getClass().getCanonicalName() + ":type=Statistics");
            Statatistic smb = new Statatistic(this);
            mbs.registerMBean(smb, name);
//            GameTimerController.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void connect() throws InitializationException {
        try {
            if (accountServerChannel == null || !accountServerChannel.isConnected()) {
                accountServerChannel = SocketChannel.open(new InetSocketAddress(config.getAccountServerIp(), config.getAccountServerPort()));
                accountServerChannel.configureBlocking(false);
                accountServerChannel.socket().setTcpNoDelay(true);
                sleep(50);
                dispatcher.addNewClient(accountServerChannel);
                // accountServerChannel.register(selector, SelectionKey.OP_READ,
                // new Attachment());

                ServerCommunicationData scd = new ServerCommunicationData();
                scd.setChannel(accountServerChannel);
                scd.setSessionId(0);
                ServerLoginData sld = new ServerLoginData();
                sld.setName("worldserver");
                sld.setPassword("worldserver");
                ServerRedirectData srd = new ServerRedirectData();
                srd.setHost(config.getMyClientIP());
                srd.setPort(config.getC2sport());
                sld.setHostData(srd);
                LoginEvent lv = new LoginEvent(sld);
                lv.setPlayer(scd);
                lv.setEventType(EventTypes.S2S_LOGIN);
                handleOutgoingEvent(lv);
            }
        } catch (Exception e) {
            logger.error("Error during server connection", e);
            throw new InitializationException("Error during server connection. see log file for more information");
        }
    }

    protected void registerEventController() {
        EventController[] ecs = config.getEventControllers().getEventController();
        Object arglist[] = new Object[1];
        arglist[0] = this;
        try {
            for (int i = 0; i < ecs.length; i++) {
                Class cls = Class.forName(ecs[i].getClazz());
                Class partypes[] = new Class[1];
                partypes[0] = WorldServer.class;
                Constructor ct = cls.getConstructor(partypes);
                Object retobj = ct.newInstance(arglist);
                eventReader.put(new Integer(ecs[i].getType()), (AbstractEventController) retobj);
            }
        } catch (Exception e) {
            logger.error("Cant init EventController", e);
            System.exit(1);
        }
    }

    public ArrayList<Integer> getSessionIDOfAllPlayers() {
        Map m = getAllPlayers();
        Set keys = m.keySet();
        ArrayList<Integer> allPlayer = new ArrayList<Integer>();
        for (Iterator iter = keys.iterator(); iter.hasNext();) {
            Integer key = (Integer) iter.next();
            ServerPlayerData element = (ServerPlayerData) m.get(key);
            allPlayer.add(element.getSessionId());
        }
        return allPlayer;
    }

    public void handleClientConnectionLost(SocketChannel channel) {
        Map players = getAllPlayers();
        Set sessiosnIdKeys = players.keySet();
        Integer sessionId = null;
        for (Iterator iter = sessiosnIdKeys.iterator(); iter.hasNext();) {
            Integer element = (Integer) iter.next();
            AbstractPlayerData spd = (AbstractPlayerData) players.get(element);
            if (channel.equals(spd.getChannel())) {
                sessionId = element;
                break;
            }
        }
        if (sessionId != null) {
            removePlayer(sessionId);
            //TODO send client an object remove event
        }
    }

    public void addServer(Integer sessionId, ServerCommunicationData scd) {
        servers.put(sessionId, scd);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

    public AbstractPlayerData getServerBySessionId(Integer id) {
        return servers.get(id);
    }

    public AbstractPlayerData getPlayerBySessionId(Integer id) {
        AbstractPlayerData apd = super.getPlayerBySessionId(id);
        if (apd == null) {
            apd = getServerBySessionId(id);
            if (apd == null) {
                // assume we are talking with the
                // accountserver; its damn bad and needs
                // to be changed later
                apd = new ServerCommunicationData();
            }
        }
        return apd;
    }

    @Override
    protected void shutdown() {
        if (logger.isDebugEnabled()) {
            logger.debug("WorldServer is shuting down....");
        }
    }
}