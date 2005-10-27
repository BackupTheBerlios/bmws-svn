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
import com.jme.input.MouseInput;
import com.jme.input.action.InputAction;
import com.jme.input.action.InputActionEvent;
import com.jme.input.action.MouseInputAction;
import com.jme.system.DisplaySystem;
import com.jme.util.LoggingSystem;
import com.jmex.bui.event.ComponentListener;

import de.mbws.client.MBWSClient;
import de.mbws.client.controller.ClientNetworkController;
import de.mbws.client.controller.LoginController;
import de.mbws.common.Player;
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

	Mouse mouse;

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
		MenuMouseAction pick = new MenuMouseAction();
		pick.setMouse(mouse);
		addAction(pick);

	}

	public void login(String login, String pass) {
		AccountData accountData = new AccountData();
		accountData.setUserName(login);
		accountData.setPassword(pass);
		AbstractGameEvent event = LoginController.getInstance().createLoginEvent(accountData, new Player());
		try {
			ClientNetworkController.getInstance().connect();
		} catch(Exception e) {
			System.out.println("ups");
		}
		ClientNetworkController.getInstance().handleOutgoingEvent(event);
		// TODO: try to log in, retrieve all info and then start the next State
		LoggingSystem.getLogger().log(Level.INFO,
				"trying to log in with login: " + login + " pass: " + pass);
		
	}

	private void startMainGameState() {
		// GameState testgame = new TestGameState("game");
		// testgame.setActive(true);
		// GameStateManager.getInstance().attachChild(testgame);
		// GameStateManager.getInstance().deactivateChildNamed("menu");
		GameStateManager.getInstance().detachChild("intro");
	}

	private static class ExitAction extends InputAction {
		public void performAction(InputActionEvent evt) {
			MBWSClient.exit();
		}
	}

	// TODO: Kerim We want to get to the next state when we click some buttons.
	// Change that later with a gui
	class MenuMouseAction extends MouseInputAction {

		@Override
		public void performAction(InputActionEvent evt) {
			if (MouseInput.get().isButtonDown(0)) {
				// System.out.println(mouse.getHotSpotPosition().x);
				// System.out.println(mouse.getHotSpotPosition().y);
				// startMainGameState();
			}

		}

	}
}