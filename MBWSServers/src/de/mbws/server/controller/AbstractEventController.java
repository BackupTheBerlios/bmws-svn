package de.mbws.server.controller;

import de.mbws.common.events.AbstractGameEvent;
import de.mbws.server.AbstractTcpServer;

/**
 * EventController
 * @version 1.0
 */
public abstract class AbstractEventController {

    protected AbstractTcpServer server = null;
    public abstract void handleEvent(AbstractGameEvent event);

    /**
     * 
     */
    public AbstractEventController(AbstractTcpServer server) {
        super();
        this.server = server;
    }

    public void sendEvent(AbstractGameEvent event) {
        server.handleOutgoingEvent(event);
    }
    
    public abstract Integer[] getSupportedEventTypes();
}



