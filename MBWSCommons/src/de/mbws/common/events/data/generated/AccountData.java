/** Generated class. Do not change !!! **/
package de.mbws.common.events.data.generated;

import de.mbws.common.events.data.AbstractEventData;
import java.util.*;
import java.nio.ByteBuffer;

public class AccountData extends AbstractEventData { 
	private String userName;
	private String password;
	private String emailAddress;


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

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	} 


	public void deserialize(ByteBuffer payload) {
		userName = readString(payload);
		password = readString(payload);
		emailAddress = readString(payload);
	}

	public int serialize(ByteBuffer payload) {
		writeString(payload, userName);
		writeString(payload, password);
		writeString(payload, emailAddress);
		return payload.position();
	}

	public static void serializeList(ByteBuffer payload, List<AccountData> list) {
		if(list==null) return;
		payload.putInt(list.size());
		Iterator<AccountData> it = list.iterator();
		while (it.hasNext()) {
			it.next().serialize(payload);
		}
	}

	public static List<AccountData> deserializeList(ByteBuffer payload) {
		List<AccountData> list = new LinkedList<AccountData>();
		int size = payload.getInt();
		for (int i=0; i<size; i++) {
			AccountData element = new AccountData();
			element.deserialize(payload);
			list.add(element);
		}
		return list;
	}
}