package de.mbws.client.controller;

import javax.swing.JOptionPane;

import de.mbws.client.Client;
import de.mbws.client.ClientPlayer;
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

    private Client client = null;

    /**
     * 
     */
    public AccountController(Client client) {
        super();
        this.client = client;
    }

    public void handleEvent(AccountEvent accountEvent) {

        client.getAccountDialog().cancelTimer();
        if (accountEvent.getEventType() == EventTypes.ACCOUNT_CREATE_FAIL) {
            client.getAccountDialog().enableDialog();
            JOptionPane.showMessageDialog(client.getAccountDialog(), "Cant Register: " + accountEvent.getAccountErrorData().getReason());
        } else if (accountEvent.getEventType() == EventTypes.ACCOUNT_CREATE_OK) {
            client.getAccountDialog().setVisible(false);
            JOptionPane.showMessageDialog(client.getMainFrame(), "Register successfull: ");
        }
    }

    public AbstractGameEvent createRegisterEvent(AccountData account, ClientPlayer player) {
       
       
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
