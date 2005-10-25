package de.mbws.common;

import java.nio.channels.SocketChannel;

import de.mbws.common.data.generated.Account;

public class Player {

    private Account account = null;
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
