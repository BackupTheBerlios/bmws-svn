package de.mbws.client.data;


public class GameObject extends AbstractGameObject {
	
	
	public GameObject(int id) {
		super(id);
	}

	/**
	 * normal Objects are not movable
	 */
	protected boolean isMovable() {
		return false;
	}

	/**
	 * We dont do anything
	 */
	protected void update(float f) {
	}

	
	

}
