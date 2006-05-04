package de.mbws.server.world.controller;

import de.mbws.server.AbstractTcpServer;
import de.mbws.server.controller.AbstractEventController;
import de.mbws.server.world.WorldServer;

/**
 * Description: 
 * @author Azarai
 *
 */
public abstract class WorldServerBaseEventController extends AbstractEventController {
   
    /**
     * @param server
     */
    public WorldServerBaseEventController(AbstractTcpServer server) {
        super(server);
    }

    protected WorldServer getWorldServer() {
        return (WorldServer) server;
    }
}
