/** Generated class. Do not change !!! **/
package de.mbws.common.eventdata.generated;

import de.mbws.common.eventdata.AbstractEventData;
import java.nio.ByteBuffer;

public class MoveData extends AbstractEventData { 
	private int playerID;
	private byte movementType;
	private float locationX;
	private float locationY;
	private float locationZ;


	public int getPlayerID() {
		return playerID;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	} 

	public byte getMovementType() {
		return movementType;
	}

	public void setMovementType(byte movementType) {
		this.movementType = movementType;
	} 

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
		playerID = payload.getInt();
		movementType = payload.get();
		locationX = payload.getFloat();
		locationY = payload.getFloat();
		locationZ = payload.getFloat();
	}

	public int serialize(ByteBuffer payload) {
		payload.putInt(playerID);
		payload.put(movementType);
		payload.putFloat(locationX);
		payload.putFloat(locationY);
		payload.putFloat(locationZ);
		return payload.position();
	}
}