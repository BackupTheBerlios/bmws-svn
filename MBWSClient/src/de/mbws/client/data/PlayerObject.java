package de.mbws.client.data;

import com.jmex.model.animation.KeyframeController;

import de.mbws.client.gui.ingame.CharacterLifeInfo;

public class PlayerObject extends MovableObject {

	protected CharacterLifeInfo characterLifeInfo;

	// TODO: What with conditions ?
	public PlayerObject(String id) {
		super(id);
	}

	public PlayerObject(String id, KeyframeController kc) {
		super(id, kc);
	}

}
