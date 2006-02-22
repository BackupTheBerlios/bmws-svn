/** Generated class. Do not change !!! **/
package de.mbws.common.events.data.generated;

import de.mbws.common.events.data.AbstractEventData;
import java.util.*;
import java.nio.ByteBuffer;

public class PlayerCharacterShortDescription extends AbstractEventData { 
	private String characterID;
	private String name;
	private char gender;
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

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
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
		gender = payload.getChar();
		race = payload.getInt();
		location = readString(payload);
		visualAppearance = new CharacterVisualAppearance();
		visualAppearance.deserialize(payload);
	}

	public int serialize(ByteBuffer payload) {
		writeString(payload, characterID);
		writeString(payload, name);
		payload.putChar(gender);
		payload.putInt(race);
		writeString(payload, location);
		visualAppearance.serialize(payload);
		return payload.position();
	}

	public static void serializeList(ByteBuffer payload, List<PlayerCharacterShortDescription> list) {
		if(list==null) return;
		payload.putInt(list.size());
		Iterator<PlayerCharacterShortDescription> it = list.iterator();
		while (it.hasNext()) {
			it.next().serialize(payload);
		}
	}

	public static List<PlayerCharacterShortDescription> deserializeList(ByteBuffer payload) {
		List<PlayerCharacterShortDescription> list = new LinkedList<PlayerCharacterShortDescription>();
		int size = payload.getInt();
		for (int i=0; i<size; i++) {
			PlayerCharacterShortDescription element = new PlayerCharacterShortDescription();
			element.deserialize(payload);
			list.add(element);
		}
		return list;
	}
}