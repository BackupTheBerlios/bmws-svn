package de.mbws.server.account;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import de.mbws.common.data.AbstractPlayerData;
import de.mbws.common.events.EventTypes;
import de.mbws.server.AbstractTcpServer;
import de.mbws.server.ServerConfig;
import de.mbws.server.account.controller.AccountEventController;
import de.mbws.server.account.controller.CharacterEventController;
import de.mbws.server.account.controller.LoginEventController;
import de.mbws.server.controller.MovementEventController;
import de.mbws.server.data.ServerPlayerData;

public class AccountServer extends AbstractTcpServer {

    private static Logger logger = Logger.getLogger(AccountServer.class);
    private static int nextSessionId = 0;

    /**
     * @param config
     */
    public AccountServer(ServerConfig config) {
        super(config);
    }

    protected void registerEventController() {
        eventReader.put(new Integer(EventTypes.LOGIN), new LoginEventController(this, EventTypes.LOGIN));
        eventReader.put(new Integer(EventTypes.LOGOUT), new LoginEventController(this, EventTypes.LOGOUT));
        eventReader.put(new Integer(EventTypes.ACCOUNT_CREATE), new AccountEventController(this, EventTypes.ACCOUNT_CREATE));
        
        eventReader.put(new Integer(EventTypes.CHARACTER_RECEIVE_REQUEST), new CharacterEventController(this, EventTypes.CHARACTER_RECEIVE_REQUEST));
        eventReader.put(new Integer(EventTypes.MOVEMENT_START_WALK), new MovementEventController(this, EventTypes.MOVEMENT_START_WALK));
    }


    public Map getAllPlayers() {
        return clients;
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
}