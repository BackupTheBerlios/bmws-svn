/** Generated class. Do not change !!! **/
package de.mbws.common.eventdata.generated;

import de.mbws.common.eventdata.AbstractEventData;
import java.util.*;
import java.nio.ByteBuffer;

public class ChatData extends AbstractEventData { 
	private String author;
	private String message;


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
		author = readString(payload);
		message = readString(payload);
	}

	public int serialize(ByteBuffer payload) {
		writeString(payload, author);
		writeString(payload, message);
		return payload.position();
	}

	public static void serializeList(ByteBuffer payload, List<ChatData> list) {
		if(list==null) return;
		payload.putInt(list.size());
		Iterator<ChatData> it = list.iterator();
		while (it.hasNext()) {
			it.next().serialize(payload);
		}
	}

	public static List<ChatData> deserializeList(ByteBuffer payload) {
		List<ChatData> list = new LinkedList<ChatData>();
		int size = payload.getInt();
		for (int i=0; i<size; i++) {
			ChatData element = new ChatData();
			element.deserialize(payload);
			list.add(element);
		}
		return list;
	}
}