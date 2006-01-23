package de.mbws.client.experimental;

import java.net.URL;

import com.jme.app.BaseGame;
import com.jme.app.SimpleGame;
import com.jme.image.Texture;
import com.jme.input.FirstPersonHandler;
import com.jme.input.InputHandler;
import com.jme.input.KeyBindingManager;
import com.jme.input.KeyInput;
import com.jme.light.DirectionalLight;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Node;
import com.jme.scene.state.LightState;
import com.jme.scene.state.TextureState;
import com.jme.scene.state.ZBufferState;
import com.jme.system.DisplaySystem;
import com.jme.system.JmeException;
import com.jme.util.TextureManager;
import com.jme.util.Timer;

import de.mbws.client.data.ClientPlayerData;
import de.mbws.client.gui.ingame.GameDesktop;
import de.mbws.client.terrain.DynamicTerrainPage;
import de.mbws.common.eventdata.generated.CharacterData;

public class Terrainloader extends BaseGame {

	protected InputHandler input;
	// the timer
	protected Timer timer;

	private GameDesktop gd;
	// Our camera object for viewing the scene
	private Camera cam;
	// private Node player;
	private Compass c;
	private float testAngle = 0;

	// the root node of the scene graph
	private Node rootNode;

	// display attributes for the window. We will keep these values
	// to allow the user to change them
	private int width, height, depth, freq;
	private boolean fullscreen;

	/**
	 * Main entry point of the application
	 */
	public static void main(String[] args) {
		Terrainloader app = new Terrainloader();
		app.setDialogBehaviour(SimpleGame.ALWAYS_SHOW_PROPS_DIALOG);
		app.start();

	}

	/**
	 * During an update we look for the escape button and update the timer to
	 * get the framerate. Things are now starting to happen, so we will update
	 * 
	 * @see com.jme.app.SimpleGame#update()
	 */
	protected void update(float interpolation) {
		// update the time to get the framerate
		timer.update();
		interpolation = timer.getTimePerFrame();

		input.update(interpolation);
		 c.setAngle(testAngle++);
		 c.updateGeometricState(interpolation, true);

		// if escape was pressed, we exit
		if (KeyBindingManager.getKeyBindingManager().isValidCommand("exit")) {
			finished = true;
		}

		rootNode.updateGeometricState(interpolation, true);
	}

	/**
	 * draws the scene graph
	 * 
	 * @see com.jme.app.SimpleGame#render()
	 */
	protected void render(float interpolation) {
		// Clear the screen
		display.getRenderer().clearBuffers();
		display.getRenderer().draw(rootNode);
		display.getRenderer().draw(c);
	}

	/**
	 * initializes the display and camera.
	 * 
	 * @see com.jme.app.SimpleGame#initSystem()
	 */
	protected void initSystem() {
		// store the properties information
		width = properties.getWidth();
		height = properties.getHeight();
		depth = properties.getDepth();
		freq = properties.getFreq();
		fullscreen = properties.getFullscreen();

		try {
			display = DisplaySystem.getDisplaySystem(properties.getRenderer());
			display.createWindow(width, height, depth, freq, fullscreen);

			cam = display.getRenderer().createCamera(width, height);
		} catch (JmeException e) {
			e.printStackTrace();
			System.exit(1);
		}

		// set the background to black
		display.getRenderer().setBackgroundColor(ColorRGBA.black);

		// initialize the camera
		cam.setFrustumPerspective(45.0f, (float) width / (float) height, 1,
				5000);
		Vector3f loc = new Vector3f(250.0f, 100.0f, 250.0f);
		Vector3f left = new Vector3f(-0.5f, 0.0f, 0.5f);
		Vector3f up = new Vector3f(0.0f, 1.0f, 0.0f);
		Vector3f dir = new Vector3f(-0.5f, 0.0f, -0.5f);
		// Move our camera to a correct place and orientation.
		cam.setFrame(loc, left, up, dir);
		/** Signal that we've changed our camera's location/frustum. */
		cam.update();

		/** Get a high resolution timer for FPS updates. */
		timer = Timer.getTimer(properties.getRenderer());

		display.getRenderer().setCamera(cam);

		KeyBindingManager.getKeyBindingManager().set("exit",
				KeyInput.KEY_ESCAPE);
	}

