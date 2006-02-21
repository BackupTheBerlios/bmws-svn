package de.mbws.common.events;

import java.nio.ByteBuffer;

import de.mbws.common.events.data.AbstractEventData;
import de.mbws.common.events.data.generated.StaticObject;

/**
 * Description: This event is for all objects that are NOT moved
 * 
 * @author Kerim
 */
public class ObjectEvent extends AbstractGameEvent {

	/**
	 * Constructor for the server. Should probably be package visible
	 * 
	 * @param payload
	 */
	public ObjectEvent(ByteBuffer payload) {
		super(payload, new StaticObject());
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

	public StaticObject getObjectInfo() {
		return (StaticObject) eventData;
	}

	@Override
	public int getEventId() {
		return EventTypes.GROUPID_EVENT_OBJECT;
	}

}
