package de.mbws.common.events;

import java.nio.ByteBuffer;

import de.mbws.common.events.data.AbstractEventData;
import de.mbws.common.events.data.generated.AccountData;

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
	public AccountEvent(ByteBuffer payload) {
        super(payload, new AccountData());
    }
    
	public AccountEvent(ByteBuffer payload, AbstractEventData eventData) {
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
    
    @Override
    public int getEventId() {
        return EventTypes.GROUPID_EVENT_ACCOUNT;
    }
}
