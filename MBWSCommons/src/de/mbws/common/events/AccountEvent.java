package de.mbws.common.events;

import java.nio.ByteBuffer;

import de.mbws.common.eventdata.AbstractEventData;
import de.mbws.common.eventdata.generated.AccountData;
import de.mbws.common.eventdata.generated.AccountErrorData;

/**
 * Description: 
 * @author Azarai
 *
 */
public class AccountEvent extends AbstractGameEvent {

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
        return EventTypes.GROUPID_EVENT_ACCOUNT;
    }
}
