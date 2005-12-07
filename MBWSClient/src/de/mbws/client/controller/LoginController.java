package de.mbws.client.controller;

import de.mbws.client.data.ClientPlayerData;
import de.mbws.common.eventdata.generated.AccountData;
import de.mbws.common.eventdata.generated.LoginData;
import de.mbws.common.eventdata.generated.ServerRedirectData;
import de.mbws.common.events.AbstractGameEvent;
import de.mbws.common.events.EventTypes;
import de.mbws.common.events.LoginEvent;
import de.mbws.common.events.ServerRedirectEvent;
import de.mbws.common.exceptions.InitializationException;

/**
 * Description:
 * 
 * @author azarai,kerim
 * 
 */
public class LoginController {

	private static LoginController instance;

	/**
	 * 
	 */
	private LoginController() {

	}

	public static LoginController getInstance() {
		if (instance == null) {
			instance = new LoginController();
		}
		return instance;
	}

	// TODO Kerim: correct error handling and next stages here !
	public void handleEvent(LoginEvent loginEvent) {
		if (loginEvent.getEventType() == EventTypes.S2C_LOGIN_FAILED) {
			System.out.println("Login failed!");
		} else if (loginEvent.getEventType() == EventTypes.S2C_LOGIN_OK) {
			// Updating the local client data:
			ClientPlayerData.getInstance().setSessionId(
					loginEvent.getPlayer().getSessionId());
			ClientPlayerData.getInstance().setAccount(
					loginEvent.getPlayer().getAccount());
			System.out.println("Session id ="
					+ loginEvent.getPlayer().getSessionId());
			System.out.println("Login ok, trying to receive character data");
			ClientNetworkController.getInstance().handleOutgoingEvent(
					CharacterController.getInstance()
							.createCharacterListReceiveEvent());

		} else if (loginEvent.getEventType() == EventTypes.S2C_LOGOUT_OK) {
			System.out.println("Logout Successfull!");
		}

	}

	public AbstractGameEvent createLoginEvent(AccountData account,
			ClientPlayerData player) {
		LoginData ld = new LoginData();
		ld.setUserName(account.getUserName());
		ld.setPassword(account.getPassword());
		LoginEvent event = new LoginEvent(ld);
		player.setSessionId(new Integer(0));
		event.setPlayer(player);

		event.setEventType(EventTypes.C2S_LOGIN);
		return event;
	}

	public AbstractGameEvent createLogoutEvent(ClientPlayerData player) {
		LoginEvent event = new LoginEvent();
		event.setPlayer(player);
		event.setEventType(EventTypes.C2S_LOGOUT);
		return event;
	}

	public void handleEvent(ServerRedirectEvent event) {
		if (event.getEventType() == EventTypes.S2C_REDIRECT_TO_WORLDSERVER) {
			// Connecting to new Server, closing this connection here
			ClientNetworkController.getInstance().disconnect();
			ServerRedirectData data = (ServerRedirectData) event.getEventData();
			try {
				ClientNetworkController.getInstance().connect(data.getHost(),
						data.getPort());
				ClientNetworkController.getInstance().handleOutgoingEvent(
						CharacterController.getInstance()
								.createEnterWorldServerEvent());
			} catch (InitializationException e) {
				System.out.println("connection to ws failed");
				e.printStackTrace();
			}
		}

	}

}
