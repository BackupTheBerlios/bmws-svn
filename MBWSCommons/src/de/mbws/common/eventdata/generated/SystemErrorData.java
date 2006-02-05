/** Generated class. Do not change !!! **/
package de.mbws.common.eventdata.generated;

import de.mbws.common.eventdata.AbstractEventData;
import java.util.*;
import java.nio.ByteBuffer;

public class SystemErrorData extends AbstractEventData { 
	private String reason;


	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	} 


	public void deserialize(ByteBuffer payload) {
		reason = readString(payload);
	}

	public int serialize(ByteBuffer payload) {
		writeString(payload, reason);
		return payload.position();
	}

	public static void serializeList(ByteBuffer payload, List<SystemErrorData> list) {
		if(list==null) return;
		payload.putInt(list.size());
		Iterator<SystemErrorData> it = list.iterator();
		while (it.hasNext()) {
			it.next().serialize(payload);
		}
	}

	public static List<SystemErrorData> deserializeList(ByteBuffer payload) {
		List<SystemErrorData> list = new LinkedList<SystemErrorData>();
		int size = payload.getInt();
		for (int i=0; i<size; i++) {
			SystemErrorData element = new SystemErrorData();
			element.deserialize(payload);
			list.add(element);
		}
		return list;
	}
}