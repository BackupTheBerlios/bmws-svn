package de.mwbs.common;

import de.mwbs.common.eventdata.AbstractEventData;

public abstract class AbstractGameEvent {

	// events
	public static final int GE_LOGIN = 1;

	/** event type */
	protected int eventType;
	protected Player player;
	protected boolean sendAck = false;
	protected Integer[] recipients;
	protected AbstractEventData eventData;
	protected byte[] payload;

	public AbstractGameEvent(byte[] payload, AbstractEventData eventData) {
		this.payload = payload;
		this.eventData = eventData;
		eventData.deserialize(payload, 2);
	}
	

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

	/**
	 * Should be called to process the event. Needs some context to process on,
	 * which will be added lateron.
	 */
	public abstract void process();


	public int serialize(byte[] buffer) {
		return eventData.serialize(buffer, 0);
	}
	
	
}
