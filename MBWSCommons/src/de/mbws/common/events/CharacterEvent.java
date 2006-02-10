package de.mbws.common.events;

import java.nio.ByteBuffer;

import de.mbws.common.events.data.AbstractEventData;
import de.mbws.common.events.data.generated.CharacterDetails;

/**
 * Description: 
 * @author Azarai
 *
 */
public class CharacterEvent extends AbstractGameEvent {

    /**
     * Constructor for the server. Should probably be package visible 
     * @param payload
     */
	public  CharacterEvent(ByteBuffer payload) {
        super(payload, new CharacterDetails());
    }
    public CharacterEvent(ByteBuffer payload, AbstractEventData eventData) {
        super(payload, eventData);
    }
    /**
     * Constructor for the client.
     */
    public CharacterEvent() {
        super(null);
    }
    
    public CharacterEvent(AbstractEventData eventData) {
        super(eventData);
    }

    public CharacterDetails getCharacterDetails() {
        return (CharacterDetails) eventData;
    }

    @Override
    public int getEventId() {
        return EventTypes.GROUPID_EVENT_CHARACTER;
    }
}