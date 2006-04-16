package de.mbws.client.state.handler;

import java.awt.event.*;

import org.apache.log4j.Logger;

import de.mbws.client.controller.CharacterController;
import de.mbws.client.controller.ClientNetworkController;
import de.mbws.client.data.Race;
import de.mbws.client.gui.character.creation.CharacterDetailsPanel;
import de.mbws.client.state.CharacterCreationState;

public class CharacterCreationStateHandler extends BaseInputHandler implements
		ActionListener, KeyListener, ItemListener {

	private static Logger logger = Logger
			.getLogger(CharacterCreationStateHandler.class);

	private CharacterDetailsPanel controlledPanel;

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
		if (e.getSource().equals(controlledPanel.getCreateBtn())) {
			Integer i = new Integer(((Race) controlledPanel.getRaceCb()
					.getSelectedItem()).getId());
			char gender = 'M';
			if (controlledPanel.getGenderFBtn().isSelected()) {
				gender = 'F';
			}
			String characterName = controlledPanel.getNameTf().getText().trim();
			ClientNetworkController.getInstance().handleOutgoingEvent(
					CharacterController.getInstance().createCreateCharacterEvent(
							characterName, gender, i.byteValue()));
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

	//TODO: put classTemplate in here !
	public void itemStateChanged(ItemEvent e) {
		Race race = (Race) controlledPanel.getRaceCb().getSelectedItem();

		if (race != null) {
			controlledPanel.getRaceInfoTa().setText(race.getDescription());
		}

		// ClassTemplate classTemplate = (Race)
		// controlledPanel.getRaceCb().getSelectedItem();
		// if (classTemplate != null) {
		// controlledPanel.getClassInfoTa().setText(classTemplate.getDescription());
		// }

	}

	public void setControlledPanel(CharacterDetailsPanel panel) {
		controlledPanel = panel;

	}
}
