package de.mbws.client.eventactions;

import java.nio.ByteBuffer;

import de.mbws.common.events.data.AbstractEventData;

public abstract class AbstractEventAction {

	protected int eventType;

	protected AbstractEventData eventData;

	public AbstractEventAction(ByteBuffer payload, AbstractEventData eventData) {
		this.eventData = eventData;
		eventData.deserialize(payload);

	}

	public AbstractEventAction(AbstractEventData eventData) {
		this.eventData = eventData;
	}

	public int getEventType() {
		return eventType;
	}

	public void setEventType(int eventType) {
		this.eventType = eventType;
	}

	public AbstractEventData getEventData() {
		return eventData;
	}
	
	public abstract void performAction();
}
