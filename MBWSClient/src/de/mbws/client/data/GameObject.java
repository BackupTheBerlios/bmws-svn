package de.mbws.client.data;

import com.jme.scene.Node;

public class GameObject  {
	
	private String objectID;
	private Node model;
	

	public GameObject() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GameObject(int id) {
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

}
