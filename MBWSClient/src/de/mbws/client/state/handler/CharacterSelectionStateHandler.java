package de.mbws.client.state.handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.mbws.client.state.CharacterSelectionState;

public class CharacterSelectionStateHandler extends BaseInputHandler implements
		ActionListener {

    private String selectedCharacter = null;

	/**
     * @param state
     */
    public CharacterSelectionStateHandler(CharacterSelectionState state) {
        super(state);
    }

    public void actionPerformed(ActionEvent event) {

//	    if (event.getAction().equals(
//				CharacterSelectionState.DELETE_CHARACTER)) {
//			deletePopUp = new BPopupWindow(state.characterWindow,
//					new BorderLayout());
//			deletePopUp
//					.add(new BLabel(ValueMapper
//							.getText(ClientGlobals.CONFIRM_DELETE)),
//							BorderLayout.NORTH);
//			deleteBtn = new BButton(ValueMapper.getText(ClientGlobals.YES));
//			deleteBtn.addListener(this);
//			deletePopUp.add(deleteBtn, BorderLayout.WEST);
//			deletePopUp.add(new BButton(ValueMapper.getText(ClientGlobals.NO)),
//					BorderLayout.EAST);
//			deletePopUp.setModal(true);
//			deletePopUp.popup(0, 0, true);

//		} else if (event.getAction().equals(
//				CharacterSelectionState.CREATE_CHARACTER)) {

//		} else if (event.getSource().equals(deleteBtn)) {
//			deletePopUp.dismiss();
//			ClientNetworkController.getInstance().handleOutgoingEvent(
//					CharacterController.getInstance()
//							.createDeleteCharacterEvent(selectedCharacter));
//
//		} else {
//			selectedCharacter = event.getAction();
//		}
	}

    public String getSelectedCharacter() {
        return selectedCharacter;
    }

    public CharacterSelectionState getState() {
        return (CharacterSelectionState) state;
    }

}
