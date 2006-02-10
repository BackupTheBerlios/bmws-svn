/** Generated class. Do not change !!! **/
package de.mbws.common.events.data.generated;

import de.mbws.common.events.data.AbstractEventData;
import java.util.*;
import java.nio.ByteBuffer;

public class CharacterWorldServerInformation extends AbstractEventData { 
	private CharacterSelection character;
	private int sessionId;


	public CharacterSelection getCharacter() {
		return character;
	}

	public void setCharacter(CharacterSelection character) {
		this.character = character;
	} 

	public int getSessionId() {
		return sessionId;
	}

	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	} 


	public void deserialize(ByteBuffer payload) {
		character = new CharacterSelection();
		character.deserialize(payload);
		sessionId = payload.getInt();
	}

	public int serialize(ByteBuffer payload) {
		character.serialize(payload);
		payload.putInt(sessionId);
		return payload.position();
	}

	public static void serializeList(ByteBuffer payload, List<CharacterWorldServerInformation> list) {
		if(list==null) return;
		payload.putInt(list.size());
		Iterator<CharacterWorldServerInformation> it = list.iterator();
		while (it.hasNext()) {
			it.next().serialize(payload);
		}
	}

	public static List<CharacterWorldServerInformation> deserializeList(ByteBuffer payload) {
		List<CharacterWorldServerInformation> list = new LinkedList<CharacterWorldServerInformation>();
		int size = payload.getInt();
		for (int i=0; i<size; i++) {
			CharacterWorldServerInformation element = new CharacterWorldServerInformation();
			element.deserialize(payload);
			list.add(element);
		}
		return list;
	}
}