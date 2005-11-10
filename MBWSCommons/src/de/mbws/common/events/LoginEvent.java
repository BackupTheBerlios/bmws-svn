package de.mbws.common.events;

import java.nio.ByteBuffer;

import de.mbws.common.eventdata.AbstractEventData;
import de.mbws.common.eventdata.generated.LoginData;

public class LoginEvent extends AbstractGameEvent {

    /**
	 * Constructor for the server. Should probably be package visible 
	 * @param payload
	 */
	LoginEvent(ByteBuffer payload) {
		super(payload, new LoginData());
	}
	
	/**
	 * Constructor for the client.
	 */
	public LoginEvent() {
		super(null);
	}
	
	// TODO create delegators for setter and getter of the data

	public LoginEvent(AbstractEventData eventData) {
		super(eventData);
		// TODO Auto-generated constructor stub
	}

	public LoginData getLoginData() {
		return (LoginData) eventData;
	}

	@Override
	public int getEventId() {
		return EventTypes.GROUPID_EVENT_LOGIN;
	}
}
