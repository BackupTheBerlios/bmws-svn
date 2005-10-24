package de.mwbs.server.controller;

import de.mwbs.common.MessageKeys;
import de.mwbs.common.Player;
import de.mwbs.common.data.generated.Account;
import de.mwbs.common.eventdata.generated.AccountErrorData;
import de.mwbs.common.events.AbstractGameEvent;
import de.mwbs.common.events.AccountEvent;
import de.mwbs.common.events.EventTypes;
import de.mwbs.server.account.AccountServer;
import de.mwbs.server.exceptions.DuplicateKeyException;
import de.mwbs.server.persistence.AccountPersistenceManager;

/**
 * Description: 
 * @author azarai
 *
 */
public class AccountEventController extends EventController {

    /**
     * @param accountServer
     * @param eventType
     */
    public AccountEventController(AccountServer accountServer, int eventType) {
        super(accountServer, eventType);
    }

    /* (non-Javadoc)
     * @see de.mwbs.server.controller.EventController#handleEvent(de.mwbs.common.GameEvent)
     */
    @Override
    public void handleEvent(AbstractGameEvent event) {
        AccountEvent l = (AccountEvent) event;
        if (event.getEventType() == EventTypes.ACCOUNT_CREATE) {
            Account account = new Account();
            account.setPassword(l.getAccountData().getPassword());
            account.setEmailaddress(l.getAccountData().getEmailAddress());
            account.setUsername(l.getAccountData().getUserName());
            try {
                AccountPersistenceManager.getInstance().createAccount(account);
                Player p = new Player();
                p.setAccount(account);
                Integer sessionId = accountServer.nextSessionId();
                p.setSessionId(sessionId);
                p.setChannel(l.getPlayer().getChannel());
                accountServer.addPlayer(sessionId, p);
                AccountEvent ae = new AccountEvent();
                ae.setPlayer(p);
                ae.setEventType(EventTypes.ACCOUNT_CREATE_OK);
                sendEvent(ae);                
            } catch (DuplicateKeyException e) {
                AccountErrorData aed = new AccountErrorData();
                aed.setReason(MessageKeys.ACCOUNT_DUPLICATE);
                AccountEvent ae = new AccountEvent(aed);
                ae.setPlayer(event.getPlayer());
                ae.setEventType(EventTypes.ACCOUNT_CREATE_FAIL);
                sendEvent(ae);
            } catch (Exception e) {
                //ignore for now
            }
        }
    }
}
