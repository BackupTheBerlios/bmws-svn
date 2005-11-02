 package de.mbws.client.controller;

import org.apache.log4j.Logger;

import com.jme.app.GameStateManager;

import de.mbws.client.data.ClientPlayerData;
import de.mbws.client.state.TestGameState;
import de.mbws.common.data.generated.CharacterStatus;
import de.mbws.common.data.generated.Characterdata;
import de.mbws.common.eventdata.generated.PlayerInfo;
import de.mbws.common.events.AbstractGameEvent;
import de.mbws.common.events.CharacterEvent;
import de.mbws.common.events.EventTypes;
import de.mbws.common.events.MoveEvent;

/**
 * Description:
 * 
 * @author kerim
 * 
 */
public class CharacterController {

	private Logger logger = Logger.getLogger(CharacterController.class);

	private static CharacterController instance;

	/**
	 * 
	 */
	private CharacterController() {

	}

	public static CharacterController getInstance() {
		if (instance == null) {
			instance = new CharacterController();
		}
		return instance;
	}

	// TODO Kerim: correct error handling and next stages here !
	public void handleEvent(CharacterEvent event) {
		if (event.getEventType() == EventTypes.CHARACTER_RECEIVE) {
			logger.info("Receiving Character!");
			Characterdata characterData = new Characterdata();
			PlayerInfo eventData = (PlayerInfo) event.getEventData();
			logger.info("Playerinfo contains:");
			logger.info(eventData.getObject().getLocation().getLocationX());
			CharacterStatus status = new CharacterStatus();
			status.setCoordinateX(eventData.getObject().getLocation()
					.getLocationX());
			status.setCoordinateY(eventData.getObject().getLocation()
					.getLocationX());
			status.setCoordinateZ(eventData.getObject().getLocation()
					.getLocationX());
			characterData.setCharacterStatus(status);
			ClientPlayerData.getInstance().setCharacterData(characterData);
			((TestGameState) GameStateManager.getInstance().getChild("game"))
					.updatePlayer();
		}

	}

	public AbstractGameEvent createCharacterReceiveEvent() {
		CharacterEvent event = new CharacterEvent();
		event.setEventType(EventTypes.CHARACTER_RECEIVE_REQUEST);
		return event;
	}

	public AbstractGameEvent createStartWalkingEvent() {
		MoveEvent event = new MoveEvent();
		event.setEventType(EventTypes.MOVEMENT_START_WALK);
		logger.info("Creating start walking event:");
		return event;
	}

	public void startWalking() {
		ClientNetworkController.getInstance().handleOutgoingEvent(
				createStartWalkingEvent());
	}

}
