package de.mwbs.client.controller;

import javax.swing.JOptionPane;

import de.mwbs.client.Client;
import de.mwbs.client.ClientPlayer;
import de.mwbs.common.data.AccountData;
import de.mwbs.common.eventdata.EventTypes;
import de.mwbs.common.eventdata.generated.LoginData;
import de.mwbs.server.events.AbstractGameEvent;
import de.mwbs.server.events.LoginEvent;

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
        if (loginEvent.getEventType() == EventTypes.LOGIN_FAILED) {
            client.getLoginDialog().enableDialog();
            JOptionPane.showMessageDialog(client.getLoginDialog(), "Login failed!");
        } else if (loginEvent.getEventType() == EventTypes.LOGIN_OK) {
            client.getLoginDialog().setVisible(false);
            JOptionPane.showMessageDialog(client.getLoginDialog(), "Login Successfull!");
        } else if (loginEvent.getEventType() == EventTypes.LOGOUT_OK) {
            client.getLoginDialog().setVisible(false);
            JOptionPane.showMessageDialog(client.getLoginDialog(), "Logout Successfull!");            
        }

    }
    
    public AbstractGameEvent createLoginEvent(AccountData account, ClientPlayer player) {
        LoginData ld = new LoginData();
        ld.setUserName(account.getUsername());
        ld.setPassword(account.getPassword());
    	LoginEvent event = new LoginEvent(ld);
        player.setSessionId(new Integer(0));
        event.setPlayer(player);

        event.setEventType(EventTypes.LOGIN);
        return event;
    }

    public AbstractGameEvent createLogoutEvent(ClientPlayer player) {
        LoginEvent event = new LoginEvent();
        event.setPlayer(player);
        event.setEventType(EventTypes.LOGOUT);
        return event;
    }

    
}
