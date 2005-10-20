package de.mwbs.common;

import org.apache.log4j.Logger;

import de.mwbs.common.eventdata.generated.LoginData;

public class LoginEvent extends AbstractGameEvent {
	private static Logger logger = Logger.getLogger("LoginEvent");

	public LoginEvent(byte[] payload) {
		super(payload, new LoginData());
	}

	public LoginData getLoginData() {
		return (LoginData) eventData;
	}

	@Override
	public void process() {
		// TODO Auto-generated method stub
		logger.debug("LoginEvent for " + getLoginData().getUserName() + " with password "
				+ getLoginData().getPassword());
	}
}
