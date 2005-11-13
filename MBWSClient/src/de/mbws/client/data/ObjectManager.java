package de.mbws.client.data;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.jme.bounding.BoundingBox;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Node;
import com.jme.scene.shape.Box;
import com.jme.system.DisplaySystem;

import de.mbws.common.eventdata.generated.WorldObject;

public class ObjectManager {

	protected static Node rootNode;

	protected static DisplaySystem display;

	private static Map<String, GameObject> objects;

	public ObjectManager() {
		super();
	}

	public static void initialize(Node node, DisplaySystem displaysystem) {
		rootNode = node;
		display = displaysystem;
		objects = new HashMap<String, GameObject>();
	}

	public static void update(float f) {
		synchronized (objects) {
			Iterator iterator = objects.values().iterator();
			do {
				if (!iterator.hasNext())
					break;
				GameObject object = (GameObject) iterator.next();

				// if(creature.getAIHandler().getTimeSinceDeath() > 30F)
				// {
				// m_rootNode.detachChild(creature.getModel());
				// iterator.remove();
				// } else
				// if(creature.getAIHandler().getTimeSinceDeath() > 20F)
				// creature.getModel().getLocalTranslation().y -= f * 2.0F;

			} while (true);
		}
	}

	/**
	 * createObject created the object based on a worldobject
	 * 
	 * @return
	 */
	// TODO: Should we use a map here with configurable values ?
	public static GameObject create(WorldObject wo) {
		GameObject object = new GameObject(wo.getObjectID());

		// TODO: Just for testing:

		// box stand in
		Box b = new Box("box", new Vector3f(), 0.35f, 0.5f, 0.25f);
		b.setDefaultColor(new ColorRGBA(ColorRGBA.white));
		b.setModelBound(new BoundingBox());
		b.updateModelBound();

		Node player2 = new Node("Player2 Node");
		player2.setLocalTranslation(new Vector3f(100, 0, 100));

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

	public static GameObject getObject(String s) {
		return (GameObject) objects.get(s);
	}

	public static Map getObjects() {
		return objects;
	}

}
