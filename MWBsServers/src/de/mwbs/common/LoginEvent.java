package de.mwbs.common;

import java.nio.ByteBuffer;


public class LoginEvent extends AbstractGameEvent {
    /** request to login */
    public static final int C_LOGIN = 1001;

    /** login ok */
    public static final int S_LOGIN_ACK_OK = 1002;

    /** login failed */
    public static final int S_LOGIN_ACK_FAIL = 1003;
    
    /** logout request */
    public static final int C_LOGOUT = 1005;

    /** notice of disconnect */
    public static final int S_DISCONNECT = 1007;

  
    private String accountName = null;
    private String password = null;
    
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void readPayload(ByteBuffer buffer) {
        setAccountName(NIOUtils.getStr(buffer));
        setPassword(NIOUtils.getStr(buffer));
        
    }

    public void writePayload(ByteBuffer buffer) {
        NIOUtils.putStr(buffer, getAccountName());
        NIOUtils.putStr(buffer, getPassword()); 
    }
}
