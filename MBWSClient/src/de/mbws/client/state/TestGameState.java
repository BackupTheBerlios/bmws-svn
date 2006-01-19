/**
 * Copyright 2005 Please see supplied licence for details.
 */
package de.mbws.client.state;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.swing.ImageIcon;

import org.apache.log4j.Logger;

import com.jme.app.StandardGameState;
import com.jme.bounding.BoundingBox;
import com.jme.bounding.BoundingSphere;
import com.jme.bounding.BoundingVolume;
import com.jme.image.Texture;
import com.jme.input.ChaseCamera;
import com.jme.input.InputHandler;
import com.jme.input.KeyInput;
import com.jme.input.MouseInput;
import com.jme.input.ThirdPersonHandler;
import com.jme.input.thirdperson.ThirdPersonMouseLook;
import com.jme.light.DirectionalLight;
import com.jme.math.FastMath;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Node;
import com.jme.scene.Skybox;
import com.jme.scene.state.AlphaState;
import com.jme.scene.state.LightState;
import com.jme.scene.state.TextureState;
import com.jme.system.DisplaySystem;
import com.jme.util.TextureManager;
import com.jmex.model.XMLparser.JmeBinaryReader;
import com.jmex.model.XMLparser.Converters.MaxToJme;
import com.jmex.terrain.TerrainBlock;
import com.jmex.terrain.util.ProceduralTextureGenerator;
import com.jmex.terrain.util.RawHeightMap;

