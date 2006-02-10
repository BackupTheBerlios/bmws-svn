/** Generated class. Do not change !!! **/
package de.mbws.common.events.data.generated;

import de.mbws.common.events.data.AbstractEventData;
import java.util.*;
import java.nio.ByteBuffer;

public class CharacterShortDescription extends AbstractEventData { 
	private String characterID;
	private String name;
	private String gender;
	private int race;
	private String location;
	private CharacterVisualAppearance visualAppearance;


	public String getCharacterID() {
		return characterID;
	}

	public void setCharacterID(String characterID) {
		this.characterID = characterID;
	} 

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	} 

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	} 

	public int getRace() {
		return race;
	}

	public void setRace(int race) {
		this.race = race;
	} 

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	} 

	public CharacterVisualAppearance getVisualAppearance() {
		return visualAppearance;
	}

	public void setVisualAppearance(CharacterVisualAppearance visualAppearance) {
		this.visualAppearance = visualAppearance;
	} 


	public void deserialize(ByteBuffer payload) {
		characterID = readString(payload);
		name = readString(payload);
		gender = readString(payload);
		race = payload.getInt();
		location = readString(payload);
		visualAppearance = new CharacterVisualAppearance();
		visualAppearance.deserialize(payload);
	}

	public int serialize(ByteBuffer payload) {
		writeString(payload, characterID);
		writeString(payload, name);
		writeString(payload, gender);
		payload.putInt(race);
		writeString(payload, location);
		visualAppearance.serialize(payload);
		return payload.position();
	}

	public static void serializeList(ByteBuffer payload, List<CharacterShortDescription> list) {
		if(list==null) return;
		payload.putInt(list.size());
		Iterator<CharacterShortDescription> it = list.iterator();
		while (it.hasNext()) {
			it.next().serialize(payload);
		}
	}

	public static List<CharacterShortDescription> deserializeList(ByteBuffer payload) {
		List<CharacterShortDescription> list = new LinkedList<CharacterShortDescription>();
		int size = payload.getInt();
		for (int i=0; i<size; i++) {
			CharacterShortDescription element = new CharacterShortDescription();
			element.deserialize(payload);
			list.add(element);
		}
		return list;
	}
}