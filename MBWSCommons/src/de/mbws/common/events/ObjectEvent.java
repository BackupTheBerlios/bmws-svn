package de.mbws.common.events;

import java.nio.ByteBuffer;

import org.apache.log4j.Logger;

import de.mbws.common.eventdata.AbstractEventData;
import de.mbws.common.eventdata.generated.PlayerInfo;
import de.mbws.common.eventdata.generated.WorldObject;

/**
 * Description: This event is for all objects that are NOT moved 
 * @author Kerim
 */
public class ObjectEvent extends AbstractGameEvent {

    
    
    private static Logger logger = Logger.getLogger("ObjectEvent");

    /**
     * Constructor for the server. Should probably be package visible 
     * @param payload
     */
    ObjectEvent(ByteBuffer payload) {
        super(payload, new PlayerInfo());
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
		// TODO Auto-generated method stub
		return 0;
	}

    

}
