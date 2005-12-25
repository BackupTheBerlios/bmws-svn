package de.mbws.client.data;

import org.apache.log4j.Logger;

import com.jme.math.Matrix3f;
import com.jme.math.Vector3f;
import com.jmex.model.animation.JointController;
import com.jmex.model.animation.KeyframeController;

import de.mbws.common.Globals;

public class MovableObject extends GameObject {

	public MovableObject(String id) {
		super(id);
		moveStatus = Globals.STANDING;
		turnStatus = Globals.NO_TURN;
	}

	public MovableObject(String id, KeyframeController kc) {
		super(id);
		moveStatus = Globals.STANDING;
		turnStatus = Globals.NO_TURN;
		keyframeController = kc;
	}

	private KeyframeController keyframeController;
	private JointController jointController;
	protected byte moveStatus;
	protected byte turnStatus;
	protected int movespeed;
	protected int turnspeed;
	protected boolean alive;
	protected long timeOfDeath;
	protected boolean isPlayer = false;
	// Animations
	protected AnimationData animationData = new AnimationData();

	private static Logger logger = Logger.getLogger("MovableObject");

	public KeyframeController getKeyframeController() {
		return keyframeController;
	}

	public void setKeyframeController(KeyframeController kc) {
		this.keyframeController = kc;
	}

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
					if (moveStatus == Globals.WALKING
							|| moveStatus == Globals.RUNNING) {
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
					if (turnStatus == Globals.TURN_RIGHT
							|| turnStatus == Globals.TURN_LEFT) {
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
		setNewControllerSequence();
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

	// TODO: should we take that into the object itself ?
	public void setNewControllerSequence() {

		if (keyframeController != null) {
			if (moveStatus == Globals.WALKING) {
				keyframeController.setNewAnimationTimes(getAnimationData()
						.getWalkStartTime(), getAnimationData()
						.getWalkEndTime());
			} else if (moveStatus == Globals.STANDING) {
				keyframeController.setNewAnimationTimes(getAnimationData()
						.getStandStartTime(), getAnimationData()
						.getStandEndTime());
			}
		} else if (jointController != null) {
			if (moveStatus == Globals.WALKING) {
				jointController.setTimes(getAnimationData().getWalkStartTime(),
						getAnimationData().getWalkEndTime());
			} else if (moveStatus == Globals.STANDING) {
				jointController.setTimes(
						getAnimationData().getStandStartTime(),
						getAnimationData().getStandEndTime());
			}
		} else {
			logger.error("keyFrameController for MovableObject: " + objectID
					+ "is null. No animation possible !!");
			return;
		}
		// .getKeyBindingManager()
		// .isValidCommand("start_hit", false)) {
		// kc.setNewAnimationTimes(45,52);
		// }
		// if (KeyBindingManager
		// .getKeyBindingManager()
		// .isValidCommand("start_end", false)) {
		//		            
		// }
		// if (KeyBindingManager
		// .getKeyBindingManager()
		// .isValidCommand("start_smoothbegin", false)) {
		// kc.setSmoothTranslation(0,25,0,196);
		// }
		// if (KeyBindingManager
		// .getKeyBindingManager()
		// .isValidCommand("start_smoothdeath", false)) {
		// kc.setSmoothTranslation(175,25,175,182);
		// }
		// if (KeyBindingManager
		// .getKeyBindingManager()
		// .isValidCommand("toggle_wrap", false)) {
		// if (kc.getRepeatType()==KeyframeController.RT_CYCLE)
		// kc.setRepeatType(KeyframeController.RT_WRAP);
		// else
		// kc.setRepeatType(KeyframeController.RT_CYCLE);
		// }
	}

	public JointController getJointController() {
		return jointController;
	}

	public void setJointController(JointController jointController) {
		this.jointController = jointController;
	}

	public AnimationData getAnimationData() {
		return animationData;
	}

	public void setAnimationData(AnimationData animationData) {
		this.animationData = animationData;
	}

}
