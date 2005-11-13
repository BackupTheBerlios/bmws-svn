package de.mbws.client.data;

public class MovableObject extends GameObject {

	public MovableObject(int id) {
		super(id);
	}

	private int moveStatus;
	private int turnStatus;
	private int movespeed;
	private boolean alive;
	private long timeOfDeath;
	
	public boolean isAlive() {
		return alive;
	}
	
	public void setAlive(boolean live) {
		alive = live;
		if (live==false) {
			timeOfDeath = System.currentTimeMillis();
		}
	}
	protected boolean isMovable() {
		return true;
	}
	
	@Override
	protected void update(float f) {
		//TODO: Move depending on status, calculate death and disappear
		if (!alive){
			long timeNow = System.currentTimeMillis();
//			after 30 seconds we are removed from the world
			//TODO: Even if we are "WE" ? Should we then not "respawn" as a "relocation" ?
			if (timeNow-timeOfDeath>30000) {
				ObjectManager.markObjectForDeletion(objectID);
			//after 20 seconds we start to "sink"
			}else if (timeNow-timeOfDeath>20000) {
				model.getLocalTranslation().y -= f*2.0F;
			}
		} else {
			
		}
	}

}
