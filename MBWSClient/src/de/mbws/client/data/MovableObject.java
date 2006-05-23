package de.mbws.client.data;

import org.apache.log4j.Logger;

import com.jme.intersection.BoundingPickResults;
import com.jme.intersection.PickData;
import com.jme.intersection.PickResults;
import com.jme.math.Matrix3f;
import com.jme.math.Ray;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jmex.model.animation.JointController;
import com.jmex.model.animation.KeyframeController;

import de.mbws.common.Globals;

public class MovableObject extends GameObject {

	public MovableObject(String id) {
		super(id);
		moveStatus = Globals.STANDING;
		turnStatus = Globals.NO_TURN;
		results.setCheckDistance(true);
	}

	public MovableObject(String id, KeyframeController kc) {
		super(id);
		moveStatus = Globals.STANDING;
		turnStatus = Globals.NO_TURN;
		keyframeController = kc;
		results.setCheckDistance(true);
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
	protected String diplayName;
	protected int currentHitpoints;
	protected int maxHitpoints;
	private PickResults results = new BoundingPickResults();
	// private PickResults results = new TrianglePickResults();
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
				getLocalTranslation().y -= f * 2.0F;
			}
		} else {
			if (!isPlayer) {
				synchronized (this) {
					if (moveStatus == Globals.WALKING || moveStatus == Globals.RUNNING) {
						Vector3f tempVa = new Vector3f();
						Vector3f loc = getLocalTranslation();
						loc.addLocal(getLocalRotation().getRotationColumn(2, tempVa)
								.multLocal(movespeed * f));
						loc.subtractLocal(0, getHeight(f), 0);
						setLocalTranslation(loc);
					} else if (moveStatus == Globals.WALKING_BACKWARD) {
						Vector3f tempVa = new Vector3f();
						Vector3f loc = getLocalTranslation();
						loc.subtractLocal(getLocalRotation().getRotationColumn(2, tempVa)
								.multLocal(movespeed * f));
						// loc.subtractLocal(0, getHeight(), 0);
						setLocalTranslation(loc);
					}
					if (turnStatus == Globals.TURN_RIGHT
							|| turnStatus == Globals.TURN_LEFT) {
						Vector3f tempVa = new Vector3f();
						Matrix3f incr = new Matrix3f();
						Matrix3f tempMa = new Matrix3f();
						Matrix3f tempMb = new Matrix3f();

						incr.loadIdentity();
						if (turnStatus == Globals.TURN_RIGHT) {
							incr.fromAxisAngle(getLocalRotation()
									.getRotationColumn(1, tempVa), -turnspeed * f);
						} else {
							incr.fromAxisAngle(getLocalRotation()
									.getRotationColumn(1, tempVa), turnspeed * f);
						}

						getLocalRotation().fromRotationMatrix(
								incr.mult(getLocalRotation().toRotationMatrix(tempMa), tempMb));
						getLocalRotation().normalize();
					}
				}
			}
		}
	}

	// BOUNDING (not good !)
	private float getHeight(float f) {
		Vector3f origin = getWorldTranslation();
		Vector3f origin2 = (Vector3f) origin.clone();
		// Vector3f origin2 = model.getWorldBound().getCenter();
		// origin2.y+=80;

		// origin2.x = worldBound.x;
		// origin2.z = worldBound.z;
		// float midHeight = model.getWorldBound().distanceTo(origin2);
		// System.out.println(midHeight);
		Vector3f destination = new Vector3f(origin2.x, origin2.y - 200, origin2.z);
		// origin.y += midHeight;
		origin2.y += 20;
		results.clear();
		Ray r = new Ray(origin2, destination);

		results.setCheckDistance(true);
		getParent().findPick(r, results);
		// rootNode.findPick(r, results);
		if (results.getNumber() > 0) {
			float distance = 50;
			System.out.println("Found: " + results.getNumber() + "results");
			Vector3f loc = null;
			for (int i = 0; i < results.getNumber(); i++) {
				String str = findNodeName(results.getPickData(i).getTargetMesh()
						.getParent());
				if (!str.equals(getName()) && !str.equals("state rootNode")
						&& !str.equals("DynamicWorld")) {
					System.out.println(str);
					float distance2 = results.getPickData(i).getDistance();
					PickData pd = results.getPickData(i);

					System.out.println("stored distance: " + distance);
					System.out.println("distance2: " + distance2);
					if (distance2 < 800 && distance2 != 0.0f && distance2 > -800) {// &&
						// distance2
						// > 0)
						// { //
						if (distance2 < distance) {
							loc = pd.getTargetMesh().getWorldTranslation();
							distance = distance2;
							System.out.println("new distance: " + distance2);
						}
					}

				}
			}
			if (distance != 50) {
				System.out.println("huhu");
				System.out.println("mm: " + (origin2.y - loc.y));
				return distance - 20;// origin2.y-loc.y;
			}
			return 10f;
		}
		return 0.0f;
	}

	// private float getHeight(float f) {
	// Vector3f origin = model.getWorldTranslation();
	// Vector3f origin2 =(Vector3f)origin.clone();
	// Vector3f worldBound = model.getWorldBound().getCenter();
	// origin2.x = worldBound.x;
	// //origin2.x = worldBound.x;
	// origin2.z = worldBound.z;
	// float midHeight = model.getWorldBound().distanceTo(origin2);
	// System.out.println(midHeight);
	// Vector3f destination = new Vector3f(origin.x, origin.y - 200, origin.z);
	// origin.y += midHeight;
	// results.clear();
	// Ray r = new Ray(origin, destination);
	//
	// results.setCheckDistance(true);
	// model.getParent().findPick(r, results);
	// // rootNode.findPick(r, results);
	// if (results.getNumber() > 0) {
	// float distance = 50;
	// System.out.println("Found: " + results.getNumber() + "results");
	// Vector3f loc = null;
	// for (int i = 0; i < results.getNumber(); i++) {
	// String str = findNodeName(results.getPickData(i)
	// .getTargetMesh().getParent());
	// if (!str.equals(model.getName())
	// && !str.equals("state rootNode")
	// && !str.equals("DynamicWorld")) {
	// System.out.println(str);
	// float distance2 = results.getPickData(i).getDistance();
	// PickData pd = results.getPickData(i);
	//					
	// //System.out.println("stored distance: " + distance);
	// //System.out.println("distance2: " + distance2);
	// if (distance2 < 800 && distance2 != 0.0f && distance2 > -800) {// &&
	// // distance2
	// // > 0)
	// // { //
	// if (distance2 < distance) {
	// loc = pd.getTargetMesh().getWorldTranslation();
	// distance = distance2;
	// System.out.println("new distance: " + distance2);
	// }
	// }
	//
	// }
	// }
	// if (loc != null) {
	// System.out.println("huhu");
	// System.out.println("mm: "+(origin2.y-loc.y));
	// return origin2.y-loc.y;
	// }
	// return 0.0f;
	// // FALLING ? (too slow)
	// // if (distance > 20) {
	// // System.out.println("huhu");
	// // return 0;
	// // } else
	// // return distance;
	// // PickData pd = results.getPickData(0);// results.getNumber() - 1);
	// // if (!pd.getTargetMesh().getParent().getName()
	// // .equals("DynamicWorld")) {
	// //
	// // System.out.println(" " + pd.getDistance());
	// // }
	//
	// }
	// return 0.0f;
	// }

	private String findNodeName(Node node) {
		// state rootNode
		if (node.getParent() != null) {
			// TODO: change that fix name !
			while ((node.getParent().getName() != null)
					&& (!node.getParent().getName().equals("state rootNode"))) {
				node = node.getParent();
			}
		}
		return node.getName();
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
						.getWalkStartTime(), getAnimationData().getWalkEndTime());
			} else if (moveStatus == Globals.STANDING) {
				keyframeController.setNewAnimationTimes(getAnimationData()
						.getStandStartTime(), getAnimationData().getStandEndTime());
			}
		} else if (jointController != null) {
			if (moveStatus == Globals.WALKING) {
				jointController.setTimes(getAnimationData().getWalkStartTime(),
						getAnimationData().getWalkEndTime());
			} else if (moveStatus == Globals.STANDING) {
				jointController.setTimes(getAnimationData().getStandStartTime(),
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

	public String getDisplayName() {
		return diplayName;
	}

	public void setDisplayName(String name) {
		this.diplayName = name;
	}

	public int getCurrentHitpoints() {
		return currentHitpoints;
	}

	public void setCurrentHitpoints(int hitpoints) {
		this.currentHitpoints = hitpoints;
	}

}
