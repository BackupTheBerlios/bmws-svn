/**
 * Copyright 2005 Please see supplied licence for details.
 */
package de.mbws.client.state;

import java.util.HashMap;

import com.jme.app.StandardGameState;
import com.jme.bounding.BoundingBox;
import com.jme.input.ChaseCamera;
import com.jme.input.InputHandler;
import com.jme.input.MouseInput;
import com.jme.input.thirdperson.ThirdPersonMouseLook;
import com.jme.light.DirectionalLight;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Node;
import com.jme.scene.Skybox;
import com.jme.scene.shape.Box;
import com.jme.scene.state.LightState;
import com.jme.system.DisplaySystem;

import de.mbws.client.state.handler.TestGameHandler;

public class TestGameState extends StandardGameState {

	private Node player;

	// private ChaseCamera chaser;
	protected InputHandler input;

	protected DisplaySystem display;

	protected Skybox skybox;

	// The chase camera, this will follow our player as he zooms around the
	// level
	private ChaseCamera chaser;

	

	public TestGameState(String name) {
		super(name);
		display = DisplaySystem.getDisplaySystem();
		
//		 Light the world
		buildLighting();
		// Build the player
		buildPlayer();
		
		// build the chase cam
		buildChaseCamera();
		// build the player input
		buildInput();

		// update the scene graph for rendering
		rootNode.updateGeometricState(0.0f, true);
		rootNode.updateRenderState();
	}
	
	private void buildInput() {
		input = new TestGameHandler(player,null);
	}
	
	
	
	
	/**
	 * we are going to build the player object here. For now, we will use a box
	 * as a place holder. This is a good demonstration that you don't always
	 * need your graphics in place before you can start working on your
	 * application.
	 * 
	 */
	private void buildPlayer() {
		// box stand in
		Box b = new Box("box", new Vector3f(), 0.35f, 0.25f, 0.5f);
		b.setModelBound(new BoundingBox());
		b.updateModelBound();

		player = new Node("Player Node");
		player.setLocalTranslation(new Vector3f(100, 0, 100));
		rootNode.attachChild(player);
		player.attachChild(b);
		player.updateWorldBound();
	}

	

	/**
	 * creates a light for the terrain.
	 */
	private void buildLighting() {
		/** Set up a basic, default light. */
		DirectionalLight light = new DirectionalLight();
		light.setDiffuse(new ColorRGBA(1.0f, 1.0f, 1.0f, 1.0f));
		light.setAmbient(new ColorRGBA(0.5f, 0.5f, 0.5f, 1.0f));
		light.setDirection(new Vector3f(1, -1, 0));
		light.setEnabled(true);

		/** Attach the light to a lightState and the lightState to rootNode. */
		LightState lightState = display.getRenderer().createLightState();
		lightState.setEnabled(true);
		lightState.attach(light);
		rootNode.setRenderState(lightState);
	}

	


	/**
	 * set the basic parameters of the chase camera. This includes the offset.
	 * We want to be behind the vehicle and a little above it. So we will the
	 * offset as 0 for x and z, but be 1.5 times higher than the node.
	 * 
	 * We then set the roll out parameters (2 units is the closest the camera
	 * can get, and 5 is the furthest).
	 * 
	 */
	private void buildChaseCamera() {
		Vector3f targetOffset = new Vector3f();
		targetOffset.y = ((BoundingBox) player.getWorldBound()).yExtent * 1.5f;
		HashMap props = new HashMap();
		props.put(ThirdPersonMouseLook.PROP_MAXROLLOUT, "60");
		props.put(ThirdPersonMouseLook.PROP_MINROLLOUT, "3");
		props.put(ChaseCamera.PROP_TARGETOFFSET, targetOffset);
		chaser = new ChaseCamera(cam, player, props);
		chaser.setActionSpeed(100f);
	}

	
	 /**
	 * This is where derived classes are supposed to put their game logic.
	 * Gets called between the input.update and
	 * rootNode.updateGeometricState calls.
	 *
	 * <p>
	 * Much like the structure of <code>SimpleGame</code>.
	 * </p>
	 *
	 * @param tpf The time since the last frame.
	 */
	protected void stateUpdate(float tpf) {
//		 update the keyboard input (move the player around)
		input.update(tpf);
		// update the chase camera to handle the player moving around.
		chaser.update(tpf);

	
		float camMinHeightPlayer = player.getWorldTranslation().y + 2f;
		cam.getLocation().y = camMinHeightPlayer;
		cam.update();

	
		rootNode.updateGeometricState(tpf, true);
	}

    /**
	 * This is where derived classes are supposed to put their render logic.
	 * Gets called before the rootNode gets rendered.
	 *
	 * <p>
	 * Much like the structure of <code>SimpleGame</code>.
	 * </p>
	 *
	 * @param tpf The time since the last frame.
	 */
	protected void stateRender(float tpf) {
		display.getRenderer().clearBuffers();
		super.stateRender(tpf);
	}
	
	/**
	 * @see com.jme.app.StandardGameState#onActivate()
	 */
	public void onActivate() {
		display.setTitle("Test Game State System - Game State");
		//TODO: SUCK MY DICK WHAT A HECK OF A BUG !!!! Take that out 
		//later when all works with a correct mouse
		MouseInput.get().setCursorVisible(false);
		super.onActivate();
	}
}