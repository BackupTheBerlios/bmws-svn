package de.mbws.server.account.controller;

import org.apache.log4j.Logger;

import de.mbws.common.data.AbstractPlayerData;
import de.mbws.common.data.db.Account;
import de.mbws.common.eventdata.generated.ServerLoginData;
import de.mbws.common.events.AbstractGameEvent;
import de.mbws.common.events.EventTypes;
import de.mbws.common.events.LoginEvent;
import de.mbws.server.account.AccountServer;
import de.mbws.server.account.persistence.AccountPersistenceManager;
import de.mbws.server.data.ServerCommunicationData;
import de.mbws.server.data.ServerPlayerData;

public class LoginEventController extends AccountServerBaseEventController {
	private static Logger logger = Logger.getLogger(LoginEventController.class);

	/**
	 * @param accountServer
	 * @param eventType
	 */
	public LoginEventController(AccountServer accountServer) {
		super(accountServer);
	}

	public void handleEvent(AbstractGameEvent event) {

        LoginEvent l = (LoginEvent) event;
		if (event.getEventType() == EventTypes.C2S_LOGIN) {
			Account account = AccountPersistenceManager.getInstance()
					.getAccount(l.getLoginData().getUserName(),
							l.getLoginData().getPassword());
			if (account == null) {
				LoginEvent lr = new LoginEvent();
				lr.setPlayer(event.getPlayer());
				lr.setEventType(EventTypes.S2C_LOGIN_FAILED);
				sendEvent(lr);
			} else {
                AbstractPlayerData p = new ServerPlayerData();
				p.setAccount(account);
				Integer sessionId = getAccountServer().nextSessionId();
				if (logger.isDebugEnabled()) {
					logger.debug("Given session id =" + sessionId
							+ " to accountName =" + account.getUsername());
				}
				p.setSessionId(sessionId);
				p.setChannel(l.getPlayer().getChannel());
                getAccountServer().addPlayer(sessionId, p);
				LoginEvent lr = new LoginEvent();
				lr.setPlayer(p);
				lr.setEventType(EventTypes.S2C_LOGIN_OK);
				sendEvent(lr);
			}
		} else if (event.getEventType() == EventTypes.C2S_LOGOUT) {
			LoginEvent lr = new LoginEvent();
			lr.setPlayer(event.getPlayer());
			lr.setEventType(EventTypes.S2C_LOGOUT_OK);
			sendEvent(lr);
            getAccountServer().removePlayer(event.getPlayer());
		} else if (event.getEventType() == EventTypes.S2S_LOGIN) {
            ServerCommunicationData scd = new ServerCommunicationData();
            ServerLoginData sld = (ServerLoginData) event.getEventData();
            scd.setHostInfo(sld.getHostData());
            Integer sessionId = getAccountServer().nextSessionId();
            if (logger.isDebugEnabled()) {
                logger.debug("Given session id =" + sessionId
                        + " to new worldserver");
            }
            scd.setSessionId(sessionId);
            scd.setChannel(l.getPlayer().getChannel());
            getAccountServer().addPlayer(sessionId, scd);

            getAccountServer().setWorldServerSessionId(sessionId);
            LoginEvent lr = new LoginEvent();
            lr.setPlayer(scd);
            lr.setEventType(EventTypes.S2S_LOGIN_OK);
            sendEvent(lr);
        }
	}
}
