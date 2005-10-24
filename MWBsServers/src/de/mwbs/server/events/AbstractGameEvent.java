package de.mwbs.server.events;

import java.nio.ByteBuffer;

import de.mwbs.common.Player;
import de.mwbs.common.eventdata.AbstractEventData;

public abstract class AbstractGameEvent {

	/** event type */
	protected int eventType;
	protected Player player;
	protected boolean sendAck = false;
	protected Integer[] recipients;
	protected AbstractEventData eventData;

	public AbstractGameEvent(ByteBuffer payload, AbstractEventData eventData) {
		this.eventData = eventData;
		eventData.deserialize(payload);
	}
	
	public AbstractGameEvent(AbstractEventData eventData) {
		this.eventData = eventData;
	}
	
	public abstract int getEventId();
	

	public void setSendAck(boolean sendAck) {
		this.sendAck = sendAck;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player p) {
		this.player = p;
	}

	public Integer[] getRecipients() {
		return recipients;
	}

	public void setRecipients(Integer[] recipients) {
		this.recipients = recipients;
	}

	public int serialize(ByteBuffer buffer) {
		int size = buffer.position();
	    if (eventData != null) {
			size = eventData.serialize(buffer);
		}
		return size;
	}

	public int getEventType() {
		return eventType;
	}

	public void setEventType(int eventType) {
		this.eventType = eventType;
	}
	
	
}
