package de.mwbs.server.controller;

import org.apache.log4j.Logger;

import de.mwbs.common.EventQueue;
import de.mwbs.common.QueueWorker;
import de.mwbs.server.account.AccountServer;
import de.mwbs.server.events.AbstractGameEvent;

/**
 * EventDispatcher
 * @version 1.0
 */
public class EventDispatcher extends QueueWorker {
    private static Logger logger = Logger.getLogger(EventDispatcher.class);
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
        EventController ec = accountServer.getEventController(event.getEventId());
        if (ec != null) {
            ec.handleEvent(event);
        }
	}
    
}



