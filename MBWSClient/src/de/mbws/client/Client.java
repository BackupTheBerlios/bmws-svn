package de.mbws.client;

import java.util.ResourceBundle;

import org.apache.log4j.BasicConfigurator;

import de.mbws.client.controller.AccountController;
import de.mbws.client.controller.LoginController;
import de.mbws.client.gui.AbstractDialog;
import de.mbws.client.gui.AccountDialog;
import de.mbws.client.gui.DialogFactory;
import de.mbws.client.gui.LoginDialog;
import de.mbws.client.gui.MainFrame;
import de.mbws.common.Player;
import de.mbws.common.data.AccountData;
import de.mbws.common.events.AbstractGameEvent;
import de.mbws.common.events.AccountEvent;
import de.mbws.common.events.LoginEvent;

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

    public void sendEvent(AbstractGameEvent event) {
        cnc.handleOutgoingEvent(event);
    }

    public static Client getInstance() {
        return instance;
    }

    public void login(AccountData account) throws Exception {
        cnc.connect();
        player = new ClientPlayer();
        AbstractGameEvent event = getLoginController().createLoginEvent(account, (ClientPlayer) player);
        sendEvent(event);
    }

    public void logout() {
    	AbstractGameEvent event = getLoginController().createLogoutEvent((ClientPlayer) player);
        sendEvent(event);
    }

    public void register(AccountData account) throws Exception {
        cnc.connect();
        player = new ClientPlayer();
        AbstractGameEvent event = getAccountController().createRegisterEvent(account, (ClientPlayer) player);
        sendEvent(event);
    }

    protected void processIncomingEvents(AbstractGameEvent event) {
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