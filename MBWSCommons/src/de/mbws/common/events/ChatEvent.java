package de.mbws.common.events;

import java.nio.ByteBuffer;

import de.mbws.common.eventdata.AbstractEventData;
import de.mbws.common.eventdata.generated.ChatData;
import de.mbws.common.eventdata.generated.LoginData;

public class ChatEvent extends AbstractGameEvent {

	/**
	 * Constructor for the server. Should probably be package visible
	 * 
	 * @param payload
	 */
	public ChatEvent(ByteBuffer payload) {
		super(payload, new LoginData());
	}

	public ChatEvent(ByteBuffer payload, AbstractEventData eventData) {
		super(payload, eventData);
	}

	/**
	 * Constructor for the client.
	 */
	public ChatEvent() {
		super(null);
	}

	public ChatEvent(AbstractEventData eventData) {
		super(eventData);
		// TODO Auto-generated constructor stub
	}

	public ChatData getChatData() {
		return (ChatData) eventData;
	}

	@Override
	public int getEventId() {
		return EventTypes.GROUPID_EVENT_CHAT;
	}
}
