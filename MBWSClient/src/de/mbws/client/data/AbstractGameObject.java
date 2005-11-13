package de.mbws.client.data;

import com.jme.scene.Node;

public abstract class AbstractGameObject  {
	
	protected String objectID;
	protected Node model;

	
	public AbstractGameObject(int id) {
		objectID = Integer.toString(id);
	}

	public Node getModel() {
		return model;
	}

	public void setModel(Node model) {
		this.model = model;
	}

	public String getObjectID() {
		return objectID;
	}

	protected abstract boolean isMovable();
	protected abstract void update(float f);
	
}
