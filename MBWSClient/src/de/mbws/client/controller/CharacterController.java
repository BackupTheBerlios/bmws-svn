 package de.mbws.client.controller;

import org.apache.log4j.Logger;

import com.jme.app.GameStateManager;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;

import de.mbws.client.data.ClientPlayerData;
import de.mbws.client.state.MainMenuState;
import de.mbws.common.data.generated.CharacterStatus;
import de.mbws.common.data.generated.Characterdata;
import de.mbws.common.eventdata.generated.IntVector3D;
import de.mbws.common.eventdata.generated.MoveData;
import de.mbws.common.eventdata.generated.NetQuaternion;
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

	public int playerid;
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
			playerid=event.getPlayerInfo().getObject().getObjectID();
			logger.info("Receiving Character!");
			Characterdata characterData = new Characterdata();
			PlayerInfo eventData = (PlayerInfo) event.getEventData();
			CharacterStatus status = new CharacterStatus();
			status.setCoordinateX(eventData.getObject().getLocation()
					.getX());
			status.setCoordinateY(eventData.getObject().getLocation()
					.getX());
			status.setCoordinateZ(eventData.getObject().getLocation()
					.getX());
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


	public AbstractGameEvent createStopRunningEvent(Vector3f loc, Quaternion rot) {
		return createMovementEvent(EventTypes.MOVEMENT_STOP_RUN, loc,  rot);
	}
	public AbstractGameEvent createStopWalkingEvent(Vector3f loc, Quaternion rot) {
		return createMovementEvent(EventTypes.MOVEMENT_STOP_WALK, loc,  rot);
	}
	public AbstractGameEvent createStopTurn(Vector3f loc, Quaternion rot) {
		return createMovementEvent(EventTypes.MOVEMENT_STOP_TURN, loc,  rot);
	}	
	
	public AbstractGameEvent createStartRunningEvent(Vector3f loc, Quaternion rot) {
		return createMovementEvent(EventTypes.MOVEMENT_START_RUN, loc,  rot);
	}
	public AbstractGameEvent createStartWalkingEvent(Vector3f loc, Quaternion rot) {
		return createMovementEvent(EventTypes.MOVEMENT_START_WALK, loc,  rot);
	}
	public AbstractGameEvent createStartTurnRightEvent(Vector3f loc, Quaternion rot) {
		return createMovementEvent(EventTypes.MOVEMENT_START_TURN_RIGHT, loc,  rot);
	}
	public AbstractGameEvent createStartTurnLeftEvent(Vector3f loc, Quaternion rot) {
		return createMovementEvent(EventTypes.MOVEMENT_START_TURN_LEFT, loc,  rot);
	}
	
	public AbstractGameEvent createMovementEvent(int eventType,Vector3f loc, Quaternion rot) {
		MoveData md = new MoveData();
		MoveEvent me = new MoveEvent(md);
		//TODO fill real data
		//TODO change int to float ?
		IntVector3D location = new IntVector3D();
		location.setX((int)loc.x);
		location.setY((int)loc.y);
		location.setZ((int)loc.z);
		md.setLocation(location);
		NetQuaternion heading = new NetQuaternion();
		heading.setW((int)rot.w);
		heading.setX((int)rot.x);
		heading.setY((int)rot.y);
		heading.setZ((int)rot.z);
		md.setHeading(heading);
		me.setEventType(eventType);
		System.out.println("assembled MoveObjectAction at: "+System.currentTimeMillis());
		return me;
	}

}
