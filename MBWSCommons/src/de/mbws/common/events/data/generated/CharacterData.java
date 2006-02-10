/** Generated class. Do not change !!! **/
package de.mbws.common.events.data.generated;

import de.mbws.common.events.data.AbstractEventData;
import java.util.*;
import java.nio.ByteBuffer;

public class CharacterData extends AbstractEventData { 
	private String characterID;
	private String name;
	private String gender;
	private short age;
	private int race;
	private String locationdescription;
	private IntVector3D location;
	private NetQuaternion heading;
	private CharacterValues normalValues;
	private CharacterStatus status;
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

	public CharacterValues getNormalValues() {
		return normalValues;
	}

	public void setNormalValues(CharacterValues normalValues) {
		this.normalValues = normalValues;
	} 

	public CharacterStatus getStatus() {
		return status;
	}

	public void setStatus(CharacterStatus status) {
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
		gender = readString(payload);
		age = payload.getShort();
		race = payload.getInt();
		locationdescription = readString(payload);
		location = new IntVector3D();
		location.deserialize(payload);
		heading = new NetQuaternion();
		heading.deserialize(payload);
		normalValues = new CharacterValues();
		normalValues.deserialize(payload);
		status = new CharacterStatus();
		status.deserialize(payload);
		visualAppearance = new CharacterVisualAppearance();
		visualAppearance.deserialize(payload);
	}

	public int serialize(ByteBuffer payload) {
		writeString(payload, characterID);
		writeString(payload, name);
		writeString(payload, gender);
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