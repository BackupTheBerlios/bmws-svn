package de.mbws.common.events;

import java.nio.ByteBuffer;

import org.apache.log4j.Logger;

import de.mbws.common.data.AbstractPlayerData;
import de.mbws.common.eventdata.generated.AccountErrorData;
import de.mbws.common.eventdata.generated.CharacterSelection;
import de.mbws.common.eventdata.generated.CharacterWorldServerInformation;
import de.mbws.common.eventdata.generated.CharactersOfPlayer;
import de.mbws.common.eventdata.generated.ServerLoginData;

public class GameEventFactory {
	private static Logger logger = Logger.getLogger("GameEventFactory");

	public static AbstractGameEvent getGameEvent(ByteBuffer payload,
			AbstractPlayerData p) {
		int eventKey = payload.getInt();
		logger.info("got event " + eventKey + " at Time: "
				+ System.currentTimeMillis());
		AbstractGameEvent event = null;
		if (eventKey == EventTypes.LOGIN) {
			event = new LoginEvent(payload);
		} else if (eventKey == EventTypes.LOGIN_FAILED) {
			event = new LoginEvent(payload);
		} else if (eventKey == EventTypes.LOGIN_OK) {
			event = new LoginEvent(payload);
		} else if (eventKey == EventTypes.LOGOUT) {
			event = new LoginEvent(payload);
		} else if (eventKey == EventTypes.LOGOUT_OK) {
			event = new LoginEvent(payload);
		} else if (eventKey == EventTypes.ACCOUNT_CREATE) {
			event = new AccountEvent(payload);
		} else if (eventKey == EventTypes.ACCOUNT_CREATE_FAIL) {
			event = new AccountEvent(payload, new AccountErrorData());
		} else if (eventKey == EventTypes.ACCOUNT_CREATE_OK) {
			event = new AccountEvent(payload);
		} else if (eventKey == EventTypes.CHARACTER_RECEIVE
				|| eventKey == EventTypes.CHARACTER_RECEIVE_REQUEST) {
			event = new CharacterEvent(payload, new CharacterSelection());
        } else if (eventKey == EventTypes.CHARACTER_START_PLAYING_REQUEST) {
            event = new CharacterEvent(payload, new CharacterSelection());
        } else if (eventKey == EventTypes.CHARACTER_START_PLAYING) {
            event = new CharacterEvent(payload, new CharacterSelection());
        } else if (eventKey == EventTypes.CHARACTER_LIST_RECEIVE_REQUEST) {
            event = new CharacterEvent(payload);
        } else if (eventKey == EventTypes.CHARACTER_ENTERS_WORLD_REQUEST) {
            event = new CharacterEvent(payload, new CharacterSelection());
        } else if (eventKey == EventTypes.CHARACTER_ENTERS_WORLD) {
            event = new CharacterEvent(payload, new CharacterSelection());
        } else if (eventKey == EventTypes.CHARACTER_NEW_CHARACTER_ENTERS_WORLD_S2S) {
            event = new CharacterEvent(payload, new CharacterWorldServerInformation());
        } else if (eventKey == EventTypes.CHARACTER_LIST_RECEIVE) {
            event = new CharacterEvent(payload, new CharactersOfPlayer());
			// TODO take below check out ?
		} else if (eventKey == EventTypes.MOVEMENT_START_WALK
				|| eventKey == EventTypes.MOVEMENT_STOP_WALK
				|| eventKey == EventTypes.MOVEMENT_START_RUN
				|| eventKey == EventTypes.MOVEMENT_STOP_RUN
				|| eventKey == EventTypes.MOVEMENT_START_TURN_LEFT
				|| eventKey == EventTypes.MOVEMENT_START_TURN_RIGHT
				|| eventKey == EventTypes.MOVEMENT_STOP_TURN
                || eventKey == EventTypes.MOVEMENT_START_WALK_BACKWARDS) {
			event = new MoveEvent(payload);
		} else if (eventKey == EventTypes.OBJECT_CREATE) {
			event = new ObjectEvent(payload);
        } else if (eventKey == EventTypes.REDIRECT_TO_WORLDSERVER) {
            event = new ServerRedirectEvent(payload);
            //down here just S2S Events; maybe we should move em to a own Factory
        } else if (eventKey == EventTypes.LOGIN || eventKey == EventTypes.LOGIN_S2S) {
            event = new LoginEvent(payload, new ServerLoginData());
        } else if (eventKey == EventTypes.LOGIN_OK || eventKey == EventTypes.LOGIN_S2S_OK) {
            event = new LoginEvent(payload);
        }

        if (event != null) {
            event.setEventType(eventKey);
			event.setPlayer(p);
		}
		return event;
	}
}