package de.mbws.client.data;

import com.jme.scene.Node;

import de.mbws.common.eventdata.generated.WorldObject;

public class ObjectNode extends Node {

	private WorldObject worldObject;
	
	public WorldObject getWorldObject() {
		return worldObject;
	}

	public void setWorldObject(WorldObject worldObject) {
		this.worldObject = worldObject;
	}

	public ObjectNode() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ObjectNode(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

}