	/**
	 * initializes the scene
	 * 
	 * @see com.jme.app.SimpleGame#initGame()
	 */
	protected void initGame() {
		display.setTitle("Flag Rush");

		rootNode = new Node("Scene graph node");
		/**
		 * Create a ZBuffer to display pixels closest to the camera above
		 * farther ones.
		 */
		ZBufferState buf = display.getRenderer().createZBufferState();
		buf.setEnabled(true);
		buf.setFunction(ZBufferState.CF_LEQUAL);
		rootNode.setRenderState(buf);
		buildInput();
		// Add terrain to the scene
		buildTerrain();
		// Light the world
		buildLighting();
		// add the tree
		 buildEnvironment();
		addGameDesktop();

		// update the scene graph for rendering
		rootNode.updateGeometricState(0.0f, true);
		rootNode.updateRenderState();
	}

	private void addGameDesktop() {
		CharacterData dummyCharacterData = new CharacterData();
		dummyCharacterData.setName("DUMMY_PLAYER");
		ClientPlayerData.getInstance().setSelectedCharacterData(dummyCharacterData);
		
		 gd = new GameDesktop("name", input);
		 gd.getLocalTranslation().set( display.getWidth() / 2, display.getHeight() / 2, 0 );
		 gd.updateGeometricState(0, true);
		 gd.updateRenderState();
			if (rootNode != null)
				rootNode.attachChild(gd);
		
	}

	private void buildInput() {
		input = new FirstPersonHandler(cam, 30, 15);// player,
		// properties.getRenderer());
	}

	// private void buildPlayer() {
	// // box stand in
	// Box b = new Box("box", new Vector3f(), 0.35f, 0.25f, 0.5f);
	// b.setModelBound(new BoundingBox());
	// b.updateModelBound();
	//
	// player = new Node("Player Node");
	// player.setLocalTranslation(new Vector3f(100, 0, 100));
	// rootNode.attachChild(player);
	// player.attachChild(b);
	// player.updateWorldBound();
	// }

	// /**
	// * buildEnvironment will create a fence. This is done by hand to show how
	// to
	// * create geometry and shared this geometry. Normally, you wouldn't build
	// * your models by hand as it is too much of a trial and error process.
	// */
	 private void buildEnvironment() {
	
	 c = new Compass("compass");
	 c.setLocalTranslation(new Vector3f(200, 200, 0));
	 c.updateGeometricState(0, true);
	 c.updateRenderState();
	
	 rootNode.attachChild(c);
	 // } catch (IOException e) {
	 // System.out.println("Damn exceptions:" + e);
	 // e.printStackTrace();
	 // }
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
	 * build the height map and terrain block.
	 */
	private void buildTerrain() {
		DynamicTerrainPage tp = new DynamicTerrainPage("smd", 128,
				new Vector3f(1, 1, 1));
		tp.setOrAttachBlock(new Vector2f(0, 0));

		URL grass = Terrainloader.class.getClassLoader().getResource(
				"resources/textures/grassb.png");

		TextureState ts = display.getRenderer().createTextureState();
		ts.setTexture(TextureManager.loadTexture(grass, Texture.MM_LINEAR,
				Texture.FM_LINEAR));

		tp.setRenderState(ts);
		// Give the terrain a bounding box.
		// tp.setModelBound(new BoundingBox());
		// tp.updateModelBound();
		tp.setLocalTranslation(new Vector3f(0, 0, 0));

		tp.setOrAttachBlock(new Vector2f(0, 128));
		URL dirt = Terrainloader.class.getClassLoader().getResource(
				"resources/textures/dirt.jpg");
		ts = display.getRenderer().createTextureState();
		ts.setTexture(TextureManager.loadTexture(dirt, Texture.MM_LINEAR,
				Texture.FM_LINEAR));

		tp.setRenderState(ts);
		// Attach the terrain TriMesh to our rootNode
		rootNode.attachChild(tp);

	}

	/**
	 * will be called if the resolution changes
	 * 
	 * @see com.jme.app.SimpleGame#reinit()
	 */
	protected void reinit() {
		display.recreateWindow(width, height, depth, freq, fullscreen);
	}

	/**
	 * clean up the textures.
	 * 
	 * @see com.jme.app.SimpleGame#cleanup()
	 */
	protected void cleanup() {

	}

}
