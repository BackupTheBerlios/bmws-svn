package de.mwbs.common;

import java.nio.ByteBuffer;

public abstract class AbstractGameEvent implements GameEvent {

    /** event type */
    protected int eventType;

    protected Player player;
    
    protected boolean sendAck = false;
    
    protected Integer[] recipients;
    
    public void setSendAck(boolean sendAck) {
        this.sendAck = sendAck;
    }

    public AbstractGameEvent() {
        super();
    }

    public int getType() {
        return eventType;
    }

    public void setType(int type) {
        this.eventType = type;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player p) {
        this.player =p;
    }

    public final void read(ByteBuffer buffer) {
        eventType = buffer.getInt();
        readPayload(buffer);
    }

    public abstract void readPayload(ByteBuffer buffer);
        
    public final int write(ByteBuffer buffer) {
        int pos = buffer.position();
        buffer.putInt(eventType);
        if (!sendAck) {
            writePayload(buffer);
        }
        return buffer.position() - pos;        
    }
    public abstract void writePayload(ByteBuffer buffer);

    public Integer[] getRecipients() {
        return recipients;
    }

    public void setRecipients(Integer[] recipients) {
        this.recipients = recipients;
    }
}
