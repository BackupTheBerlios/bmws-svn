package de.mwbs.common.events;

import java.nio.ByteBuffer;

import org.apache.log4j.Logger;

import de.mwbs.common.eventdata.AbstractEventData;
import de.mwbs.common.eventdata.generated.AccountData;
import de.mwbs.common.eventdata.generated.AccountErrorData;

/**
 * Description: 
 * @author Azarai
 *
 */
public class AccountEvent extends AbstractGameEvent {
    public static final int GE_ACCOUNT = 1;
    private static Logger logger = Logger.getLogger("AccountEvent");

    /**
     * Constructor for the server. Should probably be package visible 
     * @param payload
     */
    AccountEvent(ByteBuffer payload) {
        super(payload, new AccountData());
    }
    
    AccountEvent(ByteBuffer payload, AbstractEventData eventData) {
        super(payload, eventData);
    }   
    /**
     * Constructor for the client.
     */
    public AccountEvent() {
        super(null);
    }
    
    // TODO create delegators for setter and getter of the data

    public AccountEvent(AbstractEventData eventData) {
        super(eventData);
    }

    public AccountData getAccountData() {
        return (AccountData) eventData;
    }

    public AccountErrorData getAccountErrorData() {
        return (AccountErrorData) eventData;
    }
    
    @Override
    public int getEventId() {
        return GE_ACCOUNT;
    }
}
