package de.mbws.common.events;

import java.nio.ByteBuffer;

import de.mbws.common.eventdata.AbstractEventData;
import de.mbws.common.eventdata.generated.LoginData;
import de.mbws.common.eventdata.generated.ServerRedirectData;

/**
 * Description: 
 * @author Azarai
 *
 */
public class ServerRedirectEvent extends AbstractGameEvent {

    /**
     * Constructor for the server. Should probably be package visible 
     * @param payload
     */
    public ServerRedirectEvent(ByteBuffer payload) {
        super(payload, new ServerRedirectData());
    }
    
    /**
     * Constructor for the client.
     */
    public ServerRedirectEvent() {
        super(null);
    }
    
    public ServerRedirectEvent(AbstractEventData eventData) {
        super(eventData);
        // TODO Auto-generated constructor stub
    }

    public LoginData getLoginData() {
        return (LoginData) eventData;
    }

    @Override
    public int getEventId() {
        return EventTypes.GROUPID_EVENT_REDIRECT;
    }
}