import de.mbws.client.MBWSClient;
import de.mbws.client.data.ClientPlayerData;
import de.mbws.client.data.ObjectManager;
import de.mbws.client.eventactions.AbstractEventAction;
import de.mbws.client.experimental.Compass;
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
		display.getRenderer().setBackgroundColor(ColorRGBA.blue);
		ObjectManager.initialize(rootNode, display);
		// build the world around the player
		buildEnvironment();
		buildTerrain();
		// Light the world
		buildLighting();
		// Build the player
		buildPlayer();
		// build the chase cam
		buildChaseCamera();
		// build the player input
		// buildInput();
		// build trees
		// buildSkyBox();

		// update the scene graph for rendering
		rootNode.updateGeometricState(0.0f, true);
		rootNode.updateRenderState();

	}

	private void buildInput() {
		// input = new TestGameHandler(player, null);
		// input.setActionSpeed(250.0f);
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
		RawHeightMap rhm = new RawHeightMap(TestGameState.class
				.getClassLoader().getResource("resources/00000_00000_000.raw")
				.getFile(), 256);

		// Create a terrain block. Our integer height values will scale on the
		// map 2x larger x,
		// and 2x larger z. Our map's origin will be the regular origin, and it
		// won't create an
		// AreaClodMesh from it.
		TerrainBlock tb = new TerrainBlock("block", 256, new Vector3f(5, 5, 5),
				rhm.getHeightMap(), new Vector3f(0, 0, 0), false);

		URL grass = TestGameState.class.getClassLoader().getResource(
				"resources/textures/grassb.png");
		ProceduralTextureGenerator pg = new ProceduralTextureGenerator(rhm);
		pg.addTexture(new ImageIcon(grass), 0, 0, 1);

		// pg.addTexture(new ImageIcon(waterImage),0,30,60);
		pg.createTexture(1024);
		TextureState ts = display.getRenderer().createTextureState();
		// // Load the texture and assign it.
		ts.setTexture(TextureManager.loadTexture(pg.getImageIcon().getImage(),
				Texture.MM_LINEAR_LINEAR, Texture.FM_LINEAR, true));
		tb.setRenderState(ts);

		// TextureState ts = display.getRenderer().createTextureState();
		// ts.setTexture(TextureManager.loadTexture(grass, Texture.MM_LINEAR,
		// Texture.FM_LINEAR));
		//
		// tb.setRenderState(ts);
		// Give the terrain a bounding box.
		tb.setModelBound(new BoundingBox());
		tb.updateModelBound();
		Vector3f location = new Vector3f(ClientPlayerData.getInstance()
				.getSelectedCharacterData().getLocation().getX(), -0,
				ClientPlayerData.getInstance().getSelectedCharacterData()
						.getLocation().getZ());
		tb.setLocalTranslation(location);
		// Attach the terrain TriMesh to our rootNode
		rootNode.attachChild(tb);
	}

	private void buildSkyBox() {
		Skybox skybox = new Skybox("skybox", 200, 200, 200);

		Texture texture = TextureManager.loadTexture(TestGameState.class
				.getClassLoader().getResource("resources/textures/top.jpg"),
				Texture.MM_LINEAR, Texture.FM_LINEAR);

		skybox.setTexture(Skybox.NORTH, texture);
		skybox.setTexture(Skybox.WEST, texture);
		skybox.setTexture(Skybox.SOUTH, texture);
		skybox.setTexture(Skybox.EAST, texture);
		skybox.setTexture(Skybox.UP, texture);
		skybox.setTexture(Skybox.DOWN, texture);
		skybox.preloadTextures();
		skybox.setLocalTranslation(new Vector3f(0, 0, 0));
		rootNode.attachChild(skybox);

	}

	private void buildEnvironment() {
		// try {
		// MaxToJme C1 = new MaxToJme();
		// ByteArrayOutputStream BO = new ByteArrayOutputStream();
		// URL maxFile = TestGameState.class.getClassLoader().getResource(
		// "resources/models/crypt3.3ds");
		// C1.convert(new BufferedInputStream(maxFile.openStream()), BO);
		// JmeBinaryReader jbr = new JmeBinaryReader();
		// jbr.setProperty("bound", "box");
		// Node r = jbr.loadBinaryFormat(new ByteArrayInputStream(BO
		// .toByteArray()));
		// // TODO: Skaling is not used correctly here
		// r.setLocalScale(.01f);
		// // TODO: Tree says it needs no rotationchange
		// Quaternion temp = new Quaternion();
		// temp.fromAngleAxis(FastMath.PI / 2, new Vector3f(-1, 0, 0));
		// // // <POSITION X="9.301813" Y="2.130375" HEIGHT="-0.393879"/>
		// r.setLocalTranslation(new Vector3f(20f, 0f, 20f));
		// r.setLocalRotation(temp);
		// rootNode.attachChild(r);
		// } catch (IOException e) {
		// System.out.println("Damn exceptions:" + e);
		// e.printStackTrace();
		// }

		try {
			MaxToJme C1 = new MaxToJme();
			ByteArrayOutputStream BO = new ByteArrayOutputStream();
			URL maxFile = new File("data/plants/treernd4.3ds").toURL();
			C1.convert(new BufferedInputStream(maxFile.openStream()), BO);
			JmeBinaryReader jbr = new JmeBinaryReader();
			jbr.setProperty("bound", "box");

			Node r = jbr.loadBinaryFormat(new ByteArrayInputStream(BO
					.toByteArray()));
			// TODO: Skaling is not used correctly here
			r.setLocalScale(.05f);
			// TODO: Tree says it needs no rotationchange
			Quaternion temp = new Quaternion();
			temp.fromAngleAxis(FastMath.PI / 2, new Vector3f(-1, 0, 0));
			// <POSITION X="9.301813" Y="2.130375" HEIGHT="-0.393879"/>
			r.setLocalTranslation(new Vector3f(9.3f, 2.13f, -0.4f));
			r.setLocalRotation(temp);
			AlphaState as = DisplaySystem.getDisplaySystem().getRenderer()
					.createAlphaState();
			as.setBlendEnabled(true);
			as.setSrcFunction(AlphaState.SB_SRC_ALPHA);
			as.setDstFunction(AlphaState.DB_ONE_MINUS_SRC_ALPHA);
			as.setTestEnabled(true);
			as.setTestFunction(AlphaState.TF_GREATER);
			r.setRenderState(as);
			rootNode.attachChild(r);
			
			
			Compass c = new Compass("compass");
			c.setLocalTranslation(new Vector3f(200,200,0));
			rootNode.attachChild(c);
		} catch (IOException e) {
			System.out.println("Damn exceptions:" + e);
			e.printStackTrace();
		}

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
		BoundingVolume bv = player.getWorldBound();
		if (bv instanceof BoundingBox) {
			targetOffset.y = ((BoundingBox) player.getWorldBound()).yExtent * 1.5f;
		} else {
			targetOffset.y = ((BoundingSphere) player.getWorldBound()).radius * 1.5f;
		}

		HashMap handlerProps = new HashMap();
		handlerProps.put(ThirdPersonHandler.PROP_ROTATEONLY, "true");
		handlerProps.put(ThirdPersonHandler.PROP_DOGRADUAL, "true");
		handlerProps.put(ThirdPersonHandler.PROP_TURNSPEED, "3.1415");
		handlerProps.put(ThirdPersonHandler.PROP_LOCKBACKWARDS, "true");
		handlerProps.put(ThirdPersonHandler.PROP_STRAFETARGETALIGN, "true");
		handlerProps.put(ThirdPersonHandler.PROP_CAMERAALIGNEDMOVE, "false");

		handlerProps.put(ThirdPersonHandler.PROP_KEY_FORWARD, ""
				+ KeyInput.KEY_W);
		handlerProps.put(ThirdPersonHandler.PROP_KEY_LEFT, "" + KeyInput.KEY_A);
		handlerProps.put(ThirdPersonHandler.PROP_KEY_BACKWARD, ""
				+ KeyInput.KEY_S);
		handlerProps
				.put(ThirdPersonHandler.PROP_KEY_RIGHT, "" + KeyInput.KEY_D);
		handlerProps.put(ThirdPersonHandler.PROP_KEY_STRAFELEFT, ""
				+ KeyInput.KEY_Q);
		handlerProps.put(ThirdPersonHandler.PROP_KEY_STRAFERIGHT, ""
				+ KeyInput.KEY_E);

		// input = new TestGameHandler(player, cam, handlerProps);
		input = new TestGameHandler(player, cam, handlerProps);

		// input = new ThirdPersonHandler(player, cam, handlerProps);
		input.setActionSpeed(250.0f);

		HashMap chaserProps = new HashMap();
		// chaserProps.put(ChaseCamera.PROP_ENABLESPRING, "true");
		// chaserProps.put(ChaseCamera.PROP_DAMPINGK, "55.0");
		// chaserProps.put(ChaseCamera.PROP_SPRINGK, "756.25");

		chaserProps.put(ChaseCamera.PROP_MAXDISTANCE, "0.0");
		chaserProps.put(ChaseCamera.PROP_MINDISTANCE, "0.0");
		chaserProps.put(ChaseCamera.PROP_INITIALSPHERECOORDS, new Vector3f(
				65.0f, 0f, FastMath.DEG_TO_RAD * 12.0f));
		chaserProps.put(ChaseCamera.PROP_STAYBEHINDTARGET, "true");
		chaserProps.put(ChaseCamera.PROP_TARGETOFFSET, new Vector3f(0f,
				targetOffset.y, 0f));
		chaserProps.put(ThirdPersonMouseLook.PROP_ENABLED, "true");
		chaserProps.put(ThirdPersonMouseLook.PROP_MAXASCENT, ""
				+ FastMath.DEG_TO_RAD * 85);
		chaserProps.put(ThirdPersonMouseLook.PROP_MINASCENT, ""
				+ FastMath.DEG_TO_RAD * -15);
		chaserProps.put(ThirdPersonMouseLook.PROP_INVERTEDY, "false");
		chaserProps.put(ThirdPersonMouseLook.PROP_ROTATETARGET, "false");
		chaserProps.put(ThirdPersonMouseLook.PROP_MINROLLOUT, "6.2831855");
		chaserProps.put(ThirdPersonMouseLook.PROP_MAXROLLOUT, "240.0");
		chaserProps.put(ThirdPersonMouseLook.PROP_MOUSEXMULT, "2.0");
		chaserProps.put(ThirdPersonMouseLook.PROP_MOUSEYMULT, "30.0");
		chaserProps.put(ThirdPersonMouseLook.PROP_MOUSEROLLMULT, "240.0");
		chaserProps.put(ThirdPersonMouseLook.PROP_LOCKASCENT, "true");
		chaser = new ChaseCamera(cam, player, chaserProps);
		chaser.setActionSpeed(1.0f);

		// chaser = new ChaseCamera(cam, player, props);
		// chaser.setActionSpeed(10000f);
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

		// // TODO Fix the height and set it somewhere else !!
		// float camMinHeightPlayer = player.getWorldTranslation().y + 20f;
		// cam.getLocation().y = camMinHeightPlayer;
		// cam.update();

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
	// protected void stateRender(float tpf) {
	// display.getRenderer().clearBuffers();
	// super.stateRender(tpf);
	// }
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