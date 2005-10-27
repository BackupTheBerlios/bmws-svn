/**
 * Copyright 2005 Please see supplied licence for details.
 */

package de.mbws.client.state.handler;

import com.jme.app.GameState;
import com.jme.app.GameStateManager;
import com.jme.input.InputHandler;
import com.jme.input.KeyBindingManager;
import com.jme.input.KeyInput;
import com.jme.input.action.InputAction;
import com.jme.input.action.InputActionEvent;

/**
 * If the escape key or return  are pressed the application will open the menu. 
 * @author Kerim
 */
public class IntroHandler extends InputHandler {
    private GameState myState;

    public IntroHandler( GameState myState ) {
        setKeyBindings();
        //setUpMouse();
        this.myState = myState;
    }

    private void setKeyBindings() {
        KeyBindingManager.getKeyBindingManager().set("escape", KeyInput.KEY_ESCAPE);
        addAction( new EnterMenuAction(), "escape", false );

        KeyBindingManager.getKeyBindingManager().set("enter", KeyInput.KEY_RETURN);
        addAction( new EnterMenuAction(), "enter", false );
    }

//    private void setUpMouse() {
//        DisplaySystem display = DisplaySystem.getDisplaySystem();
//        Mouse mouse = new AbsoluteMouse("Mouse Input", display.getWidth(),
//                display.getHeight());
//        setMouse(mouse);
//    }

    

    private class EnterMenuAction extends InputAction {
        public void performAction( InputActionEvent evt ) {
        	GameStateManager.getInstance().activateChildNamed("menu");
            myState.setActive(false); // Deactivate this (the menu) state.
        }
    }
}