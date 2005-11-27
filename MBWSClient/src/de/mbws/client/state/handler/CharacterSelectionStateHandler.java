package de.mbws.client.state.handler;

import com.jme.app.GameState;
import com.jme.app.GameStateManager;
import com.jme.input.InputHandler;
import com.jmex.bui.BButton;
import com.jmex.bui.BLabel;
import com.jmex.bui.BPopupWindow;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.layout.BorderLayout;

import de.mbws.client.controller.CharacterController;
import de.mbws.client.controller.ClientNetworkController;
import de.mbws.client.state.CharacterSelectionState;
import de.mbws.client.state.TestGameState;

public class CharacterSelectionStateHandler extends InputHandler implements
		ActionListener {

	private CharacterSelectionState state;
	private boolean startGame = false;
	private String selectedCharacter = null;

	public CharacterSelectionStateHandler() {
		super();
	}

	public CharacterSelectionStateHandler(CharacterSelectionState state) {
		super();
		this.state = state;
	}

	public void actionPerformed(ActionEvent event) {

		if (event.getAction().equals(CharacterSelectionState.STARTGAME)) {
			ClientNetworkController.getInstance()
					.handleOutgoingEvent(
							CharacterController.getInstance()
									.createCharacterStartPlayingEvent(
											selectedCharacter));
		} else if (event.getAction().equals(
				CharacterSelectionState.DELETE_CHARACTER)) {
			BPopupWindow deletePopUp = new BPopupWindow(state.characterWindow,
					new BorderLayout());
			deletePopUp.add(new BLabel("Really want to delete the char ?"),
					BorderLayout.NORTH);
			deletePopUp.add(new BButton("Yes"), BorderLayout.WEST);
			deletePopUp.add(new BButton("No"), BorderLayout.EAST);
			deletePopUp.setModal(true);
			deletePopUp.popup(0,0,true);

		} else if (event.getAction().equals(
				CharacterSelectionState.CREATE_CHARACTER)) {

		} else {
			selectedCharacter = event.getAction();
			state.startGameBtn.setEnabled(true);
			state.deleteBtn.setEnabled(true);
			state.createBtn.setEnabled(true);

			// state.getCharacterDescription().clearText();
			// List<CharacterShortDescription> allCharacters = ClientPlayerData
			// .getInstance().getAllCharactersOfPlayer();
			// if (allCharacters != null) {
			// Iterator<CharacterShortDescription> it =
			// allCharacters.iterator();
			// while (it.hasNext()) {
			// CharacterShortDescription aCharacter = it.next();
			// if (aCharacter.getCharacterID() == event.getAction()) {
			//
			// state.getCharacterDescription().setText(
			// aCharacter.getName() + " " + aCharacter.getGender()
			// + "\n " + aCharacter.getRace());
			// }
			// }
			// }

		}
	}

	public void setStartGame(boolean b) {
		startGame = b;
	}

	/**
	 * overrides update from super, checks if we can start the game
	 */
	public void update(float time) {
		if (startGame == true) {
			startMainGameState();
			startGame = false;
		}
		super.update(time);
	}

	// TODO: move that to the characterselectionstate !
	public void startMainGameState() {
		GameState testgame = new TestGameState("game");
		testgame.setActive(true);
		GameStateManager.getInstance().attachChild(testgame);
		GameStateManager.getInstance().deactivateChildNamed(
				"characterSelection");
	}

}
