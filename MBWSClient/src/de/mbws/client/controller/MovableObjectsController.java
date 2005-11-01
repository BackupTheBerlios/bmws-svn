package de.mbws.client.controller;

import org.apache.log4j.Logger;

import de.mbws.client.data.ClientPlayerData;
import de.mbws.common.eventdata.generated.AccountData;
import de.mbws.common.eventdata.generated.LoginData;
import de.mbws.common.events.AbstractGameEvent;
import de.mbws.common.events.EventTypes;
import de.mbws.common.events.LoginEvent;
import de.mbws.common.events.MoveEvent;

/**
 * Description: This class handles all movementEvents related to objects that move, 
 * like NPCs, PCs etc. The player is NOT handled here !
 * 
 * @authorkerim
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
		if (moveEvent.getEventType() == EventTypes.START_WALK) {
			logger.info("Received event start walking");
		} else if (moveEvent.getEventType() == EventTypes.STOP_WALK) {
			logger.info("Received event stop walking");
		} else if (moveEvent.getEventType() == EventTypes.START_RUN) {
			logger.info("Received event start running");
		} else if (moveEvent.getEventType() == EventTypes.STOP_RUN) {
			logger.info("Received event stop running");
		} else if (moveEvent.getEventType() == EventTypes.START_TURN_LEFT) {
			logger.info("Received event start turning left");
		} else if (moveEvent.getEventType() == EventTypes.STOP_TURN_LEFT) {
			logger.info("Received event stop turning left");
		} else if (moveEvent.getEventType() == EventTypes.START_TURN_RIGHT) {
			logger.info("Received event start turning right");
		} else if (moveEvent.getEventType() == EventTypes.STOP_TURN_RIGHT) {
			logger.info("Received event stop turning right");
		} else {
			logger.error("Event not known to client !!");
		}

	}

	public AbstractGameEvent createLoginEvent(AccountData account,
			ClientPlayerData player) {
		LoginData ld = new LoginData();
		ld.setUserName(account.getUserName());
		ld.setPassword(account.getPassword());
		LoginEvent event = new LoginEvent(ld);
		player.setSessionId(new Integer(0));
		event.setPlayer(player);

		event.setEventType(EventTypes.LOGIN);
		return event;
	}

	public AbstractGameEvent createLogoutEvent(ClientPlayerData player) {
		LoginEvent event = new LoginEvent();
		event.setPlayer(player);
		event.setEventType(EventTypes.LOGOUT);
		return event;
	}

}
