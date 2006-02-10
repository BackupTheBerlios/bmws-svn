/** Generated class. Do not change !!! **/
package de.mbws.common.events.data.generated;

import de.mbws.common.events.data.AbstractEventData;
import java.util.*;
import java.nio.ByteBuffer;

public class CharactersOfPlayer extends AbstractEventData { 
	private List<CharacterData> charactersOfPlayer;


	public List<CharacterData> getCharactersOfPlayer() {
		return charactersOfPlayer;
	}

	public void setCharactersOfPlayer(List<CharacterData> charactersOfPlayer) {
		this.charactersOfPlayer = charactersOfPlayer;
	} 


	public void deserialize(ByteBuffer payload) {
		charactersOfPlayer = CharacterData.deserializeList(payload);
	}

	public int serialize(ByteBuffer payload) {
		CharacterData.serializeList(payload, charactersOfPlayer);
		return payload.position();
	}

	public static void serializeList(ByteBuffer payload, List<CharactersOfPlayer> list) {
		if(list==null) return;
		payload.putInt(list.size());
		Iterator<CharactersOfPlayer> it = list.iterator();
		while (it.hasNext()) {
			it.next().serialize(payload);
		}
	}

	public static List<CharactersOfPlayer> deserializeList(ByteBuffer payload) {
		List<CharactersOfPlayer> list = new LinkedList<CharactersOfPlayer>();
		int size = payload.getInt();
		for (int i=0; i<size; i++) {
			CharactersOfPlayer element = new CharactersOfPlayer();
			element.deserialize(payload);
			list.add(element);
		}
		return list;
	}
}