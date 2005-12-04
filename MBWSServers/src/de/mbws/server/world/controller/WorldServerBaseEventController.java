package de.mbws.server.world.controller;

import de.mbws.common.events.AbstractGameEvent;
import de.mbws.server.AbstractTcpServer;
import de.mbws.server.controller.AbstractEventController;
import de.mbws.server.world.WorldServer;

/**
 * Description: 
 * @author Azarai
 *
 */
public class WorldServerBaseEventController extends AbstractEventController {

    /**
     * @param server
     */
    public WorldServerBaseEventController(AbstractTcpServer server) {
        super(server);
    }

    /* (non-Javadoc)
     * @see de.mbws.server.controller.AbstractEventController#handleEvent(de.mbws.common.events.AbstractGameEvent)
     */
    @Override
    public void handleEvent(AbstractGameEvent event) {


    }
    
    protected WorldServer getWorldServer() {
        return (WorldServer) server;
    }
}
