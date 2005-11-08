package de.mbws.client.data;

import java.util.HashMap;

import org.apache.log4j.Logger;

import de.mbws.common.data.AbstractPlayerData;

/**
 * Description:
 * 
 * @author Kerim
 * 
 */
public class ObjectStore extends AbstractPlayerData {

	private static ObjectStore instance;

	private HashMap<String, ObjectNode> nonMovableObjects = new HashMap<String, ObjectNode>();

	private HashMap<String, ObjectNode> movableObjects = new HashMap<String, ObjectNode>();

	private Logger logger = Logger.getLogger(ObjectStore.class);
	
	private ObjectStore() {
	}

	public static ObjectStore getInstance() {
		if (instance == null) {
			instance = new ObjectStore();
		}
		return instance;
	}

	public void addOrReplaceObject(String key, ObjectNode o) {
		nonMovableObjects.put(key,o);
	}

	public void addOrReplaceMovableObject(String key, ObjectNode o) {
		movableObjects.put(key,o);
	}

	public void deleteObject(String key) {
		if (nonMovableObjects.remove(key)==null) {
			logger.warn("Object: "+key.toString()+" not found in objectstore");
		}
	}

	public ObjectNode deleteMovableObject(Object key) {
		ObjectNode deletedObject = movableObjects.remove(key);
		return deletedObject;
	}
	
	public ObjectNode getObject(String key) {
		return nonMovableObjects.get(key);
	}

	public ObjectNode getMovableObject(String key) {
		return movableObjects.get(key);
	}

}
