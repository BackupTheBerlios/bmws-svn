package de.mbws.server.account.controller;

import java.nio.ByteBuffer;

import de.mbws.common.events.*;
import de.mbws.common.events.data.generated.CharacterSelection;
import de.mbws.common.events.data.generated.CharacterWorldServerInformation;
import de.mbws.common.events.data.generated.CreateCharacter;
import de.mbws.common.events.data.generated.ServerLoginData;
import de.mbws.server.controller.AbstractEventTransformer;

/**
 * Description:
 * 
 * @author Azarai
 */
public class AccountServerEventTransformer extends AbstractEventTransformer {

    /*
     * (non-Javadoc)
     * 
     * @see de.mbws.server.controller.AbstractEventTransformer#transformPayload2Event(java.nio.ByteBuffer)
     */
    @Override
    protected AbstractGameEvent transformPayload2Event(ByteBuffer payload, int eventKey) {
        AbstractGameEvent event = null;
        if (eventKey == EventTypes.C2S_LOGIN) {
            event = new LoginEvent(payload);
        } else if (eventKey == EventTypes.C2S_LOGOUT) {
            event = new LoginEvent();
        } else if (eventKey == EventTypes.C2S_ACCOUNT_CREATE) {
            event = new AccountEvent(payload);
        } else if (eventKey == EventTypes.C2S_CHARACTER_CREATE_REQUEST) {
            event = new CharacterEvent(payload, new CreateCharacter());
        } else if (eventKey == EventTypes.C2S_CHARACTER_RECEIVE_REQUEST) {
            event = new CharacterEvent(payload, new CharacterSelection());
        } else if (eventKey == EventTypes.C2S_CHARACTER_START_PLAYING_REQUEST) {
            event = new CharacterEvent(payload, new CharacterSelection());
        } else if (eventKey == EventTypes.C2S_CHARACTER_LIST_RECEIVE_REQUEST) {
            event = new CharacterEvent(payload, null);
        } else if (eventKey == EventTypes.C2S_CHARACTER_ENTERS_WORLD_REQUEST) {
            event = new CharacterEvent(payload, new CharacterSelection());
            // FIXME why is c2s_login twice in here?
        } else if (eventKey == EventTypes.C2S_LOGIN || eventKey == EventTypes.S2S_LOGIN) {
            event = new LoginEvent(payload, new ServerLoginData());
        } else if (eventKey == EventTypes.S2S_CHARACTER_NEW_CHARACTER_ENTERS_WORLD_OK) {
            event = new CharacterEvent(payload, new CharacterWorldServerInformation());
        }
        return event;
    }

}
