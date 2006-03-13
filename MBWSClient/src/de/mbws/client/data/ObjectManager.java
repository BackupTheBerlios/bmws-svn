package de.mbws.client.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

import com.jme.bounding.BoundingBox;
import com.jme.math.FastMath;
import com.jme.math.Matrix3f;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Controller;
import com.jme.scene.Node;
import com.jme.scene.shape.Box;
import com.jme.system.DisplaySystem;
import com.jmex.model.XMLparser.JmeBinaryReader;
import com.jmex.model.animation.JointController;
import com.jmex.model.animation.KeyframeController;

import de.mbws.common.events.data.generated.CharacterData;
import de.mbws.common.events.data.generated.CharacterVisualAppearance;
import de.mbws.common.events.data.generated.StaticObject;

public class ObjectManager {

	private static Logger logger = Logger.getLogger(ObjectManager.class);

	private static final String TEXTURE_BASE_PATH = "/textures/";
	// private static final String MODEL_BASE_PATH = "/model/";
	private static final String BASE_PATH = "data/characters/";
	private static final String GENERIC_CHARACTER_PATH = "generic/";
	private static final String BASE_MODEL = "/model/basemodel.jme";
	// private static final String BASE_TEXTURE = "/basemodel.jpg";

	protected static Node rootNode;
	protected static Node player;

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
		player = createPlayer();
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

	public static Node getPlayer() {
		if (player == null) {
			player = createPlayer();
		}
		return player;
	}

