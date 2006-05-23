package de.mbws.client.data;

import com.jmex.model.animation.KeyframeController;

public class PCObject extends MovableObject {

	// protected CharacterLifeInfo characterLifeInfo;

	// TODO: What with conditions ?
	public PCObject(String id) {
		super(id);
	}

	public PCObject(String id, KeyframeController kc) {
		super(id, kc);
	}

}
