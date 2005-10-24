package de.mwbs.server.controller;

import org.apache.log4j.Logger;

import de.mwbs.common.GameEvent;
import de.mwbs.common.LoginEvent;
import de.mwbs.common.Player;
import de.mwbs.common.data.generated.Account;
import de.mwbs.server.account.AccountServer;
import de.mwbs.server.persistence.AccountPersistenceManager;

public class LoginEventController extends EventController {
    private static Logger logger = Logger.getLogger(LoginEventController.class);
    /**
     * @param accountServer
     * @param eventType
     */
    public LoginEventController(AccountServer accountServer, int eventType) {
        super(accountServer, eventType);
    }


    public void handleEvent(GameEvent event) {

        LoginEvent l = (LoginEvent) event;
        if (event.getType() == LoginEvent.C_LOGIN) {
            Account account = AccountPersistenceManager.getInstance().getAccount(l.getAccountName(), l.getPassword());
            if (account == null) {
                LoginEvent lr = new LoginEvent();
                lr.setPlayer(event.getPlayer());
                lr.setType(LoginEvent.S_LOGIN_ACK_FAIL);
                sendEvent(lr);
            } else {
                Player p = new Player();
                p.setAccount(account);
                Integer sessionId = accountServer.nextSessionId();
                if (logger.isDebugEnabled()) {
                    logger.debug("Given session id =" + sessionId + " to accountName =" + account.getUsername());
                }
                p.setSessionId(sessionId);
                p.setChannel(l.getPlayer().getChannel());
                accountServer.addPlayer(sessionId, p);
                LoginEvent lr = new LoginEvent();
                lr.setPlayer(p);
                lr.setType(LoginEvent.S_LOGIN_ACK_OK);
                sendEvent(lr);
            }
        } else if (event.getType() == LoginEvent.C_LOGOUT) {
            LoginEvent lr = new LoginEvent();
            lr.setPlayer(event.getPlayer());
            lr.setType(LoginEvent.S_DISCONNECT);
            sendEvent(lr);
            accountServer.removePlayer(event.getPlayer());
        }
        
    }

}
