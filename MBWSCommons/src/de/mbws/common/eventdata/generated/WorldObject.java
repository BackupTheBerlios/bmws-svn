/** Generated class. Do not change !!! **/
package de.mbws.common.eventdata.generated;

import de.mbws.common.eventdata.AbstractEventData;
import java.nio.ByteBuffer;

public class WorldObject extends AbstractEventData { 
	private int objectID;
	private int modelID;
	private IntVector3D location;
	private IntVector3D heading;


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


	public void deserialize(ByteBuffer payload) {
		objectID = payload.getInt();
		modelID = payload.getInt();
		location = new IntVector3D();
		location.deserialize(payload);
		heading = new IntVector3D();
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