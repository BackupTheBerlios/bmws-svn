package de.mwbs.server.controller;

import de.mwbs.common.AccountEvent;
import de.mwbs.common.GameEvent;
import de.mwbs.common.MessageKeys;
import de.mwbs.common.Player;
import de.mwbs.common.data.generated.Account;
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
    public void handleEvent(GameEvent event) {
        AccountEvent l = (AccountEvent) event;
        if (event.getType() == AccountEvent.C_REGISTER) {
            Account account = new Account();
            account.setPassword(l.getPassword());
            account.setEmailaddress(l.getEmailAddress());
            account.setUsername(l.getAccountName());
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
                ae.setType(AccountEvent.S_REGISTER_ACK_OK);
                sendEvent(ae);                
            } catch (DuplicateKeyException e) {
                AccountEvent ae = new AccountEvent();
                ae.setPlayer(event.getPlayer());
                ae.setType(AccountEvent.S_REGISTER_ACK_FAIL);
                ae.setAddidionalMessageKey(MessageKeys.ACCOUNT_DUPLICATE);
                sendEvent(ae);
            } catch (Exception e) {
                //ignore for now
            }
        }
    }
}
