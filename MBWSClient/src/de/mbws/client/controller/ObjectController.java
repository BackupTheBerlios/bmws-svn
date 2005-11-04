package de.mbws.client.controller;

import org.apache.log4j.Logger;

import de.mbws.common.events.EventTypes;
import de.mbws.common.events.ObjectEvent;

/**
 * Description: This class handles all events related to objects like creation, taking, destruction etc.
 * Events related to moving objects is NOT handled here, neither concerning the objects a player already has !
 * Note that FOR NOW also npcs are just "objects" as long as they dont move !
 * @author kerim
 * 
 */
public class ObjectController {

	private Logger logger = Logger.getLogger(ObjectController.class);
	private static ObjectController instance;

	private ObjectController() {

	}

	public static ObjectController getInstance() {
		if (instance == null) {
			instance = new ObjectController();
		}
		return instance;
	}
	
//	 TODO Kerim: correct error handling and next stages here !
	public void handleEvent(ObjectEvent moveEvent) {
		if (moveEvent.getEventType() == EventTypes.OBJECT_CREATE) {
			logger.info("Received event to create an object");
		} else if (moveEvent.getEventType() == EventTypes.OBJECT_DESTROY) {
			logger.info("Received event to destroy an object");
		} else if (moveEvent.getEventType() == EventTypes.OBJECT_TAKEN) {
			logger.info("Received event that an object was taken");
		} else if (moveEvent.getEventType() == EventTypes.OBJECT_DROPPED) {
			logger.info("Received event that an object was dropped by some npc/player");
		} else {
			logger.error("Event not known to client !!");
		}

	}
}
