package de.mbws.server.controller;

import de.mbws.common.MessageKeys;
import de.mbws.common.data.AbstractPlayerData;
import de.mbws.common.data.generated.Account;
import de.mbws.common.eventdata.generated.AccountErrorData;
import de.mbws.common.events.AbstractGameEvent;
import de.mbws.common.events.AccountEvent;
import de.mbws.common.events.EventTypes;
import de.mbws.server.account.AccountServer;
import de.mbws.server.data.ServerPlayerData;
import de.mbws.server.exceptions.DuplicateKeyException;
import de.mbws.server.persistence.AccountPersistenceManager;

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
                AbstractPlayerData p = new ServerPlayerData();
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
