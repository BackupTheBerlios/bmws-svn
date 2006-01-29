package de.mbws.client.state;

import java.util.logging.Level;

import com.jme.app.AbstractGame;
import com.jme.app.BaseGame;
import com.jme.app.GameState;
import com.jme.app.GameStateManager;
import com.jme.input.KeyInput;
import com.jme.input.MouseInput;
import com.jme.input.joystick.JoystickInput;
import com.jme.system.DisplaySystem;
import com.jme.system.JmeException;
import com.jme.util.LoggingSystem;
import com.jme.util.Timer;

import de.mbws.client.controller.ClientNetworkController;
import de.mbws.client.net.ActionQueue;


public class TestGameStateTest extends BaseGame {
	/** Only used in the static exit method. */
	private static AbstractGame instance;
	/** High resolution timer for jME. */
	public static Timer timer;
	/** Simply an easy way to get at timer.getTimePerFrame(). */
	private float timePerFrame;
	public static ActionQueue actionQueue = new ActionQueue();

	public static void main(String[] args) {
		TestGameStateTest app = new TestGameStateTest();
		app.start();
	}

	protected void simpleInitGame() {
		// TODO: Kerim : Reactivate introstate later
		GameStateManager.create();
		// GameState intro = new IntroState("intro");
		// intro.setActive(true);
		// GameStateManager.getInstance().attachChild(intro);
		GameState tgs = new TestGameState("SomeName");
		tgs.setActive(true);
		GameStateManager.getInstance().attachChild(tgs);
		try {
	          /**
				 * Get a DisplaySystem acording to the renderer selected in the
				 * startup box.
				 */
				display = DisplaySystem.getDisplaySystem(properties.getRenderer());
//				display.createWindow(properties.getWidth(), properties.getHeight(),
//						properties.getDepth(), properties.getFreq(), properties
//								.getFullscreen());
			} catch (JmeException e) {
				e.printStackTrace();
				System.exit(1);
			}

			/** Get a high resolution timer for FPS updates. */
			timer = Timer.getTimer(properties.getRenderer());

	}

	@Override
	protected void update(float arg0) {
		// Recalculate the framerate.
		timer.update();
		timePerFrame = timer.getTimePerFrame();

		// Update the current game state.
		GameStateManager.getInstance().update(timePerFrame);
	}

	@Override
	protected void render(float arg0) {
		// Clears the previously rendered information.
		display.getRenderer().clearBuffers();
		// Render the current game state.
		GameStateManager.getInstance().render(timePerFrame);
	}

	@Override
	protected void initSystem() {
		try {
	          /**
				 * Get a DisplaySystem acording to the renderer selected in the
				 * startup box.
				 */
				display = DisplaySystem.getDisplaySystem(properties.getRenderer());
				display.createWindow(properties.getWidth(), properties.getHeight(),
						properties.getDepth(), properties.getFreq(), properties
								.getFullscreen());
			} catch (JmeException e) {
				e.printStackTrace();
				System.exit(1);
			}

			/** Get a high resolution timer for FPS updates. */
			timer = Timer.getTimer(properties.getRenderer());
	}

	@Override
	protected void initGame() {
		instance = this;
		display.setTitle("Test Client");
		ClientNetworkController.getInstance().start();
		GameStateManager.create();
		// TODO: Kerim : Reactivate introstate later
		// GameState intro = new IntroState("intro");
		// intro.setActive(true);
		// GameStateManager.getInstance().attachChild(intro);
		GameState state = new TestGameState("menu");
		state.setActive(true);
		GameStateManager.getInstance().attachChild(state);
	}

	/**
	 * Static method to finish this application.
	 */
	public static void exit() {
		instance.finish();
	}

	/**
	 * Empty.
	 * 
	 * @see AbstractGame#reinit()
	 */
	protected void reinit() {
	}

    @Override
    protected void quit() {
        super.quit();
        System.exit(0);
    }

