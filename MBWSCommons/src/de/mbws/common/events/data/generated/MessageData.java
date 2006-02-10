/** Generated class. Do not change !!! **/
package de.mbws.common.events.data.generated;

import de.mbws.common.events.data.AbstractEventData;
import java.util.*;
import java.nio.ByteBuffer;

public class MessageData extends AbstractEventData { 
	private byte type;
	private String author;
	private String message;


	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	} 

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	} 

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	} 


	public void deserialize(ByteBuffer payload) {
		type = payload.get();
		author = readString(payload);
		message = readString(payload);
	}

	public int serialize(ByteBuffer payload) {
		payload.put(type);
		writeString(payload, author);
		writeString(payload, message);
		return payload.position();
	}

	public static void serializeList(ByteBuffer payload, List<MessageData> list) {
		if(list==null) return;
		payload.putInt(list.size());
		Iterator<MessageData> it = list.iterator();
		while (it.hasNext()) {
			it.next().serialize(payload);
		}
	}

	public static List<MessageData> deserializeList(ByteBuffer payload) {
		List<MessageData> list = new LinkedList<MessageData>();
		int size = payload.getInt();
		for (int i=0; i<size; i++) {
			MessageData element = new MessageData();
			element.deserialize(payload);
			list.add(element);
		}
		return list;
	}
}