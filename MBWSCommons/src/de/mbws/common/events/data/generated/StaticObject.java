/** Generated class. Do not change !!! **/
package de.mbws.common.events.data.generated;

import de.mbws.common.events.data.AbstractEventData;
import java.util.*;
import java.nio.ByteBuffer;

public class StaticObject extends AbstractEventData { 
	private String objectID;
	private int modelID;
	private IntVector3D location;
	private NetQuaternion heading;


	public String getObjectID() {
		return objectID;
	}

	public void setObjectID(String objectID) {
		this.objectID = objectID;
	} 

	public int getModelID() {
		return modelID;
	}

	public void setModelID(int modelID) {
		this.modelID = modelID;
	} 

	public IntVector3D getLocation() {
		return location;
	}

	public void setLocation(IntVector3D location) {
		this.location = location;
	} 

	public NetQuaternion getHeading() {
		return heading;
	}

	public void setHeading(NetQuaternion heading) {
		this.heading = heading;
	} 


	public void deserialize(ByteBuffer payload) {
		objectID = readString(payload);
		modelID = payload.getInt();
		location = new IntVector3D();
		location.deserialize(payload);
		heading = new NetQuaternion();
		heading.deserialize(payload);
	}

	public int serialize(ByteBuffer payload) {
		writeString(payload, objectID);
		payload.putInt(modelID);
		location.serialize(payload);
		heading.serialize(payload);
		return payload.position();
	}

	public static void serializeList(ByteBuffer payload, List<StaticObject> list) {
		if(list==null) return;
		payload.putInt(list.size());
		Iterator<StaticObject> it = list.iterator();
		while (it.hasNext()) {
			it.next().serialize(payload);
		}
	}

	public static List<StaticObject> deserializeList(ByteBuffer payload) {
		List<StaticObject> list = new LinkedList<StaticObject>();
		int size = payload.getInt();
		for (int i=0; i<size; i++) {
			StaticObject element = new StaticObject();
			element.deserialize(payload);
			list.add(element);
		}
		return list;
	}
}