/** Generated class. Do not change !!! **/
package de.mbws.common.events.data.generated;

import de.mbws.common.events.data.AbstractEventData;
import java.util.*;
import java.nio.ByteBuffer;

public class PlayerStatus extends AbstractEventData { 
	private PlayerValues currentValues;
	private String charstatus;
	private String pvp;
	private String gamestatus;
	private IntVector3D location;
	private NetQuaternion heading;
	private int freexp;


	public PlayerValues getCurrentValues() {
		return currentValues;
	}

	public void setCurrentValues(PlayerValues currentValues) {
		this.currentValues = currentValues;
	} 

	public String getCharstatus() {
		return charstatus;
	}

	public void setCharstatus(String charstatus) {
		this.charstatus = charstatus;
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

	public int getFreexp() {
		return freexp;
	}

	public void setFreexp(int freexp) {
		this.freexp = freexp;
	} 


	public void deserialize(ByteBuffer payload) {
		currentValues = new PlayerValues();
		currentValues.deserialize(payload);
		charstatus = readString(payload);
		pvp = readString(payload);
		gamestatus = readString(payload);
		location = new IntVector3D();
		location.deserialize(payload);
		heading = new NetQuaternion();
		heading.deserialize(payload);
		freexp = payload.getInt();
	}

	public int serialize(ByteBuffer payload) {
		currentValues.serialize(payload);
		writeString(payload, charstatus);
		writeString(payload, pvp);
		writeString(payload, gamestatus);
		location.serialize(payload);
		heading.serialize(payload);
		payload.putInt(freexp);
		return payload.position();
	}

	public static void serializeList(ByteBuffer payload, List<PlayerStatus> list) {
		if(list==null) return;
		payload.putInt(list.size());
		Iterator<PlayerStatus> it = list.iterator();
		while (it.hasNext()) {
			it.next().serialize(payload);
		}
	}

	public static List<PlayerStatus> deserializeList(ByteBuffer payload) {
		List<PlayerStatus> list = new LinkedList<PlayerStatus>();
		int size = payload.getInt();
		for (int i=0; i<size; i++) {
			PlayerStatus element = new PlayerStatus();
			element.deserialize(payload);
			list.add(element);
		}
		return list;
	}
}