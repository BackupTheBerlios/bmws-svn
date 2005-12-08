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
import de.mbws.common.events.ServerRedirectEvent;

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
		} else if (eventKey == EventTypes.S2C_MOVABLE_OBJECT_CREATE) {
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
		if (eventKey == EventTypes.C2S_LOGIN) {
			event = new LoginEvent(payload);
		} else if (eventKey == EventTypes.S2C_LOGIN_FAILED) {
			event = new LoginEvent(payload);
		} else if (eventKey == EventTypes.S2C_LOGIN_OK) {
			event = new LoginEvent(payload);
		} else if (eventKey == EventTypes.C2S_LOGOUT) {
			event = new LoginEvent(payload);
		} else if (eventKey == EventTypes.S2C_LOGOUT_OK) {
			event = new LoginEvent(payload);
		} else if (eventKey == EventTypes.C2S_ACCOUNT_CREATE) {
			event = new AccountEvent(payload);
		} else if (eventKey == EventTypes.S2C_ACCOUNT_CREATE_FAIL) {
			event = new AccountEvent(payload, new AccountErrorData());
		} else if (eventKey == EventTypes.S2C_ACCOUNT_CREATE_OK) {
			event = new AccountEvent(payload);
		} else if (eventKey == EventTypes.S2C_CHARACTER_RECEIVE
				|| eventKey == EventTypes.C2S_CHARACTER_RECEIVE_REQUEST) {
			event = new CharacterEvent(payload);
		} else if (eventKey == EventTypes.S2C_CHARACTER_LIST_RECEIVE
				|| eventKey == EventTypes.C2S_CHARACTER_LIST_RECEIVE_REQUEST) {
			event = new CharacterEvent(payload, new CharactersOfPlayer());
		} else if (eventKey == EventTypes.S2C_CHARACTER_ENTERS_WORLD) {
			event = new CharacterEvent(payload);		
		} else if (eventKey == (EventTypes.S2C_REDIRECT_TO_WORLDSERVER)){
			event = new ServerRedirectEvent(payload);
		}
		
		
		if (event != null) {
			event.setPlayer(p);
			event.setEventType(eventKey);
		}
		return event;
	}
}
