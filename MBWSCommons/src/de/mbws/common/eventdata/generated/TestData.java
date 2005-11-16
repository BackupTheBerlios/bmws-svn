/** Generated class. Do not change !!! **/
package de.mbws.common.eventdata.generated;

import de.mbws.common.eventdata.AbstractEventData;
import java.util.*;
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

	public static void serializeList(ByteBuffer payload, List<TestData> list) {
		payload.putInt(list.size());
		Iterator<TestData> it = list.iterator();
		while (it.hasNext()) {
			it.next().serialize(payload);
		}
	}

	public static List<TestData> deserializeList(ByteBuffer payload) {
		List<TestData> list = new LinkedList<TestData>();
		int size = payload.getInt();
		for (int i=0; i<size; i++) {
			TestData element = new TestData();
			element.deserialize(payload);
			list.add(element);
		}
		return list;
	}
}