package de.mwbs.client;

import java.util.ResourceBundle;

import org.apache.log4j.BasicConfigurator;

import de.mwbs.client.controller.AccountController;
import de.mwbs.client.controller.LoginController;
import de.mwbs.client.gui.AbstractDialog;
import de.mwbs.client.gui.AccountDialog;
import de.mwbs.client.gui.DialogFactory;
import de.mwbs.client.gui.LoginDialog;
import de.mwbs.client.gui.MainFrame;
import de.mwbs.common.AccountEvent;
import de.mwbs.common.GameEvent;
import de.mwbs.common.LoginEvent;
import de.mwbs.common.Player;
import de.mwbs.common.data.AccountData;

public class Client {

    private static ResourceBundle resourceBundle = null;

    private static Player player = null;

    MainFrame mainFrame = null;

    ClientNetworkController cnc = null;

    private static Client instance = null;

    LoginController lc = null;

    AccountController ac = null;

    AbstractDialog accountDialog = null;

    AbstractDialog loginDialog = null;

    public static void main(String[] args) {
        resourceBundle = ResourceBundle.getBundle("client");
        BasicConfigurator.configure();
        Client c = new Client();
        c.mainFrame = new MainFrame(c);

        c.cnc = new ClientNetworkController(c);
        c.cnc.start();

        c.mainFrame.setVisible(true);
        Client.instance = c;
    }

    /**
     * 
     */
    public Client() {
    }

    /**
     * @return Returns the resourceBundle.
     */
    public static ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public static Player getPlayer() {
        return player;
    }

    public void sendEvent(GameEvent event) {
        cnc.handleOutgoingEvent(event);
    }

    public static Client getInstance() {
        return instance;
    }

    public void login(AccountData account) throws Exception {
        cnc.connect();
        player = new ClientPlayer();
        GameEvent event = getLoginController().createLoginEvent(account, (ClientPlayer) player);
        sendEvent(event);
    }

    public void logout() {
        GameEvent event = getLoginController().createLogoutEvent((ClientPlayer) player);
        sendEvent(event);
    }

    public void register(AccountData account) throws Exception {
        cnc.connect();
        player = new ClientPlayer();
        GameEvent event = getAccountController().createRegisterEvent(account, (ClientPlayer) player);
        sendEvent(event);
    }

    protected void processIncomingEvents(GameEvent event) {
        event.setPlayer(player);
        if (event instanceof LoginEvent) {
            lc.handleEvent((LoginEvent) event);
        } else if (event instanceof AccountEvent) {
            getAccountController().handleEvent((AccountEvent) event);
        }
    }

    public AccountDialog getAccountDialog() {
        if (accountDialog == null)
            accountDialog = DialogFactory.createAccountDialog(this);
        return (AccountDialog) accountDialog;
    }

    public AccountController getAccountController() {
        if (ac == null)
            ac = new AccountController(this);
        return ac;
    }

    public LoginController getLoginController() {
        if (lc == null)
            lc = new LoginController(this);
        return lc;
    }

    public LoginDialog getLoginDialog() {
        if (loginDialog == null)
            loginDialog = DialogFactory.createLoginDialog(this);
        return (LoginDialog) loginDialog;
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }
}