/** Generated class. Do not change !!! **/
package de.mbws.common.eventdata.generated;

import de.mbws.common.eventdata.AbstractEventData;
import java.nio.ByteBuffer;

public class CreateObject extends AbstractEventData { 
	private int objectID;
	private int modelID;
	private FloatVector3D location;
	private FloatVector3D heading;


	public int getObjectID() {
		return objectID;
	}

	public void setObjectID(int objectID) {
		this.objectID = objectID;
	} 

	public int getModelID() {
		return modelID;
	}

	public void setModelID(int modelID) {
		this.modelID = modelID;
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
		modelID = payload.getInt();
		location = new FloatVector3D();
		location.deserialize(payload);
		heading = new FloatVector3D();
		heading.deserialize(payload);
	}

	public int serialize(ByteBuffer payload) {
		payload.putInt(objectID);
		payload.putInt(modelID);
		location.serialize(payload);
		heading.serialize(payload);
		return payload.position();
	}
}