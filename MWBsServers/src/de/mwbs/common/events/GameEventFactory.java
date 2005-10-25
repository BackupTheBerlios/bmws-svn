package de.mwbs.common.events;

import java.nio.ByteBuffer;

import org.apache.log4j.Logger;

import de.mwbs.common.Player;
import de.mwbs.common.eventdata.generated.AccountErrorData;
import de.mwbs.server.RequestDispatcher;

public class GameEventFactory {
    private static Logger logger = Logger.getLogger("GameEventFactory");

    public static AbstractGameEvent getGameEvent(ByteBuffer payload, Player p) {
        int eventKey = payload.getInt();
        logger.debug("got event " + eventKey + " with payload " + RequestDispatcher.bytesToString(payload.array()));
        AbstractGameEvent event = null;
        if (eventKey == EventTypes.LOGIN) {
            event = new LoginEvent(payload);
        } else if (eventKey == EventTypes.LOGIN_FAILED) {
            event = new LoginEvent(payload);
            event.setEventType(eventKey);
        } else if (eventKey == EventTypes.LOGIN_OK) {
            event = new LoginEvent(payload);
            event.setEventType(eventKey);
        } else if (eventKey == EventTypes.LOGOUT) {
            event = new LoginEvent(payload);
            event.setEventType(eventKey);
        } else if (eventKey == EventTypes.LOGOUT_OK) {
            event = new LoginEvent(payload);
            event.setEventType(eventKey);
        } else if (eventKey == EventTypes.ACCOUNT_CREATE) {
            event = new AccountEvent(payload);
            event.setEventType(eventKey);
        } else if (eventKey == EventTypes.ACCOUNT_CREATE_FAIL) {
            event = new AccountEvent(payload, new AccountErrorData());
            event.setEventType(eventKey);
        } else if (eventKey == EventTypes.ACCOUNT_CREATE_OK) {
            event = new AccountEvent(payload);
            event.setEventType(eventKey);
        }
        if (event != null) {
            event.setPlayer(p);
        }
        return event;
    }
}
