package de.mbws.client.controller;

import org.apache.log4j.Logger;

import com.jme.app.GameStateManager;

import de.mbws.client.data.ClientPlayerData;
import de.mbws.client.state.MainMenuState;
import de.mbws.common.data.AccountData;
import de.mbws.common.eventdata.generated.LoginData;
import de.mbws.common.eventdata.generated.ServerRedirectData;
import de.mbws.common.events.AbstractGameEvent;
import de.mbws.common.events.AccountEvent;
import de.mbws.common.events.EventTypes;
import de.mbws.common.events.LoginEvent;
import de.mbws.common.events.ServerRedirectEvent;
import de.mbws.common.exceptions.InitializationException;

/**
 * Description:
 * 
 * @author azarai
 * 
 */
public class AccountController {

	private Logger logger = Logger.getLogger(AccountController.class);
    private static AccountController instance;
    /**
     * 
     */
    private AccountController() {
    }

    public static AccountController getInstance() {
    	if (instance==null){
    		instance = new AccountController();
    	}
    	return instance;
    }
    public void handleEvent(AccountEvent accountEvent) {
        if (accountEvent.getEventType() == EventTypes.S2C_ACCOUNT_CREATE_FAIL) {
        	//TODO: Kerim Correct error Handling here
        	logger.info("Cant Register: " + accountEvent.getAccountErrorData().getReason());
        	((MainMenuState)GameStateManager.getInstance().getChild("menu")).displayInfo("Account creation failed");
        } else if (accountEvent.getEventType() == EventTypes.S2C_ACCOUNT_CREATE_OK) {
            // TODO: Kerim enter next stage !
        	logger.info("Registration ok");
        	((MainMenuState)GameStateManager.getInstance().getChild("menu")).displayInfo("Account created");
        }
    }

    public AbstractGameEvent createRegisterEvent(AccountData account, ClientPlayerData player) {
        de.mbws.common.eventdata.generated.AccountData accountData = new de.mbws.common.eventdata.generated.AccountData();
        
        accountData.setUserName(account.getUsername());
        accountData.setNewPassword(account.getPassword());
        //event.setPasswordConfirmation(account.getPasswordConfirmation());
        accountData.setPassword(account.getPassword());
        accountData.setEmailAddress(account.getEmailaddress());
        AccountEvent event = new AccountEvent(accountData);
        event.setEventType(EventTypes.C2S_ACCOUNT_CREATE);
        player.setSessionId(new Integer(0));
        event.setPlayer(player);
        return event;
    }
//  TODO Kerim: correct error handling and next stages here !
	public void handleLoginEvent(LoginEvent loginEvent) {
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

	public AbstractGameEvent createLoginEvent(de.mbws.common.eventdata.generated.AccountData account,
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
	public void handleServerRedirectionEvent(ServerRedirectEvent event) {
		if (event.getEventType() == EventTypes.S2C_REDIRECT_TO_WORLDSERVER) {
			//TODO: we do this because of sync problems. should be solved by server however !
//			try {
//				Thread.sleep(5000);
//			} catch (InterruptedException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
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
