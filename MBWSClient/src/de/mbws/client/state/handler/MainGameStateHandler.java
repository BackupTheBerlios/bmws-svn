package de.mbws.client.state.handler;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.jme.input.KeyBindingManager;
import com.jme.input.KeyInput;
import com.jme.input.action.InputAction;
import com.jme.input.action.InputActionEvent;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.scene.Spatial;

import de.mbws.client.MBWSClient;
import de.mbws.client.controller.CharacterController;
import de.mbws.client.controller.ClientNetworkController;
import de.mbws.client.data.ClientPlayerData;
import de.mbws.client.gui.ingame.GameDesktop;
import de.mbws.client.state.BaseGameState;
import de.mbws.common.Globals;
import de.mbws.common.events.EventTypes;

public class MainGameStateHandler extends BaseInputHandler {

	private static Logger logger = Logger.getLogger("MainGameStateHandler");
	
	public static final String PROP_KEY_FORWARD = "fwdKey";
	public static final String PROP_KEY_BACKWARD = "backKey";
	public static final String PROP_KEY_LEFT = "leftKey";
	public static final String PROP_KEY_RIGHT = "rightKey";
	// public static final String PROP_KEY_STRAFELEFT = "strfLeftKey";
	// public static final String PROP_KEY_STRAFERIGHT = "strfRightKey";

	public static final String PROP_MOVEMENT_SPEED = "moveSpeed";
	public static final String PROP_TURN_SPEED = "turnSpeed";
	public static final String PROP_MOVE_BACK_SPEED = "moveBackSpeed";
	
	public static final String PROP_HIDE_CHAT_WINDOW = "hideChatWindow";

	public static GameDesktop gd;
	private Spatial player;
	// TODO: do we really need to keep this here ?

	private float playerMovementSpeed;
	private float playerTurnSpeed;
	private float playerMoveBackSpeed;

	
	/**
	 * Supply the node to control and the api that will handle input creation.
	 * 
	 * @param node
	 *            the node we wish to move
	 * @param api
	 *            the library that will handle creation of the input.
	 */
	public MainGameStateHandler(HashMap props, BaseGameState state) {
		super(state);
		updateKeyBindings(props);

	}

	/**
	 * creates the keyboard object, allowing us to obtain the values of a
	 * keyboard as keys are pressed. It then sets the actions to be triggered
	 * based on if certain keys are pressed (WSAD).
	 * 
	 * @param api
	 */

	public void updateKeyBindings(HashMap props) {
		KeyBindingManager keyboard = KeyBindingManager.getKeyBindingManager();
		keyboard.set(PROP_KEY_FORWARD, getIntProp(props, PROP_KEY_FORWARD,
				KeyInput.KEY_W));
		keyboard.set(PROP_KEY_BACKWARD, getIntProp(props, PROP_KEY_BACKWARD,
				KeyInput.KEY_S));
		keyboard.set(PROP_KEY_LEFT, getIntProp(props, PROP_KEY_LEFT,
				KeyInput.KEY_A));
		keyboard.set(PROP_KEY_RIGHT, getIntProp(props, PROP_KEY_RIGHT,
				KeyInput.KEY_D));
		
		keyboard.set("chatwindow", KeyInput.KEY_C);
		// keyboard.set(PROP_KEY_STRAFELEFT, getIntProp(props,
		// PROP_KEY_STRAFELEFT, KeyInput.KEY_Q));
		// keyboard.set(PROP_KEY_STRAFERIGHT, getIntProp(props,
		// PROP_KEY_STRAFERIGHT, KeyInput.KEY_E));
		// TODO make this cleaner later
		keyboard.set("exit", KeyInput.KEY_ESCAPE);

		playerMovementSpeed = getFloatProp(props, PROP_MOVEMENT_SPEED, 30f);
		playerTurnSpeed = getFloatProp(props, PROP_TURN_SPEED, 1.5f);
		playerMoveBackSpeed = getFloatProp(props, PROP_MOVE_BACK_SPEED, 15f);
		setActions();
	}

	protected void setActions() {
//		addAction(new KeyNodeForwardAction(player, playerMovementSpeed),
//				PROP_KEY_FORWARD, true);
//		addAction(new KeyNodeBackwardAction(player, playerMoveBackSpeed),
//				PROP_KEY_BACKWARD, true);
//		addAction(new KeyNodeRotateRightAction(player, playerTurnSpeed),
//				PROP_KEY_RIGHT, true);
//		addAction(new KeyNodeRotateLeftAction(player, playerTurnSpeed),
//				PROP_KEY_LEFT, true);

		addAction(new ExitAction(), "exit", false);
		addAction(new ChatWindowAction(), "chatwindow", false);
	}
	
	

