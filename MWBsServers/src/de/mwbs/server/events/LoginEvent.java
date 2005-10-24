package de.mwbs.server.events;

import java.nio.ByteBuffer;

import org.apache.log4j.Logger;

import de.mwbs.common.eventdata.AbstractEventData;
import de.mwbs.common.eventdata.generated.LoginData;

public class LoginEvent extends AbstractGameEvent {
	
	public static final int GE_LOGIN = 1;
	private static Logger logger = Logger.getLogger("LoginEvent");

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
		return GE_LOGIN;
	}
}
