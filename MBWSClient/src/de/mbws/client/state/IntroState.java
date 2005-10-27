/**
 * Copyright 2005 Please see supplied licence for details.
 */
package de.mbws.client.state;

import com.jme.app.GameState;
import com.jme.app.StandardGameState;
import com.jme.input.InputHandler;
import com.jme.renderer.Renderer;
import com.jme.scene.Text;
import com.jme.scene.state.LightState;
import com.jme.system.DisplaySystem;

import de.mbws.client.state.handler.IntroHandler;

/** 
 * @author Kerim
 */
public class IntroState extends StandardGameState {
	
	
	/** Our display system. */
	private DisplaySystem display;

    private Text text;

    private InputHandler input;
	
	public IntroState(String name) {
		super(name);
		
		display = DisplaySystem.getDisplaySystem();
		initInput();
		initText();
		
		rootNode.setLightCombineMode(LightState.OFF);
		rootNode.setRenderQueueMode(Renderer.QUEUE_ORTHO);
		rootNode.updateRenderState();
		rootNode.updateGeometricState(0, true);
    }
	
	/**
	 * @see com.jme.app.StandardGameState#onActivate()
	 */
	public void onActivate() {
		display.setTitle("BMWS - INTRO");
		super.onActivate();
	}
	
	/**
	 * Inits the input handler we will to end the intro.
	 */
	protected void initInput() {
		input = new IntroHandler( this );
	}
	

	/**
	 * Inits the button placed at the center of the screen.
	 * TODO: Kerim : We dont need that later when we play an intro
	 */
	private void initText() {
        text = Text.createDefaultTextLabel( "info" );
        text.print( "press enter (should play a movie)" );
        text.getLocalTranslation().set( 0, 100, 0 );
		
        rootNode.attachChild( text );
	}
	
	/**
	 * Updates input and button.
	 * 
	 * @param tpf The time since last frame.
	 * @see GameState#update(float)
	 */
	protected void stateUpdate(float tpf) {
		input.update(tpf);
		rootNode.updateGeometricState(tpf, true);
	}
	
}