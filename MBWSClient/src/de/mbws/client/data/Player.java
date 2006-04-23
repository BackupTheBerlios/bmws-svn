package de.mbws.client.data;

import com.jmex.model.animation.KeyframeController;

import de.mbws.client.gui.ingame.CharacterLifeInfo;

public class Player extends MovableObject {

	protected CharacterLifeInfo characterLifeInfo;

	// TODO: What with conditions ?
	public Player(String id) {
		super(id);
	}

	public Player(String id, KeyframeController kc) {
		super(id, kc);
	}

}
