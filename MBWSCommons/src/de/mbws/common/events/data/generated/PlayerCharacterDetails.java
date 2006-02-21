/** Generated class. Do not change !!! **/
package de.mbws.common.events.data.generated;

import de.mbws.common.events.data.AbstractEventData;
import java.util.*;
import java.nio.ByteBuffer;

public class PlayerCharacterDetails extends AbstractEventData { 
	private PlayerCharacterShortDescription description;
	private IntVector3D location;
	private NetQuaternion heading;


	public PlayerCharacterShortDescription getDescription() {
		return description;
	}

	public void setDescription(PlayerCharacterShortDescription description) {
		this.description = description;
	} 

	public IntVector3D getLocation() {
		return location;
	}

	public void setLocation(IntVector3D location) {
		this.location = location;
	} 

	public NetQuaternion getHeading() {
		return heading;
	}

	public void setHeading(NetQuaternion heading) {
		this.heading = heading;
	} 


	public void deserialize(ByteBuffer payload) {
		description = new PlayerCharacterShortDescription();
		description.deserialize(payload);
		location = new IntVector3D();
		location.deserialize(payload);
		heading = new NetQuaternion();
		heading.deserialize(payload);
	}

	public int serialize(ByteBuffer payload) {
		description.serialize(payload);
		location.serialize(payload);
		heading.serialize(payload);
		return payload.position();
	}

	public static void serializeList(ByteBuffer payload, List<PlayerCharacterDetails> list) {
		if(list==null) return;
		payload.putInt(list.size());
		Iterator<PlayerCharacterDetails> it = list.iterator();
		while (it.hasNext()) {
			it.next().serialize(payload);
		}
	}

	public static List<PlayerCharacterDetails> deserializeList(ByteBuffer payload) {
		List<PlayerCharacterDetails> list = new LinkedList<PlayerCharacterDetails>();
		int size = payload.getInt();
		for (int i=0; i<size; i++) {
			PlayerCharacterDetails element = new PlayerCharacterDetails();
			element.deserialize(payload);
			list.add(element);
		}
		return list;
	}
}