/** Generated class. Do not change !!! **/
package de.mbws.common.events.data.generated;

import de.mbws.common.events.data.AbstractEventData;
import java.util.*;
import java.nio.ByteBuffer;

public class PlayerValues extends AbstractEventData { 
	private int health;
	private int mana;
	private int stamina;
	private int strength;
	private int intelligence;
	private int dexterity;
	private int constitution;


	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	} 

	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	} 

	public int getStamina() {
		return stamina;
	}

	public void setStamina(int stamina) {
		this.stamina = stamina;
	} 

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	} 

	public int getIntelligence() {
		return intelligence;
	}

	public void setIntelligence(int intelligence) {
		this.intelligence = intelligence;
	} 

	public int getDexterity() {
		return dexterity;
	}

	public void setDexterity(int dexterity) {
		this.dexterity = dexterity;
	} 

	public int getConstitution() {
		return constitution;
	}

	public void setConstitution(int constitution) {
		this.constitution = constitution;
	} 


	public void deserialize(ByteBuffer payload) {
		health = payload.getInt();
		mana = payload.getInt();
		stamina = payload.getInt();
		strength = payload.getInt();
		intelligence = payload.getInt();
		dexterity = payload.getInt();
		constitution = payload.getInt();
	}

	public int serialize(ByteBuffer payload) {
		payload.putInt(health);
		payload.putInt(mana);
		payload.putInt(stamina);
		payload.putInt(strength);
		payload.putInt(intelligence);
		payload.putInt(dexterity);
		payload.putInt(constitution);
		return payload.position();
	}

	public static void serializeList(ByteBuffer payload, List<PlayerValues> list) {
		if(list==null) return;
		payload.putInt(list.size());
		Iterator<PlayerValues> it = list.iterator();
		while (it.hasNext()) {
			it.next().serialize(payload);
		}
	}

	public static List<PlayerValues> deserializeList(ByteBuffer payload) {
		List<PlayerValues> list = new LinkedList<PlayerValues>();
		int size = payload.getInt();
		for (int i=0; i<size; i++) {
			PlayerValues element = new PlayerValues();
			element.deserialize(payload);
			list.add(element);
		}
		return list;
	}
}