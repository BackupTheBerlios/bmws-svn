package de.mbws.common.events;

import java.nio.ByteBuffer;

import org.apache.log4j.Logger;

import de.mbws.common.data.AbstractPlayerData;
import de.mbws.common.eventdata.generated.AccountErrorData;
import de.mbws.common.utils.StringUtils;

public class GameEventFactory {
	private static Logger logger = Logger.getLogger("GameEventFactory");

	public static AbstractGameEvent getGameEvent(ByteBuffer payload,
			AbstractPlayerData p) {
		int eventKey = payload.getInt();
		logger.debug("got event " + eventKey + " with payload "
				+ StringUtils.bytesToString(payload.array())+" at Time: "+System.currentTimeMillis());
		AbstractGameEvent event = null;
		if (eventKey == EventTypes.LOGIN) {
			event = new LoginEvent(payload);
			event.setEventType(eventKey);
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
		} else if (eventKey == EventTypes.CHARACTER_RECEIVE
				|| eventKey == EventTypes.CHARACTER_RECEIVE_REQUEST) {
			event = new CharacterEvent(payload);
			event.setEventType(eventKey);
			// TODO take below check out ?
		} else if (eventKey == EventTypes.MOVEMENT_START_WALK
				|| eventKey == EventTypes.MOVEMENT_STOP_WALK
				|| eventKey == EventTypes.MOVEMENT_START_RUN
				|| eventKey == EventTypes.MOVEMENT_STOP_RUN
				|| eventKey == EventTypes.MOVEMENT_START_TURN_LEFT
				|| eventKey == EventTypes.MOVEMENT_START_TURN_RIGHT
				|| eventKey == EventTypes.MOVEMENT_STOP_TURN) {
			event = new MoveEvent(payload);
			event.setEventType(eventKey);
		} else if (eventKey == EventTypes.OBJECT_CREATE) {
			event = new ObjectEvent(payload);
			event.setEventType(eventKey);
		}
		if (event != null) {
			event.setPlayer(p);
		}
		return event;
	}
}
