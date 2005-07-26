package de.mwbs.common;

import java.nio.ByteBuffer;


/**
 * GameEvent.java
 *
 * Interface for GameEvents, all event classes must implement this interface.
 * 
 * @version 1.0
 */
public interface GameEvent {
    public int getType();
    public void setType(int type);
    
    public Player getPlayer();
    public void setPlayer(Player p);
    
    public Integer[] getRecipients();
    public void setRecipients(Integer[] recipients);
    
    public void read(ByteBuffer buffer);
    public int write(ByteBuffer buffer);
}

