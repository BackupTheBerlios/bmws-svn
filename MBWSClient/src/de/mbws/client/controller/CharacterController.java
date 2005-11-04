 package de.mbws.client.controller;

import org.apache.log4j.Logger;

import com.jme.app.GameStateManager;

import de.mbws.client.data.ClientPlayerData;
import de.mbws.client.state.MainMenuState;
import de.mbws.common.data.generated.CharacterStatus;
import de.mbws.common.data.generated.Characterdata;
import de.mbws.common.eventdata.generated.IntVector3D;
import de.mbws.common.eventdata.generated.MoveData;
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
			logger.info("setting flag to start next state");
			((MainMenuState) GameStateManager.getInstance().getChild("menu"))
			.getInput().setStartNextState(true);
		}

	}

	public AbstractGameEvent createCharacterReceiveEvent() {
		CharacterEvent event = new CharacterEvent();
		event.setEventType(EventTypes.CHARACTER_RECEIVE_REQUEST);
		return event;
	}

	

	public void startWalking() {
		ClientNetworkController.getInstance().handleOutgoingEvent(
				createStartWalkingEvent());
	}

	public AbstractGameEvent createStopRunningEvent() {
		return createMovementEvent(EventTypes.MOVEMENT_STOP_RUN);
	}
	public AbstractGameEvent createStopWalkingEvent() {
		return createMovementEvent(EventTypes.MOVEMENT_STOP_WALK);
	}
	public AbstractGameEvent createStopTurnRightEvent() {
		return createMovementEvent(EventTypes.MOVEMENT_STOP_TURN_RIGHT);
	}
	public AbstractGameEvent createStopTurnLeftEvent() {
		return createMovementEvent(EventTypes.MOVEMENT_STOP_TURN_LEFT);
	}
	
	public AbstractGameEvent createStartRunningEvent() {
		return createMovementEvent(EventTypes.MOVEMENT_START_RUN);
	}
	public AbstractGameEvent createStartWalkingEvent() {
		return createMovementEvent(EventTypes.MOVEMENT_START_WALK);
	}
	public AbstractGameEvent createStartTurnRightEvent() {
		return createMovementEvent(EventTypes.MOVEMENT_START_TURN_RIGHT);
	}
	public AbstractGameEvent createStartTurnLeftEvent() {
		return createMovementEvent(EventTypes.MOVEMENT_START_TURN_LEFT);
	}
	
	public AbstractGameEvent createMovementEvent(int eventType) {
		MoveData md = new MoveData();
		MoveEvent me = new MoveEvent(md);
		//TODO fill real data
		IntVector3D location = new IntVector3D();
		location.setLocationX(0);
		location.setLocationY(0);
		location.setLocationZ(0);
		md.setLocation(location);
		IntVector3D heading = new IntVector3D();
		heading.setLocationX(0);
		heading.setLocationY(0);
		heading.setLocationZ(0);
		md.setHeading(heading);
		me.setEventType(eventType);
		return me;
	}

}
