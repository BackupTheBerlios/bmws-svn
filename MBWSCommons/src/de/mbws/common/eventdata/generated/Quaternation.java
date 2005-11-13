/** Generated class. Do not change !!! **/
package de.mbws.common.eventdata.generated;

import de.mbws.common.eventdata.AbstractEventData;
import java.nio.ByteBuffer;

public class Quaternation extends AbstractEventData { 
	private int w;
	private int x;
	private int y;
	private int z;


	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	} 

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	} 

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	} 

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	} 


	public void deserialize(ByteBuffer payload) {
		w = payload.getInt();
		x = payload.getInt();
		y = payload.getInt();
		z = payload.getInt();
	}

	public int serialize(ByteBuffer payload) {
		payload.putInt(w);
		payload.putInt(x);
		payload.putInt(y);
		payload.putInt(z);
		return payload.position();
	}
}