/**
 * Copyright 2005 Please see supplied licence for details.
 */
package de.mbws.client.state.handler;

import java.util.HashMap;

import com.jme.input.KeyBindingManager;
import com.jme.input.KeyInput;
import com.jme.input.ThirdPersonHandler;
import com.jme.input.action.InputAction;
import com.jme.input.action.InputActionEvent;
import com.jme.input.thirdperson.ThirdPersonBackwardAction;
import com.jme.input.thirdperson.ThirdPersonForwardAction;
import com.jme.input.thirdperson.ThirdPersonLeftAction;
import com.jme.input.thirdperson.ThirdPersonRightAction;
import com.jme.input.thirdperson.ThirdPersonStrafeLeftAction;
import com.jme.input.thirdperson.ThirdPersonStrafeRightAction;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.scene.Spatial;

import de.mbws.client.MBWSClient;
import de.mbws.client.controller.CharacterController;
import de.mbws.client.controller.ClientNetworkController;
import de.mbws.client.data.ClientPlayerData;
import de.mbws.common.Globals;
import de.mbws.common.events.EventTypes;

public class TestGameHandler extends ThirdPersonHandler {


	private Spatial player;

	/**
	 * Supply the node to control and the api that will handle input creation.
	 * 
	 * @param node
	 *            the node we wish to move
	 * @param api
	 *            the library that will handle creation of the input.
	 */
	public TestGameHandler(Spatial target, Camera cam, HashMap props) {
		super(target,cam,props);
		player = target;
		updateKeyBindings(null);

	}
	
	

	/**
	 * creates the keyboard object, allowing us to obtain the values of a
	 * keyboard as keys are pressed. It then sets the actions to be triggered
	 * based on if certain keys are pressed (WSAD).
	 * 
	 * @param api
	 */
	public void updateKeyBindings(HashMap props) {
		super.updateKeyBindings(props);
		KeyBindingManager keyboard = KeyBindingManager.getKeyBindingManager();

		// TODO make this cleaner later
		keyboard.set("exit", KeyInput.KEY_ESCAPE);
		
	}

	protected void setActions() {
        addAction( new ThirdPersonForwardAction( this, 30f ), PROP_KEY_FORWARD, true );
        addAction( new ThirdPersonBackwardAction( this, 15f ), PROP_KEY_BACKWARD, true );
        addAction( new ThirdPersonRightAction( this, 5f ), PROP_KEY_RIGHT, true );
        addAction( new ThirdPersonLeftAction( this, 5f ), PROP_KEY_LEFT, true );
        addAction( new ThirdPersonStrafeRightAction( this, 5f ), PROP_KEY_STRAFERIGHT, true );
        addAction( new ThirdPersonStrafeLeftAction( this, 5f ), PROP_KEY_STRAFELEFT, true );
        addAction( new ExitAction(), "exit", false);
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
				sendTurnMovementEvent(Globals.TURN_LEFT,
						EventTypes.MOVEMENT_START_TURN_LEFT, location, rotation);
			} else if (!turnLeft && turnStatus == Globals.TURN_LEFT) {
				sendTurnMovementEvent(Globals.NO_TURN,
						EventTypes.MOVEMENT_STOP_TURN, location, rotation);
			} else if (turnRight && turnStatus != Globals.TURN_RIGHT) {
				sendTurnMovementEvent(Globals.TURN_RIGHT,
						EventTypes.MOVEMENT_START_TURN_RIGHT, location,
						rotation);
			} else if (!turnRight && turnStatus == Globals.TURN_RIGHT) {
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

}
