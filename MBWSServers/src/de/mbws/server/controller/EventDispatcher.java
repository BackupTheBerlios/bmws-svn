package de.mbws.server.controller;

import de.mbws.common.EventQueue;
import de.mbws.common.QueueWorker;
import de.mbws.common.events.AbstractGameEvent;
import de.mbws.server.account.AccountServer;

/**
 * EventDispatcher
 * @version 1.0
 */
public class EventDispatcher extends QueueWorker {

    protected AccountServer accountServer = null;
       
    /**
     * 
     */
    public EventDispatcher(AccountServer accountServer, EventQueue queue, int queueWorkerSize) {
        super();
        this.accountServer = accountServer;
        initWorker(queueWorkerSize, queue);
    }

    protected void processEvent(AbstractGameEvent event) {
        EventController ec = accountServer.getEventController(event.getEventType());
        if (ec != null) {
            ec.handleEvent(event);
        }
	}
    
}



