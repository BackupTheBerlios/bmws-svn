/** Generated class. Do not change !!! **/
package de.mbws.common.eventdata.generated;

import de.mbws.common.eventdata.AbstractEventData;
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
}