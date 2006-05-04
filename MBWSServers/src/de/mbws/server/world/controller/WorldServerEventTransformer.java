package de.mbws.server.world.controller;

import java.nio.ByteBuffer;

import de.mbws.common.events.*;
import de.mbws.common.events.data.generated.CharacterSelection;
import de.mbws.common.events.data.generated.CharacterWorldServerInformation;
import de.mbws.server.controller.AbstractEventTransformer;

/**
 * Description:
 * 
 * @author Azarai
 */
public class WorldServerEventTransformer extends AbstractEventTransformer {

    /*
     * (non-Javadoc)
     * 
     * @see de.mbws.server.controller.AbstractEventTransformer#transformPayload2Event(java.nio.ByteBuffer,
     *      int)
     */
    @Override
    protected AbstractGameEvent transformPayload2Event(ByteBuffer payload, int eventKey) {
        AbstractGameEvent event = null;
        if (eventKey == EventTypes.C2S_CHARACTER_ENTERS_WORLD_REQUEST) {
            event = new CharacterEvent(payload, new CharacterSelection());
        } else if (eventKey == EventTypes.S2S_CHARACTER_NEW_CHARACTER_ENTERS_WORLD) {
            event = new CharacterEvent(payload, new CharacterWorldServerInformation());
        } else if (eventKey == EventTypes.MOVEMENT_START_WALK || eventKey == EventTypes.MOVEMENT_STOP_WALK
                || eventKey == EventTypes.MOVEMENT_START_RUN || eventKey == EventTypes.MOVEMENT_STOP_RUN
                || eventKey == EventTypes.MOVEMENT_START_TURN_LEFT || eventKey == EventTypes.MOVEMENT_START_TURN_RIGHT
                || eventKey == EventTypes.MOVEMENT_STOP_TURN || eventKey == EventTypes.MOVEMENT_START_WALK_BACKWARDS) {
            event = new MoveEvent(payload);
        } else if (eventKey == EventTypes.S2C_LOGIN_OK || eventKey == EventTypes.S2S_LOGIN_OK) {
            event = new LoginEvent(payload);
        } else if (eventKey == EventTypes.CHAT_WHISPER || eventKey == EventTypes.CHAT_SAY || eventKey == EventTypes.CHAT_SHOUT
                || eventKey == EventTypes.CHAT_GROUP_SAY || eventKey == EventTypes.CHAT_PM || eventKey == EventTypes.CHAT_EMOTE
                || eventKey == EventTypes.CHAT_ADMIN_COMMAND) {
            event = new MessageEvent(payload);
        }
        return event;
    }

}