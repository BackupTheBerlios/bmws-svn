/** Generated class. Do not change !!! **/
package de.mbws.common.events.data.generated;

import de.mbws.common.events.data.AbstractEventData;
import java.util.*;
import java.nio.ByteBuffer;

public class SystemInformationData extends AbstractEventData { 
	private byte severity;
	private String messageCodeKey;
	private String additionalInfo;


	public byte getSeverity() {
		return severity;
	}

	public void setSeverity(byte severity) {
		this.severity = severity;
	} 

	public String getMessageCodeKey() {
		return messageCodeKey;
	}

	public void setMessageCodeKey(String messageCodeKey) {
		this.messageCodeKey = messageCodeKey;
	} 

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	} 


	public void deserialize(ByteBuffer payload) {
		severity = payload.get();
		messageCodeKey = readString(payload);
		additionalInfo = readString(payload);
	}

	public int serialize(ByteBuffer payload) {
		payload.put(severity);
		writeString(payload, messageCodeKey);
		writeString(payload, additionalInfo);
		return payload.position();
	}

	public static void serializeList(ByteBuffer payload, List<SystemInformationData> list) {
		if(list==null) return;
		payload.putInt(list.size());
		Iterator<SystemInformationData> it = list.iterator();
		while (it.hasNext()) {
			it.next().serialize(payload);
		}
	}

	public static List<SystemInformationData> deserializeList(ByteBuffer payload) {
		List<SystemInformationData> list = new LinkedList<SystemInformationData>();
		int size = payload.getInt();
		for (int i=0; i<size; i++) {
			SystemInformationData element = new SystemInformationData();
			element.deserialize(payload);
			list.add(element);
		}
		return list;
	}
}