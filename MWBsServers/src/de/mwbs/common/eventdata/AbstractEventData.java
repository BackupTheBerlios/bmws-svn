package de.mwbs.common.eventdata;

public abstract class AbstractEventData {
	protected byte[] payload;
	protected int cursor = 0;
	protected int length = 0;


	public void setPayload(byte[] payload, int offset) {
		this.payload = payload;
		this.cursor = offset;
	}
	
	public void writeInt(int data) {
		payload[cursor] = (byte) (data & 0x00ff);
		payload[cursor + 1] = (byte) (data & 0xff00);
		cursor += 2;
	}

	public int readInt() {
		int ret = payload[cursor] | payload[cursor + 1] << 8;
		cursor += 2;
		return ret;
	}

	public String readString() {
		int len = readInt();
		String str = new String(payload, cursor, len);
		cursor += len;
		return str;
	}

	public void writeString(String str) {
		writeInt(str.length());
		for (int i = 0; i < str.length(); i++) {
			payload[cursor] = (byte) str.charAt(cursor);
			cursor++;
		}
	}
	
	public abstract void deserialize(byte[] payload, int offset);

	public abstract int serialize(byte[] payload, int offset);

}
