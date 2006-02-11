/** Generated class. Do not change !!! **/
package de.mbws.common.events.data.generated;

import de.mbws.common.events.data.AbstractEventData;
import java.util.*;
import java.nio.ByteBuffer;

public class NetQuaternion extends AbstractEventData { 
	private float w;
	private float x;
	private float y;
	private float z;


	public float getW() {
		return w;
	}

	public void setW(float w) {
		this.w = w;
	} 

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	} 

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	} 

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	} 


	public void deserialize(ByteBuffer payload) {
		w = payload.getFloat();
		x = payload.getFloat();
		y = payload.getFloat();
		z = payload.getFloat();
	}

	public int serialize(ByteBuffer payload) {
		payload.putFloat(w);
		payload.putFloat(x);
		payload.putFloat(y);
		payload.putFloat(z);
		return payload.position();
	}

	public static void serializeList(ByteBuffer payload, List<NetQuaternion> list) {
		if(list==null) return;
		payload.putInt(list.size());
		Iterator<NetQuaternion> it = list.iterator();
		while (it.hasNext()) {
			it.next().serialize(payload);
		}
	}

	public static List<NetQuaternion> deserializeList(ByteBuffer payload) {
		List<NetQuaternion> list = new LinkedList<NetQuaternion>();
		int size = payload.getInt();
		for (int i=0; i<size; i++) {
			NetQuaternion element = new NetQuaternion();
			element.deserialize(payload);
			list.add(element);
		}
		return list;
	}
}