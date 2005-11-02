/** Generated class. Do not change !!! **/
package de.mbws.common.eventdata.generated;

import de.mbws.common.eventdata.AbstractEventData;
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
}