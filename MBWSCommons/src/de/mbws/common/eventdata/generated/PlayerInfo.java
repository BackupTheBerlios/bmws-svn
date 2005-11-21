/** Generated class. Do not change !!! **/
package de.mbws.common.eventdata.generated;

import de.mbws.common.eventdata.AbstractEventData;
import java.util.*;
import java.nio.ByteBuffer;

public class PlayerInfo extends AbstractEventData { 
	private WorldObject object;
	private int visualappearance;


	public WorldObject getObject() {
		return object;
	}

	public void setObject(WorldObject object) {
		this.object = object;
	} 

	public int getVisualappearance() {
		return visualappearance;
	}

	public void setVisualappearance(int visualappearance) {
		this.visualappearance = visualappearance;
	} 


	public void deserialize(ByteBuffer payload) {
		object = new WorldObject();
		object.deserialize(payload);
		visualappearance = payload.getInt();
	}

	public int serialize(ByteBuffer payload) {
		object.serialize(payload);
		payload.putInt(visualappearance);
		return payload.position();
	}

	public static void serializeList(ByteBuffer payload, List<PlayerInfo> list) {
		if(list==null) return;
		payload.putInt(list.size());
		Iterator<PlayerInfo> it = list.iterator();
		while (it.hasNext()) {
			it.next().serialize(payload);
		}
	}

	public static List<PlayerInfo> deserializeList(ByteBuffer payload) {
		List<PlayerInfo> list = new LinkedList<PlayerInfo>();
		int size = payload.getInt();
		for (int i=0; i<size; i++) {
			PlayerInfo element = new PlayerInfo();
			element.deserialize(payload);
			list.add(element);
		}
		return list;
	}
}