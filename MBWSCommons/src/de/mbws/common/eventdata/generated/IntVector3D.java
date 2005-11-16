/** Generated class. Do not change !!! **/
package de.mbws.common.eventdata.generated;

import de.mbws.common.eventdata.AbstractEventData;
import java.util.*;
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

	public static void serializeList(ByteBuffer payload, List<IntVector3D> list) {
		payload.putInt(list.size());
		Iterator<IntVector3D> it = list.iterator();
		while (it.hasNext()) {
			it.next().serialize(payload);
		}
	}

	public static List<IntVector3D> deserializeList(ByteBuffer payload) {
		List<IntVector3D> list = new LinkedList<IntVector3D>();
		int size = payload.getInt();
		for (int i=0; i<size; i++) {
			IntVector3D element = new IntVector3D();
			element.deserialize(payload);
			list.add(element);
		}
		return list;
	}
}