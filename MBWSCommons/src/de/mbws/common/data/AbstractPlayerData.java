package de.mbws.common.data;

import java.nio.channels.SocketChannel;

/**
 * Description: 
 * @author Azarai
 *
 */
public abstract class AbstractPlayerData {

    private SocketChannel channel;
    private Integer sessionId;
    
    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public SocketChannel getChannel() {
        return channel;
    }

    public void setChannel(SocketChannel channel) {
        this.channel = channel;
    }    
}
