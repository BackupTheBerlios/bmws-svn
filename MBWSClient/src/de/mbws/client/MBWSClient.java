/**
 * Copyright 2005 Please see supplied licence for details.
 */
package de.mbws.client;

import java.util.logging.Level;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

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
import de.mbws.client.state.MainMenuState;

/**
 * This is the main entry point of the gameclient
 * 
 * @author Kerim Mansour
 * 
 */
// TODO: Kerim: should we use a different game type here ?
public class MBWSClient extends BaseGame {
    private static Logger logger = Logger.getLogger(MBWSClient.class);
	/** Only used in the static exit method. */
	private static AbstractGame instance;

	/** High resolution timer for jME. */
	public static Timer timer;

	/** Simply an easy way to get at timer.getTimePerFrame(). */
	private float timePerFrame;

	public static final String CLIENT = "MBWSClient Version: 0.1";

	/**
	 * This is called every frame in BaseGame.start()
	 * 
	 * @param interpolation
	 *            unused in this implementation
	 * @see AbstractGame#update(float interpolation)
	 */
	protected final void update(float interpolation) {
		// Recalculate the framerate.
		timer.update();
		timePerFrame = timer.getTimePerFrame();

		// Update the current game state.
		GameStateManager.getInstance().update(timePerFrame);
	}

	/**
	 * This is called every frame in BaseGame.start(), after update()
	 * 
	 * @param interpolation
	 *            unused in this implementation
	 * @see AbstractGame#render(float interpolation)
	 */
	protected final void render(float interpolation) {
		// Clears the previously rendered information.
		display.getRenderer().clearBuffers();
		// Render the current game state.
		GameStateManager.getInstance().render(timePerFrame);
	}

	/**
	 * Creates display, sets up camera, and binds keys. Called in
	 * BaseGame.start() directly after the dialog box.
	 * 
	 * @see AbstractGame#initSystem()
	 */
	protected final void initSystem() {
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

	/**
	 * Called in BaseGame.start() after initSystem(). We only create the
	 * GameStateManager and add to it two states (intro and menu). Then we
	 * activate the first one in order to have it rendered. New states should be
	 * added by other states and not here. Keep in mind to clean up however.
	 * Also we start the Networkcontroller.
	 * 
	 * @see AbstractGame#initGame()
	 */
	protected final void initGame() {
		instance = this;
		display.setTitle(CLIENT);
		ClientNetworkController.getInstance().start();
		GameStateManager.create();
		// TODO: Kerim : Reactivate introstate later
		// GameState intro = new IntroState("intro");
		// intro.setActive(true);
		// GameStateManager.getInstance().attachChild(intro);
		GameState mainMenu = new MainMenuState("menu");
		mainMenu.setActive(true);
		GameStateManager.getInstance().attachChild(mainMenu);
	}

	/**
	 * Cleans up the keyboard and game state system.
	 * 
	 * @see AbstractGame#cleanup()
	 */
	protected void cleanup() {
		LoggingSystem.getLogger().log(Level.INFO, "Cleaning up resources.");
		GameStateManager.getInstance().cleanup();
		KeyInput.destroyIfInitalized();
		MouseInput.destroyIfInitalized();
		JoystickInput.destroyIfInitalized();
	}

	/**
	 * Mainmethod. We instanciate a new Client. Propertyfile is only shown if no
	 * previous configuration existed.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
        BasicConfigurator.configure();
        PropertyConfigurator.configure("log4j.properties");
        logger.info("Init log4j ... done");
		MBWSClient app = new MBWSClient();
		app.setDialogBehaviour(
				MBWSClient.FIRSTRUN_OR_NOCONFIGFILE_SHOW_PROPS_DIALOG,
				MBWSClient.class.getClassLoader().getResource(
						"resources/IntroAndMainMenu/Properties.jpg"));
		app.start();
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

}
