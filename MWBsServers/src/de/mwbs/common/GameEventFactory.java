package de.mwbs.common;


public class GameEventFactory {
    
    public static AbstractGameEvent getGameEvent(byte[] payload, Player p){
		// byte order has to be the same as in AbstractEventData
		int eventKey = payload[0] | payload[1] << 8;
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
