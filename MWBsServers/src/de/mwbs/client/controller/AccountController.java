package de.mwbs.client.controller;

import javax.swing.JOptionPane;

import de.mwbs.client.Client;
import de.mwbs.client.ClientPlayer;
import de.mwbs.common.AccountEvent;
import de.mwbs.common.GameEvent;
import de.mwbs.common.data.AccountData;

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
        if (accountEvent.getType() == AccountEvent.S_REGISTER_ACK_FAIL) {
            client.getAccountDialog().enableDialog();
            JOptionPane.showMessageDialog(client.getAccountDialog(), "Cant Register: " + accountEvent.getAddidionalMessageKey());
        } else if (accountEvent.getType() == AccountEvent.S_REGISTER_ACK_OK) {
            client.getAccountDialog().setVisible(false);
            JOptionPane.showMessageDialog(client.getMainFrame(), "Register successfull: ");
        }
    }

    public GameEvent createRegisterEvent(AccountData account, ClientPlayer player) {
        AccountEvent event = new AccountEvent();
        player.setSessionId(new Integer(0));
        event.setPlayer(player);
        event.setAccountName(account.getUsername());
        event.setPassword(account.getPassword());
        event.setPasswordConfirmation(account.getPasswordConfirmation());
        event.setPasswordold(account.getPasswordold());
        event.setEmailAddress(account.getEmailaddress());
        event.setType(AccountEvent.C_REGISTER);
        return event;
    }
}
