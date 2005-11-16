/** Generated class. Do not change !!! **/
package de.mbws.common.eventdata.generated;

import de.mbws.common.eventdata.AbstractEventData;
import java.util.*;
import java.nio.ByteBuffer;

public class AccountErrorData extends AbstractEventData { 
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

	public static void serializeList(ByteBuffer payload, List<AccountErrorData> list) {
		payload.putInt(list.size());
		Iterator<AccountErrorData> it = list.iterator();
		while (it.hasNext()) {
			it.next().serialize(payload);
		}
	}

	public static List<AccountErrorData> deserializeList(ByteBuffer payload) {
		List<AccountErrorData> list = new LinkedList<AccountErrorData>();
		int size = payload.getInt();
		for (int i=0; i<size; i++) {
			AccountErrorData element = new AccountErrorData();
			element.deserialize(payload);
			list.add(element);
		}
		return list;
	}
}