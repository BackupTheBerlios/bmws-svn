/** Generated class. Do not change !!! **/
package de.mbws.common.eventdata.generated;

import de.mbws.common.eventdata.AbstractEventData;
import java.nio.ByteBuffer;

public class MoveData extends AbstractEventData { 
	private int objectID;
	private byte movementType;
	private FloatVector3D location;
	private FloatVector3D heading;


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

	public FloatVector3D getLocation() {
		return location;
	}

	public void setLocation(FloatVector3D location) {
		this.location = location;
	} 

	public FloatVector3D getHeading() {
		return heading;
	}

	public void setHeading(FloatVector3D heading) {
		this.heading = heading;
	} 


	public void deserialize(ByteBuffer payload) {
		objectID = payload.getInt();
		movementType = payload.get();
		location = new FloatVector3D();
		location.deserialize(payload);
		heading = new FloatVector3D();
		heading.deserialize(payload);
	}

	public int serialize(ByteBuffer payload) {
		payload.putInt(objectID);
		payload.put(movementType);
		location.serialize(payload);
		heading.serialize(payload);
		return payload.position();
	}
}