package de.mbws.client.state;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import com.jme.bounding.BoundingBox;
import com.jme.image.Image;
import com.jme.image.Texture;
import com.jme.input.*;
import com.jme.input.thirdperson.ThirdPersonMouseLook;
import com.jme.math.FastMath;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.shape.Box;
import com.jme.scene.state.AlphaState;
import com.jme.scene.state.TextureState;
import com.jme.scene.state.ZBufferState;
import com.jme.system.DisplaySystem;
import com.jme.util.TextureManager;
import com.jme.util.geom.Debugger;
import com.jmex.effects.ParticleManager;

import de.mbws.client.MBWSClient;
import de.mbws.client.data.ObjectManager;
import de.mbws.client.eventactions.AbstractEventAction;
import de.mbws.client.gui.ingame.GameDesktop;
import de.mbws.client.net.ActionQueue;
import de.mbws.client.state.handler.BaseInputHandler;
import de.mbws.client.state.handler.MainGameStateHandler;
import de.mbws.client.state.handler.MousePick;
import de.mbws.client.worldloader.DynamicWorld;

public class OutdoorGameState extends BaseGameState {

	private Node player;
	// private InputHandler inputHandler;
	// TODO: put that in MBWSInputManager ?
	private InputHandler chaseCam;
	private InputHandler playerInputHandler;
	private DisplaySystem display;
	private AbsoluteMouse cursor;
	// TODO: remove that later
	private boolean showBounds = true;

	private DynamicWorld terrain;
	private static Logger logger = Logger.getLogger(OutdoorGameState.class);

	/**
	 * the actionQueue is where we store those actions we receive from the net.
	 */
	private ActionQueue actionQueue = MBWSClient.actionQueue;

	public OutdoorGameState(String name) {
		super(name);
		display = DisplaySystem.getDisplaySystem();
		// TODO: take that out into a delegator before this class is called,
		// should be called also if player starts in houses etc.
		ObjectManager.initialize(rootNode, display);

		createCustomCursor();
		buildTestBox();
		buildTestBox2();
		buildEnvironment();
		buildPlayer();
		buildCamera();
		// buildSky();
		createRain();

		rootNode.updateGeometricState(0.0f, true);
		rootNode.updateRenderState();

	}

	// private void buildSky() {
	// // TODO: size seems a problem, use skydome anyway ?
	// skybox = new Skybox("skybox", 600, 200, 600);
	//
	// Texture texture = TextureManager.loadTexture(OutdoorGameState.class
	// .getClassLoader().getResource("resources/textures/top.jpg"),
	// Texture.MM_LINEAR, Texture.FM_LINEAR);
	//
	// skybox.setTexture(Skybox.NORTH, texture);
	// skybox.setTexture(Skybox.WEST, texture);
	// skybox.setTexture(Skybox.SOUTH, texture);
	// skybox.setTexture(Skybox.EAST, texture);
	// skybox.setTexture(Skybox.UP, texture);
	// skybox.setTexture(Skybox.DOWN, texture);
	// skybox.preloadTextures();
	// skybox.setLocalTranslation(player.getLocalTranslation());
	//
	// // TODO: Uh this is bad ... we dont want to have it attached to the
	// // player
	// // player.attachChild(skybox);
	// rootNode.attachChild(skybox);
	//
	// }

	protected void initJMEDesktop() {
		desktopNode = new GameDesktop("Desktop", input);
		// jmeDesktop = ((GameDesktop)desktopNode).getDesktop();
		((MainGameStateHandler) playerInputHandler).gd = (GameDesktop) desktopNode;
		guiRootNode.attachChild(desktopNode);
	}

	private void buildCamera() {
		HashMap chaserProps = new HashMap();

		// enable our custom properties
		chaserProps.put(ThirdPersonMouseLook.PROP_ENABLED, "true");
		// we dont want the cam to allways get back behind the player
		chaserProps.put(ChaseCamera.PROP_STAYBEHINDTARGET, "false");

		chaserProps.put(ChaseCamera.PROP_INITIALSPHERECOORDS, new Vector3f(65.0f,
				0f, FastMath.DEG_TO_RAD * 12.0f));

		chaserProps.put(ChaseCamera.PROP_TARGETOFFSET, new Vector3f(0f, 10f, 0f));
		// targetOffset.y, 0f));

		// no speeding to the camposition, just set it directly
		chaserProps.put(ChaseCamera.PROP_ENABLESPRING, "false");

		// we want to look up and down too and invert y direction
		chaserProps.put(ThirdPersonMouseLook.PROP_LOCKASCENT, "false");
		chaserProps.put(ThirdPersonMouseLook.PROP_INVERTEDY, "true");
		// maximum and minimum angle from which to look at character from
		// above/below
		chaserProps.put(ThirdPersonMouseLook.PROP_MAXASCENT, ""
				+ FastMath.DEG_TO_RAD * 85);
		chaserProps.put(ThirdPersonMouseLook.PROP_MINASCENT, ""
				+ FastMath.DEG_TO_RAD * -25);
		// dont rotate the player with the mouse
		chaserProps.put(ThirdPersonMouseLook.PROP_ROTATETARGET, "false");

		// Amount of rotation around figure
		chaserProps.put(ThirdPersonMouseLook.PROP_MOUSEXMULT, "20.0");
		chaserProps.put(ThirdPersonMouseLook.PROP_MOUSEYMULT, "20.0");
		// only rotate when mousebutton is pressed
		chaserProps.put(ThirdPersonMouseLook.PROP_MOUSEBUTTON_FOR_LOOKING, "1");

		// minimum and maximum distance the chasecam can be away from the char
		chaserProps.put(ChaseCamera.PROP_MINDISTANCE, "10.0");
		chaserProps.put(ChaseCamera.PROP_MAXDISTANCE, "110.0");

		// Mousewheel minimum distance, maximum and step per wheel tick
		chaserProps.put(ThirdPersonMouseLook.PROP_MINROLLOUT, "10");
		chaserProps.put(ThirdPersonMouseLook.PROP_MAXROLLOUT, "100");
		chaserProps.put(ThirdPersonMouseLook.PROP_MOUSEROLLMULT, "5.0");

		chaseCam = new ChaseCamera(cam, player, chaserProps);
		chaseCam.setActionSpeed(1.0f);
		cam.setFrustumFar(2000);
		// input.addToAttachedHandlers(chaseCam);

	}

