/** Generated class. Do not change !!! **/
package de.mbws.common.events.data.generated;

import de.mbws.common.events.data.AbstractEventData;
import java.util.*;
import java.nio.ByteBuffer;

public class ServerLoginData extends AbstractEventData { 
	private String name;
	private String password;
	private ServerRedirectData hostData;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	} 

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	} 

	public ServerRedirectData getHostData() {
		return hostData;
	}

	public void setHostData(ServerRedirectData hostData) {
		this.hostData = hostData;
	} 


	public void deserialize(ByteBuffer payload) {
		name = readString(payload);
		password = readString(payload);
		hostData = new ServerRedirectData();
		hostData.deserialize(payload);
	}

	public int serialize(ByteBuffer payload) {
		writeString(payload, name);
		writeString(payload, password);
		hostData.serialize(payload);
		return payload.position();
	}

	public static void serializeList(ByteBuffer payload, List<ServerLoginData> list) {
		if(list==null) return;
		payload.putInt(list.size());
		Iterator<ServerLoginData> it = list.iterator();
		while (it.hasNext()) {
			it.next().serialize(payload);
		}
	}

	public static List<ServerLoginData> deserializeList(ByteBuffer payload) {
		List<ServerLoginData> list = new LinkedList<ServerLoginData>();
		int size = payload.getInt();
		for (int i=0; i<size; i++) {
			ServerLoginData element = new ServerLoginData();
			element.deserialize(payload);
			list.add(element);
		}
		return list;
	}
}