package de.mbws.client.state.handler;

import com.jme.app.GameState;
import com.jme.app.GameStateManager;
import com.jme.input.InputHandler;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;

import de.mbws.client.controller.CharacterController;
import de.mbws.client.controller.ClientNetworkController;
import de.mbws.client.state.CharacterSelectionState;
import de.mbws.client.state.TestGameState;

public class CharacterSelectionStateHandler extends InputHandler implements ActionListener {

	private CharacterSelectionState state;
	private boolean startGame = false;
	
	public CharacterSelectionStateHandler() {
		super();
	}
	
	public CharacterSelectionStateHandler(CharacterSelectionState state) {
		super();
		this.state = state;
	}

	public void actionPerformed(ActionEvent event) {
		
		String selectedCharacter = event.getAction();
		ClientNetworkController.getInstance().handleOutgoingEvent(
				CharacterController.getInstance()
						.createCharacterStartPlayingEvent(selectedCharacter));
	}

	public void setStartGame(boolean b) {
		startGame = b;
	}
	
	/**
	 * overrides update from super, checks if we can start the game
	 */ 
	public void update( float time ) {
		if (startGame==true) {
			startMainGameState();
			startGame=false;
		}
		super.update(time);
	}
//	TODO: move that to the characterselectionstate !
	public void startMainGameState() {
		 GameState testgame = new TestGameState("game");
		 testgame.setActive(true);
		 GameStateManager.getInstance().attachChild(testgame);
		 GameStateManager.getInstance().deactivateChildNamed("characterSelection");
	}

}
