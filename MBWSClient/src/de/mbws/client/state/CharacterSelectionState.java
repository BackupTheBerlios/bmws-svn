/**
 * Copyright 2005 Please see supplied licence for details.
 */
package de.mbws.client.state;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.net.MalformedURLException;
import java.util.logging.Level;

import javax.swing.JDesktopPane;

import org.apache.log4j.Logger;

import com.jme.image.Texture;
import com.jme.input.MouseInput;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.Node;
import com.jme.scene.shape.Quad;
import com.jme.scene.state.LightState;
import com.jme.scene.state.TextureState;
import com.jme.util.LoggingSystem;
import com.jme.util.TextureManager;

import de.mbws.client.data.ClientPlayerData;
import de.mbws.client.data.ObjectManager;
import de.mbws.client.gui.character.selection.ActionPanel;
import de.mbws.client.gui.character.selection.CharacterListPanel;
import de.mbws.client.state.handler.CharacterSelectionStateHandler;
import de.mbws.common.events.data.generated.CharacterData;
import de.mbws.common.events.data.generated.PlayerCharacterData;

/**
 * @author Kerim
 */
public class CharacterSelectionState extends BaseGameState implements
		PropertyChangeListener {
	private static Logger logger = Logger
			.getLogger(CharacterSelectionState.class);

	private CharacterData characterToShow;
	private String characterToDelete;
	private boolean showNewModel = false;

	// /** THE CURSOR NODE WHICH HOLDS THE MOUSE GOTTEN FROM INPUT. */
	// private Node cursor;

	public static final String CREATE_CHARACTER = "CREATECHARACTER";
	public static final String DELETE_CHARACTER = "DELETECHARACTER";
	public static final String STARTGAME = "STARTGAME";

	public CharacterSelectionState(String name) {
		super(name);

		// display = DisplaySystem.getDisplaySystem();
		display.getRenderer().setBackgroundColor(ColorRGBA.white);
		initGUI();
		setupMenu();
		// initText();
		// setupButtons();
		// initCursor();
		MouseInput.get().setCursorVisible(true);

		rootNode.setLightCombineMode(LightState.OFF);
		rootNode.setRenderQueueMode(Renderer.QUEUE_OPAQUE);
		// rootNode.setRenderQueueMode(Renderer.QUEUE_TRANSPARENT);
		// rootNode.setRenderQueueMode(Renderer.QUEUE_ORTHO);
		rootNode.updateRenderState();
		rootNode.updateGeometricState(0, true);
	}

	private void setupMenu() {
		JDesktopPane desktopPane = jmeDesktop.getJDesktop();
		// desktopPane.removeAll();
		ActionPanel actionPanel = new ActionPanel(getInputHandler());
		actionPanel.setSize(desktopPane.getWidth(), 80);
		int x = (desktopPane.getWidth() / 2) - (actionPanel.getWidth() / 2);
		int y = desktopPane.getHeight() - actionPanel.getHeight();
		actionPanel.setLocation(0, y);

		desktopPane.add(actionPanel);

		CharacterListPanel clp = new CharacterListPanel(ClientPlayerData
				.getInstance().getAllCharactersOfPlayer(), getInputHandler());
		clp.addPropertyChangeListener(
				CharacterListPanel.CHARACTER_SELECTION_CHANGE, actionPanel);
		clp.addPropertyChangeListener(
				CharacterListPanel.CHARACTER_SELECTION_CHANGE, this);
		clp.setSize(200, desktopPane.getHeight()
				- (desktopPane.getHeight() / 4));
		clp.setLocation(desktopPane.getWidth() - clp.getWidth(), 0);

		desktopPane.add(clp);

		desktopPane.repaint();
		desktopPane.revalidate();
	}

	/**
	 * @see com.jme.app.StandardGameState#onActivate()
	 */
	public void onActivate() {
		display.setTitle("MBWS - Select Your Character State");
		super.onActivate();
	}

	/**
	 * Initializes the 2D Background and the Buttons
	 */
	private void initGUI() {
		Quad backgroundQuad = new Quad("characterselectionbackground");
		backgroundQuad.initialize(display.getWidth(), display.getHeight());
		backgroundQuad.setLocalTranslation((new Vector3f(0, 0, -400)));
		// backgroundQuad.setLocalTranslation((new Vector3f(
		// display.getWidth() / 2, display.getHeight() / 2, 0)));

		LoggingSystem.getLogger().log(
				Level.INFO,
				"display size = " + (float) display.getWidth() + " "
						+ (float) display.getHeight());

		TextureState ts = display.getRenderer().createTextureState();

		try {
			ts.setTexture(TextureManager.loadTexture(new File(
					"data/images/IntroAndMainMenu/Background.jpg").toURL(),
					Texture.MM_LINEAR, Texture.FM_LINEAR, ts
							.getMaxAnisotropic(), true));
		} catch (MalformedURLException e) {
			logger.warn("Background image not found");
			e.printStackTrace();
		}
		ts.setEnabled(true);
		backgroundQuad.setRenderState(ts);

		rootNode.attachChild(backgroundQuad);

	}

	public CharacterSelectionStateHandler getInputHandler() {
		return (CharacterSelectionStateHandler) input;
	}

	@Override
	protected void initInputHandler() {
		input = new CharacterSelectionStateHandler(this);

	}

	public void propertyChange(PropertyChangeEvent evt) {
		PlayerCharacterData selectedCharacter = (PlayerCharacterData) evt
				.getNewValue();

		CharacterData cd = new CharacterData();
		cd.setGender(selectedCharacter.getGender());
		cd.setCharacterID(selectedCharacter.getCharacterID());
		cd.setRace(selectedCharacter.getRace());
		if (characterToShow != null) {
			characterToDelete = characterToShow.getCharacterID();
		}
		characterToShow = cd;
		showNewModel = true;

	}

	public void update(float tpf) {
		if (showNewModel == true) {
			Node n = ObjectManager.loadNode(characterToShow);
			if (n != null) {
				if (characterToDelete != null) {
					rootNode.detachChildNamed(characterToDelete);
					characterToDelete = null;
				}
				// n.setLocalTranslation(new Vector3f(display.getWidth() / 2,
				// display
				// .getHeight() / 2, -30));
				n.setLocalTranslation(new Vector3f(0, 0, 0));
				n.setLocalScale(0.1f);
				n.setName(characterToShow.getCharacterID());
				rootNode.attachChild(n);
				rootNode.updateRenderState();
				rootNode.updateGeometricState(0, true);
				showNewModel = false;

			} else {
				logger.warn("node = null");

			}

		}
		super.update(tpf);
	}
}