	/**
	 * createObject created the object based on a worldobject
	 * 
	 * @return
	 */
	// TODO: Should we use a map here with configurable values ?
	// TODO: Replace GameObject by the correct type
	public static AbstractGameObject create(StaticObject so) {

		// if (wo.getMovespeed() == 0 && wo.getTurnspeed() == 0) {
		// // TODO Set a nonmovable object here
		// logger.info("Supposed to set a nonmovable object here");
		// //GameObject object = new GameObject(wo.getObjectID());
		// } else {
		logger.info("Supposed to set a movable object here");
		MovableObject object = new MovableObject(so.getObjectID());
		object.setAlive(true);
		// object.setMovespeed(wo.getMovespeed());
		// object.setTurnspeed(wo.getTurnspeed());
		// TODO: Just for testing:
		object.setMovespeed(30);
		object.setTurnspeed(5);
		// box stand in
		Box b = new Box("box", new Vector3f(), 10f, 5f, 10f);
		b.setDefaultColor(new ColorRGBA(ColorRGBA.white));
		b.setModelBound(new BoundingBox());
		b.updateModelBound();

		// TODO: "player2 node" wont work, trying integer.toString of
		// objectid!!
		Node player2 = new Node(so.getObjectID());

		player2.setLocalTranslation(new Vector3f(so.getLocation().getX(), so
				.getLocation().getY(), so.getLocation().getZ()));
		player2.setLocalRotation(new Quaternion(so.getHeading().getX(), so
				.getHeading().getY(), so.getHeading().getZ(), so.getHeading()
				.getW()));

		rootNode.attachChild(player2);
		player2.attachChild(b);
		player2.updateWorldBound();
		object.setModel(player2);
		rootNode.attachChild(player2);

		
		// object.initialize(map);
		synchronized (objects) {
			objects.put(object.getObjectID(), object);
		}

		return object;
		// }
		// return null;
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

	private static Node createPlayer() {
		Player object = ClientPlayerData.getInstance().getPlayer();
		object.setAlive(true);
		object.setMovespeed(30);
		object.setTurnspeed(5);

		int race = ClientPlayerData.getInstance().getSelectedCharacterData()
				.getRace();
		char gender = ClientPlayerData.getInstance().getSelectedCharacterData()
				.getGender();

		CharacterVisualAppearance appearance = ClientPlayerData.getInstance()
				.getSelectedCharacterData().getVisualAppearance();

		try {
			URL urlOfTexture = new File(BASE_PATH + GENERIC_CHARACTER_PATH
					+ race + "/" + gender + TEXTURE_BASE_PATH).toURL();

			URL urlOfModel = new File(BASE_PATH + GENERIC_CHARACTER_PATH + race
					+ "/" + gender + BASE_MODEL).toURL();

			URL urlOfPropertyFile = new File(BASE_PATH + GENERIC_CHARACTER_PATH
					+ race + "/" + gender + "/model/model.properties").toURL();

			Configuration modelConfiguration = new PropertiesConfiguration(
					new File(urlOfPropertyFile.getFile()));// urlOfPropertyFile.getFile()));
			float scaling = modelConfiguration.getFloat("scale", 1.0f);
			float rotateAroundY = modelConfiguration.getFloat("rotate.y", 0.0f);
			float animationSpeed = modelConfiguration.getFloat(
					"animation.speed", 1f);

			object.getAnimationData().setAnimationSpeed(animationSpeed);
			object.getAnimationData().setWalkStartTime(
					modelConfiguration.getInt("animation.walk.start", 2));
			object.getAnimationData().setWalkEndTime(
					modelConfiguration.getInt("animation.walk.end", 14));
			object.getAnimationData().setStandStartTime(
					modelConfiguration.getInt("animation.stand.start", 292));
			object.getAnimationData().setStandEndTime(
					modelConfiguration.getInt("animation.stand.end", 325));

			FileInputStream fi = new FileInputStream(
					new File(BASE_PATH + GENERIC_CHARACTER_PATH + race + "/"
							+ gender + BASE_MODEL));// urlOfModel.getFile()));

			Node modelNode = null;

			JmeBinaryReader jbr = new JmeBinaryReader();
			jbr.setProperty("texurl", urlOfTexture);
			jbr.setProperty("bound", "box"); // Doesnt work ?
			try {
				long time = System.currentTimeMillis();
				modelNode = jbr.loadBinaryFormat(fi);
				logger.info("Time to convert from .jme to SceneGraph:"
						+ (System.currentTimeMillis() - time));
			} catch (IOException e) {
				logger.error("damn exceptions:" + e.getMessage());
			}

			modelNode.setLocalScale(scaling);
			if (rotateAroundY != 0) {
				Matrix3f localRotate = new Matrix3f();
				localRotate.fromAxisAngle(new Vector3f(0.0F, 1.0F, 0.0F),
						-(rotateAroundY * 0.5f * FastMath.PI));
				modelNode.setLocalRotation(localRotate);
			}
			Controller c = modelNode.getChild(0).getController(0);
			if (c instanceof KeyframeController) {
				object.setKeyframeController((KeyframeController) c);
				object.getKeyframeController().setSpeed(animationSpeed);
				object.getKeyframeController().setNewAnimationTimes(
						object.getAnimationData().getStandStartTime(),
						object.getAnimationData().getStandEndTime());
			} else {
				object.setJointController((JointController) c);
				object.getJointController().setSpeed(animationSpeed);
				object.getJointController().setTimes(
						object.getAnimationData().getStandStartTime(),
						object.getAnimationData().getStandEndTime());
			}
			Node player = new Node(ClientPlayerData.getInstance().getPlayer()
					.getObjectID());
			Vector3f location = new Vector3f(ClientPlayerData.getInstance()
					.getSelectedCharacterData().getLocation().getX(),
					ClientPlayerData.getInstance().getSelectedCharacterData()
							.getLocation().getY(), ClientPlayerData
							.getInstance().getSelectedCharacterData()
							.getLocation().getZ());
			player.setLocalTranslation(location);
			// player.setLocalRotation(localRotate);

			// Quaternion temp=new Quaternion();
			// temp.fromAngleAxis(FastMath.PI/2,new Vector3f(-1,0,0));
			// freakmd2.setLocalRotation(temp);

			// BoundingSphere bs = new BoundingSphere();
			// bs.setCenter(new Vector3f(0, 0, 0));
			// bs.setRadius(2);

			// modelNode.setWorldBound(new BoundingBox());// bs);
			// modelNode.updateWorldBound();
			rootNode.attachChild(player);
			player.attachChild(modelNode);
			player.updateWorldBound();
			object.setModel(player);

			synchronized (objects) {
				objects.put(object.getObjectID(), object);
			}

			return player;
		} catch (Exception e1) {

			e1.printStackTrace();
			return null;
		}
	}

	public static Node loadNode(CharacterData ocd) {
		int race = ocd.getRace();
		char gender = ocd.getGender();
		try {
			MovableObject object = new MovableObject("SelectionScreenModel");
			URL urlOfTexture = new File(BASE_PATH + GENERIC_CHARACTER_PATH
					+ race + "/" + gender + TEXTURE_BASE_PATH).toURL();

			URL urlOfModel = new File(BASE_PATH + GENERIC_CHARACTER_PATH + race
					+ "/" + gender + BASE_MODEL).toURL();

			URL urlOfPropertyFile = new File(BASE_PATH + GENERIC_CHARACTER_PATH
					+ race + "/" + gender + "/model/model.properties").toURL();

			Configuration modelConfiguration = new PropertiesConfiguration(
					new File(urlOfPropertyFile.getFile()));// urlOfPropertyFile.getFile()));
			float scaling = modelConfiguration.getFloat("scale", 1.0f);
			float rotateAroundY = modelConfiguration.getFloat("rotate.y", 0.0f);
			float animationSpeed = modelConfiguration.getFloat(
					"animation.speed", 1f);

			object.getAnimationData().setAnimationSpeed(animationSpeed);
			object.getAnimationData().setStandStartTime(
					modelConfiguration.getInt("animation.stand.start", 292));
			object.getAnimationData().setStandEndTime(
					modelConfiguration.getInt("animation.stand.end", 325));

			FileInputStream fi = new FileInputStream(
					new File(BASE_PATH + GENERIC_CHARACTER_PATH + race + "/"
							+ gender + BASE_MODEL));// urlOfModel.getFile()));

			Node modelNode = null;

			JmeBinaryReader jbr = new JmeBinaryReader();
			jbr.setProperty("texurl", urlOfTexture);
			jbr.setProperty("bound", "box"); // Doesnt work ?
			try {
				long time = System.currentTimeMillis();
				modelNode = jbr.loadBinaryFormat(fi);
				logger.info("Time to convert from .jme to SceneGraph:"
						+ (System.currentTimeMillis() - time));
			} catch (IOException e) {
				logger.error("damn exceptions:" + e.getMessage());
			}

			modelNode.setLocalScale(scaling);
			if (rotateAroundY != 0) {
				Matrix3f localRotate = new Matrix3f();
				localRotate.fromAxisAngle(new Vector3f(0.0F, 1.0F, 0.0F),
						-(rotateAroundY * 0.5f * FastMath.PI));
				modelNode.setLocalRotation(localRotate);
			}
			
			Controller c = modelNode.getChild(0).getController(0);
			if (c instanceof KeyframeController) {
				object.setKeyframeController((KeyframeController) c);
				object.getKeyframeController().setSpeed(animationSpeed);
				object.getKeyframeController().setNewAnimationTimes(
						object.getAnimationData().getStandStartTime(),
						object.getAnimationData().getStandEndTime());
			} else {
				object.setJointController((JointController) c);
				object.getJointController().setSpeed(animationSpeed);
				object.getJointController().setTimes(
						object.getAnimationData().getStandStartTime(),
						object.getAnimationData().getStandEndTime());
			}
			return modelNode;
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	public static Node createMovableObject(CharacterData ocd) {

		// TODO: See if we have set all variables (we have not !)
		MovableObject object = new MovableObject(ocd.getCharacterID());
		object.setAlive(true);
		object.setMovespeed(30);
		object.setTurnspeed(5);

		object.setName(ocd.getName());
		int race = ocd.getRace();
		char gender = ocd.getGender();
		CharacterVisualAppearance appearance = ocd.getVisualAppearance();

		try {
			URL urlOfTexture = new File(BASE_PATH + GENERIC_CHARACTER_PATH
					+ race + "/" + gender + TEXTURE_BASE_PATH).toURL();

			URL urlOfModel = new File(BASE_PATH + GENERIC_CHARACTER_PATH + race
					+ "/" + gender + BASE_MODEL).toURL();

			URL urlOfPropertyFile = new File(BASE_PATH + GENERIC_CHARACTER_PATH
					+ race + "/" + gender + "/model/model.properties").toURL();

			Configuration modelConfiguration = new PropertiesConfiguration(
					new File(urlOfPropertyFile.getFile()));// urlOfPropertyFile.getFile()));
			float scaling = modelConfiguration.getFloat("scale", 1.0f);
			float rotateAroundY = modelConfiguration.getFloat("rotate.y", 0.0f);
			float animationSpeed = modelConfiguration.getFloat(
					"animation.speed", 1f);

			object.getAnimationData().setAnimationSpeed(animationSpeed);
			object.getAnimationData().setWalkStartTime(
					modelConfiguration.getInt("animation.walk.start", 2));
			object.getAnimationData().setWalkEndTime(
					modelConfiguration.getInt("animation.walk.end", 14));
			object.getAnimationData().setStandStartTime(
					modelConfiguration.getInt("animation.stand.start", 292));
			object.getAnimationData().setStandEndTime(
					modelConfiguration.getInt("animation.stand.end", 325));

			FileInputStream fi = new FileInputStream(
					new File(BASE_PATH + GENERIC_CHARACTER_PATH + race + "/"
							+ gender + BASE_MODEL));// urlOfModel.getFile()));

			Node modelNode = null;

			JmeBinaryReader jbr = new JmeBinaryReader();
			jbr.setProperty("texurl", urlOfTexture);
			jbr.setProperty("bound", "box"); // Doesnt work ?
			try {
				long time = System.currentTimeMillis();
				modelNode = jbr.loadBinaryFormat(fi);
				logger.info("Time to convert from .jme to SceneGraph:"
						+ (System.currentTimeMillis() - time));
			} catch (IOException e) {
				logger.error("damn exceptions:" + e.getMessage());
			}
			modelNode.updateRenderState();
			modelNode.setLocalScale(scaling);
			if (rotateAroundY != 0) {
				Matrix3f localRotate = new Matrix3f();
				localRotate.fromAxisAngle(new Vector3f(0.0F, 1.0F, 0.0F),
						-(rotateAroundY * 0.5f * FastMath.PI));
				modelNode.setLocalRotation(localRotate);
			}
			Controller c = modelNode.getChild(0).getController(0);
			if (c instanceof KeyframeController) {
				object.setKeyframeController((KeyframeController) c);
				object.getKeyframeController().setSpeed(animationSpeed);
				object.getKeyframeController().setNewAnimationTimes(
						object.getAnimationData().getStandStartTime(),
						object.getAnimationData().getStandEndTime());
			} else {
				object.setJointController((JointController) c);
				object.getJointController().setSpeed(animationSpeed);
				object.getJointController().setTimes(
						object.getAnimationData().getStandStartTime(),
						object.getAnimationData().getStandEndTime());
			}
			Node objectNode = new Node(object.getObjectID());
			Vector3f location = new Vector3f(ocd.getLocation().getX(), ocd
					.getLocation().getY(), ocd.getLocation().getZ());
			objectNode.setLocalTranslation(location);

			rootNode.attachChild(objectNode);
			
			objectNode.attachChild(modelNode);
			objectNode.updateWorldBound();
			object.setModel(objectNode);

			synchronized (objects) {
				objects.put(object.getObjectID(), object);
			}

			return objectNode;
		} catch (Exception e1) {

			e1.printStackTrace();
			return null;
		}
	}

}