	@Override
	protected void cleanup() {
		LoggingSystem.getLogger().log(Level.INFO, "Cleaning up resources.");
		GameStateManager.getInstance().cleanup();
		KeyInput.destroyIfInitalized();
		MouseInput.destroyIfInitalized();
		JoystickInput.destroyIfInitalized();
	}
}




// import static java.util.logging.Logger.global;
// now use global.severe("This is a severe error");



//FaultFractalHeightMap heightMap = new FaultFractalHeightMap(257, 32, 0, 255,
//0.75f);
//heightMap.save("rawtest");
//heightMap.unloadHeightMap();


/*RawHeightMap Load*/
//heightMap2 = new RawHeightMap("rawtest.raw", 3);
//
//Vector3f terrainScale = new Vector3f(10,1,10);
//heightMap2.setHeightScale( 0.001f);
//TerrainPage tb = new TerrainPage("Terrain", 33, heightMap2.getSize(), terrainScale,
//                            heightMap2.getHeightMap(), false);
//
//tb.setDetailTexture(1, 16);
//
//ProceduralTextureGenerator pt = new ProceduralTextureGenerator(heightMap2);
//pt.addTexture(new ImageIcon(TestTerrain.class.getClassLoader().getResource(
//"jmetest/data/texture/grass.jpg")), -128, 0, 128);
//pt.addTexture(new ImageIcon(TestTerrain.class.getClassLoader().getResource(
//"jmetest/data/texture/dirt.jpg")), 0, 128, 255);
//pt.addTexture(new ImageIcon(TestTerrain.class.getClassLoader().getResource(
//"jmetest/data/texture/highest.jpg")), 128, 255, 384);
//
//pt.createTexture(512);
//
//TextureState ts = display.getRenderer().createTextureState();
//ts.setEnabled(true);
//Texture t1 = TextureManager.loadTexture(
//pt.getImageIcon().getImage(),
//Texture.MM_LINEAR_LINEAR,
//Texture.FM_LINEAR,
//true);
//ts.setTexture(t1, 0);
//
//Texture t2 = TextureManager.loadTexture(TestTerrain.class.getClassLoader().
//                                   getResource(
//"jmetest/data/texture/Detail.jpg"),
//                                   Texture.MM_LINEAR_LINEAR,
//                                   Texture.FM_LINEAR);
//ts.setTexture(t2, 1);
//t2.setWrap(Texture.WM_WRAP_S_WRAP_T);
//
//t1.setApply(Texture.AM_COMBINE);
//t1.setCombineFuncRGB(Texture.ACF_MODULATE);
//t1.setCombineSrc0RGB(Texture.ACS_TEXTURE);
//t1.setCombineOp0RGB(Texture.ACO_SRC_COLOR);
//t1.setCombineSrc1RGB(Texture.ACS_PRIMARY_COLOR);
//t1.setCombineOp1RGB(Texture.ACO_SRC_COLOR);
//t1.setCombineScaleRGB(1.0f);
//
//t2.setApply(Texture.AM_COMBINE);
//t2.setCombineFuncRGB(Texture.ACF_ADD_SIGNED);
//t2.setCombineSrc0RGB(Texture.ACS_TEXTURE);
//t2.setCombineOp0RGB(Texture.ACO_SRC_COLOR);
//t2.setCombineSrc1RGB(Texture.ACS_PREVIOUS);
//t2.setCombineOp1RGB(Texture.ACO_SRC_COLOR);
//t2.setCombineScaleRGB(1.0f);
//tb.setRenderState(ts);
//
//FogState fs = display.getRenderer().createFogState();
//fs.setDensity(0.5f);
//fs.setEnabled(true);
//fs.setColor(new ColorRGBA(0.5f, 0.5f, 0.5f, 0.5f));
//fs.setEnd(1000);
//fs.setStart(500);
//fs.setDensityFunction(FogState.DF_LINEAR);
//fs.setApplyFunction(FogState.AF_PER_VERTEX);
//tb.setRenderState(fs);
//rootNode.attachChild(tb);


