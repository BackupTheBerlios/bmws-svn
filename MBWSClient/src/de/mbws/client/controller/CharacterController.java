package de.mbws.client.controller;

import java.util.List;

import org.apache.log4j.Logger;

import com.jme.app.GameStateManager;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;

import de.mbws.client.data.ClientPlayerData;
import de.mbws.client.data.Player;
import de.mbws.client.state.CharacterSelectionState;
import de.mbws.client.state.MainMenuState;
import de.mbws.common.data.generated.CharacterStatus;
import de.mbws.common.data.generated.Characterdata;
import de.mbws.common.eventdata.generated.CharacterDetails;
import de.mbws.common.eventdata.generated.CharacterSelection;
import de.mbws.common.eventdata.generated.CharacterShortDescription;
import de.mbws.common.eventdata.generated.CharactersOfPlayer;
import de.mbws.common.eventdata.generated.IntVector3D;
import de.mbws.common.eventdata.generated.MoveData;
import de.mbws.common.eventdata.generated.NetQuaternion;
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
			logger.info("Receiving Details of Character!");
			Player player = new Player(event.getCharacterDetails().getDescription().getCharacterID());
			Characterdata characterData = new Characterdata();
			CharacterDetails eventData = (CharacterDetails) event.getEventData();
			CharacterStatus status = new CharacterStatus();
			status.setCoordinateX(eventData.getLocation().getX());
			status.setCoordinateY(eventData.getLocation().getY());
			status.setCoordinateZ(eventData.getLocation().getZ());
			characterData.setCharacterStatus(status);
			ClientPlayerData.getInstance().setCharacterData(characterData);
			ClientPlayerData.getInstance().setPlayer(player);
			logger.info("setting flag to start next state");
			((MainMenuState) GameStateManager.getInstance().getChild("menu"))
					.getInput().setStartNextState(true);
		} else if (event.getEventType() == EventTypes.CHARACTER_LIST_RECEIVE) {
			logger.info("Receiving all Characters of Player!");

			List<CharacterShortDescription> allCharacters = ((CharactersOfPlayer) event
					.getEventData()).getDescriptions();

			ClientPlayerData.getInstance().setAllCharactersOfPlayer(
					allCharacters);
			if (allCharacters != null) {

			}
			logger
					.info("retrieved all existing characters, going into selectionstate");
			((MainMenuState) GameStateManager.getInstance().getChild("menu"))
					.getInput().setStartNextState(true);
		} else if (event.getEventType() == EventTypes.CHARACTER_START_PLAYING) {
			logger.info("Start gameplay!");
			Player player = new Player(event.getCharacterDetails().getDescription().getCharacterID());
			Characterdata characterData = new Characterdata();
            CharacterDetails eventData = (CharacterDetails) event.getEventData();
			CharacterStatus status = new CharacterStatus();
			status.setCoordinateX(eventData.getLocation().getX());
			status.setCoordinateY(eventData.getLocation().getY());
			status.setCoordinateZ(eventData.getLocation().getZ());
			characterData.setCharacterStatus(status);
			ClientPlayerData.getInstance().setCharacterData(characterData);
			ClientPlayerData.getInstance().setPlayer(player);
			logger.info("setting flag to start next state");
			((CharacterSelectionState) GameStateManager.getInstance().getChild(
					"characterSelection")).getInput().setStartGame(true);
		}
	}

	public AbstractGameEvent createCharacterReceiveEvent() {
		CharacterEvent event = new CharacterEvent();
		event.setEventType(EventTypes.CHARACTER_RECEIVE_REQUEST);
		return event;
	}

	public AbstractGameEvent createCharacterListReceiveEvent() {
		CharacterEvent event = new CharacterEvent();
		event.setEventType(EventTypes.CHARACTER_LIST_RECEIVE_REQUEST);
		return event;
	}

	public AbstractGameEvent createMovementEvent(int eventType,
			byte movementType, byte turnType, Vector3f loc, Quaternion rot) {
		MoveData md = new MoveData();
		MoveEvent me = new MoveEvent(md);
		// TODO change int to float ?
		md
				.setObjectID(ClientPlayerData.getInstance().getPlayer()
						.getObjectID());
		IntVector3D location = new IntVector3D();
		location.setX((int) loc.x);
		location.setY((int) loc.y);
		location.setZ((int) loc.z);
		md.setLocation(location);
		NetQuaternion heading = new NetQuaternion();
		heading.setW((int) rot.w);
		heading.setX((int) rot.x);
		heading.setY((int) rot.y);
		heading.setZ((int) rot.z);
		md.setHeading(heading);
		me.setEventType(eventType);
		md.setMovementType(movementType);
		md.setTurnType(turnType);
		logger
				.info("assembled MoveObjectAction(EventType,MovementType,Turntype) "
						+ eventType
						+ ","
						+ movementType
						+ ","
						+ turnType
						+ " at: " + System.currentTimeMillis());
		return me;
	}

	public AbstractGameEvent createCharacterStartPlayingEvent(String characterID) {
		CharacterSelection cs = new CharacterSelection();
		cs.setCharacterID(characterID);
		CharacterEvent event = new CharacterEvent(cs);
		event.setEventType(EventTypes.CHARACTER_START_PLAYING_REQUEST);

		return event;
	}
}
