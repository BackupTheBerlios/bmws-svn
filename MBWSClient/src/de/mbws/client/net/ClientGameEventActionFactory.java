package de.mbws.client.net;

import java.nio.ByteBuffer;

import org.apache.log4j.Logger;

import de.mbws.client.data.ClientPlayerData;
import de.mbws.client.eventactions.AbstractEventAction;
import de.mbws.client.eventactions.CreateObjectAction;
import de.mbws.client.eventactions.DestroyObjectAction;
import de.mbws.client.eventactions.MoveObjectAction;
import de.mbws.common.data.AbstractPlayerData;
import de.mbws.common.eventdata.generated.AccountErrorData;
import de.mbws.common.eventdata.generated.CharactersOfPlayer;
import de.mbws.common.eventdata.generated.MoveData;
import de.mbws.common.eventdata.generated.WorldObject;
import de.mbws.common.events.AbstractGameEvent;
import de.mbws.common.events.AccountEvent;
import de.mbws.common.events.CharacterEvent;
import de.mbws.common.events.EventTypes;
import de.mbws.common.events.LoginEvent;

public class ClientGameEventActionFactory {

	private static Logger logger = Logger.getLogger("GameEventActionFactory");

	public ClientGameEventActionFactory() {
		super();
	}

	public static AbstractEventAction getGameEventAction(ByteBuffer payload,
			ClientPlayerData instance) {
		int eventKey = payload.getInt();

		AbstractEventAction action = null;
		if (eventKey == EventTypes.MOVEMENT_START_WALK
				|| eventKey == EventTypes.MOVEMENT_START_RUN
				|| eventKey == EventTypes.MOVEMENT_STOP_WALK
				|| eventKey == EventTypes.MOVEMENT_START_TURN_LEFT
				|| eventKey == EventTypes.MOVEMENT_STOP_TURN
				|| eventKey == EventTypes.MOVEMENT_START_TURN_RIGHT
				|| eventKey == EventTypes.MOVEMENT_START_WALK_BACKWARDS) {
			action = new MoveObjectAction(payload, new MoveData());
			action.setEventType(eventKey);
		} else if (eventKey == EventTypes.OBJECT_CREATE) {
			action = new CreateObjectAction(payload, new WorldObject());
			action.setEventType(eventKey);
		} else if (eventKey == EventTypes.OBJECT_DESTROY) {
			action = new DestroyObjectAction(payload, new WorldObject());
			action.setEventType(eventKey);
		} else if (eventKey == EventTypes.MOVABLE_OBJECT_CREATE) {
			// TODO: Kerim Change action !
			action = new CreateObjectAction(payload, new WorldObject());
			action.setEventType(eventKey);
		} else {
			return null;
		}
		logger.info("EventAction +" + eventKey + " dequeued at: "
				+ System.currentTimeMillis());
		// if (action != null) {
		// action.setPlayer(p);
		// }
		return action;
	}

	public static AbstractGameEvent getGameEvent(ByteBuffer payload,
			AbstractPlayerData p) {
		int eventKey = payload.getInt();
		logger.info("got event " + eventKey + " at Time: "
				+ System.currentTimeMillis());
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
		} else if (eventKey == EventTypes.CHARACTER_LIST_RECEIVE
				|| eventKey == EventTypes.CHARACTER_LIST_RECEIVE_REQUEST) {
			event = new CharacterEvent(payload, new CharactersOfPlayer());
			event.setEventType(eventKey);
			
		} else if (eventKey == EventTypes.CHARACTER_START_PLAYING) {
			event = new CharacterEvent(payload);
			event.setEventType(eventKey);
			
		}
		
		
		if (event != null) {
			event.setPlayer(p);
		}
		return event;
	}
}
