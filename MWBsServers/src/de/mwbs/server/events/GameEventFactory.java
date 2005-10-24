package de.mwbs.server.events;

import java.nio.ByteBuffer;

import org.apache.log4j.Logger;

import de.mwbs.common.Player;
import de.mwbs.common.eventdata.EventTypes;
import de.mwbs.server.RequestDispatcher;


public class GameEventFactory {
	private static Logger logger = Logger.getLogger("GameEventFactory");
	
    public static AbstractGameEvent getGameEvent(ByteBuffer payload, Player p){
		int eventKey = payload.getInt();
    	logger.debug("got event "+eventKey+" with payload "+RequestDispatcher.bytesToString(payload.array()));
		AbstractGameEvent event = null;
		if (eventKey == EventTypes.LOGIN) {
			event = new LoginEvent(payload);
		}
		if (eventKey == EventTypes.LOGIN_FAILED) {
			event = new LoginEvent(payload);
			event.setEventType(eventKey);
		}
		if (eventKey == EventTypes.LOGIN_OK) {
			event = new LoginEvent(payload);
			event.setEventType(eventKey);
		}			
        if(event != null) {
            event.setPlayer(p);
        }
        return event;
    }
}
