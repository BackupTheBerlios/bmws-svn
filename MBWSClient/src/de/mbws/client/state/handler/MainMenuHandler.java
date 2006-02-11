/**
 * Copyright 2005 Please see supplied licence for details.
 */
package de.mbws.client.state.handler;

import java.util.logging.Level;

import com.jme.input.AbsoluteMouse;
import com.jme.input.KeyBindingManager;
import com.jme.input.KeyInput;
import com.jme.input.Mouse;
import com.jme.input.action.InputAction;
import com.jme.input.action.InputActionEvent;
import com.jme.system.DisplaySystem;
import com.jme.util.LoggingSystem;

import de.mbws.client.MBWSClient;
import de.mbws.client.controller.AccountController;
import de.mbws.client.controller.ClientNetworkController;
import de.mbws.client.data.ClientGlobals;
import de.mbws.client.data.ClientPlayerData;
import de.mbws.client.state.MainMenuState;
import de.mbws.common.events.AbstractGameEvent;
import de.mbws.common.events.data.generated.AccountData;

/**
 * This input handler is used to navigate the menu. It should start the
 * apropriate next states depending on where on th screen the mouse is clicked.
 * 
 * @author Kerim
 */

public class MainMenuHandler extends BaseInputHandler {
	// Mouse mouse;

	public MainMenuHandler(MainMenuState myState) {
		super(myState);
		// setKeyBindings();
		// setUpMouse();
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
		AccountData accountData = new AccountData();
		accountData.setUserName(login);
		accountData.setPassword(pass);
		AbstractGameEvent event = AccountController.getInstance()
				.createLoginEvent(accountData, ClientPlayerData.getInstance());
		try {

			ClientNetworkController.getInstance().connect(
					MBWSClient.mbwsConfiguration
							.getString(ClientGlobals.ACCOUNT_SERVER_IP),
					MBWSClient.mbwsConfiguration
							.getInt(ClientGlobals.ACCOUNT_SERVER_PORT));

		} catch (Exception e) {
			e.printStackTrace();
		}
		ClientNetworkController.getInstance().handleOutgoingEvent(event);
	}

	private static class ExitAction extends InputAction {
		public void performAction(InputActionEvent evt) {
			MBWSClient.exit();
		}
	}

	public MainMenuState getState() {
		return (MainMenuState) state;
	}

}