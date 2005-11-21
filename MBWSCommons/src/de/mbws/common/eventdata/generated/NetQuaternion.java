/** Generated class. Do not change !!! **/
package de.mbws.common.eventdata.generated;

import de.mbws.common.eventdata.AbstractEventData;
import java.util.*;
import java.nio.ByteBuffer;

public class NetQuaternion extends AbstractEventData { 
	private int w;
	private int x;
	private int y;
	private int z;


	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	} 

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
		w = payload.getInt();
		x = payload.getInt();
		y = payload.getInt();
		z = payload.getInt();
	}

	public int serialize(ByteBuffer payload) {
		payload.putInt(w);
		payload.putInt(x);
		payload.putInt(y);
		payload.putInt(z);
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