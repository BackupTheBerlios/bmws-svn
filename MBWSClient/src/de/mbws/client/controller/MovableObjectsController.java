package de.mbws.client.controller;

import org.apache.log4j.Logger;

import de.mbws.common.events.EventTypes;
import de.mbws.common.events.MoveEvent;

/**
 * Description: This class handles all movementEvents related to objects that move, 
 * like NPCs, PCs etc. The player is NOT handled here !
 * 
 * @author kerim
 * 
 */
public class MovableObjectsController {

	private Logger logger = Logger.getLogger(MovableObjectsController.class);
	private static MovableObjectsController instance;

	/**
	 * 
	 */
	private MovableObjectsController() {

	}

	public static MovableObjectsController getInstance() {
		if (instance == null) {
			instance = new MovableObjectsController();
		}
		return instance;
	}

	// TODO Kerim: correct error handling and next stages here !
	public void handleEvent(MoveEvent moveEvent) {
		if (moveEvent.getEventType() == EventTypes.MOVEMENT_START_WALK) {
			logger.info("Received event start walking");
		} else if (moveEvent.getEventType() == EventTypes.MOVEMENT_STOP_WALK) {
			logger.info("Received event stop walking");
		} else if (moveEvent.getEventType() == EventTypes.MOVEMENT_START_RUN) {
			logger.info("Received event start running");
		} else if (moveEvent.getEventType() == EventTypes.MOVEMENT_STOP_RUN) {
			logger.info("Received event stop running");
		} else if (moveEvent.getEventType() == EventTypes.MOVEMENT_START_TURN_LEFT) {
			logger.info("Received event start turning left");
		} else if (moveEvent.getEventType() == EventTypes.MOVEMENT_STOP_TURN) {
			logger.info("Received event stop turning left");
		} else if (moveEvent.getEventType() == EventTypes.MOVEMENT_START_TURN_RIGHT) {
			logger.info("Received event start turning right");
		} else {
			logger.error("Event not known to client !!");
		}

	}

	
	
}
