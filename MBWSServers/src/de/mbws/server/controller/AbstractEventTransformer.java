package de.mbws.server.controller;

import java.nio.ByteBuffer;

import org.apache.log4j.Logger;

import de.mbws.common.data.AbstractPlayerData;
import de.mbws.common.events.AbstractGameEvent;

/**
 * Description: 
 * @author Azarai
 *
 */
public abstract class AbstractEventTransformer {

    private static Logger logger = Logger.getLogger(AbstractEventTransformer.class);
    
    public AbstractGameEvent getGameEvent(ByteBuffer payload, AbstractPlayerData p) {
        int eventKey = payload.getInt();
        logger.info("got event " + eventKey + " at Time: " + System.currentTimeMillis());
        AbstractGameEvent event = transformPayload2Event(payload, eventKey);       
        if (event != null) {
            event.setEventType(eventKey);
            event.setPlayer(p);
        }
        payload.clear();
        return event;
    }
    
    protected abstract AbstractGameEvent transformPayload2Event (ByteBuffer payload, int eventKey);
}