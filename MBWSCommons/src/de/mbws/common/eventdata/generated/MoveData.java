/** Generated class. Do not change !!! **/
package de.mbws.common.eventdata.generated;

import de.mbws.common.eventdata.AbstractEventData;
import java.util.*;
import java.nio.ByteBuffer;

public class MoveData extends AbstractEventData { 
	private int objectID;
	private byte movementType;
	private IntVector3D location;
	private IntVector3D heading;
	private List<IntVector3D> otherObjects;


	public int getObjectID() {
		return objectID;
	}

	public void setObjectID(int objectID) {
		this.objectID = objectID;
	} 

	public byte getMovementType() {
		return movementType;
	}

	public void setMovementType(byte movementType) {
		this.movementType = movementType;
	} 

	public IntVector3D getLocation() {
		return location;
	}

	public void setLocation(IntVector3D location) {
		this.location = location;
	} 

	public IntVector3D getHeading() {
		return heading;
	}

	public void setHeading(IntVector3D heading) {
		this.heading = heading;
	} 

	public List<IntVector3D> getOtherObjects() {
		return otherObjects;
	}

	public void setOtherObjects(List<IntVector3D> otherObjects) {
		this.otherObjects = otherObjects;
	} 


	public void deserialize(ByteBuffer payload) {
		objectID = payload.getInt();
		movementType = payload.get();
		location = new IntVector3D();
		location.deserialize(payload);
		heading = new IntVector3D();
		heading.deserialize(payload);
		otherObjects = IntVector3D.deserializeList(payload);
	}

	public int serialize(ByteBuffer payload) {
		payload.putInt(objectID);
		payload.put(movementType);
		location.serialize(payload);
		heading.serialize(payload);
		IntVector3D.serializeList(payload, otherObjects);
		return payload.position();
	}

	public static void serializeList(ByteBuffer payload, List<MoveData> list) {
		payload.putInt(list.size());
		Iterator<MoveData> it = list.iterator();
		while (it.hasNext()) {
			it.next().serialize(payload);
		}
	}

	public static List<MoveData> deserializeList(ByteBuffer payload) {
		List<MoveData> list = new LinkedList<MoveData>();
		int size = payload.getInt();
		for (int i=0; i<size; i++) {
			MoveData element = new MoveData();
			element.deserialize(payload);
			list.add(element);
		}
		return list;
	}
}