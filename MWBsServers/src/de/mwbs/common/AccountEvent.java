package de.mwbs.common;

import java.nio.ByteBuffer;


public class AccountEvent extends AbstractGameEvent {
    /** request to register */
    public static final int C_REGISTER = 1010;

    /** register ok */
    public static final int S_REGISTER_ACK_OK = 1011;

    /** register failed */
    public static final int S_REGISTER_ACK_FAIL = 1012;
     
    private String accountName = null;
    private String password = null;
    private String passwordConfirmation = null;
    private String passwordold = null;
    private String emailAddress = null;
    private String addidionalMessageKey = null;
    
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getPasswordold() {
        return passwordold;
    }

    public void setPasswordold(String passwordold) {
        this.passwordold = passwordold;
    }

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
        setPasswordConfirmation(NIOUtils.getStr(buffer));
        setPasswordold(NIOUtils.getStr(buffer));
        setEmailAddress(NIOUtils.getStr(buffer));
        setAddidionalMessageKey(NIOUtils.getStr(buffer));
        
    }

    public void writePayload(ByteBuffer buffer) {
        NIOUtils.putStr(buffer, getAccountName());
        NIOUtils.putStr(buffer, getPassword());
        NIOUtils.putStr(buffer, getPasswordConfirmation());
        NIOUtils.putStr(buffer, getPasswordold());
        NIOUtils.putStr(buffer, getEmailAddress());
        NIOUtils.putStr(buffer, getAddidionalMessageKey());
    }

    public String getAddidionalMessageKey() {
        return addidionalMessageKey;
    }

    public void setAddidionalMessageKey(String addidionalMessageKey) {
        this.addidionalMessageKey = addidionalMessageKey;
    }
}
