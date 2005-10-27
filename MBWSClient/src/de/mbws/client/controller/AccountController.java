package de.mbws.client.controller;

import de.mbws.common.Player;
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
        if (accountEvent.getEventType() == EventTypes.ACCOUNT_CREATE_FAIL) {
        	//TODO: Kerim Correct error Handling here
            System.out.println("Cant Register: " + accountEvent.getAccountErrorData().getReason());
        } else if (accountEvent.getEventType() == EventTypes.ACCOUNT_CREATE_OK) {
            // TODO: Kerim enter next stage !
        }
    }

    public AbstractGameEvent createRegisterEvent(AccountData account, Player player) {
        de.mbws.common.eventdata.generated.AccountData accountData = new de.mbws.common.eventdata.generated.AccountData();
        
        accountData.setUserName(account.getUsername());
        accountData.setNewPassword(account.getPassword());
        //event.setPasswordConfirmation(account.getPasswordConfirmation());
        accountData.setPassword(account.getPassword());
        accountData.setEmailAddress(account.getEmailaddress());
        AccountEvent event = new AccountEvent(accountData);
        event.setEventType(EventTypes.ACCOUNT_CREATE);
        player.setSessionId(new Integer(0));
        event.setPlayer(player);
        return event;
    }
}
