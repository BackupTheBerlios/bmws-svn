/**
 * Copyright 2005 Please see supplied licence for details.
 */
package de.mbws.client.state.handler;

import com.jme.input.InputHandler;
import com.jme.input.KeyBindingManager;
import com.jme.input.KeyInput;
import com.jme.input.action.InputAction;
import com.jme.input.action.InputActionEvent;
import com.jme.input.action.KeyNodeBackwardAction;
import com.jme.input.action.KeyNodeForwardAction;
import com.jme.input.action.KeyNodeRotateLeftAction;
import com.jme.input.action.KeyNodeRotateRightAction;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.scene.Spatial;

import de.mbws.client.MBWSClient;
import de.mbws.client.controller.CharacterController;
import de.mbws.client.controller.ClientNetworkController;
import de.mbws.client.data.ClientPlayerData;
import de.mbws.common.Globals;
import de.mbws.common.events.EventTypes;

public class TestGameHandler extends InputHandler {

	private static final String STAND = "stand";

	private static final String TURN_LEFT = "turnLeft";

	private static final String TURN_RIGHT = "turnRight";

	private static final String BACKWARD = "backward";

	private static final String FORWARD = "forward";

	private Spatial player;

	/**
	 * Supply the node to control and the api that will handle input creation.
	 * 
	 * @param node
	 *            the node we wish to move
	 * @param api
	 *            the library that will handle creation of the input.
	 */
	public TestGameHandler(Spatial node, String api) {

		setKeyBindings(api);
		setActions(node);
		// setStatusRelatedKeys();
		player = node;

	}

	// TODO: Kerim: eventually we should extend the existing actions instead of
	// making a double binding to a key
	// private void setStatusRelatedKeys() {
	// KeyBindingManager.getKeyBindingManager().set("walk",
	// KeyInput.KEY_W);
	// addAction(new ForwardWalkAction(), "walk", false);
	// }
	//
	// private static class ForwardWalkAction extends InputAction {
	// public void performAction(InputActionEvent evt) {
	// CharacterController.getInstance().startWalking();
	// }
	// }
	/**
	 * creates the keyboard object, allowing us to obtain the values of a
	 * keyboard as keys are pressed. It then sets the actions to be triggered
	 * based on if certain keys are pressed (WSAD).
	 * 
	 * @param api
	 */
	private void setKeyBindings(String api) {
		KeyBindingManager keyboard = KeyBindingManager.getKeyBindingManager();

		keyboard.set(FORWARD, KeyInput.KEY_W);
		keyboard.set(BACKWARD, KeyInput.KEY_S);
		keyboard.set(TURN_RIGHT, KeyInput.KEY_D);
		keyboard.set(TURN_LEFT, KeyInput.KEY_A);

		// TODO make this cleaner later
		keyboard.set("exit", KeyInput.KEY_ESCAPE);
		addAction(new ExitAction(), "exit", false);
	}

	/**
	 * assigns action classes to triggers. These actions handle moving the node
	 * forward, backward and rotating it.
	 * 
	 * @param node
	 *            the node to control.
	 */
	private void setActions(Spatial node) {
		KeyNodeForwardAction forward = new KeyNodeForwardAction(node, 30f);
		addAction(forward, FORWARD, true);

		KeyNodeBackwardAction backward = new KeyNodeBackwardAction(node, 15f);
		addAction(backward, BACKWARD, true);

		KeyNodeRotateRightAction rotateRight = new KeyNodeRotateRightAction(
				node, 5f);
		rotateRight.setLockAxis(node.getLocalRotation().getRotationColumn(1));
		addAction(rotateRight, TURN_RIGHT, true);

		KeyNodeRotateLeftAction rotateLeft = new KeyNodeRotateLeftAction(node,
				5f);
		rotateLeft.setLockAxis(node.getLocalRotation().getRotationColumn(1));
		addAction(rotateLeft, TURN_LEFT, true);
	}

