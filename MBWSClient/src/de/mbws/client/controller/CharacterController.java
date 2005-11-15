 package de.mbws.client.controller;

import org.apache.log4j.Logger;

import com.jme.app.GameStateManager;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;

import de.mbws.client.data.ClientPlayerData;
import de.mbws.client.data.Player;
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
			Player player = new Player(event.getPlayerInfo().getObject().getObjectID());
			Characterdata characterData = new Characterdata();
			PlayerInfo eventData = (PlayerInfo) event.getEventData();
			CharacterStatus status = new CharacterStatus();
			status.setCoordinateX(eventData.getObject().getLocation()
					.getX());
			status.setCoordinateY(eventData.getObject().getLocation()
					.getY());
			status.setCoordinateZ(eventData.getObject().getLocation()
					.getZ());
			characterData.setCharacterStatus(status);
			ClientPlayerData.getInstance().setCharacterData(characterData);
			ClientPlayerData.getInstance().setPlayer(player);
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


//	public AbstractGameEvent createStopRunningEvent(byte movementType, byte turnType, Vector3f loc, Quaternion rot) {
//		return createMovementEvent(EventTypes.MOVEMENT_STOP_RUN, movementType, turnType, loc,  rot);
//	}
//	public AbstractGameEvent createStopWalkingEvent(byte movementType, byte turnType, Vector3f loc, Quaternion rot) {
//		return createMovementEvent(EventTypes.MOVEMENT_STOP_WALK, movementType, turnType, loc,  rot);
//	}
//	public AbstractGameEvent createStopTurnEvent(byte movementType, byte turnType, Vector3f loc, Quaternion rot) {
//		return createMovementEvent(EventTypes.MOVEMENT_STOP_TURN, movementType, turnType, loc,  rot);
//	}	
//	
//	public AbstractGameEvent createStartRunningEvent(byte movementType, byte turnType, Vector3f loc, Quaternion rot) {
//		return createMovementEvent(EventTypes.MOVEMENT_START_RUN, movementType, turnType, loc,  rot);
//	}
//	public AbstractGameEvent createStartWalkingEvent(byte movementType, byte turnType, Vector3f loc, Quaternion rot) {
//		return createMovementEvent(EventTypes.MOVEMENT_START_WALK, movementType, turnType, loc,  rot);
//	}
//	public AbstractGameEvent createStartWalkingBackwardsEvent(byte movementType, byte turnType, Vector3f loc, Quaternion rot) {
//		return createMovementEvent(EventTypes.MOVEMENT_START_WALK_BACKWARDS, movementType, turnType, loc,  rot);
//	}
//	public AbstractGameEvent createStartTurnRightEvent(byte movementType, byte turnType, Vector3f loc, Quaternion rot) {
//		return createMovementEvent(EventTypes.MOVEMENT_START_TURN_RIGHT, movementType, turnType, loc,  rot);
//	}
//	public AbstractGameEvent createStartTurnLeftEvent(byte movementType, byte turnType, Vector3f loc, Quaternion rot) {
//		return createMovementEvent(EventTypes.MOVEMENT_START_TURN_LEFT, movementType, turnType, loc,  rot);
//	}
	
	public AbstractGameEvent createMovementEvent(int eventType,byte movementType, byte turnType, Vector3f loc, Quaternion rot) {
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
		md.setMovementType(movementType);
		md.setTurnType(turnType);
		System.out.println("assembled MoveObjectAction at: "+System.currentTimeMillis());
		return me;
	}

}