	private void buildPlayer() {
		player = ObjectManager.getPlayer();
		((MainGameStateHandler) playerInputHandler).setPlayer(player);
		playerInputHandler.addAction(new MousePick(cam, rootNode, cursor, display));
	}

	private void buildEnvironment() {
		terrain = new DynamicWorld();
		rootNode.attachChild(terrain);
		try {
			terrain.init(rootNode, cam, display, "data/world/world", "data/characters/generic/1/M");
			terrain.setVisibilityRadius(cam.getFrustumFar());
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// TODO PUT THAT IN UPDATE
	public void update(float tpf) {
		/** If toggle_bounds is a valid command (via key B), change bounds. */
		if (KeyBindingManager.getKeyBindingManager().isValidCommand(
				"toggle_bounds", false)) {
			showBounds = !showBounds;
		}

		if (((GameDesktop) desktopNode).getDesktop().getFocusOwner() != null) {
			playerInputHandler.setEnabled(false);
		} else {
			playerInputHandler.setEnabled(true);
		}
		super.update(tpf);

		terrain.update(cam);
		// update the chase camera to handle the player moving around.

		chaseCam.update(tpf);
		Vector3f camlocation = cam.getLocation();
		if (terrain.getHeight(camlocation) > camlocation.y) {
			camlocation.y = terrain.getHeight(camlocation);
			cam.setLocation(camlocation);
		}
		// skybox.setLocalTranslation(cam.getLocation());
		// }
		// heightAtPoint = terrain.
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
		Vector3f location = player.getLocalTranslation();
		if (location.y < terrain.getHeight(location)) {
			location.y = terrain.getHeight(location);
			player.setLocalTranslation(location);
		}

		rootNode.updateGeometricState(tpf, true);

	}

	@Override
	protected void initInputHandler() {
		input = new BaseInputHandler(this);
		playerInputHandler = new MainGameStateHandler(null, this);

		input.addToAttachedHandlers(playerInputHandler);
		// TODO: remove testcode !
		KeyBindingManager.getKeyBindingManager().set("toggle_bounds",
				KeyInput.KEY_B);
	}

	private void createCustomCursor() {
		try {
			cursor = new AbsoluteMouse("cursor", display.getWidth(), display
					.getHeight());

			// Get a picture for my mouse.
			TextureState ts = display.getRenderer().createTextureState();
			URL cursorLoc;

			cursorLoc = new File("data/images/cursor/cursor1.png").toURL();
			Texture t = TextureManager.loadTexture(cursorLoc, Texture.MM_LINEAR,
					Texture.FM_LINEAR, Image.GUESS_FORMAT_NO_S3TC, 1, true);
			ts.setTexture(t);
			cursor.setRenderState(ts);

			// Make the mouse's background blend with what's already there
			AlphaState as = display.getRenderer().createAlphaState();
			as.setBlendEnabled(true);
			as.setSrcFunction(AlphaState.SB_SRC_ALPHA);
			as.setDstFunction(AlphaState.DB_ONE_MINUS_SRC_ALPHA);
			as.setTestEnabled(true);
			as.setTestFunction(AlphaState.TF_GREATER);
			cursor.setRenderState(as);

			// Assign the mouse to an input handler
			cursor.registerWithInputHandler(input);

			rootNode.attachChild(cursor);

			// important for JMEDesktop: use system coordinates
			cursor.setUsingDelta(false);
			cursor.getXUpdateAction().setSpeed(1);
			cursor.getYUpdateAction().setSpeed(1);

			cursor.setCullMode(Spatial.CULL_NEVER);

			// MouseInput.get().setCursorVisible(false);
		} catch (Exception e) {
			logger.error("Couldnt load cursor: ", e);
		}
	}

	private void buildTestBox() {
		Node boxNode = new Node("Box");
		Box box = new Box("box", new Vector3f(), 200, 10, 50);
		box.setModelBound(new BoundingBox(new Vector3f(330, 9, 190), 200, 10, 50));
		box.updateModelBound();

		TextureState ts = display.getRenderer().createTextureState();
		URL imageLoc;
		try {
			imageLoc = new File("./data/images/stone.jpg").toURL();

			Texture t = TextureManager.loadTexture(imageLoc, Texture.MM_LINEAR,
					Texture.FM_LINEAR);
			ts.setTexture(t);
			box.setRenderState(ts);
			box.updateRenderState();

			boxNode.attachChild(box);
			boxNode.getLocalTranslation().y = +4;
			boxNode.getLocalTranslation().x = +230;
			boxNode.getLocalTranslation().z = +330;
			rootNode.attachChild(boxNode);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void buildTestBox2() {
		Node boxNode = new Node("Box2");
		Box box = new Box("box2", new Vector3f(), 50, 10, 200);
		box.setModelBound(new BoundingBox());
		box.updateModelBound();
		TextureState ts = display.getRenderer().createTextureState();
		URL imageLoc;
		try {
			imageLoc = new File("./data/images/moss.jpg").toURL();

			Texture t = TextureManager.loadTexture(imageLoc, Texture.MM_LINEAR,
					Texture.FM_LINEAR);
			ts.setTexture(t);
			box.setRenderState(ts);
			box.updateRenderState();

			boxNode.attachChild(box);
			boxNode.getLocalTranslation().y = +6;
			boxNode.getLocalTranslation().x = +230;
			boxNode.getLocalTranslation().z = +330;
			rootNode.attachChild(boxNode);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void createRain() {
		// Vector3f[] lines = new Vector3f[260];
		// int count = 0;
		// float pointZ = 140.0f;
		// for (int j = 0; j < 6; j++) {
		// float pointX = -50.0f;
		// for (int i = 0; i < 26; i++) {
		// lines[count++] = new Vector3f(pointX, 50.0f, pointZ);
		// pointX += 4;
		// }
		// pointZ += 10.0f;
		// }
		// Line line = new Line("Line", lines, null, null, null);
		ParticleManager manager = new ParticleManager(400);// ,
		manager.setGravityForce(new Vector3f(0.0f, -0.0030f, 0.0f));
		manager.setEmissionDirection(new Vector3f(0.0f, -1.0f, 0.0f));
		manager.setEmissionMaximumAngle(2.1816616f);
		manager.setEmissionMinimumAngle(0.0f);
		manager.setSpeed(1f);
		manager.setParticlesMinimumLifeTime(1626.0f);
		manager.setStartSize(1f);
		manager.setEndSize(1f);

		manager.setStartColor(new ColorRGBA(0.16078432f, 0.16078432f, 1.0f, 1.0f));
		manager.setEndColor(new ColorRGBA(0.16078432f, 0.16078432f, 1.0f,
				0.15686275f));
		manager.setRandomMod(0.0f);
		manager.setControlFlow(false);
		manager.setReleaseRate(400);
		manager.setReleaseVariance(0.0f);
		manager.setInitialVelocity(1.0f);
		manager.setParticleSpinSpeed(0.0f);

		manager.warmUp(1000);
		manager.getParticles().addController(manager);
		AlphaState as1 = display.getRenderer().createAlphaState();
		as1.setBlendEnabled(true);
		as1.setSrcFunction(AlphaState.SB_SRC_ALPHA);
		as1.setDstFunction(AlphaState.DB_ONE);
		as1.setTestEnabled(true);
		as1.setTestFunction(AlphaState.TF_GREATER);
		as1.setEnabled(true);
		TextureState ts = display.getRenderer().createTextureState();
		try {
			ts.setTexture(TextureManager.loadTexture(new File(

			"data/images/rain.png").toURL(), Texture.MM_LINEAR_LINEAR,
					Texture.FM_LINEAR));
		} catch (Exception e) {
			logger.error(e);
		}
		ts.setEnabled(true);

		// manager.setGeometry(line);

		Node myNode = new Node("Particle Nodes");

		myNode.setRenderState(as1);
		myNode.setRenderState(ts);
		myNode.attachChild(manager.getParticles());
		ZBufferState zstate = DisplaySystem.getDisplaySystem().getRenderer()
				.createZBufferState();
		zstate.setEnabled(false);
		manager.getParticles().setRenderState(zstate);
		myNode.setLocalTranslation(new Vector3f(0, 200, 0));
		player.attachChild(myNode);
	}

	// TODO TAKE jmeDesktop AWAY from the basegamestate
	public void setActive(boolean active) {
		if (active) {
			onActivate();
		}
		display.setTitle("OUTDOOR-GameState");
		input.setEnabled(active);
		// jmeDesktop.getJDesktop().setEnabled(active);
		// super.setActive(active);
		this.active = active;

	}

	public void render(float tpf) {
		Renderer r = display.getRenderer();
		if (showBounds)
			Debugger.drawBounds(rootNode, r, true);
		super.render(tpf);
	}

}
