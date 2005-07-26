package de.mwbs.common;

import java.nio.ByteBuffer;

public class GameEventFactory {

    
    public static GameEvent getGameEvent(byte[] payload, Player p){
        ByteBuffer buffer = ByteBuffer.wrap(payload);
        int eventType = buffer.getInt();
        buffer.position(0);
        GameEvent event = null;
        if(LoginEvent.C_LOGIN == eventType) {
            event = new LoginEvent();
            event.read(buffer);
                       
        } else if (LoginEvent.C_LOGOUT == eventType) {
            event = new LoginEvent();
            event.read(buffer);           
        } else if (LoginEvent.S_LOGIN_ACK_OK == eventType) {
            event = new LoginEvent();
            event.read(buffer);           
        } else if (LoginEvent.S_LOGIN_ACK_FAIL == eventType) {
            event = new LoginEvent();
            event.read(buffer);           
        } else if (LoginEvent.S_DISCONNECT == eventType) {
            event = new LoginEvent();
            event.read(buffer);           
        }
        
        if(AccountEvent.C_REGISTER == eventType) {
            event = new AccountEvent();
            event.read(buffer);
        } else if (AccountEvent.S_REGISTER_ACK_FAIL == eventType) {
            event = new AccountEvent();
            event.read(buffer);            
        } else if (AccountEvent.S_REGISTER_ACK_OK == eventType) {
            event = new AccountEvent();
            event.read(buffer);
        }
        
        if(event != null) {
            event.setPlayer(p);
        }
        return event;
    }
}
