package de.mwbs.client.controller;

import javax.swing.JOptionPane;

import de.mwbs.client.Client;
import de.mwbs.client.ClientPlayer;
import de.mwbs.common.GameEvent;
import de.mwbs.common.LoginEvent;
import de.mwbs.common.data.AccountData;

/**
 * Description: 
 * @author azarai
 *
 */
public class LoginController {

    private Client client = null;
    /**
     * 
     */
    public LoginController(Client client) {
        super();
        this.client = client;
    }

    public void handleEvent(LoginEvent loginEvent) {
        client.getLoginDialog().cancelTimer();
        if (loginEvent.getType() == LoginEvent.S_LOGIN_ACK_FAIL) {
            client.getLoginDialog().enableDialog();
            JOptionPane.showMessageDialog(client.getLoginDialog(), "Login failed!");
        } else if (loginEvent.getType() == LoginEvent.S_LOGIN_ACK_OK) {
            client.getLoginDialog().setVisible(false);
            JOptionPane.showMessageDialog(client.getLoginDialog(), "Login Successfull!");
        } else if (loginEvent.getType() == LoginEvent.S_DISCONNECT) {
            client.getLoginDialog().setVisible(false);
            JOptionPane.showMessageDialog(client.getLoginDialog(), "Logout Successfull!");            
        }

    }
    
    public GameEvent createLoginEvent(AccountData account, ClientPlayer player) {
        LoginEvent event = new LoginEvent();
        player.setSessionId(new Integer(0));
        event.setPlayer(player);
        event.setAccountName(account.getUsername());
        event.setPassword(account.getPassword());
        event.setType(LoginEvent.C_LOGIN);
        return event;
    }

    public GameEvent createLogoutEvent(ClientPlayer player) {
        LoginEvent event = new LoginEvent();
        event.setPlayer(player);
        event.setType(LoginEvent.C_LOGOUT);
        return event;
    }

    
}
