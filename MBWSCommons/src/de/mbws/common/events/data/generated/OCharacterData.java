/** Generated class. Do not change !!! **/
package de.mbws.common.events.data.generated;

import de.mbws.common.events.data.AbstractEventData;
import java.util.*;
import java.nio.ByteBuffer;

public class OCharacterData extends AbstractEventData { 
	private CharacterVisualAppearance visualAppearance;
	private String characterID;
	private String name;
	private String gender;
	private int race;
	private IntVector3D location;
	private NetQuaternion heading;
	private int maxHealth;
	private int currentHealth;
	private byte pvp;
	private int predefinedModel;


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

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	} 

	public int getCurrentHealth() {
		return currentHealth;
	}

	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	} 

	public byte getPvp() {
		return pvp;
	}

	public void setPvp(byte pvp) {
		this.pvp = pvp;
	} 

	public int getPredefinedModel() {
		return predefinedModel;
	}

	public void setPredefinedModel(int predefinedModel) {
		this.predefinedModel = predefinedModel;
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
		maxHealth = payload.getInt();
		currentHealth = payload.getInt();
		pvp = payload.get();
		predefinedModel = payload.getInt();
	}

	public int serialize(ByteBuffer payload) {
		visualAppearance.serialize(payload);
		writeString(payload, characterID);
		writeString(payload, name);
		writeString(payload, gender);
		payload.putInt(race);
		location.serialize(payload);
		heading.serialize(payload);
		payload.putInt(maxHealth);
		payload.putInt(currentHealth);
		payload.put(pvp);
		payload.putInt(predefinedModel);
		return payload.position();
	}

	public static void serializeList(ByteBuffer payload, List<OCharacterData> list) {
		if(list==null) return;
		payload.putInt(list.size());
		Iterator<OCharacterData> it = list.iterator();
		while (it.hasNext()) {
			it.next().serialize(payload);
		}
	}

	public static List<OCharacterData> deserializeList(ByteBuffer payload) {
		List<OCharacterData> list = new LinkedList<OCharacterData>();
		int size = payload.getInt();
		for (int i=0; i<size; i++) {
			OCharacterData element = new OCharacterData();
			element.deserialize(payload);
			list.add(element);
		}
		return list;
	}
}