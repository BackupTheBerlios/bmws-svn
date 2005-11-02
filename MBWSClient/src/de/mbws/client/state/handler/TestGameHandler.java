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
import com.jme.scene.Spatial;

import de.mbws.client.controller.CharacterController;

/**
 * Input Handler for the Flag Rush game. This controls a supplied spatial
 * allowing us to move it forward, backward and rotate it left and right.
 * @author Mark Powell
 *
 */
public class TestGameHandler extends InputHandler {

    /**
     * Supply the node to control and the api that will handle input creation.
     * @param node the node we wish to move
     * @param api the library that will handle creation of the input.
     */
    public TestGameHandler(Spatial node, String api) {

        setKeyBindings(api);
        setActions(node);
        setStatusRelatedKeys();

    }
    
    //TODO: Kerim: eventually we should extend the existing actions instead of
    //making a double binding to a key
    private void setStatusRelatedKeys() {
    	KeyBindingManager.getKeyBindingManager().set("walk",
				KeyInput.KEY_W);
		addAction(new ForwardWalkAction(), "walk", false);
    }

    private static class ForwardWalkAction extends InputAction {
		public void performAction(InputActionEvent evt) {
			CharacterController.getInstance().startWalking();
		}
	}
    /**
     * creates the keyboard object, allowing us to obtain the values of a keyboard as keys are
     * pressed. It then sets the actions to be triggered based on if certain keys are pressed (WSAD).
     * @param api
     */
    private void setKeyBindings(String api) {
        KeyBindingManager keyboard = KeyBindingManager.getKeyBindingManager();

        keyboard.set("forward", KeyInput.KEY_W);
        keyboard.set("backward", KeyInput.KEY_S);
        keyboard.set("turnRight", KeyInput.KEY_D);
        keyboard.set("turnLeft", KeyInput.KEY_A);
    }

    /**
     * assigns action classes to triggers. These actions handle moving the node forward, backward and 
     * rotating it.
     * @param node the node to control.
     */
    private void setActions(Spatial node) {
        KeyNodeForwardAction forward = new KeyNodeForwardAction(node, 30f);
        addAction(forward, "forward", true);
        
        KeyNodeBackwardAction backward = new KeyNodeBackwardAction(node, 15f);
        addAction(backward, "backward", true);
        
        KeyNodeRotateRightAction rotateRight = new KeyNodeRotateRightAction(node, 5f);
        rotateRight.setLockAxis(node.getLocalRotation().getRotationColumn(1));
        addAction(rotateRight, "turnRight", true);
        
        KeyNodeRotateLeftAction rotateLeft = new KeyNodeRotateLeftAction(node, 5f);
        rotateLeft.setLockAxis(node.getLocalRotation().getRotationColumn(1));
        addAction(rotateLeft, "turnLeft", true);
    }
}
