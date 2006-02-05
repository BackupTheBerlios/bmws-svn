package de.mbws.client.state.handler;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.apache.log4j.Logger;

import de.mbws.client.gui.character.creation.CharacterDetailsPanel;
import de.mbws.client.state.CharacterCreationState;
import de.mbws.common.data.generated.Characterdata;

public class CharacterCreationStateHandler extends BaseInputHandler implements PropertyChangeListener{
    private static Logger logger = Logger.getLogger(CharacterCreationStateHandler.class);
    private Characterdata characterData = new Characterdata();
	/**
     * @param state
     */
    public CharacterCreationStateHandler(CharacterCreationState state) {
        super(state);
    }

    public CharacterCreationState getState() {
        return (CharacterCreationState) state;
    }

    public Characterdata getCharacterData() {
        return characterData;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (CharacterDetailsPanel.CHARACTER_RACE_CHANGE.equals(evt.getPropertyName())) {
            if (logger.isDebugEnabled()) {
                logger.debug(evt.getNewValue());
            }
//            characterData.setRace((Race)evt.getNewValue());
        } else if (CharacterDetailsPanel.CHARACTER_GENDER_CHANGE.equals(evt.getPropertyName())) {
            characterData.setGender((String)evt.getNewValue());
        }
    }
}
