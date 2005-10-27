/** Generated class. Do not change !!! **/
package de.mbws.common.eventdata.generated;

import de.mbws.common.eventdata.AbstractEventData;
import java.nio.ByteBuffer;

public class UpdateLocation extends AbstractEventData { 
	private float startLocationX;
	private float startLocationY;
	private float startLocationZ;


	public float getStartLocationX() {
		return startLocationX;
	}

	public void setStartLocationX(float startLocationX) {
		this.startLocationX = startLocationX;
	} 

	public float getStartLocationY() {
		return startLocationY;
	}

	public void setStartLocationY(float startLocationY) {
		this.startLocationY = startLocationY;
	} 

	public float getStartLocationZ() {
		return startLocationZ;
	}

	public void setStartLocationZ(float startLocationZ) {
		this.startLocationZ = startLocationZ;
	} 


	public void deserialize(ByteBuffer payload) {
		startLocationX = payload.getFloat();
		startLocationY = payload.getFloat();
		startLocationZ = payload.getFloat();
	}

	public int serialize(ByteBuffer payload) {
		payload.putFloat(startLocationX);
		payload.putFloat(startLocationY);
		payload.putFloat(startLocationZ);
		return payload.position();
	}
}