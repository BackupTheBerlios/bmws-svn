/** Generated class. Do not change !!! **/
package de.mbws.common.eventdata.generated;

import de.mbws.common.eventdata.AbstractEventData;
import java.nio.ByteBuffer;

public class TestData extends AbstractEventData { 
	private String test;


	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	} 


	public void deserialize(ByteBuffer payload) {
		test = readString(payload);
	}

	public int serialize(ByteBuffer payload) {
		writeString(payload, test);
		return payload.position();
	}
}