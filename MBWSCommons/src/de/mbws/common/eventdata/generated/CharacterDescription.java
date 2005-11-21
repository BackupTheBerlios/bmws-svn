/** Generated class. Do not change !!! **/
package de.mbws.common.eventdata.generated;

import de.mbws.common.eventdata.AbstractEventData;
import java.util.*;
import java.nio.ByteBuffer;

public class CharacterDescription extends AbstractEventData { 
	private String name;
	private String characterClass;
	private String level;
	private String location;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	} 

	public String getCharacterClass() {
		return characterClass;
	}

	public void setCharacterClass(String characterClass) {
		this.characterClass = characterClass;
	} 

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	} 

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	} 


	public void deserialize(ByteBuffer payload) {
		name = readString(payload);
		characterClass = readString(payload);
		level = readString(payload);
		location = readString(payload);
	}

	public int serialize(ByteBuffer payload) {
		writeString(payload, name);
		writeString(payload, characterClass);
		writeString(payload, level);
		writeString(payload, location);
		return payload.position();
	}

	public static void serializeList(ByteBuffer payload, List<CharacterDescription> list) {
		if(list==null) return;
		payload.putInt(list.size());
		Iterator<CharacterDescription> it = list.iterator();
		while (it.hasNext()) {
			it.next().serialize(payload);
		}
	}

	public static List<CharacterDescription> deserializeList(ByteBuffer payload) {
		List<CharacterDescription> list = new LinkedList<CharacterDescription>();
		int size = payload.getInt();
		for (int i=0; i<size; i++) {
			CharacterDescription element = new CharacterDescription();
			element.deserialize(payload);
			list.add(element);
		}
		return list;
	}
}