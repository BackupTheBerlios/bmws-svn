/**
 * Copyright 2005 Please see supplied licence for details.
 */
package de.mbws.client.state;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import com.jme.app.GameState;
import com.jme.app.StandardGameState;
import com.jme.input.MouseInput;
import com.jme.math.Vector3f;
import com.jme.renderer.Renderer;
import com.jme.scene.Text;
import com.jme.scene.shape.Quad;
import com.jme.scene.state.LightState;
import com.jme.system.DisplaySystem;
import com.jme.util.LoggingSystem;
import com.jmex.bui.BButton;
import com.jmex.bui.BContainer;
import com.jmex.bui.BDecoratedWindow;
import com.jmex.bui.BLookAndFeel;
import com.jmex.bui.BRootNode;
import com.jmex.bui.BScrollPane;
import com.jmex.bui.BTextArea;
import com.jmex.bui.BWindow;
import com.jmex.bui.PolledRootNode;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.layout.GroupLayout;

import de.mbws.client.MBWSClient;
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

	/** Our display system. */
	private DisplaySystem display;

	private Text text;

	private CharacterSelectionStateHandler input;

	BRootNode _root;
	BWindow window;
	// BWindow characterWindow;
	BLookAndFeel lnf;
	BButton abortBtn;
	BButton startGameBtn;
	BButton createNewCharacterBtn;
	BContainer cont;
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

	// private void setupButtons() {
	//	 
	//	
	// BContainer cont = new BContainer(GroupLayout
	// .makeVert(GroupLayout.CENTER));
	// BContainer loginContainer = new BContainer(GroupLayout
	// .makeHoriz(GroupLayout.LEFT));
	// BContainer passwordContainer = new BContainer(GroupLayout
	// .makeHoriz(GroupLayout.LEFT));
	// BLabel loginLabel = new BLabel("Login");
	// BLabel passwordLabel = new BLabel("Pass");
	// loginTF = new BTextField("sack");//Settings.getInstance().getLogin());
	// loginTF.setPreferredSize(new Dimension(100, 30));
	// passwordTF = new
	// BTextField("sack");//Settings.getInstance().getPassword());
	// passwordTF.setPreferredSize(new Dimension(100, 30));
	// BButton login = new BButton("Login");
	// BButton account = new BButton("Create Account");
	// BButton options = new BButton("Options");
	//	
	// loginContainer.add(loginLabel);
	// loginContainer.add(loginTF);
	// passwordContainer.add(passwordLabel);
	// passwordContainer.add(passwordTF);
	// cont.add(loginContainer);
	// cont.add(passwordContainer);
	// cont.add(login);
	// cont.add(account);
	// cont.add(options);
	//	
	// window.add(cont, BorderLayout.CENTER);
	//	
	// window.setSize(200, 250);
	// window.setLocation(display.getWidth() / 2 - window.getWidth() / 2,
	// display.getHeight() / 2 - window.getHeight() / 2);
	// _root.addWindow(window);

	private void fillData() {
		List<CharacterShortDescription> allCharacters = ClientPlayerData
				.getInstance().getAllCharactersOfPlayer();
		if (allCharacters != null) {
			Iterator<CharacterShortDescription> it = allCharacters.iterator();
			while (it.hasNext()) {
				CharacterShortDescription aCharacter = it.next();
				BButton characterButton = new BButton(aCharacter.getName()
						+ " (" + aCharacter.getRace() + "/"
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
		window = new BDecoratedWindow(lnf, null);
		// GroupLayout glay = GroupLayout.makeVStretch();
		// glay.setGap(0);
		// cont = new BContainer(glay);
		cont = new BContainer(GroupLayout.makeVert(GroupLayout.TOP));
		fillData();
		characterDescription = new BTextArea();

		window.add(new BScrollPane(cont), BorderLayout.WEST);
		// _root.addWindow(window);
		// Dimension ps = window.getPreferredSize();
		// window.setBounds(100, 300, ps.width, 2 * ps.height / 3);
		window.setSize(250, 250);
		window.setLocation(0, 0);

		// characterWindow = new BDecoratedWindow(lnf, null);
		// characterWindow.add(characterDescription, BorderLayout.EAST);
		// characterWindow.setSize(200, 250);
		// characterWindow.setLocation(display.getWidth()-200, 0);
		_root.addWindow(window);
		// _root.addWindow(characterWindow);
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

		// TextureState ts = display.getRenderer().createTextureState();
		// ts.setTexture(TextureManager.loadTexture(getClass().getClassLoader()
		// .getResource("resources/IntroAndMainMenu/Background.jpg"),
		// Texture.MM_LINEAR, Texture.FM_LINEAR, ts.getMaxAnisotropic(),
		// true));
		//
		// ts.setEnabled(true);
		// backgroundQuad.setRenderState(ts);

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

	// public void displayInfo(String info) {
	// infoWindow = new BPopupWindow(window,new BorderLayout());
	// infoWindow.add(new BLabel(info),BorderLayout.CENTER);
	// BButton button = new BButton("OK");
	// button.addListener(new ActionListener() {
	// public void actionPerformed(ActionEvent event) {
	// infoWindow.dismiss();
	// }
	//			
	// });
	// infoWindow.add(button, BorderLayout.SOUTH);
	// infoWindow.setLocation(display.getWidth() / 2 - window.getWidth() / 2,
	// display.getHeight() / 2 - window.getHeight() / 2);
	// _root.addWindow(infoWindow);
	//		
	// }

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