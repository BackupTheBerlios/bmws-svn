/** Generated class. Do not change !!! **/
package de.mbws.common.events.data.generated;

import de.mbws.common.events.data.AbstractEventData;
import java.util.*;
import java.nio.ByteBuffer;

public class PlayerCharacterStatus extends AbstractEventData { 
	private PlayerCharacterAttributes currentAttributes;
	private String pvp;
	private String gamestatus;
	private IntVector3D location;
	private NetQuaternion heading;


	public PlayerCharacterAttributes getCurrentAttributes() {
		return currentAttributes;
	}

	public void setCurrentAttributes(PlayerCharacterAttributes currentAttributes) {
		this.currentAttributes = currentAttributes;
	} 

	public String getPvp() {
		return pvp;
	}

	public void setPvp(String pvp) {
		this.pvp = pvp;
	} 

	public String getGamestatus() {
		return gamestatus;
	}

	public void setGamestatus(String gamestatus) {
		this.gamestatus = gamestatus;
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


	public void deserialize(ByteBuffer payload) {
		currentAttributes = new PlayerCharacterAttributes();
		currentAttributes.deserialize(payload);
		pvp = readString(payload);
		gamestatus = readString(payload);
		location = new IntVector3D();
		location.deserialize(payload);
		heading = new NetQuaternion();
		heading.deserialize(payload);
	}

	public int serialize(ByteBuffer payload) {
		currentAttributes.serialize(payload);
		writeString(payload, pvp);
		writeString(payload, gamestatus);
		location.serialize(payload);
		heading.serialize(payload);
		return payload.position();
	}

	public static void serializeList(ByteBuffer payload, List<PlayerCharacterStatus> list) {
		if(list==null) return;
		payload.putInt(list.size());
		Iterator<PlayerCharacterStatus> it = list.iterator();
		while (it.hasNext()) {
			it.next().serialize(payload);
		}
	}

	public static List<PlayerCharacterStatus> deserializeList(ByteBuffer payload) {
		List<PlayerCharacterStatus> list = new LinkedList<PlayerCharacterStatus>();
		int size = payload.getInt();
		for (int i=0; i<size; i++) {
			PlayerCharacterStatus element = new PlayerCharacterStatus();
			element.deserialize(payload);
			list.add(element);
		}
		return list;
	}
}