package de.mbws.client.state.handler;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.apache.log4j.Logger;

import de.mbws.client.data.Race;
import de.mbws.client.gui.character.creation.CharacterDetailsPanel;
import de.mbws.client.state.CharacterCreationState;
import de.mbws.common.eventdata.generated.CharacterData;

public class CharacterCreationStateHandler extends BaseInputHandler implements PropertyChangeListener {

    private static Logger logger = Logger.getLogger(CharacterCreationStateHandler.class);

    private CharacterData characterData = new CharacterData();

    private PropertyChangeSupport changes = new PropertyChangeSupport(this);

    public static final String CHARACTER_VALIDATION_CHANGE = "characterValidationChange";

    /**
     * @param state
     */
    public CharacterCreationStateHandler(CharacterCreationState state) {
        super(state);
    }

    public CharacterCreationState getState() {
        return (CharacterCreationState) state;
    }

    public CharacterData getCharacterData() {
        return characterData;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (CharacterDetailsPanel.CHARACTER_RACE_CHANGE.equals(evt.getPropertyName())) {
            if (logger.isDebugEnabled()) {
                logger.debug(evt.getNewValue());
            }
            characterData.setRace(((Race) evt.getNewValue()).getId());
        } else if (CharacterDetailsPanel.CHARACTER_GENDER_CHANGE.equals(evt.getPropertyName())) {
            characterData.setGender((String) evt.getNewValue());
        } else if (CharacterDetailsPanel.CHARACTER_NAME_CHANGE.equals(evt.getPropertyName())) {
            characterData.setName((String) evt.getNewValue());
        }

        if (isValidCharacter()) {
            changes.firePropertyChange(CHARACTER_VALIDATION_CHANGE, null, true);
        } else {
            changes.firePropertyChange(CHARACTER_VALIDATION_CHANGE, null, false);
        }
    }

    private boolean isValidCharacter() {
        boolean result = false;
        if (characterData.getName() != null && characterData.getRace() > 0 && characterData.getGender() != null) {
            result = true;
        }
        return result;
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        changes.addPropertyChangeListener(l);
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener l) {
        changes.addPropertyChangeListener(propertyName, l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        changes.removePropertyChangeListener(l);
    }
}
