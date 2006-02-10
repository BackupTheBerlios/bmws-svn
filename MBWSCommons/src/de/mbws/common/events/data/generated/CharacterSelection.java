/** Generated class. Do not change !!! **/
package de.mbws.common.events.data.generated;

import de.mbws.common.events.data.AbstractEventData;
import java.util.*;
import java.nio.ByteBuffer;

public class CharacterSelection extends AbstractEventData { 
	private String characterID;


	public String getCharacterID() {
		return characterID;
	}

	public void setCharacterID(String characterID) {
		this.characterID = characterID;
	} 


	public void deserialize(ByteBuffer payload) {
		characterID = readString(payload);
	}

	public int serialize(ByteBuffer payload) {
		writeString(payload, characterID);
		return payload.position();
	}

	public static void serializeList(ByteBuffer payload, List<CharacterSelection> list) {
		if(list==null) return;
		payload.putInt(list.size());
		Iterator<CharacterSelection> it = list.iterator();
		while (it.hasNext()) {
			it.next().serialize(payload);
		}
	}

	public static List<CharacterSelection> deserializeList(ByteBuffer payload) {
		List<CharacterSelection> list = new LinkedList<CharacterSelection>();
		int size = payload.getInt();
		for (int i=0; i<size; i++) {
			CharacterSelection element = new CharacterSelection();
			element.deserialize(payload);
			list.add(element);
		}
		return list;
	}
}