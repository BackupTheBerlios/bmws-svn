/** Generated class. Do not change !!! **/
package de.mbws.common.eventdata.generated;

import java.nio.ByteBuffer;

import de.mbws.common.eventdata.AbstractEventData;

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
}