package de.mbws.common.events;

import java.nio.ByteBuffer;

import de.mbws.common.events.data.AbstractEventData;
import de.mbws.common.events.data.generated.WorldObject;

/**
 * Description: This event is for all objects that are NOT moved 
 * @author Kerim
 */
public class ObjectEvent extends AbstractGameEvent {

    /**
     * Constructor for the server. Should probably be package visible 
     * @param payload
     */
	public  ObjectEvent(ByteBuffer payload) {
        super(payload, new WorldObject());
    }
    
    /**
     * Constructor for the client.
     */
    public ObjectEvent() {
        super(null);
    }
    
    public ObjectEvent(AbstractEventData eventData) {
        super(eventData);
    }

    public WorldObject getObjectInfo() {
        return (WorldObject) eventData;
    }

	@Override
	public int getEventId() {
		return EventTypes.GROUPID_EVENT_OBJECT;
	}

    

}
