package de.mbws.server.account;

import java.lang.reflect.Constructor;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.apache.log4j.Logger;

import de.mbws.common.data.AbstractPlayerData;
import de.mbws.server.AbstractTcpServer;
import de.mbws.server.ServerConfig;
import de.mbws.server.configuration.EventController;
import de.mbws.server.controller.AbstractEventController;
import de.mbws.server.data.ServerPlayerData;
import de.mbws.server.management.MBeanHelper;
import de.mbws.server.management.statistic.Statatistic;

public class AccountServer extends AbstractTcpServer {

    private static Logger logger = Logger.getLogger(AccountServer.class);
    private static int nextSessionId = 0;

    private Integer worldServerSessionId=0;
    /**
     * @param config
     */
    public AccountServer(ServerConfig config) {
        super(config);
        try {
            MBeanServer mbs = MBeanHelper.getMBeanServer();
            ObjectName name = new ObjectName(this.getClass().getCanonicalName()+ ":type=Statistics");
            Statatistic smb = new Statatistic(this);
            mbs.registerMBean(smb, name);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
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
                partypes[0] = AccountServer.class;
                Constructor ct = cls.getConstructor(partypes);
                Object retobj = ct.newInstance(arglist);
                eventReader.put(new Integer(ecs[i].getType()), (AbstractEventController) retobj);
            }
        } catch (Exception e) {
            logger.error("Cant init EventController", e);
            System.exit(1);
        }
        
//        eventReader.put(new Integer(EventTypes.LOGIN), new LoginEventController(this, EventTypes.LOGIN));
//        eventReader.put(new Integer(EventTypes.LOGOUT), new LoginEventController(this, EventTypes.LOGOUT));
//        eventReader.put(new Integer(EventTypes.ACCOUNT_CREATE), new AccountEventController(this, EventTypes.ACCOUNT_CREATE));
//        
//        eventReader.put(new Integer(EventTypes.CHARACTER_RECEIVE_REQUEST), new CharacterEventController(this, EventTypes.CHARACTER_RECEIVE_REQUEST));
//        eventReader.put(new Integer(EventTypes.MOVEMENT_START_WALK), new MovementEventController(this, EventTypes.MOVEMENT_START_WALK));
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
    
    public void addPlayer(Integer sessionId, AbstractPlayerData p) {
        clients.put(sessionId, p);
    }

    public void removePlayer(AbstractPlayerData p) {
        removePlayer(p.getSessionId());
    }
    
    public void removePlayer(Integer sessionId) {
        clients.remove(sessionId);
        if (logger.isDebugEnabled()) {
            logger.debug("Session removed id=" + sessionId);
        }
    }
    
    /**
     * Return the next available sessionId
     */
    public synchronized Integer nextSessionId() {
        return new Integer(++nextSessionId);
    }
    
    public void handleClientConnectionLost(SocketChannel channel) {
        Map players = getAllPlayers();
        Set sessiosnIdKeys = players.keySet();
        Integer sessionId = null;
        for (Iterator iter = sessiosnIdKeys.iterator(); iter.hasNext();) {
            Integer element = (Integer) iter.next();
            ServerPlayerData spd = (ServerPlayerData) players.get(element);
            if (channel.equals(spd.getChannel())) {
                sessionId = element;
                break;
            }
        }
        if (sessionId != null) {
            removePlayer(sessionId);
        }
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

    public Integer getWorldServerSessionId() {
        return worldServerSessionId;
    }

    public void setWorldServerSessionId(Integer worldServerSessionId) {
        this.worldServerSessionId = worldServerSessionId;
    }
}