	public void update(float time) {
		// //TODO: We only take care of walking at the moment
		// 2 events ?? why ?
		super.update(time);
		// TODO: Take this out and register individual actions !
		Vector3f location = player.getLocalTranslation();
		Quaternion rotation = player.getLocalRotation();
		boolean forward = KeyBindingManager.getKeyBindingManager()
				.isValidCommand(PROP_KEY_FORWARD);
		boolean backward = KeyBindingManager.getKeyBindingManager()
				.isValidCommand(PROP_KEY_BACKWARD);
		int moveStatus = ClientPlayerData.getInstance().getPlayer()
				.getMoveStatus();
		// TODO: Refactor !
		if (!(forward && backward)) {
			if (!forward
					&& (moveStatus == Globals.WALKING || moveStatus == Globals.RUNNING)) {
				sendMovementEvent(Globals.STANDING,
						EventTypes.MOVEMENT_STOP_WALK, location, rotation);
			} else if (forward && moveStatus != Globals.WALKING) {
				sendMovementEvent(Globals.WALKING,
						EventTypes.MOVEMENT_START_WALK, location, rotation);
			} else if (!backward && moveStatus == Globals.WALKING_BACKWARD) {
				sendMovementEvent(Globals.STANDING,
						EventTypes.MOVEMENT_STOP_WALK, location, rotation);
			} else if (backward && moveStatus != Globals.WALKING_BACKWARD) {
				sendMovementEvent(Globals.WALKING_BACKWARD,
						EventTypes.MOVEMENT_START_WALK_BACKWARDS, location,
						rotation);
			}
		}
		boolean turnLeft = KeyBindingManager.getKeyBindingManager()
				.isValidCommand(PROP_KEY_LEFT);
		boolean turnRight = KeyBindingManager.getKeyBindingManager()
				.isValidCommand(PROP_KEY_RIGHT);
		if (!(turnRight && turnLeft)) {
			int turnStatus = ClientPlayerData.getInstance().getPlayer()
					.getTurnStatus();
			if (turnLeft && turnStatus != Globals.TURN_LEFT) {
				//logger.info("sending turn left: "+rotation.toString());
				sendTurnMovementEvent(Globals.TURN_LEFT,
						EventTypes.MOVEMENT_START_TURN_LEFT, location, rotation);
			} else if (!turnLeft && turnStatus == Globals.TURN_LEFT) {
				logger.info("sending turn stop: "+rotation.toString());
				sendTurnMovementEvent(Globals.NO_TURN,
						EventTypes.MOVEMENT_STOP_TURN, location, rotation);
			} else if (turnRight && turnStatus != Globals.TURN_RIGHT) {
				sendTurnMovementEvent(Globals.TURN_RIGHT,
						EventTypes.MOVEMENT_START_TURN_RIGHT, location,
						rotation);
			} else if (!turnRight && turnStatus == Globals.TURN_RIGHT) {
				//logger.info("sending turn stop: "+rotation.toString());
				sendTurnMovementEvent(Globals.NO_TURN,
						EventTypes.MOVEMENT_STOP_TURN, location, rotation);
			}
		}
	}

	private void sendMovementEvent(byte newMoveStatus, int movementType,
			Vector3f location, Quaternion rotation) {
		ClientPlayerData.getInstance().getPlayer().setMoveStatus(newMoveStatus);
		ClientNetworkController.getInstance().handleOutgoingEvent(
				CharacterController.getInstance().createMovementEvent(
						movementType,
						newMoveStatus,
						ClientPlayerData.getInstance().getPlayer()
								.getTurnStatus(), location, rotation));
	}

	private void sendTurnMovementEvent(byte newTurnStatus, int movementType,
			Vector3f location, Quaternion rotation) {
		ClientPlayerData.getInstance().getPlayer().setTurnStatus(newTurnStatus);
		ClientNetworkController.getInstance().handleOutgoingEvent(
				CharacterController.getInstance().createMovementEvent(
						movementType,
						ClientPlayerData.getInstance().getPlayer()
								.getMoveStatus(), newTurnStatus, location,
						rotation));
	}

	private static class ExitAction extends InputAction {
		public void performAction(InputActionEvent evt) {
			MBWSClient.exit();
		}
	}
	
	private static class ChatWindowAction extends InputAction {
		public void performAction(InputActionEvent evt) {
			gd.getChatWindow().setVisible(!gd.getChatWindow().isVisible());
		}
	}

	public void setPlayer(Spatial player) {
		this.player = player;
	}

}
