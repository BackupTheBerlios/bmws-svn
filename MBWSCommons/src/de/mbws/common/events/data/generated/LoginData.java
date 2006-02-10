/** Generated class. Do not change !!! **/
package de.mbws.common.events.data.generated;

import de.mbws.common.events.data.AbstractEventData;
import java.util.*;
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

	public static void serializeList(ByteBuffer payload, List<LoginData> list) {
		if(list==null) return;
		payload.putInt(list.size());
		Iterator<LoginData> it = list.iterator();
		while (it.hasNext()) {
			it.next().serialize(payload);
		}
	}

	public static List<LoginData> deserializeList(ByteBuffer payload) {
		List<LoginData> list = new LinkedList<LoginData>();
		int size = payload.getInt();
		for (int i=0; i<size; i++) {
			LoginData element = new LoginData();
			element.deserialize(payload);
			list.add(element);
		}
		return list;
	}
}