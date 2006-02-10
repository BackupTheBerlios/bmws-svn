/** Generated class. Do not change !!! **/
package de.mbws.common.events.data.generated;

import de.mbws.common.events.data.AbstractEventData;
import java.util.*;
import java.nio.ByteBuffer;

public class TestData extends AbstractEventData { 
	private String test;
	private List<IntVector3D> otherObjects;


	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	} 

	public List<IntVector3D> getOtherObjects() {
		return otherObjects;
	}

	public void setOtherObjects(List<IntVector3D> otherObjects) {
		this.otherObjects = otherObjects;
	} 


	public void deserialize(ByteBuffer payload) {
		test = readString(payload);
		otherObjects = IntVector3D.deserializeList(payload);
	}

	public int serialize(ByteBuffer payload) {
		writeString(payload, test);
		IntVector3D.serializeList(payload, otherObjects);
		return payload.position();
	}

	public static void serializeList(ByteBuffer payload, List<TestData> list) {
		if(list==null) return;
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