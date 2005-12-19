package de.mbws.client.data;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.jme.bounding.BoundingBox;
import com.jme.bounding.BoundingSphere;
import com.jme.image.Texture;
import com.jme.input.KeyBindingManager;
import com.jme.input.KeyInput;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Node;
import com.jme.scene.shape.Box;
import com.jme.scene.state.TextureState;
import com.jme.system.DisplaySystem;
import com.jme.util.TextureManager;
import com.jmex.model.XMLparser.JmeBinaryReader;
import com.jmex.model.XMLparser.Converters.Md2ToJme;
import com.jmex.model.animation.KeyframeController;

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

		if (wo.getMovespeed() == 0 && wo.getTurnspeed() == 0) {
			// TODO Set a nonmovable object here
		} else {
			MovableObject object = new MovableObject(wo.getObjectID());
			object.setAlive(true);
			// object.setMovespeed(wo.getMovespeed());
			// object.setTurnspeed(wo.getTurnspeed());
			// TODO: Just for testing:
			object.setMovespeed(30);
			object.setTurnspeed(5);
			// box stand in
			Box b = new Box("box", new Vector3f(), 0.35f, 0.5f, 0.25f);
			b.setDefaultColor(new ColorRGBA(ColorRGBA.white));
			b.setModelBound(new BoundingBox());
			b.updateModelBound();

			// TODO: "player2 node" wont work, trying integer.toString of
			// objectid!!
			Node player2 = new Node(wo.getObjectID());
			// player2.setLocalTranslation(new Vector3f(100, 0, 100));

			player2.setLocalTranslation(new Vector3f(wo.getLocation().getX(),
					wo.getLocation().getY(), wo.getLocation().getZ()));
			player2.setLocalRotation(new Quaternion(wo.getHeading().getX(), wo
					.getHeading().getY(), wo.getHeading().getZ(), wo
					.getHeading().getW()));

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
		return null;
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
		Player object = ClientPlayerData.getInstance().getPlayer();//new Player(ClientPlayerData.getInstance().getPlayer().getObjectID());
		object.setAlive(true);
		object.setMovespeed(30);
		object.setTurnspeed(5);
		
		//trying a md2 model now
		Md2ToJme converter=new Md2ToJme();
        ByteArrayOutputStream BO=new ByteArrayOutputStream();

        URL textu=ObjectManager.class.getClassLoader().getResource("resources/textures/characters/generic/0/drfreak.jpg");
        URL freak=ObjectManager.class.getClassLoader().getResource("resources/models/characters/generic/0/drfreak.md2");
        Node freakmd2=null;

        try {
            long time = System.currentTimeMillis();
            converter.convert(freak.openStream(),BO);
            System.out.println("Time to convert from md2 to .jme:"+ ( System.currentTimeMillis()-time));
        } catch (IOException e) {
            System.out.println("damn exceptions:" + e.getMessage());
        }
        JmeBinaryReader jbr=new JmeBinaryReader();
        try {
            long time=System.currentTimeMillis();
            freakmd2=jbr.loadBinaryFormat(new ByteArrayInputStream(BO.toByteArray()));
            System.out.println("Time to convert from .jme to SceneGraph:"+ ( System.currentTimeMillis()-time));
        } catch (IOException e) {
            System.out.println("damn exceptions:" + e.getMessage());
        }

        TextureState ts = display.getRenderer().createTextureState();
        ts.setEnabled(true);
        ts.setTexture(
        TextureManager.loadTexture(
            textu,
            Texture.MM_LINEAR,
            Texture.FM_LINEAR));
        freakmd2.setRenderState(ts);
       // freakmd2.setLocalTranslation(new Vector3f(0,0,-20));
        freakmd2.setLocalScale(.2f);
        
        object.setKeyframeController((KeyframeController) freakmd2.getChild(0).getController(0));
        object.getKeyframeController().setSpeed(10);
        // Note: W S A D Left Down Up Right F12 ESC T L B C Already used
        //TODO: replace these with wsad ;)
        KeyBindingManager.getKeyBindingManager().set("start_run",KeyInput.KEY_R);
        KeyBindingManager.getKeyBindingManager().set("start_hit",KeyInput.KEY_H);
        KeyBindingManager.getKeyBindingManager().set("toggle_wrap",KeyInput.KEY_Z);
        KeyBindingManager.getKeyBindingManager().set("start_end",KeyInput.KEY_E);
        KeyBindingManager.getKeyBindingManager().set("start_smoothbegin",KeyInput.KEY_B);
        KeyBindingManager.getKeyBindingManager().set("start_smoothdeath",KeyInput.KEY_Q);
        //rootNode.attachChild(freakmd2);

//		Box b = new Box("box2", new Vector3f(), 0.35f, 0.25f, 0.5f);
//		b.setModelBound(new BoundingBox());
//		b.updateModelBound();
		Node player = new Node(ClientPlayerData.getInstance().getPlayer()
				.getObjectID());
		Vector3f location = new Vector3f(ClientPlayerData.getInstance()
				.getCharacterData().getCharacterStatus().getCoordinateX(),
				ClientPlayerData.getInstance().getCharacterData()
						.getCharacterStatus().getCoordinateY(),
				ClientPlayerData.getInstance().getCharacterData()
						.getCharacterStatus().getCoordinateZ());
		freakmd2.setLocalTranslation(location); //player
		BoundingSphere bs = new BoundingSphere();
		bs.setCenter(new Vector3f (0,0,0));
		bs.setRadius(2);
		freakmd2.setWorldBound(bs);
		freakmd2.updateWorldBound();
		rootNode.attachChild(player);
		player.attachChild(freakmd2);
		player.updateWorldBound();
		object.setModel(player);

		synchronized (objects) {
			objects.put(object.getObjectID(), object);
		}
		return player;
	}

}
