/** Generated class. Do not change !!! **/
package de.mbws.common.events.data.generated;

import de.mbws.common.events.data.AbstractEventData;
import java.util.*;
import java.nio.ByteBuffer;

public class CharacterData extends AbstractEventData { 
	private CharacterVisualAppearance visualAppearance;
	private String characterID;
	private String name;
	private String gender;
	private int race;
	private IntVector3D location;
	private NetQuaternion heading;
	private byte pvp;
	private int woundLevel;


	public CharacterVisualAppearance getVisualAppearance() {
		return visualAppearance;
	}

	public void setVisualAppearance(CharacterVisualAppearance visualAppearance) {
		this.visualAppearance = visualAppearance;
	} 

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

	public byte getPvp() {
		return pvp;
	}

	public void setPvp(byte pvp) {
		this.pvp = pvp;
	} 

	public int getWoundLevel() {
		return woundLevel;
	}

	public void setWoundLevel(int woundLevel) {
		this.woundLevel = woundLevel;
	} 


	public void deserialize(ByteBuffer payload) {
		visualAppearance = new CharacterVisualAppearance();
		visualAppearance.deserialize(payload);
		characterID = readString(payload);
		name = readString(payload);
		gender = readString(payload);
		race = payload.getInt();
		location = new IntVector3D();
		location.deserialize(payload);
		heading = new NetQuaternion();
		heading.deserialize(payload);
		pvp = payload.get();
		woundLevel = payload.getInt();
	}

	public int serialize(ByteBuffer payload) {
		visualAppearance.serialize(payload);
		writeString(payload, characterID);
		writeString(payload, name);
		writeString(payload, gender);
		payload.putInt(race);
		location.serialize(payload);
		heading.serialize(payload);
		payload.put(pvp);
		payload.putInt(woundLevel);
		return payload.position();
	}

	public static void serializeList(ByteBuffer payload, List<CharacterData> list) {
		if(list==null) return;
		payload.putInt(list.size());
		Iterator<CharacterData> it = list.iterator();
		while (it.hasNext()) {
			it.next().serialize(payload);
		}
	}

	public static List<CharacterData> deserializeList(ByteBuffer payload) {
		List<CharacterData> list = new LinkedList<CharacterData>();
		int size = payload.getInt();
		for (int i=0; i<size; i++) {
			CharacterData element = new CharacterData();
			element.deserialize(payload);
			list.add(element);
		}
		return list;
	}
}