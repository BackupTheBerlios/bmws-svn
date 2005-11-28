/** Generated class. Do not change !!! **/
package de.mbws.common.eventdata.generated;

import de.mbws.common.eventdata.AbstractEventData;
import java.util.*;
import java.nio.ByteBuffer;

public class CharacterDetails extends AbstractEventData { 
	private CharacterShortDescription description;
	private IntVector3D location;
	private NetQuaternion heading;


	public CharacterShortDescription getDescription() {
		return description;
	}

	public void setDescription(CharacterShortDescription description) {
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
		description = new CharacterShortDescription();
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

	public static void serializeList(ByteBuffer payload, List<CharacterDetails> list) {
		if(list==null) return;
		payload.putInt(list.size());
		Iterator<CharacterDetails> it = list.iterator();
		while (it.hasNext()) {
			it.next().serialize(payload);
		}
	}

	public static List<CharacterDetails> deserializeList(ByteBuffer payload) {
		List<CharacterDetails> list = new LinkedList<CharacterDetails>();
		int size = payload.getInt();
		for (int i=0; i<size; i++) {
			CharacterDetails element = new CharacterDetails();
			element.deserialize(payload);
			list.add(element);
		}
		return list;
	}
}