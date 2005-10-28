/** Generated class. Do not change !!! **/
package de.mbws.common.eventdata.generated;

import de.mbws.common.eventdata.AbstractEventData;
import java.nio.ByteBuffer;

public class PlayerInfo extends AbstractEventData { 
	private UpdateLocation location;
	private int visualappearance;


	public UpdateLocation getLocation() {
		return location;
	}

	public void setLocation(UpdateLocation location) {
		this.location = location;
	} 

	public int getVisualappearance() {
		return visualappearance;
	}

	public void setVisualappearance(int visualappearance) {
		this.visualappearance = visualappearance;
	} 


	public void deserialize(ByteBuffer payload) {
		location = new UpdateLocation();
		location.deserialize(payload);
		visualappearance = payload.getInt();
	}

	public int serialize(ByteBuffer payload) {
		location.serialize(payload);
		payload.putInt(visualappearance);
		return payload.position();
	}
}