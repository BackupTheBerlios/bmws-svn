package de.mbws.server.account.controller;

import de.mbws.common.MessageKeys;
import de.mbws.common.events.AbstractGameEvent;
import de.mbws.common.events.AccountEvent;
import de.mbws.common.events.EventTypes;
import de.mbws.common.events.data.generated.SystemErrorData;
import de.mbws.server.AbstractTcpServer;
import de.mbws.server.account.persistence.AccountPersistenceManager;
import de.mbws.server.data.ServerPlayerData;
import de.mbws.server.data.db.generated.Account;
import de.mbws.server.exceptions.DuplicateKeyException;

/**
 * Description: 
 * @author azarai
 *
 */
public class AccountEventController extends AccountServerBaseEventController {

    private static final Integer[] supportedEventTypes = { EventTypes.C2S_ACCOUNT_CREATE };

    /**
     * @param server
     */
    public AccountEventController(AbstractTcpServer server) {
        super(server);
    }
    /* (non-Javadoc)
     * @see de.mwbs.server.controller.EventController#handleEvent(de.mwbs.common.GameEvent)
     */
    @Override
    public void handleEvent(AbstractGameEvent event) {
        AccountEvent l = (AccountEvent) event;
        if (event.getEventType() == EventTypes.C2S_ACCOUNT_CREATE) {
            Account account = new Account();
            account.setPassword(l.getAccountData().getPassword());
            account.setEmailaddress(l.getAccountData().getEmailAddress());
            account.setUsername(l.getAccountData().getUserName());
            try {
                AccountPersistenceManager.getInstance().createAccount(account);
                ServerPlayerData p = new ServerPlayerData();
                p.setAccount(account);
                Integer sessionId = getAccountServer().nextSessionId();
                p.setSessionId(sessionId);
                p.setChannel(l.getPlayer().getChannel());
//                getAccountServer().addPlayer(sessionId, p);
                AccountEvent ae = new AccountEvent();
                ae.setPlayer(p);
                ae.setEventType(EventTypes.S2C_ACCOUNT_CREATE_OK);
                sendEvent(ae);                
            } catch (DuplicateKeyException e) {
                SystemErrorData aed = new SystemErrorData();
                aed.setReason(MessageKeys.ACCOUNT_DUPLICATE);
                AccountEvent ae = new AccountEvent(aed);
                ae.setPlayer(event.getPlayer());
                ae.setEventType(EventTypes.S2C_ACCOUNT_CREATE_FAIL);
                sendEvent(ae);
            } catch (Exception e) {
                //ignore for now
            }
        }
    }
    /*
     * (non-Javadoc)
     * 
     * @see de.mbws.server.controller.AbstractEventController#getSupportedEventTypes()
     */
    @Override
    public Integer[] getSupportedEventTypes() {
        return supportedEventTypes;
    }
}
