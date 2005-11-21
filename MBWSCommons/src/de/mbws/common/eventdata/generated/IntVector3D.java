/** Generated class. Do not change !!! **/
package de.mbws.common.eventdata.generated;

import de.mbws.common.eventdata.AbstractEventData;
import java.util.*;
import java.nio.ByteBuffer;

public class IntVector3D extends AbstractEventData { 
	private int x;
	private int y;
	private int z;


	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	} 

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	} 

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	} 


	public void deserialize(ByteBuffer payload) {
		x = payload.getInt();
		y = payload.getInt();
		z = payload.getInt();
	}

	public int serialize(ByteBuffer payload) {
		payload.putInt(x);
		payload.putInt(y);
		payload.putInt(z);
		return payload.position();
	}

	public static void serializeList(ByteBuffer payload, List<IntVector3D> list) {
		if(list==null) return;
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