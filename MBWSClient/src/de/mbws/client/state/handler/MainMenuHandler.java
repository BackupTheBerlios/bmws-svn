/**
 * Copyright 2005 Please see supplied licence for details.
 */
package de.mbws.client.state.handler;

import java.util.logging.Level;

import com.jme.app.GameState;
import com.jme.app.GameStateManager;
import com.jme.input.AbsoluteMouse;
import com.jme.input.InputHandler;
import com.jme.input.KeyBindingManager;
import com.jme.input.KeyInput;
import com.jme.input.Mouse;
import com.jme.input.action.InputAction;
import com.jme.input.action.InputActionEvent;
import com.jme.system.DisplaySystem;
import com.jme.util.LoggingSystem;
import com.jmex.bui.event.ComponentListener;

import de.mbws.client.MBWSClient;
import de.mbws.client.controller.AccountController;
import de.mbws.client.controller.ClientNetworkController;
import de.mbws.client.data.ClientPlayerData;
import de.mbws.client.state.CharacterSelectionState;
import de.mbws.common.eventdata.generated.AccountData;
import de.mbws.common.events.AbstractGameEvent;

/**
 * This input handler is used to navigate the menu. It should start the
 * apropriate next states depending on where on th screen the mouse is clicked.
 * 
 * 
 * @author Kerim
 */
// TODO: replace this (and parts in MainMenuState) with a BUI-Interface ?
public class MainMenuHandler extends InputHandler implements ComponentListener {
	GameState myState;
	boolean startNextState = false;
	// Mouse mouse;

	public MainMenuHandler(GameState myState) {
		setKeyBindings();
		setUpMouse();
		this.myState = myState;
	}

	private void setKeyBindings() {
		KeyBindingManager.getKeyBindingManager().set("exit",
				KeyInput.KEY_ESCAPE);
		addAction(new ExitAction(), "exit", false);
	}

	private void setUpMouse() {
		DisplaySystem display = DisplaySystem.getDisplaySystem();
		Mouse mouse = new AbsoluteMouse("Mouse Input", display.getWidth(),
				display.getHeight());
		setMouse(mouse);
	}

	public void login(String login, String pass) {
		LoggingSystem.getLogger().log(Level.INFO,
				"trying to log in with login: " + login + " pass: " + pass);
		//((MainMenuState)myState).displayInfo("Trying to log in");
		AccountData accountData = new AccountData();
		accountData.setUserName(login);
		accountData.setPassword(pass);
		AbstractGameEvent event = AccountController.getInstance()
				.createLoginEvent(accountData, ClientPlayerData.getInstance());
		try {
			//ClientNetworkController.getInstance().connect("62.75.214.103",5000);
			ClientNetworkController.getInstance().connect("localhost",5000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ClientNetworkController.getInstance().handleOutgoingEvent(event);
	}

	public void startCharacterSelectionState() {
         GameState ingame = new CharacterSelectionState("characterSelection");
         ingame.setActive(true);
         GameStateManager.getInstance().attachChild(ingame);
         myState.setActive(false); 
	}

	private static class ExitAction extends InputAction {
		public void performAction(InputActionEvent evt) {
			MBWSClient.exit();
		}
	}

	/**
	 * overrides update from super, checks if we can start the game
	 */ 
	public void update( float time ) {
		if (startNextState==true) {
			startCharacterSelectionState();
			startNextState=false;
		}
		super.update(time);
	}
	
	public void setStartNextState(boolean b) {
		startNextState = b;
	}

    public GameState getMyState() {
        return myState;
    }
	

}