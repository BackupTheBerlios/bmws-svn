package de.mbws.client.controller;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.jme.app.GameStateManager;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;

import de.mbws.client.data.ClientPlayerData;
import de.mbws.client.data.Player;
import de.mbws.client.state.CharacterCreationState;
import de.mbws.client.state.CharacterSelectionState;
import de.mbws.client.state.MainMenuState;
import de.mbws.client.state.handler.BaseInputHandler;
import de.mbws.common.events.AbstractGameEvent;
import de.mbws.common.events.CharacterEvent;
import de.mbws.common.events.EventTypes;
import de.mbws.common.events.MoveEvent;
import de.mbws.common.events.data.generated.*;

/**
 * Description:
 * 
 * @author kerim
 * 
 */
public class CharacterController {

	private Logger logger = Logger.getLogger(CharacterController.class);

	private static CharacterController instance;
	private static String currentCharacterID;

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
		if (event.getEventType() == EventTypes.S2C_CHARACTER_RECEIVE) {
			// TODO this wont bring us to the characterselectionstate !!!
			// TODO See what we do here since its not used at the moment!
			// logger.info("Receiving Details of Character!");
			// Player player = new
			// Player(event.getCharacterDetails().getDescription().getCharacterID());
			// Characterdata characterData = new Characterdata();
			// CharacterDetails eventData = (CharacterDetails)
			// event.getEventData();
			// CharacterStatus status = new CharacterStatus();
			// status.setCoordinateX(eventData.getLocation().getX());
			// status.setCoordinateY(eventData.getLocation().getY());
			// status.setCoordinateZ(eventData.getLocation().getZ());
			// characterData.setCharacterStatus(status);
			// ClientPlayerData.getInstance().setCharacterData(characterData);
			// ClientPlayerData.getInstance().setPlayer(player);
			// logger.info("setting flag to start next state");
			// ((MainMenuState) GameStateManager.getInstance().getChild("menu"))
			// .getInputHandler().setStartNextState(true);
		} else if (event.getEventType() == EventTypes.S2C_CHARACTER_LIST_RECEIVE) {
			logger
					.info("Receiving Shortdescriptions of all Characters of Player!");

			List<CharacterData> allCharacters = ((CharactersOfPlayer) event
					.getEventData()).getCharactersOfPlayer();

			ClientPlayerData.getInstance().setAllCharactersOfPlayer(
					allCharacters);

			logger
					.info("retrieved all existing characters, going into selectionstate");
			((MainMenuState) GameStateManager.getInstance().getChild("menu"))
					.getInputHandler().requestStateSwitch(
							BaseInputHandler.GAMESTATE_CHARACTER_SELECTION);
		} else if (event.getEventType() == EventTypes.S2C_CHARACTER_ENTERS_WORLD) { // ) {
			logger.info("Start gameplay!");
			String characterID = event.getCharacterDetails()
			.getDescription().getCharacterID();
			Player player = new Player(characterID);
			List<CharacterData> allCharacters = ClientPlayerData.getInstance()
					.getAllCharactersOfPlayer();
			if (allCharacters != null) {
				for (Iterator iter = allCharacters.iterator(); iter.hasNext();) {
					CharacterData element = (CharacterData) iter.next();
					if (element.getCharacterID().trim().equals(
							characterID)) {
						ClientPlayerData.getInstance()
								.setSelectedCharacterData(element);
						break;
					}
				}
			}

			ClientPlayerData.getInstance().setPlayer(player);
			logger.info("setting flag to start next state");
			((CharacterSelectionState) GameStateManager.getInstance().getChild(
					"characterSelection")).getInputHandler()
					.requestStateSwitch(BaseInputHandler.GAMESTATE_INGAME);
		} else if (event.getEventType() == EventTypes.S2C_CHARACTER_CREATE_OK) {
            logger.info("character creation successfully");
            ((CharacterCreationState) GameStateManager.getInstance().getChild("characterCreation")).displayInfo("Character created successfully.");
        }
	}

	public AbstractGameEvent createCharacterReceiveEvent() {
		CharacterEvent event = new CharacterEvent();
		event.setEventType(EventTypes.C2S_CHARACTER_RECEIVE_REQUEST);
		return event;
	}

	public AbstractGameEvent createCharacterListReceiveEvent() {
		CharacterEvent event = new CharacterEvent();
		event.setEventType(EventTypes.C2S_CHARACTER_LIST_RECEIVE_REQUEST);
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
		heading.setW(rot.w);
		heading.setX(rot.x);
		heading.setY(rot.y);
		heading.setZ(rot.z);
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
		event.setEventType(EventTypes.C2S_CHARACTER_START_PLAYING_REQUEST);
		currentCharacterID = characterID;

		return event;
	}

	public AbstractGameEvent createEnterWorldServerEvent() {
		CharacterSelection cs = new CharacterSelection();
		cs.setCharacterID(currentCharacterID);
		CharacterEvent event = new CharacterEvent(cs);
		event.setEventType(EventTypes.C2S_CHARACTER_ENTERS_WORLD_REQUEST);

		return event;
	}

	public AbstractGameEvent createDeleteCharacterEvent(String characterID) {
		CharacterSelection cs = new CharacterSelection();
		cs.setCharacterID(characterID);
		CharacterEvent event = new CharacterEvent(cs);
		event.setEventType(EventTypes.C2S_CHARACTER_DELETE_REQUEST);

		return event;
	}

	public AbstractGameEvent createCreateCharacterEvent(String name,
			byte gender, byte race) {
		CreateCharacter cc = new CreateCharacter();
		cc.setGender(gender);
		cc.setRace(race);
		cc.setName(name);
		CharacterEvent event = new CharacterEvent(cc);
		event.setEventType(EventTypes.C2S_CHARACTER_CREATE_REQUEST);

		return event;
	}
}
