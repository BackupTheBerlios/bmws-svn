/** Generated class. Do not change !!! **/
package de.mbws.common.events.data.generated;

import de.mbws.common.events.data.AbstractEventData;
import java.util.*;
import java.nio.ByteBuffer;

public class CreatureData extends AbstractEventData { 
	private String creatureID;
	private String name;
	private IntVector3D location;
	private NetQuaternion heading;
	private int maxHealth;
	private int currentHealth;
	private int predefinedModel;


	public String getCreatureID() {
		return creatureID;
	}

	public void setCreatureID(String creatureID) {
		this.creatureID = creatureID;
	} 

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public int getPredefinedModel() {
		return predefinedModel;
	}

	public void setPredefinedModel(int predefinedModel) {
		this.predefinedModel = predefinedModel;
	} 


	public void deserialize(ByteBuffer payload) {
		creatureID = readString(payload);
		name = readString(payload);
		location = new IntVector3D();
		location.deserialize(payload);
		heading = new NetQuaternion();
		heading.deserialize(payload);
		maxHealth = payload.getInt();
		currentHealth = payload.getInt();
		predefinedModel = payload.getInt();
	}

	public int serialize(ByteBuffer payload) {
		writeString(payload, creatureID);
		writeString(payload, name);
		location.serialize(payload);
		heading.serialize(payload);
		payload.putInt(maxHealth);
		payload.putInt(currentHealth);
		payload.putInt(predefinedModel);
		return payload.position();
	}

	public static void serializeList(ByteBuffer payload, List<CreatureData> list) {
		if(list==null) return;
		payload.putInt(list.size());
		Iterator<CreatureData> it = list.iterator();
		while (it.hasNext()) {
			it.next().serialize(payload);
		}
	}

	public static List<CreatureData> deserializeList(ByteBuffer payload) {
		List<CreatureData> list = new LinkedList<CreatureData>();
		int size = payload.getInt();
		for (int i=0; i<size; i++) {
			CreatureData element = new CreatureData();
			element.deserialize(payload);
			list.add(element);
		}
		return list;
	}
}