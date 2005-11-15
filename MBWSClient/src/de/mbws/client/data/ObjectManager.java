package de.mbws.client.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.jme.bounding.BoundingBox;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Node;
import com.jme.scene.shape.Box;
import com.jme.system.DisplaySystem;

import de.mbws.common.eventdata.generated.WorldObject;

public class ObjectManager {

	protected static Node rootNode;

	protected static DisplaySystem display;

	private static Map<String, AbstractGameObject> objects;

	private static ArrayList<String> listOfObjectsToDelete;

	public ObjectManager() {
		super();
	}

	public static void initialize(Node node, DisplaySystem displaysystem) {
		rootNode = node;
		display = displaysystem;
		objects = new HashMap<String, AbstractGameObject>();
		listOfObjectsToDelete = new ArrayList<String>();
	}

	public static void update(float f) {
		synchronized (objects) {
			for (Iterator<AbstractGameObject> object = objects.values()
					.iterator(); object.hasNext();) {
				object.next().update(f);
			}
			if (!listOfObjectsToDelete.isEmpty()) {
				for (Iterator<String> objectsToDelete = listOfObjectsToDelete
						.iterator(); objectsToDelete.hasNext();) {
					detach(objectsToDelete.next());
				}
				listOfObjectsToDelete.clear();
			}
		}
	}

	/**
	 * createObject created the object based on a worldobject
	 * 
	 * @return
	 */
	// TODO: Should we use a map here with configurable values ?
	// TODO: Replace GameObject by the correct type
	public static AbstractGameObject create(WorldObject wo) {
		MovableObject object = new MovableObject(wo.getObjectID());
		object.setAlive(true);
		object.setMovespeed(30);
		object.setTurnspeed(5);
		// TODO: Just for testing:

		// box stand in
		Box b = new Box("box", new Vector3f(), 0.35f, 0.5f, 0.25f);
		b.setDefaultColor(new ColorRGBA(ColorRGBA.white));
		b.setModelBound(new BoundingBox());
		b.updateModelBound();

		//TODO: "player2 node" wont work, trying integer.toString of objectid!!
		Node player2 = new Node(Integer.toString(wo.getObjectID()));
		// player2.setLocalTranslation(new Vector3f(100, 0, 100));

		player2.setLocalTranslation(new Vector3f(wo.getLocation().getX(), wo
				.getLocation().getY(), wo.getLocation().getZ()));
		player2.setLocalRotation(new Quaternion(wo.getHeading().getX(), wo
				.getHeading().getY(), wo.getHeading().getZ(), wo.getHeading()
				.getW()));

		rootNode.attachChild(player2);
		player2.attachChild(b);
		player2.updateWorldBound();
		object.setModel(player2);
		rootNode.attachChild(player2);

		// TODO Remove above
		// STEP1: Load
		// Node n = ....
		// object.setModel(n);
		// STEP2: Set coordinates and attach
		// m_rootNode.attachChild(n);
		// STEP 3: UPDATE STATES
		// ((Node) (n)).setLocalScale(15F);
		// ((Node) (n)).updateGeometricState(10F, true);
		// ((Node) (n)).updateRenderState();

		// object.initialize(map);
		synchronized (objects) {
			objects.put(object.getObjectID(), object);
		}

		return object;
	}

	public static void detach(String id) {
		synchronized (objects) {
			objects.remove(id);
		}
		rootNode.detachChildNamed(id);
	}

	public static void detach(int id) {
		String idString = Integer.toString(id);
		synchronized (objects) {
			objects.remove(idString);
		}
		rootNode.detachChildNamed(idString);
	}

	public static AbstractGameObject getObject(String s) {
		return objects.get(s);
	}

	public static Map getObjects() {
		return objects;
	}

	public static void markObjectForDeletion(String objectID) {
		listOfObjectsToDelete.add(objectID);
	}

	public static Node createPlayer() {
		Player object = new Player(Integer.parseInt(ClientPlayerData
				.getInstance().getPlayer().getObjectID()));
		object.setAlive(true);
		object.setMovespeed(30);
		object.setTurnspeed(5);

		Box b = new Box("box", new Vector3f(), 0.35f, 0.25f, 0.5f);
		b.setModelBound(new BoundingBox());
		b.updateModelBound();
		Node player = new Node(ClientPlayerData.getInstance().getPlayer()
				.getObjectID());
		Vector3f location = new Vector3f(ClientPlayerData.getInstance()
				.getCharacterData().getCharacterStatus().getCoordinateX(),
				ClientPlayerData.getInstance().getCharacterData()
						.getCharacterStatus().getCoordinateY(),
				ClientPlayerData.getInstance().getCharacterData()
						.getCharacterStatus().getCoordinateZ());
		player.setLocalTranslation(location);

		rootNode.attachChild(player);
		player.attachChild(b);
		player.updateWorldBound();
		object.setModel(player);

		synchronized (objects) {
			objects.put(object.getObjectID(), object);
		}
		return player;
	}

}
