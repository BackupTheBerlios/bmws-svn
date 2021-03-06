/** Generated class. Do not change !!! **/
package de.mbws.common.events.data.generated;

import de.mbws.common.events.data.AbstractEventData;
import java.util.*;
import java.nio.ByteBuffer;

public class PlayerCharacterData extends AbstractEventData { 
	private String characterID;
	private String name;
	private char gender;
	private short age;
	private int race;
	private String locationdescription;
	private IntVector3D location;
	private NetQuaternion heading;
	private PlayerCharacterAttributes normalValues;
	private PlayerCharacterStatus status;
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

	public short getAge() {
		return age;
	}

	public void setAge(short age) {
		this.age = age;
	} 

	public int getRace() {
		return race;
	}

	public void setRace(int race) {
		this.race = race;
	} 

	public String getLocationdescription() {
		return locationdescription;
	}

	public void setLocationdescription(String locationdescription) {
		this.locationdescription = locationdescription;
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

	public PlayerCharacterAttributes getNormalValues() {
		return normalValues;
	}

	public void setNormalValues(PlayerCharacterAttributes normalValues) {
		this.normalValues = normalValues;
	} 

	public PlayerCharacterStatus getStatus() {
		return status;
	}

	public void setStatus(PlayerCharacterStatus status) {
		this.status = status;
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
		age = payload.getShort();
		race = payload.getInt();
		locationdescription = readString(payload);
		location = new IntVector3D();
		location.deserialize(payload);
		heading = new NetQuaternion();
		heading.deserialize(payload);
		normalValues = new PlayerCharacterAttributes();
		normalValues.deserialize(payload);
		status = new PlayerCharacterStatus();
		status.deserialize(payload);
		visualAppearance = new CharacterVisualAppearance();
		visualAppearance.deserialize(payload);
	}

	public int serialize(ByteBuffer payload) {
		writeString(payload, characterID);
		writeString(payload, name);
		payload.putChar(gender);
		payload.putShort(age);
		payload.putInt(race);
		writeString(payload, locationdescription);
		location.serialize(payload);
		heading.serialize(payload);
		normalValues.serialize(payload);
		status.serialize(payload);
		visualAppearance.serialize(payload);
		return payload.position();
	}

	public static void serializeList(ByteBuffer payload, List<PlayerCharacterData> list) {
		if(list==null) return;
		payload.putInt(list.size());
		Iterator<PlayerCharacterData> it = list.iterator();
		while (it.hasNext()) {
			it.next().serialize(payload);
		}
	}

	public static List<PlayerCharacterData> deserializeList(ByteBuffer payload) {
		List<PlayerCharacterData> list = new LinkedList<PlayerCharacterData>();
		int size = payload.getInt();
		for (int i=0; i<size; i++) {
			PlayerCharacterData element = new PlayerCharacterData();
			element.deserialize(payload);
			list.add(element);
		}
		return list;
	}
}