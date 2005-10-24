package de.mwbs.server.controller;

import org.apache.log4j.Logger;

import de.mwbs.common.GameEvent;
import de.mwbs.server.account.AccountServer;

/**
 * EventController
 * @version 1.0
 */
public abstract class EventController {

    private static Logger logger = Logger.getLogger(EventController.class);
    protected AccountServer accountServer = null;
    protected int eventType = 0; 
    public abstract void handleEvent(GameEvent event);

    /**
     * 
     */
    public EventController(AccountServer accountServer, int eventType) {
        super();
        this.eventType = eventType;
        this.accountServer = accountServer;
    }

    public void sendEvent(GameEvent event) {
        accountServer.handleOutgoingEvent(event);
    }
}



