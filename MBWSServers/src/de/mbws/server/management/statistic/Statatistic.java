package de.mbws.server.management.statistic;

import de.mbws.server.AbstractTcpServer;

/**
 * Description: 
 * @author Azarai
 *
 */
public class Statatistic implements StatatisticMBean {

    private AbstractTcpServer server;
    /* (non-Javadoc)
     * @see de.mbws.server.management.statistic.IStatatisticMBean#getCurrentPlayersOnline()
     */
    public int getCurrentPlayersOnline() {
        return server.getAllPlayers().size();
    }
    /**
     * @param server
     */
    public Statatistic(AbstractTcpServer server) {
        super();
        // TODO Auto-generated constructor stub
        this.server = server;
    }
    /**
     * 
     */
    public Statatistic() {
        super();
        // TODO Auto-generated constructor stub
    }

}
