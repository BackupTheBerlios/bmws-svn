/** Generated class. Do not change !!! **/
package de.mbws.common.events.data.generated;

import de.mbws.common.events.data.AbstractEventData;
import java.util.*;
import java.nio.ByteBuffer;

public class PlayerShortDescription extends AbstractEventData { 
	private String characterID;
	private String name;
	private String gender;
	private int race;
	private String location;
	private VisualAppearance visualAppearance;


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

	public VisualAppearance getVisualAppearance() {
		return visualAppearance;
	}

	public void setVisualAppearance(VisualAppearance visualAppearance) {
		this.visualAppearance = visualAppearance;
	} 


	public void deserialize(ByteBuffer payload) {
		characterID = readString(payload);
		name = readString(payload);
		gender = readString(payload);
		race = payload.getInt();
		location = readString(payload);
		visualAppearance = new VisualAppearance();
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

	public static void serializeList(ByteBuffer payload, List<PlayerShortDescription> list) {
		if(list==null) return;
		payload.putInt(list.size());
		Iterator<PlayerShortDescription> it = list.iterator();
		while (it.hasNext()) {
			it.next().serialize(payload);
		}
	}

	public static List<PlayerShortDescription> deserializeList(ByteBuffer payload) {
		List<PlayerShortDescription> list = new LinkedList<PlayerShortDescription>();
		int size = payload.getInt();
		for (int i=0; i<size; i++) {
			PlayerShortDescription element = new PlayerShortDescription();
			element.deserialize(payload);
			list.add(element);
		}
		return list;
	}
}