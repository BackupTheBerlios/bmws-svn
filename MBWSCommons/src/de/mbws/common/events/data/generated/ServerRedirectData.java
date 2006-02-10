/** Generated class. Do not change !!! **/
package de.mbws.common.events.data.generated;

import de.mbws.common.events.data.AbstractEventData;
import java.util.*;
import java.nio.ByteBuffer;

public class ServerRedirectData extends AbstractEventData { 
	private String host;
	private int port;


	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	} 

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	} 


	public void deserialize(ByteBuffer payload) {
		host = readString(payload);
		port = payload.getInt();
	}

	public int serialize(ByteBuffer payload) {
		writeString(payload, host);
		payload.putInt(port);
		return payload.position();
	}

	public static void serializeList(ByteBuffer payload, List<ServerRedirectData> list) {
		if(list==null) return;
		payload.putInt(list.size());
		Iterator<ServerRedirectData> it = list.iterator();
		while (it.hasNext()) {
			it.next().serialize(payload);
		}
	}

	public static List<ServerRedirectData> deserializeList(ByteBuffer payload) {
		List<ServerRedirectData> list = new LinkedList<ServerRedirectData>();
		int size = payload.getInt();
		for (int i=0; i<size; i++) {
			ServerRedirectData element = new ServerRedirectData();
			element.deserialize(payload);
			list.add(element);
		}
		return list;
	}
}