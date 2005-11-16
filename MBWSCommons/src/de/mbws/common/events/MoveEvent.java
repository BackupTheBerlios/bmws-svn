package de.mbws.common.events;

import java.nio.ByteBuffer;

import de.mbws.common.eventdata.AbstractEventData;
import de.mbws.common.eventdata.generated.MoveData;

/**
 * Description: This event is for all objects that can move or be moved
 * @author Kerim
 */
public class MoveEvent extends AbstractGameEvent {

    /**
     * Constructor for the server. Should probably be package visible 
     * @param payload
     */
	public  MoveEvent(ByteBuffer payload) {
        super(payload, new MoveData());
    }
    
    /**
     * Constructor for the client.
     */
    public MoveEvent() {
        super(null);
    }
    
    public MoveEvent(AbstractEventData eventData) {
        super(eventData);
    }

    public MoveData getMoveData() {
        return (MoveData) eventData;
    }

    @Override
    public int getEventId() {
        return EventTypes.GROUPID_EVENT_MOVEMENT;
    }

}
