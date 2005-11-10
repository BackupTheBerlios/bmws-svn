package de.mbws.server.controller;

import de.mbws.common.EventQueue;
import de.mbws.common.QueueWorker;
import de.mbws.common.events.AbstractGameEvent;
import de.mbws.server.AbstractTcpServer;

/**
 * EventDispatcher
 * @version 1.0
 */
public class EventDispatcher extends QueueWorker {

    protected AbstractTcpServer server = null;
       
    /**
     * 
     */
    public EventDispatcher(AbstractTcpServer server, EventQueue queue, int queueWorkerSize) {
        super();
        this.server = server;
        initWorker(queueWorkerSize, queue);
    }

    protected void processEvent(AbstractGameEvent event) {
        EventController ec = server.getEventController(event.getEventType());
        if (ec != null) {
            ec.handleEvent(event);
        }
	}
    
}



