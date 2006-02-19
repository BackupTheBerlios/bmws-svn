package de.mbws.common.events;

import java.nio.ByteBuffer;

import de.mbws.common.events.data.AbstractEventData;
import de.mbws.common.events.data.generated.CharacterData;
import de.mbws.common.events.data.generated.OCharacterData;

/**
 * Description: This event is for all objects that are NOT moved 
 * @author Kerim
 */
public class PCEvent extends AbstractGameEvent {

    /**
     * Constructor for the server. Should probably be package visible 
     * @param payload
     */
	public  PCEvent(ByteBuffer payload) {
        super(payload, new CharacterData());
    }
    
    /**
     * Constructor for the client.
     */
    public PCEvent() {
        super(null);
    }
    
    public PCEvent(AbstractEventData eventData) {
        super(eventData);
    }

    public OCharacterData getData() {
        return (OCharacterData) eventData;
    }

	@Override
	public int getEventId() {
		return EventTypes.GROUPID_EVENT_OBJECT;
	}

    

}
