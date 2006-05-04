package de.mbws.server.account;

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
import de.mbws.server.core.MBWSServerPlugin;
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
            AbstractPlayerData spd = (AbstractPlayerData) players.get(element);
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

    @Override
    protected void processShutdown() {
        if (logger.isDebugEnabled()) {
            logger.debug("Accountserver is shuting down....");
        }
        //TODO additional stuff required during shutdown?
    }

    /* (non-Javadoc)
     * @see de.mbws.server.AbstractTcpServer#getEventControllerIdentifier()
     */
    @Override
    protected String getEventControllerIdentifier() {
        return MBWSServerPlugin.ACCOUNT_SERVER_EXTENSION_NAME;
    }
}