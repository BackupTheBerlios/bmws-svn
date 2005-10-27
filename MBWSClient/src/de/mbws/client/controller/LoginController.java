package de.mbws.client.controller;

import de.mbws.common.Player;
import de.mbws.common.eventdata.generated.AccountData;
import de.mbws.common.eventdata.generated.LoginData;
import de.mbws.common.events.AbstractGameEvent;
import de.mbws.common.events.EventTypes;
import de.mbws.common.events.LoginEvent;

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
		if (loginEvent.getEventType() == EventTypes.LOGIN_FAILED) {
			System.out.println("Login failed!");
		} else if (loginEvent.getEventType() == EventTypes.LOGIN_OK) {
			System.out.println("Login ok");
		} else if (loginEvent.getEventType() == EventTypes.LOGOUT_OK) {
			System.out.println("Logout Successfull!");
		}

	}

	public AbstractGameEvent createLoginEvent(AccountData account, Player player) {
		LoginData ld = new LoginData();
		ld.setUserName(account.getUserName());
		ld.setPassword(account.getPassword());
		LoginEvent event = new LoginEvent(ld);
		player.setSessionId(new Integer(0));
		event.setPlayer(player);

		event.setEventType(EventTypes.LOGIN);
		return event;
	}

	public AbstractGameEvent createLogoutEvent(Player player) {
		LoginEvent event = new LoginEvent();
		event.setPlayer(player);
		event.setEventType(EventTypes.LOGOUT);
		return event;
	}

}
