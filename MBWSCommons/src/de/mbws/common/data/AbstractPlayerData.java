package de.mbws.common.data;

import java.nio.channels.SocketChannel;

import de.mbws.common.data.db.generated.Account;

/**
 * Description: 
 * @author Azarai
 *
 */
public abstract class AbstractPlayerData {
    private Account account;
    private SocketChannel channel;
    private Integer sessionId;
    
    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public SocketChannel getChannel() {
        return channel;
    }

    public void setChannel(SocketChannel channel) {
        this.channel = channel;
    }    
}
