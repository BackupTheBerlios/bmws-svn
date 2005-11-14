package de.mbws.client.data;

import com.jme.math.Matrix3f;
import com.jme.math.Vector3f;

import de.mbws.common.Globals;

public class MovableObject extends GameObject {
	
	public MovableObject(int id) {
		super(id);
		moveStatus = Globals.STANDING;
		turnStatus = Globals.NO_TURN;
	}

	protected byte moveStatus;
	protected byte turnStatus;
	protected int movespeed;
	protected int turnspeed;
	protected boolean alive;
	protected long timeOfDeath;
	protected boolean isPlayer = false;

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean live) {
		alive = live;
		if (live == false) {
			timeOfDeath = System.currentTimeMillis();
		}
	}

	protected boolean isMovable() {
		return true;
	}

	@Override
	protected void update(float f) {
		// TODO: Move depending on status, calculate death and disappear
		if (!alive) {
			long timeNow = System.currentTimeMillis();
			// after 30 seconds we are removed from the world
			// TODO: Even if we are "WE" ? Should we then not "respawn" as a
			// "relocation" ?
			if (timeNow - timeOfDeath > 30000) {
				ObjectManager.markObjectForDeletion(objectID);
				// after 20 seconds we start to "sink"
			} else if (timeNow - timeOfDeath > 20000) {
				model.getLocalTranslation().y -= f * 2.0F;
			}
		} else {
			if (!isPlayer) {
				synchronized (model) {
					if (moveStatus == Globals.WALKING || moveStatus == Globals.RUNNING) {
						Vector3f tempVa = new Vector3f();
						Vector3f loc = model.getLocalTranslation();
						loc.addLocal(model.getLocalRotation()
								.getRotationColumn(2, tempVa).multLocal(
										movespeed * f));
						model.setLocalTranslation(loc);
					} else if (moveStatus == Globals.WALKING_BACKWARD) {
						Vector3f tempVa = new Vector3f();
						Vector3f loc = model.getLocalTranslation();
						loc.subtractLocal(model.getLocalRotation()
								.getRotationColumn(2, tempVa).multLocal(
										movespeed * f));
						model.setLocalTranslation(loc);
					}
					if (turnStatus == Globals.TURN_RIGHT || turnStatus == Globals.TURN_LEFT) {
						Vector3f tempVa = new Vector3f();
						Matrix3f incr = new Matrix3f();
						Matrix3f tempMa = new Matrix3f();
						Matrix3f tempMb = new Matrix3f();

						incr.loadIdentity();
						if (turnStatus == Globals.TURN_RIGHT) {
							incr.fromAxisAngle(model.getLocalRotation()
									.getRotationColumn(1, tempVa), -turnspeed
									* f);
						} else {
							incr.fromAxisAngle(model.getLocalRotation()
									.getRotationColumn(1, tempVa), turnspeed
									* f);
						}

						model.getLocalRotation().fromRotationMatrix(
								incr.mult(model.getLocalRotation()
										.toRotationMatrix(tempMa), tempMb));
						model.getLocalRotation().normalize();
					}
				}
			}
		}
	}

	public int getMovespeed() {
		return movespeed;
	}

	public void setMovespeed(int movespeed) {
		this.movespeed = movespeed;
	}

	public byte getMoveStatus() {
		return moveStatus;
	}

	public void setMoveStatus(byte moveStatus) {
		this.moveStatus = moveStatus;
	}

	public int getTurnspeed() {
		return turnspeed;
	}

	public void setTurnspeed(int turnspeed) {
		this.turnspeed = turnspeed;
	}

	public byte getTurnStatus() {
		return turnStatus;
	}

	public void setTurnStatus(byte turnStatus) {
		this.turnStatus = turnStatus;
	}

}
