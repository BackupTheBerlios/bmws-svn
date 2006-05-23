package de.mbws.client.data;

import com.jmex.model.animation.KeyframeController;

public class NPCObject extends MovableObject {

	// protected CharacterLifeInfo characterLifeInfo;

	// TODO: What with conditions ?
	public NPCObject(String id) {
		super(id);
	}

	public NPCObject(String id, KeyframeController kc) {
		super(id, kc);
	}

}
