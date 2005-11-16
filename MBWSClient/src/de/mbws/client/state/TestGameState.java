/**
 * Copyright 2005 Please see supplied licence for details.
 */
package de.mbws.client.state;

import java.util.HashMap;

import org.apache.log4j.Logger;

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
import com.jme.scene.shape.Box;
import com.jme.scene.state.LightState;
import com.jme.system.DisplaySystem;

import de.mbws.client.MBWSClient;
import de.mbws.client.data.ObjectManager;
import de.mbws.client.eventactions.AbstractEventAction;
import de.mbws.client.net.ActionQueue;
import de.mbws.client.state.handler.TestGameHandler;

public class TestGameState extends StandardGameState {

	private Node player;
	protected InputHandler input;
	protected DisplaySystem display;

	// protected Skybox skybox;

	// The chase camera, this will follow our player as he zooms around the
	// level
	private ChaseCamera chaser;

	/**
	 * the actionQueue is where we store those actions we receive from the net.
	 */
	private ActionQueue actionQueue = MBWSClient.actionQueue;

	private static Logger logger = Logger.getLogger("TestGameState");

	public TestGameState(String name) {
		super(name);
		this.display = DisplaySystem.getDisplaySystem();
		ObjectManager.initialize(rootNode, display);
		
		// Light the world
		buildLighting();
		// Build the player
		buildPlayer();
		// build the chase cam
		buildChaseCamera();
		// build the player input
		buildInput();
		// build the world around the player
		buildTerrain();
		// update the scene graph for rendering
		rootNode.updateGeometricState(0.0f, true);
		rootNode.updateRenderState();

	}

	private void buildInput() {
		input = new TestGameHandler(player, null);
	}

	/**
	 * we are going to build the player object here. For now, we will use a box
	 * as a place holder. This is a good demonstration that you don't always
	 * need your graphics in place before you can start working on your
	 * application.
	 * 
	 */
	// TODO: Kerim do that in objectmanager ?
	private void buildPlayer() {
		player = ObjectManager.createPlayer();
	}

	private void buildTerrain() {
		Box b = new Box("terrainbox", new Vector3f(), 200f, 0.1f, 200f);
		b.setModelBound(new BoundingBox());
		b.updateModelBound();
		Node player = new Node("terrainnode");
		Vector3f location = new Vector3f(0,0,0);
		player.setLocalTranslation(location);

		rootNode.attachChild(player);
		player.attachChild(b);
		player.updateWorldBound();
		
		
//		try {
//			Image result = null;
//			TerrainBlock tb;
//
//			File file = new File("map/demo.jpg");
//			ImageInputStream iis = ImageIO
//					.createImageInputStream(new FileInputStream(file));
//			ImageReader reader = new JPEGImageReader(new JPEGImageReaderSpi());
//			reader.setInput(iis, true);
//			result = reader.read(0);
//
//			ImageBasedHeightMap heightMap = new ImageBasedHeightMap(result);
//			// MidPointHeightMap heightMap = new MidPointHeightMap(64, 1f);
//			// Scale the data
//			Vector3f terrainScale = new Vector3f(10f, 1f, 10f);
//			// create a terrainblock
//			tb = new TerrainBlock("Terrain", heightMap.getSize(), terrainScale,
//					heightMap.getHeightMap(), new Vector3f(0, 0, 0), false);
//
//			tb.setModelBound(new BoundingBox());
//			tb.updateModelBound();
//
//			// generate a terrain texture with 2 textures
//			ProceduralTextureGenerator pt = new ProceduralTextureGenerator(
//					heightMap);
//			pt.addTexture(new ImageIcon(TestGameState.class.getClassLoader()
//					.getResource("texture/water.png")), 0, 0, 5);
//			pt.addTexture(new ImageIcon(TestGameState.class.getClassLoader()
//					.getResource("texture/dirt.jpg")), 1, 10, 20);
//			pt.addTexture(new ImageIcon(TestGameState.class.getClassLoader()
//					.getResource("texture/grassb.png")), 15, 70, 90);
//			pt.addTexture(new ImageIcon(TestGameState.class.getClassLoader()
//					.getResource("texture/highest.jpg")), 60, 130, 256);
//			pt.createTexture(32);
//
//			// assign the texture to the terrain
//			TextureState ts = display.getRenderer().createTextureState();
//			Texture t1 = TextureManager.loadTexture(pt.getImageIcon()
//					.getImage(), Texture.MM_LINEAR_LINEAR, Texture.FM_LINEAR,
//					true);
//			ts.setTexture(t1,0);
//
//			tb.setRenderState(ts);
//			tb.setRenderQueueMode(Renderer.QUEUE_OPAQUE);
//			rootNode.attachChild(tb);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

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
		props.put(ThirdPersonMouseLook.PROP_MAXROLLOUT, "40");
		props.put(ThirdPersonMouseLook.PROP_MINROLLOUT, "3");
		props.put(ChaseCamera.PROP_TARGETOFFSET, targetOffset);
		chaser = new ChaseCamera(cam, player, props);
		chaser.setActionSpeed(100f);
	}

	/**
	 * This is where derived classes are supposed to put their game logic. Gets
	 * called between the input.update and rootNode.updateGeometricState calls.
	 * 
	 * <p>
	 * Much like the structure of <code>SimpleGame</code>.
	 * </p>
	 * 
	 * @param tpf
	 *            The time since the last frame.
	 */
	protected void stateUpdate(float tpf) {
		// update the keyboard input (move the player around)
		input.update(tpf);
		// update the chase camera to handle the player moving around.
		chaser.update(tpf);

		float camMinHeightPlayer = player.getWorldTranslation().y + 2f;
		cam.getLocation().y = camMinHeightPlayer;
		cam.update();

		AbstractEventAction action = actionQueue.deQueue();

		if (action != null) {
			logger.info("Message " + action.getEventType() + " dequeued at "
					+ System.currentTimeMillis());
			action.performAction();
			logger.info("Message " + action.getEventType() + " performed at "
					+ System.currentTimeMillis());
		}
		ObjectManager.update(tpf);

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
	 * @param tpf
	 *            The time since the last frame.
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
		// TODO: SUCK MY DICK WHAT A HECK OF A BUG !!!! Take that out
		// later when all works with a correct mouse
		MouseInput.get().setCursorVisible(false);
		super.onActivate();
	}

}