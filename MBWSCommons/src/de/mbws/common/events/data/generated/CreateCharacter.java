/** Generated class. Do not change !!! **/
package de.mbws.common.events.data.generated;

import de.mbws.common.events.data.AbstractEventData;
import java.util.*;
import java.nio.ByteBuffer;

public class CreateCharacter extends AbstractEventData { 
	private byte race;
	private String gender;
	private String name;


	public byte getRace() {
		return race;
	}

	public void setRace(byte race) {
		this.race = race;
	} 

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	} 

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	} 


	public void deserialize(ByteBuffer payload) {
		race = payload.get();
		gender = readString(payload);
		name = readString(payload);
	}

	public int serialize(ByteBuffer payload) {
		payload.put(race);
		writeString(payload, gender);
		writeString(payload, name);
		return payload.position();
	}

	public static void serializeList(ByteBuffer payload, List<CreateCharacter> list) {
		if(list==null) return;
		payload.putInt(list.size());
		Iterator<CreateCharacter> it = list.iterator();
		while (it.hasNext()) {
			it.next().serialize(payload);
		}
	}

	public static List<CreateCharacter> deserializeList(ByteBuffer payload) {
		List<CreateCharacter> list = new LinkedList<CreateCharacter>();
		int size = payload.getInt();
		for (int i=0; i<size; i++) {
			CreateCharacter element = new CreateCharacter();
			element.deserialize(payload);
			list.add(element);
		}
		return list;
	}
}