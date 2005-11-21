package de.mbws.common.events;

import java.nio.ByteBuffer;

import de.mbws.common.eventdata.AbstractEventData;
import de.mbws.common.eventdata.generated.CharactersOfPlayer;

/**
 * Description: 
 * @author Kerim
 *
 */
public class CharactersOfPlayerEvent extends AbstractGameEvent {

    /**
     * Constructor for the server. Should probably be package visible 
     * @param payload
     */
	public  CharactersOfPlayerEvent(ByteBuffer payload) {
        super(payload, new CharactersOfPlayer());
    }
    
    /**
     * Constructor for the client.
     */
    public CharactersOfPlayerEvent() {
        super(null);
    }
    
    public CharactersOfPlayerEvent(AbstractEventData eventData) {
        super(eventData);
    }

    public CharactersOfPlayer getCharactersOfPlayer() {
        return (CharactersOfPlayer) eventData;
    }

    @Override
    public int getEventId() {
        return EventTypes.GROUPID_EVENT_CHARACTER;
    }
}