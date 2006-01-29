package de.mbws.client.state.handler;

import de.mbws.client.state.CharacterSelectionState;

public class CharacterSelectionStateHandler extends BaseInputHandler {

	/**
     * @param state
     */
    public CharacterSelectionStateHandler(CharacterSelectionState state) {
        super(state);
    }

    public CharacterSelectionState getState() {
        return (CharacterSelectionState) state;
    }
}
