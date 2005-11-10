package de.mbws.server.account.controller;

import org.apache.log4j.Logger;

import de.mbws.common.data.AbstractPlayerData;
import de.mbws.common.data.generated.Account;
import de.mbws.common.events.AbstractGameEvent;
import de.mbws.common.events.EventTypes;
import de.mbws.common.events.LoginEvent;
import de.mbws.server.account.AccountServer;
import de.mbws.server.account.persistence.AccountPersistenceManager;
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

		if (event.getEventType() == EventTypes.LOGIN) {
			LoginEvent l = (LoginEvent) event;
			Account account = AccountPersistenceManager.getInstance()
					.getAccount(l.getLoginData().getUserName(),
							l.getLoginData().getPassword());
			if (account == null) {
				LoginEvent lr = new LoginEvent();
				lr.setPlayer(event.getPlayer());
				lr.setEventType(EventTypes.LOGIN_FAILED);
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
				lr.setEventType(EventTypes.LOGIN_OK);
				sendEvent(lr);
			}
		} else if (event.getEventType() == EventTypes.LOGOUT) {
			LoginEvent lr = new LoginEvent();
			lr.setPlayer(event.getPlayer());
			lr.setEventType(EventTypes.LOGOUT_OK);
			sendEvent(lr);
            getAccountServer().removePlayer(event.getPlayer());
		}
	}
}