/** Generated class. Do not change !!! **/
package de.mbws.common.eventdata.generated;

import de.mbws.common.eventdata.AbstractEventData;
import java.util.*;
import java.nio.ByteBuffer;

public class WorldObject extends AbstractEventData { 
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

	public static void serializeList(ByteBuffer payload, List<WorldObject> list) {
		if(list==null) return;
		payload.putInt(list.size());
		Iterator<WorldObject> it = list.iterator();
		while (it.hasNext()) {
			it.next().serialize(payload);
		}
	}

	public static List<WorldObject> deserializeList(ByteBuffer payload) {
		List<WorldObject> list = new LinkedList<WorldObject>();
		int size = payload.getInt();
		for (int i=0; i<size; i++) {
			WorldObject element = new WorldObject();
			element.deserialize(payload);
			list.add(element);
		}
		return list;
	}
}