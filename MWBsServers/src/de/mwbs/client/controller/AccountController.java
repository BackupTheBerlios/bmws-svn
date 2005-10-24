package de.mwbs.client.controller;

import javax.swing.JOptionPane;

import de.mwbs.client.Client;
import de.mwbs.client.ClientPlayer;
import de.mwbs.common.data.AccountData;
import de.mwbs.common.events.AbstractGameEvent;
import de.mwbs.common.events.AccountEvent;
import de.mwbs.common.events.EventTypes;

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
        AccountEvent event = new AccountEvent();
        player.setSessionId(new Integer(0));
        event.setPlayer(player);
        de.mwbs.common.eventdata.generated.AccountData accountData = new de.mwbs.common.eventdata.generated.AccountData();
        
        accountData.setUserName(account.getUsername());
        accountData.setNewPassword(account.getPassword());
        //event.setPasswordConfirmation(account.getPasswordConfirmation());
        accountData.setPassword(account.getPasswordold());
        accountData.setEmailAddress(account.getEmailaddress());
        event.setEventType(EventTypes.ACCOUNT_CREATE);
        return event;
    }
}
