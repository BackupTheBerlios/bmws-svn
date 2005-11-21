/** Generated class. Do not change !!! **/
package de.mbws.common.eventdata.generated;

import de.mbws.common.eventdata.AbstractEventData;
import java.util.*;
import java.nio.ByteBuffer;

public class CharactersOfPlayer extends AbstractEventData { 
	private List<CharacterShortDescription> descriptions;


	public List<CharacterShortDescription> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(List<CharacterShortDescription> descriptions) {
		this.descriptions = descriptions;
	} 


	public void deserialize(ByteBuffer payload) {
		descriptions = CharacterShortDescription.deserializeList(payload);
	}

	public int serialize(ByteBuffer payload) {
		CharacterShortDescription.serializeList(payload, descriptions);
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