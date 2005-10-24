package de.mwbs.common;

import java.nio.ByteBuffer;

import org.apache.log4j.Logger;

import de.mwbs.server.RequestDispatcher;


public class GameEventFactory {
	private static Logger logger = Logger.getLogger("GameEventFactory");
	
    public static AbstractGameEvent getGameEvent(ByteBuffer payload, Player p){
		int eventKey = payload.getInt();
    	logger.debug("got event "+eventKey+" with payload "+RequestDispatcher.bytesToString(payload.array()));
		AbstractGameEvent event = null;
		if (eventKey == AbstractGameEvent.GE_LOGIN) {
			event = new LoginEvent(payload);
		}
        if(event != null) {
            event.setPlayer(p);
        }
        return event;
    }
}
