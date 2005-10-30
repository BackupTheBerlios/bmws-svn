/**
 * Copyright 2005 Please see supplied licence for details.
 */
package de.mbws.client.state;

import java.util.logging.Level;

import com.jme.app.GameState;
import com.jme.app.StandardGameState;
import com.jme.image.Texture;
import com.jme.input.MouseInput;
import com.jme.math.Vector3f;
import com.jme.renderer.Renderer;
import com.jme.scene.Node;
import com.jme.scene.Text;
import com.jme.scene.shape.Quad;
import com.jme.scene.state.AlphaState;
import com.jme.scene.state.LightState;
import com.jme.scene.state.TextureState;
import com.jme.system.DisplaySystem;
import com.jme.util.LoggingSystem;
import com.jme.util.TextureManager;
import com.jmex.bui.BButton;
import com.jmex.bui.BContainer;
import com.jmex.bui.BDecoratedWindow;
import com.jmex.bui.BLabel;
import com.jmex.bui.BLookAndFeel;
import com.jmex.bui.BRootNode;
import com.jmex.bui.BTextField;
import com.jmex.bui.BWindow;
import com.jmex.bui.PolledRootNode;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.layout.GroupLayout;
import com.jmex.bui.util.Dimension;

import de.mbws.client.MBWSClient;
import de.mbws.client.state.handler.MainMenuHandler;

/**
 * @author Kerim
 */
public class MainMenuState extends StandardGameState {

	/** The cursor node which holds the mouse gotten from input. */
	private Node cursor;

	/** Our display system. */
	private DisplaySystem display;

	private Text text;

	private MainMenuHandler input;

	BTextField loginTF;

	BTextField passwordTF;
	BRootNode _root;
	

	public MainMenuState(String name) {
		super(name);
		
		display = DisplaySystem.getDisplaySystem();
		initInput();
		initGUI();
		initText();
		setupButtons();
		//initCursor();
		 MouseInput.get().setCursorVisible(true);

		
		

		rootNode.setLightCombineMode(LightState.OFF);
		rootNode.setRenderQueueMode(Renderer.QUEUE_ORTHO);
		rootNode.updateRenderState();
		rootNode.updateGeometricState(0, true);
	}

	private void setupButtons() {
		_root = new PolledRootNode(MBWSClient.timer, input);
		rootNode.attachChild(_root);
		BLookAndFeel lnf = BLookAndFeel.getDefaultLookAndFeel();
		BWindow window = new BDecoratedWindow(lnf, null);

		BContainer cont = new BContainer(GroupLayout
				.makeVert(GroupLayout.CENTER));
		BContainer loginContainer = new BContainer(GroupLayout
				.makeHoriz(GroupLayout.LEFT));
		BContainer passwordContainer = new BContainer(GroupLayout
				.makeHoriz(GroupLayout.LEFT));
		BLabel loginLabel = new BLabel("Login");
		BLabel passwordLabel = new BLabel("Pass");
		loginTF = new BTextField("sack");//Settings.getInstance().getLogin());
		loginTF.setPreferredSize(new Dimension(100, 30));
		passwordTF = new BTextField("sack");//Settings.getInstance().getPassword());
		passwordTF.setPreferredSize(new Dimension(100, 30));
		BButton login = new BButton("Login");
		BButton options = new BButton("Options");

		loginContainer.add(loginLabel);
		loginContainer.add(loginTF);
		passwordContainer.add(passwordLabel);
		passwordContainer.add(passwordTF);
		cont.add(loginContainer);
		cont.add(passwordContainer);
		cont.add(login);
		cont.add(options);

		window.add(cont, BorderLayout.CENTER);

		window.setSize(200, 150);
		window.setLocation(display.getWidth() / 2 - window.getWidth() / 2,
				display.getHeight() / 2 - window.getHeight() / 2);
		_root.addWindow(window);
		loginTF.requestFocus();
		login.addListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String password = passwordTF.getText();
				String login = loginTF.getText();
				if (login != null && password != null) {
					input.login(login, password);
				}
			}
		});

	}

	/**
	 * @see com.jme.app.StandardGameState#onActivate()
	 */
	public void onActivate() {
		display.setTitle("Test Game State System - Menu State");
		super.onActivate();
	}

	/**
	 * Inits the input handler we will use for navigation of the menu.
	 */
	protected void initInput() {
		input = new MainMenuHandler(this);
	}

	/**
	 * Creates a pretty cursor.
	 */
	private void initCursor() {
		Texture texture = TextureManager.loadTexture(MainMenuState.class
				.getClassLoader()
				.getResource("rsrc/textures/cursor1.png"),
				Texture.MM_LINEAR_LINEAR, Texture.FM_LINEAR);

		TextureState ts = display.getRenderer().createTextureState();
		ts.setEnabled(true);
		ts.setTexture(texture);

		AlphaState alpha = display.getRenderer().createAlphaState();
		alpha.setBlendEnabled(true);
		alpha.setSrcFunction(AlphaState.SB_SRC_ALPHA);
		alpha.setDstFunction(AlphaState.DB_ONE);
		alpha.setTestEnabled(true);
		alpha.setTestFunction(AlphaState.TF_GREATER);
		alpha.setEnabled(true);

		input.getMouse().setRenderState(ts);
		//input.getMouse().setRenderState(alpha);
		input.getMouse().setLocalScale(new Vector3f(1, 1, 1));
		
		cursor = new Node("Cursor");
		cursor.attachChild(input.getMouse());

		rootNode.attachChild(cursor);
	}

	/**
	 * Initializes the 2D Background and the Buttons
	 */
	private void initGUI() {
		Quad backgroundQuad = new Quad("background");
		backgroundQuad.initialize((float) display.getWidth(), (float) display
				.getHeight());
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
	 * Inits the button placed at the center of the screen.
	 */
	private void initText() {
		text = Text.createDefaultTextLabel("menu");
		text.print("press esc (exit) or click (next step)");
		text.getLocalTranslation().set(0, 100, 0);

		rootNode.attachChild(text);
	}

	/**
	 * Updates input and button.
	 * 
	 * @param tpf
	 *            The time since last frame.
	 * @see GameState#update(float)
	 */
	protected void stateUpdate(float tpf) {
		input.update(tpf);
		rootNode.updateGeometricState(tpf, true);
	}
}