package de.mbws.common.events.data;

import java.nio.ByteBuffer;

public abstract class AbstractEventData {

	public String readString(ByteBuffer payload) {
		int len = payload.getInt();
		byte[] buffer = new byte[len];
		payload.get(buffer);
		String str = new String(buffer);
		return str;
	}

	public void writeString(ByteBuffer payload, String str) {
		payload.putInt(str.length());
		for (int i = 0; i < str.length(); i++) {
			payload.put((byte) str.charAt(i));
		}
	}
	
	public abstract void deserialize(ByteBuffer payload);

	public abstract int serialize(ByteBuffer payload);

}
