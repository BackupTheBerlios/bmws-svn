/**
 * Copyright 2005 Please see supplied licence for details.
 */
package de.mbws.client.state;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import com.jme.app.GameState;
import com.jme.app.StandardGameState;
import com.jme.image.Texture;
import com.jme.input.MouseInput;
import com.jme.math.Vector3f;
import com.jme.renderer.Renderer;
import com.jme.scene.shape.Quad;
import com.jme.scene.state.LightState;
import com.jme.scene.state.TextureState;
import com.jme.system.DisplaySystem;
import com.jme.util.LoggingSystem;
import com.jme.util.TextureManager;
import com.jmex.bui.BButton;
import com.jmex.bui.BContainer;
import com.jmex.bui.BDecoratedWindow;
import com.jmex.bui.BLookAndFeel;
import com.jmex.bui.BRootNode;
import com.jmex.bui.BTextArea;
import com.jmex.bui.BWindow;
import com.jmex.bui.PolledRootNode;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.layout.GroupLayout;
import com.jmex.bui.util.Dimension;

import de.mbws.client.MBWSClient;
import de.mbws.client.ValueMapper;
import de.mbws.client.data.ClientPlayerData;
import de.mbws.client.gui.MenuLookAndFeel;
import de.mbws.client.state.handler.CharacterSelectionStateHandler;
import de.mbws.common.eventdata.generated.CharacterShortDescription;

/**
 * @author Kerim
 */
public class CharacterSelectionState extends StandardGameState {

	// /** THE CURSOR NODE WHICH HOLDS THE MOUSE GOTTEN FROM INPUT. */
	// private Node cursor;

	public static final String CREATE_CHARACTER = "CREATECHARACTER";
	public static final String DELETE_CHARACTER = "DELETECHARACTER";
	public static final String STARTGAME = "STARTGAME";

	/** Our display system. */
	private DisplaySystem display;

	//private Text text;

	private CharacterSelectionStateHandler input;

	BRootNode _root;
	public BWindow characterWindow;
	BContainer cont;

	BWindow controllWindow;
	BContainer controllContainer;
	// BWindow characterWindow;
	BLookAndFeel lnf;

	public BButton startGameBtn;
	public BButton deleteBtn;
	public BButton createBtn;

	BTextArea characterDescription;

	public CharacterSelectionState(String name) {
		super(name);

		display = DisplaySystem.getDisplaySystem();
		initInput();
		initGUI();
		initBUIGUI();
		// initText();
		// setupButtons();
		// initCursor();
		MouseInput.get().setCursorVisible(true);

		rootNode.setLightCombineMode(LightState.OFF);
		rootNode.setRenderQueueMode(Renderer.QUEUE_ORTHO);
		rootNode.updateRenderState();
		rootNode.updateGeometricState(0, true);
	}

	private void fillData() {
		List<CharacterShortDescription> allCharacters = ClientPlayerData
				.getInstance().getAllCharactersOfPlayer();
		if (allCharacters != null) {
			Iterator<CharacterShortDescription> it = allCharacters.iterator();
			while (it.hasNext()) {
				CharacterShortDescription aCharacter = it.next();
				BButton characterButton = new BButton(aCharacter.getName()
						+ " (" + ValueMapper.getRaceName(aCharacter.getRace()) + "/"
						+ aCharacter.getGender() + "/"
						+ aCharacter.getLocation() + ")", input, aCharacter
						.getCharacterID());
				cont.add(characterButton);

			}
		}
	}

	private void initBUIGUI() {
		_root = new PolledRootNode(MBWSClient.timer, input);
		rootNode.attachChild(_root);
		lnf = MenuLookAndFeel.getDefaultLookAndFeel();
		characterWindow = new BDecoratedWindow(lnf, null);
		cont = new BContainer(GroupLayout.makeVert(GroupLayout.TOP));
		fillData();
		characterDescription = new BTextArea();

		// window.add(new BScrollPane(cont), BorderLayout.WEST);
		characterWindow.add(cont, BorderLayout.WEST);
		characterWindow.setSize(250, 250);
		characterWindow.setLocation(display.getWidth() - 250, display
				.getHeight() - 250);

		controllWindow = new BDecoratedWindow(lnf, null);
		controllContainer = new BContainer(GroupLayout
				.makeHoriz(GroupLayout.CENTER));

		controllWindow.setSize(250, 50);
		controllWindow.setLocation(display.getWidth() - 250, 0);

		startGameBtn = new BButton("Start", input, STARTGAME);
		startGameBtn.setPreferredSize(new Dimension(70, 30));
		startGameBtn.setEnabled(false);
		controllContainer.add(startGameBtn);
		
		

		deleteBtn = new BButton("Delete", input, DELETE_CHARACTER);
		deleteBtn.setEnabled(false);
		deleteBtn.setPreferredSize(new Dimension(70, 30));
		controllContainer.add(deleteBtn);
		

		createBtn = new BButton("Create", input, CREATE_CHARACTER);
		createBtn.setEnabled(false);
		createBtn.setPreferredSize(new Dimension(70, 30));
		controllContainer.add(createBtn);
		
		
		controllWindow.add(controllContainer, BorderLayout.CENTER);
		
		_root.addWindow(characterWindow);
		_root.addWindow(controllWindow);
	}

	/**
	 * @see com.jme.app.StandardGameState#onActivate()
	 */
	public void onActivate() {
		display.setTitle("MBWS - Select Your Character State");
		super.onActivate();
	}

	/**
	 * Inits the input handler we will use for navigation of the menu.
	 */
	protected void initInput() {
		input = new CharacterSelectionStateHandler(this);
	}

	/**
	 * Initializes the 2D Background and the Buttons
	 */
	private void initGUI() {
		Quad backgroundQuad = new Quad("characterselectionbackground");
		backgroundQuad.initialize(display.getWidth(), display.getHeight());
		backgroundQuad.setLocalTranslation((new Vector3f(
				display.getWidth() / 2, display.getHeight() / 2, 0)));

		LoggingSystem.getLogger().log(
				Level.INFO,
				"display size = " + (float) display.getWidth() + " "
						+ (float) display.getHeight());

		 TextureState ts = display.getRenderer().createTextureState();
		 ts.setTexture(TextureManager.loadTexture(getClass().getClassLoader()
		 .getResource("resources/IntroAndMainMenu/Background.jpg"),
		 Texture.MM_LINEAR, Texture.FM_LINEAR, ts.getMaxAnisotropic(),
		 true));
		
		 ts.setEnabled(true);
		 backgroundQuad.setRenderState(ts);

		rootNode.attachChild(backgroundQuad);

	}

	/**
	 * Updates input and button.
	 * 
	 * @param tpf
	 *            The time since last frame.
	 * @see GameState#update(float)
	 */
	protected void stateUpdate(float tpf) {
		// input.update(tpf);
		rootNode.updateGeometricState(tpf, true);
	}

	public CharacterSelectionStateHandler getInput() {
		return input;
	}

	public BTextArea getCharacterDescription() {
		return characterDescription;
	}

	public void setCharacterDescription(BTextArea characterDescription) {
		this.characterDescription = characterDescription;
	}
}