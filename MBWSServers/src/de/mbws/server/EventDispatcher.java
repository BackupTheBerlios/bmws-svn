package de.mbws.server;

import de.mbws.common.EventQueue;
import de.mbws.common.QueueWorker;
import de.mbws.common.events.AbstractGameEvent;
import de.mbws.server.controller.AbstractEventController;

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
        AbstractEventController ec = server.getEventController(event.getEventId());
        if (ec != null) {
            ec.handleEvent(event);
        }
	}
    
}



