package de.mbws.client.controller;

import org.apache.log4j.Logger;

import com.jme.app.GameStateManager;

import de.mbws.client.data.ObjectNode;
import de.mbws.client.data.ObjectStore;
import de.mbws.client.state.TestGameState;
import de.mbws.common.events.EventTypes;
import de.mbws.common.events.ObjectEvent;

/**
 * Description: This class handles all events related to objects like creation,
 * taking, destruction etc. Events related to moving objects is NOT handled
 * here, neither concerning the objects a player already has ! Note that FOR NOW
 * also npcs are just "objects" as long as they dont move !
 * 
 * @author kerim
 * 
 */
public class ObjectController {

	private Logger logger = Logger.getLogger(ObjectController.class);

	private static ObjectController instance;

	private TestGameState gameState = (TestGameState) GameStateManager
			.getInstance().getChild("game");

	private ObjectController() {

	}

	public static ObjectController getInstance() {
		if (instance == null) {
			instance = new ObjectController();
		}
		return instance;
	}

	// TODO Kerim: correct error handling and next stages here !
	public void handleEvent(ObjectEvent objectEvent) {
		if (objectEvent.getEventType() == EventTypes.OBJECT_CREATE) {
			logger.info("Received event to create an object");
			ObjectNode n = gameState.addObject(objectEvent.getObjectInfo());
			n.setWorldObject(objectEvent.getObjectInfo());
			ObjectStore.getInstance().addOrReplaceObject(
					Integer.toString(objectEvent.getObjectInfo().getObjectID()), n);
		} else if (objectEvent.getEventType() == EventTypes.OBJECT_DESTROY) {
			logger.info("Received event to destroy an object");
		} else if (objectEvent.getEventType() == EventTypes.OBJECT_TAKEN) {
			logger.info("Received event that an object was taken");
		} else if (objectEvent.getEventType() == EventTypes.OBJECT_DROPPED) {
			logger
					.info("Received event that an object was dropped by some npc/player");
		} else if (objectEvent.getEventType() == EventTypes.MOVABLE_OBJECT_CREATE) {
			logger.info("Received event to create a movable");
			ObjectNode n = gameState.addObject(objectEvent.getObjectInfo());
			n.setWorldObject(objectEvent.getObjectInfo());
			ObjectStore.getInstance().addOrReplaceMovableObject(Integer.toString(objectEvent.getObjectInfo().getObjectID()), n);
		} else if (objectEvent.getEventType() == EventTypes.MOVABLE_OBJECT_DESTROY) {
			logger.info("Received event to destroy a movable");
			ObjectNode deletedObject = ObjectStore.getInstance().deleteMovableObject(Integer.toString(objectEvent.getObjectInfo().getObjectID()));
			if (deletedObject==null) {
				logger.warn("MovableObject: "+objectEvent.getObjectInfo().getObjectID()+" not found in objectstore");
			} else {
				gameState.deleteObject(deletedObject);
			}
		} else {
			logger.error("Event not known to client !!");
		}

	}
}