	public void update(float time) {
		// //TODO: We only take care of walking at the moment
		// 2 events ?? why ?
		super.update(time);
		//TODO: Take this out and register individual actions !
		Vector3f location = player.getLocalTranslation();
		Quaternion rotation = player.getLocalRotation();
		boolean forward = KeyBindingManager.getKeyBindingManager()
				.isValidCommand(FORWARD);
		boolean backward = KeyBindingManager.getKeyBindingManager()
				.isValidCommand(BACKWARD);
		int moveStatus = ClientPlayerData.getInstance().getPlayer()
				.getMoveStatus();
		//TODO: Refactor !
		if (!(forward && backward)) {
			if (!forward
					&& (moveStatus == Globals.WALKING || moveStatus == Globals.RUNNING)) {
				sendMovementEvent(Globals.STANDING, EventTypes.MOVEMENT_STOP_WALK,location, rotation);
			} else if (forward && moveStatus != Globals.WALKING) {
				sendMovementEvent(Globals.WALKING, EventTypes.MOVEMENT_START_WALK,location, rotation);
			} else if (!backward && moveStatus == Globals.WALKING_BACKWARD) {
				sendMovementEvent(Globals.STANDING, EventTypes.MOVEMENT_STOP_WALK,location, rotation);
			} else if (backward && moveStatus != Globals.WALKING_BACKWARD) {
				sendMovementEvent(Globals.WALKING_BACKWARD, EventTypes.MOVEMENT_START_WALK_BACKWARDS,location, rotation);
			}
		}
		boolean turnLeft = KeyBindingManager.getKeyBindingManager()
				.isValidCommand(TURN_LEFT);
		boolean turnRight = KeyBindingManager.getKeyBindingManager()
				.isValidCommand(TURN_RIGHT);
		if (!(turnRight && turnLeft)) {
			int turnStatus = ClientPlayerData.getInstance().getPlayer()
					.getTurnStatus();
			if (turnLeft && turnStatus != Globals.TURN_LEFT) {
				sendTurnMovementEvent(Globals.TURN_LEFT, EventTypes.MOVEMENT_START_TURN_LEFT,location, rotation);
			} else if (!turnLeft && turnStatus == Globals.TURN_LEFT) {
				sendTurnMovementEvent(Globals.NO_TURN, EventTypes.MOVEMENT_STOP_TURN,location, rotation);
			} else if (turnRight && turnStatus != Globals.TURN_RIGHT) {
				sendTurnMovementEvent(Globals.TURN_RIGHT, EventTypes.MOVEMENT_START_TURN_RIGHT,location, rotation);
			} else if (!turnRight && turnStatus == Globals.TURN_RIGHT) {
				sendTurnMovementEvent(Globals.NO_TURN, EventTypes.MOVEMENT_STOP_TURN,location, rotation);
			}
		}
	}
	private void sendMovementEvent(byte newMoveStatus, int movementType, Vector3f location, Quaternion rotation) {
		ClientPlayerData.getInstance().getPlayer().setMoveStatus(
				newMoveStatus);
		ClientNetworkController.getInstance().handleOutgoingEvent(
				CharacterController.getInstance()
						.createMovementEvent(movementType,
								newMoveStatus,
								ClientPlayerData.getInstance()
										.getPlayer().getTurnStatus(),
								location, rotation));
	}
	
	private void sendTurnMovementEvent(byte newTurnStatus, int movementType, Vector3f location, Quaternion rotation) {
		ClientPlayerData.getInstance().getPlayer().setTurnStatus(
				newTurnStatus);
		ClientNetworkController.getInstance().handleOutgoingEvent(
				CharacterController.getInstance()
						.createMovementEvent(movementType,
								ClientPlayerData.getInstance()
								.getPlayer().getMoveStatus(),
								newTurnStatus,
								location, rotation));
	}

	private static class ExitAction extends InputAction {
		public void performAction(InputActionEvent evt) {
			MBWSClient.exit();
		}
	}

}
