/** Generated class. Do not change !!! **/
package de.mbws.common.eventdata.generated;

import de.mbws.common.eventdata.AbstractEventData;
import java.util.*;
import java.nio.ByteBuffer;

public class MoveData extends AbstractEventData { 
	private String objectID;
	private byte movementType;
	private byte turnType;
	private IntVector3D location;
	private NetQuaternion heading;


	public String getObjectID() {
		return objectID;
	}

	public void setObjectID(String objectID) {
		this.objectID = objectID;
	} 

	public byte getMovementType() {
		return movementType;
	}

	public void setMovementType(byte movementType) {
		this.movementType = movementType;
	} 

	public byte getTurnType() {
		return turnType;
	}

	public void setTurnType(byte turnType) {
		this.turnType = turnType;
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
		movementType = payload.get();
		turnType = payload.get();
		location = new IntVector3D();
		location.deserialize(payload);
		heading = new NetQuaternion();
		heading.deserialize(payload);
	}

	public int serialize(ByteBuffer payload) {
		writeString(payload, objectID);
		payload.put(movementType);
		payload.put(turnType);
		location.serialize(payload);
		heading.serialize(payload);
		return payload.position();
	}

	public static void serializeList(ByteBuffer payload, List<MoveData> list) {
		if(list==null) return;
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