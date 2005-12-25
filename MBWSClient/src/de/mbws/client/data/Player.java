package de.mbws.client.data;

import com.jmex.model.animation.KeyframeController;

public class Player extends MovableObject {

	
	public Player(String id) {
		super(id);
	}
	
	public Player(String id, KeyframeController kc) {
		super(id,kc);
	}
	

}
