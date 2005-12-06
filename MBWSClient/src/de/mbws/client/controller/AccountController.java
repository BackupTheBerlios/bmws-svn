package de.mbws.client.controller;

import org.apache.log4j.Logger;

import com.jme.app.GameStateManager;

import de.mbws.client.data.ClientPlayerData;
import de.mbws.client.state.MainMenuState;
import de.mbws.common.data.AccountData;
import de.mbws.common.events.AbstractGameEvent;
import de.mbws.common.events.AccountEvent;
import de.mbws.common.events.EventTypes;

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
}
