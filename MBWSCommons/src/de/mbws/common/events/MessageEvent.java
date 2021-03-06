package de.mbws.common.events;

import java.nio.ByteBuffer;

import de.mbws.common.events.data.AbstractEventData;
import de.mbws.common.events.data.generated.MessageData;


public class MessageEvent extends AbstractGameEvent {

	/**
	 * Constructor for the server. Should probably be package visible
	 * 
	 * @param payload
	 */
	public MessageEvent(ByteBuffer payload) {
		super(payload, new MessageData());
	}

	public MessageEvent(ByteBuffer payload, AbstractEventData eventData) {
		super(payload, eventData);
	}

	/**
	 * Constructor for the client.
	 */
	public MessageEvent() {
		super(null);
	}

	public MessageEvent(AbstractEventData eventData) {
		super(eventData);
	}

	public MessageData getMessageData() {
		return (MessageData) eventData;
	}

	@Override
	public int getEventId() {
		return EventTypes.GROUPID_EVENT_CHAT;
	}
}
