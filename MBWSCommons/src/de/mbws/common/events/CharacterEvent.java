package de.mbws.common.events;

import java.nio.ByteBuffer;

import de.mbws.common.eventdata.AbstractEventData;
import de.mbws.common.eventdata.generated.PlayerInfo;

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
        super(payload, new PlayerInfo());
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

    public PlayerInfo getPlayerInfo() {
        return (PlayerInfo) eventData;
    }

    @Override
    public int getEventId() {
        return EventTypes.GROUPID_EVENT_CHARACTER;
    }
}