package de.mbws.common.events;

import java.nio.ByteBuffer;

import org.apache.log4j.Logger;

import de.mbws.common.data.AbstractPlayerData;
import de.mbws.common.events.data.generated.*;

public class GameEventFactory {
    private static Logger logger = Logger.getLogger("GameEventFactory");

    public static AbstractGameEvent getGameEvent(ByteBuffer payload, AbstractPlayerData p) {
        int eventKey = payload.getInt();
        logger.info("got event " + eventKey + " at Time: " + System.currentTimeMillis());
        AbstractGameEvent event = null;
        if (eventKey == EventTypes.C2S_LOGIN) {
            event = new LoginEvent(payload);
        } else if (eventKey == EventTypes.S2C_LOGIN_FAILED) {
            event = new LoginEvent(payload);
        } else if (eventKey == EventTypes.S2C_LOGIN_OK) {
            event = new LoginEvent(payload);
        } else if (eventKey == EventTypes.C2S_LOGOUT) {
            event = new LoginEvent();
        } else if (eventKey == EventTypes.S2C_LOGOUT_OK) {
            event = new LoginEvent(payload);
        } else if (eventKey == EventTypes.C2S_ACCOUNT_CREATE) {
            event = new AccountEvent(payload);
        } else if (eventKey == EventTypes.S2C_ACCOUNT_CREATE_FAIL) {
            event = new AccountEvent(payload, new SystemErrorData());
        } else if (eventKey == EventTypes.S2C_ACCOUNT_CREATE_OK) {
            event = new AccountEvent(payload);
        } else if (eventKey == EventTypes.C2S_CHARACTER_CREATE_REQUEST) {
            event = new CharacterEvent(payload, new CreateCharacter());
        } else if (eventKey == EventTypes.S2C_CHARACTER_RECEIVE) {
            event = new CharacterEvent(payload, new CharacterDetails());
        } else if (eventKey == EventTypes.C2S_CHARACTER_RECEIVE_REQUEST) {
            event = new CharacterEvent(payload, new CharacterSelection());
        } else if (eventKey == EventTypes.C2S_CHARACTER_START_PLAYING_REQUEST) {
            event = new CharacterEvent(payload, new CharacterSelection());
        } else if (eventKey == EventTypes.S2C_CHARACTER_START_PLAYING) {
            event = new CharacterEvent(payload, new CharacterSelection());
        } else if (eventKey == EventTypes.C2S_CHARACTER_LIST_RECEIVE_REQUEST) {
            event = new CharacterEvent(payload, null);
        } else if (eventKey == EventTypes.C2S_CHARACTER_ENTERS_WORLD_REQUEST) {
            event = new CharacterEvent(payload, new CharacterSelection());
        } else if (eventKey == EventTypes.S2C_CHARACTER_ENTERS_WORLD) {
            event = new CharacterEvent(payload, new CharacterSelection());
        } else if (eventKey == EventTypes.S2S_CHARACTER_NEW_CHARACTER_ENTERS_WORLD
                || eventKey == EventTypes.S2S_CHARACTER_NEW_CHARACTER_ENTERS_WORLD_OK) {
            event = new CharacterEvent(payload, new CharacterWorldServerInformation());
        } else if (eventKey == EventTypes.S2C_CHARACTER_LIST_RECEIVE) {
            event = new CharacterEvent(payload, new CharactersOfPlayer());
            // TODO take below check out ?
        } else if (eventKey == EventTypes.MOVEMENT_START_WALK || eventKey == EventTypes.MOVEMENT_STOP_WALK
                || eventKey == EventTypes.MOVEMENT_START_RUN || eventKey == EventTypes.MOVEMENT_STOP_RUN
                || eventKey == EventTypes.MOVEMENT_START_TURN_LEFT || eventKey == EventTypes.MOVEMENT_START_TURN_RIGHT
                || eventKey == EventTypes.MOVEMENT_STOP_TURN || eventKey == EventTypes.MOVEMENT_START_WALK_BACKWARDS) {
            event = new MoveEvent(payload);
        } else if (eventKey == EventTypes.OBJECT_CREATE) {
            event = new ObjectEvent(payload);
        } else if (eventKey == EventTypes.S2C_REDIRECT_TO_WORLDSERVER) {
            event = new ServerRedirectEvent(payload);
            // down here just S2S Events; maybe we should move em to a own
            // Factory
        } else if (eventKey == EventTypes.C2S_LOGIN || eventKey == EventTypes.S2S_LOGIN) {
            event = new LoginEvent(payload, new ServerLoginData());
        } else if (eventKey == EventTypes.S2C_LOGIN_OK || eventKey == EventTypes.S2S_LOGIN_OK) {
            event = new LoginEvent(payload);
        } else if (eventKey == EventTypes.CHAT_WHISPER || eventKey == EventTypes.CHAT_SAY || eventKey == EventTypes.CHAT_SHOUT
                || eventKey == EventTypes.CHAT_GROUP_SAY || eventKey == EventTypes.CHAT_PM || eventKey == EventTypes.CHAT_EMOTE
                || eventKey == EventTypes.CHAT_ADMIN_COMMAND) {
            event = new MessageEvent(payload);
        }

        if (event != null) {
            event.setEventType(eventKey);
            event.setPlayer(p);
        }
        payload.clear();
        return event;
    }
}