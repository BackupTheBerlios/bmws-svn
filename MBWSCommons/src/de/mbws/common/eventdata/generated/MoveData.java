/** Generated class. Do not change !!! **/
package de.mbws.common.eventdata.generated;

import de.mbws.common.eventdata.AbstractEventData;
import java.nio.ByteBuffer;

public class MoveData extends AbstractEventData { 
	private int startMovement;
	private float startLocationX;
	private float startLocationY;
	private float startLocationZ;
	private int movementType;


	public int getStartMovement() {
		return startMovement;
	}

	public void setStartMovement(int startMovement) {
		this.startMovement = startMovement;
	} 

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

	public int getMovementType() {
		return movementType;
	}

	public void setMovementType(int movementType) {
		this.movementType = movementType;
	} 


	public void deserialize(ByteBuffer payload) {
		startMovement = payload.getInt();
		startLocationX = payload.getFloat();
		startLocationY = payload.getFloat();
		startLocationZ = payload.getFloat();
		movementType = payload.getInt();
	}

	public int serialize(ByteBuffer payload) {
		payload.putInt(startMovement);
		payload.putFloat(startLocationX);
		payload.putFloat(startLocationY);
		payload.putFloat(startLocationZ);
		payload.putInt(movementType);
		return payload.position();
	}
}