/** Generated class. Do not change !!! **/
package de.mbws.common.eventdata.generated;

import de.mbws.common.eventdata.AbstractEventData;
import java.nio.ByteBuffer;

public class FloatVector3D extends AbstractEventData { 
	private float locationX;
	private float locationY;
	private float locationZ;


	public float getLocationX() {
		return locationX;
	}

	public void setLocationX(float locationX) {
		this.locationX = locationX;
	} 

	public float getLocationY() {
		return locationY;
	}

	public void setLocationY(float locationY) {
		this.locationY = locationY;
	} 

	public float getLocationZ() {
		return locationZ;
	}

	public void setLocationZ(float locationZ) {
		this.locationZ = locationZ;
	} 


	public void deserialize(ByteBuffer payload) {
		locationX = payload.getFloat();
		locationY = payload.getFloat();
		locationZ = payload.getFloat();
	}

	public int serialize(ByteBuffer payload) {
		payload.putFloat(locationX);
		payload.putFloat(locationY);
		payload.putFloat(locationZ);
		return payload.position();
	}
}