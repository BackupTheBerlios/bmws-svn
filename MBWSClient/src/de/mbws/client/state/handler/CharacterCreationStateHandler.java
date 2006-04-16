package de.mbws.client.state.handler;

import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;

import org.apache.log4j.Logger;

import de.mbws.client.controller.CharacterController;
import de.mbws.client.controller.ClientNetworkController;
import de.mbws.client.gui.character.creation.CharacterDetailsPanel;
import de.mbws.client.state.CharacterCreationState;
import de.mbws.common.Globals;
import de.mbws.common.events.data.generated.CharacterData;

public class CharacterCreationStateHandler extends BaseInputHandler implements
		ActionListener, KeyListener, ItemListener {

	private static Logger logger = Logger
			.getLogger(CharacterCreationStateHandler.class);

	private CharacterData characterData = new CharacterData();

	private PropertyChangeSupport changes = new PropertyChangeSupport(this);

	private CharacterDetailsPanel controlledPanel;

	public static final String CHARACTER_VALIDATION_CHANGE = "characterValidationChange";

	/**
	 * @param state
	 */
	public CharacterCreationStateHandler(CharacterCreationState state) {
		super(state);
		characterData.setGender(Globals.GENDER_MALE);
	}

	public CharacterCreationState getState() {
		return (CharacterCreationState) state;
	}

	public CharacterData getCharacterData() {
		return characterData;
	}

	public void propertyChange(PropertyChangeEvent evt) {
		// if (CharacterDetailsPanel.CHARACTER_RACE_CHANGE.equals(evt
		// .getPropertyName())) {
		// if (logger.isDebugEnabled()) {
		// logger.debug(evt.getNewValue());
		// }
		// characterData.setRace(((Race) evt.getNewValue()).getId());
		// } else if (CharacterDetailsPanel.CHARACTER_GENDER_CHANGE.equals(evt
		// .getPropertyName())) {
		// characterData
		// .setGender(((Character) evt.getNewValue()).charValue());
		// } else if (CharacterDetailsPanel.CHARACTER_NAME_CHANGE.equals(evt
		// .getPropertyName())) {
		// characterData.setName((String) evt.getNewValue());
		// }

		if (isValidCharacter()) {
			changes.firePropertyChange(CHARACTER_VALIDATION_CHANGE, null, true);
		} else {
			changes.firePropertyChange(CHARACTER_VALIDATION_CHANGE, null, false);
		}
	}

	private boolean isValidCharacter() {
		boolean result = false;
		if (characterData.getName() != null
				&& characterData.getRace() > 0
				&& (characterData.getGender() == 'M' || characterData.getGender() == 'F')) {
			result = true;
		}
		return result;
	}

	private boolean validateInput() {
		if (!controlledPanel.getNameTf().getText().trim().equals("")) {
			if (controlledPanel.getGenderMBtn().isSelected()
					|| controlledPanel.getGenderFBtn().isSelected()) {
				if ((controlledPanel.getRaceCb().getSelectedIndex() > -1)
						&& (controlledPanel.getClassCb().getSelectedIndex() > -1)) {
					controlledPanel.getCreateBtn().setEnabled(true);
					return true;
				}
			}
		}
		controlledPanel.getCreateBtn().setEnabled(false);
		return false;
	}

	public void actionPerformed(ActionEvent e) {
//		if (!validateInput()) {
//			return;
//		}

		if (e.getSource().equals(controlledPanel.getCreateBtn())) {
			Integer i = new Integer(getCharacterData().getRace());
			ClientNetworkController.getInstance().handleOutgoingEvent(
					CharacterController.getInstance().createCreateCharacterEvent(
							getCharacterData().getName(), getCharacterData().getGender(),
							i.byteValue()));
		} else if (e.getSource().equals(controlledPanel.getCancelBtn())) {
			requestStateSwitch(BaseInputHandler.GAMESTATE_CHARACTER_SELECTION);
		}
	}

	public void keyTyped(KeyEvent e) {
		validateInput();
	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void itemStateChanged(ItemEvent e) {
		// if (event.getStateChange() == 1) {
		// Race race = (Race) event.getItem();

	}

	public void setControlledPanel(CharacterDetailsPanel panel) {
		controlledPanel = panel;

	}
}
