package de.mbws.client.controller;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.jme.app.GameState;
import com.jme.app.GameStateManager;

import de.mbws.client.data.ClientPlayerData;
import de.mbws.client.state.BaseGameState;
import de.mbws.client.state.MainMenuState;
import de.mbws.client.state.handler.BaseInputHandler;
import de.mbws.common.eventdata.generated.AccountData;
import de.mbws.common.eventdata.generated.LoginData;
import de.mbws.common.eventdata.generated.ServerRedirectData;
import de.mbws.common.eventdata.generated.SystemErrorData;
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
        if (instance == null) {
            instance = new AccountController();
        }
        return instance;
    }

    /**
     * TODO: Texte auslagern
     * 
     * @param accountEvent
     */
    public void handleEvent(AccountEvent accountEvent) {
        if (accountEvent.getEventType() == EventTypes.S2C_ACCOUNT_CREATE_FAIL) {
            // TODO: Kerim Correct error Handling here
            SystemErrorData sed = (SystemErrorData)accountEvent.getEventData();
            logger.info("Cant Register: " + sed.getReason());
            ((MainMenuState) GameStateManager.getInstance().getChild("menu")).displayError("Account creation failed:\n"
                    + sed.getReason());
        } else if (accountEvent.getEventType() == EventTypes.S2C_ACCOUNT_CREATE_OK) {
            // TODO: Kerim enter next stage !
            logger.info("Registration ok");
            ((MainMenuState) GameStateManager.getInstance().getChild("menu")).displayInfo("Account created");
        }
    }

    public AbstractGameEvent createRegisterEvent(AccountData account, ClientPlayerData player) {
        AccountEvent event = new AccountEvent(account);
        event.setEventType(EventTypes.C2S_ACCOUNT_CREATE);
        player.setSessionId(new Integer(0));
        event.setPlayer(player);
        return event;
    }

    /**
     * TODO: texte auslagern
     * 
     * @param loginEvent
     */
    public void handleLoginEvent(LoginEvent loginEvent) {
        if (loginEvent.getEventType() == EventTypes.S2C_LOGIN_FAILED) {
            if (logger.isDebugEnabled()) {
                logger.debug("Login failed!");
            }
            ((MainMenuState) GameStateManager.getInstance().getChild("menu")).displayError("Login failed!");
        } else if (loginEvent.getEventType() == EventTypes.S2C_LOGIN_OK) {
            // Updating the local client data:
            ClientPlayerData.getInstance().setSessionId(loginEvent.getPlayer().getSessionId());
            ClientPlayerData.getInstance().setAccount(loginEvent.getPlayer().getAccount());
            if (logger.isDebugEnabled()) {
                logger.debug("Session id =" + loginEvent.getPlayer().getSessionId());
                logger.debug("Login ok, trying to receive character data");
            }
            ClientNetworkController.getInstance().handleOutgoingEvent(CharacterController.getInstance().createCharacterListReceiveEvent());
        } else if (loginEvent.getEventType() == EventTypes.S2C_LOGOUT_OK) {
            if (logger.isDebugEnabled()) {
                logger.debug("Logout Successfull!");
            }
            ArrayList children = GameStateManager.getInstance().getChildren();
            for (Iterator iter = children.iterator(); iter.hasNext();) {
                GameState element = (GameState) iter.next();
                if( element.isActive()) {
                    if (element instanceof BaseGameState) {
                        ((BaseGameState) element).getInputHandler().requestStateSwitch(BaseInputHandler.GAMESTATE_MAINMENU); 
                    }
                }
            }
        }
    }

    public AbstractGameEvent createLoginEvent(de.mbws.common.eventdata.generated.AccountData account, ClientPlayerData player) {
        LoginData ld = new LoginData();
        ld.setUserName(account.getUserName());
        ld.setPassword(account.getPassword());
        LoginEvent event = new LoginEvent(ld);
        player.setSessionId(new Integer(0));
        event.setPlayer(player);

        event.setEventType(EventTypes.C2S_LOGIN);
        return event;
    }

    public AbstractGameEvent createLogoutEvent() {
        LoginEvent event = new LoginEvent();
        event.setPlayer(ClientPlayerData.getInstance());
        event.setEventType(EventTypes.C2S_LOGOUT);
        return event;
    }

    public void handleServerRedirectionEvent(ServerRedirectEvent event) {
        if (event.getEventType() == EventTypes.S2C_REDIRECT_TO_WORLDSERVER) {
            // Connecting to new Server, closing this connection here
            ClientNetworkController.getInstance().disconnect();
            ServerRedirectData data = (ServerRedirectData) event.getEventData();
            try {
                ClientNetworkController.getInstance().connect(data.getHost(), data.getPort());
                ClientNetworkController.getInstance().handleOutgoingEvent(CharacterController.getInstance().createEnterWorldServerEvent());
            } catch (InitializationException e) {
                if (logger.isDebugEnabled()) {
                    logger.debug("connection to ws failed", e);
                }
                ((MainMenuState) GameStateManager.getInstance().getChild("menu")).displayError("Cant connect to worldserver");
            }
        }

    }

}
