/** Generated class. Do not change !!! **/
package de.mbws.common.eventdata.generated;

import de.mbws.common.eventdata.AbstractEventData;
import java.nio.ByteBuffer;

public class LoginData extends AbstractEventData { 
	private String userName;
	private String password;


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	} 

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	} 


	public void deserialize(ByteBuffer payload) {
		userName = readString(payload);
		password = readString(payload);
	}

	public int serialize(ByteBuffer payload) {
		writeString(payload, userName);
		writeString(payload, password);
		return payload.position();
	}
}