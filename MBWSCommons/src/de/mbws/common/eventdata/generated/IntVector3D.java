/** Generated class. Do not change !!! **/
package de.mbws.common.eventdata.generated;

import de.mbws.common.eventdata.AbstractEventData;
import java.nio.ByteBuffer;

public class IntVector3D extends AbstractEventData { 
	private int locationX;
	private int locationY;
	private int locationZ;


	public int getLocationX() {
		return locationX;
	}

	public void setLocationX(int locationX) {
		this.locationX = locationX;
	} 

	public int getLocationY() {
		return locationY;
	}

	public void setLocationY(int locationY) {
		this.locationY = locationY;
	} 

	public int getLocationZ() {
		return locationZ;
	}

	public void setLocationZ(int locationZ) {
		this.locationZ = locationZ;
	} 


	public void deserialize(ByteBuffer payload) {
		locationX = payload.getInt();
		locationY = payload.getInt();
		locationZ = payload.getInt();
	}

	public int serialize(ByteBuffer payload) {
		payload.putInt(locationX);
		payload.putInt(locationY);
		payload.putInt(locationZ);
		return payload.position();
	}
}