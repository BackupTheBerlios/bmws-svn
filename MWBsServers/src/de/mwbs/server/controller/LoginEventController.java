package de.mwbs.server.controller;

import org.apache.log4j.Logger;

import de.mwbs.common.Player;
import de.mwbs.common.data.generated.Account;
import de.mwbs.common.eventdata.EventTypes;
import de.mwbs.server.account.AccountServer;
import de.mwbs.server.events.AbstractGameEvent;
import de.mwbs.server.events.LoginEvent;
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

	public void handleEvent(AbstractGameEvent event) {

		if (event.getEventId() == EventTypes.LOGIN) {
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
				Player p = new Player();
				p.setAccount(account);
				Integer sessionId = accountServer.nextSessionId();
				if (logger.isDebugEnabled()) {
					logger.debug("Given session id =" + sessionId
							+ " to accountName =" + account.getUsername());
				}
				p.setSessionId(sessionId);
				p.setChannel(l.getPlayer().getChannel());
				accountServer.addPlayer(sessionId, p);
				LoginEvent lr = new LoginEvent();
				lr.setPlayer(p);
				lr.setEventType(EventTypes.LOGIN_OK);
				sendEvent(lr);
			}
		} else if (event.getEventId() == EventTypes.LOGOUT) {
			LoginEvent lr = new LoginEvent();
			lr.setPlayer(event.getPlayer());
			lr.setEventType(EventTypes.LOGOUT_OK);
			sendEvent(lr);
			accountServer.removePlayer(event.getPlayer());
		}

	}

}
