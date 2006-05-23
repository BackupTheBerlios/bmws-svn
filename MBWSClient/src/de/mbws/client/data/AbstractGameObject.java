package de.mbws.client.data;

import com.jme.scene.Node;

public abstract class AbstractGameObject  extends Node{
	//private boolean selected = false;
	protected String objectID;
	//protected Node model;

	
	public AbstractGameObject(String id) {
		super(id);
		objectID = id;
	}

//	public Node getModel() {
//		return model;
//	}
//
//	public void setModel(Node model) {
//		this.model = model;
//	}

	public String getObjectID() {
		return objectID;
	}
//	public boolean isSelected() {
//		return selected;
//	}
//
//	public void setSelected(boolean selected) {
//		this.selected = selected;
//	}

	protected abstract boolean isMovable();
	protected abstract void update(float f);
	
}
