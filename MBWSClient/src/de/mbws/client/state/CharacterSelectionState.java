/**
 * Copyright 2005 Please see supplied licence for details.
 */
package de.mbws.client.state;

import java.util.logging.Level;

import javax.swing.JDesktopPane;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

import com.jme.app.GameState;
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
import com.jmex.bui.BLookAndFeel;
import com.jmex.bui.BRootNode;
import com.jmex.bui.BTextArea;
import com.jmex.bui.BWindow;

import de.mbws.client.data.ClientPlayerData;
import de.mbws.client.gui.character.selection.ActionPanel;
import de.mbws.client.gui.character.selection.CharacterListPanel;
import de.mbws.client.state.handler.CharacterSelectionStateHandler;

/**
 * @author Kerim
 */
public class CharacterSelectionState extends BaseGameState {

	// /** THE CURSOR NODE WHICH HOLDS THE MOUSE GOTTEN FROM INPUT. */
	// private Node cursor;

	public static final String CREATE_CHARACTER = "CREATECHARACTER";
	public static final String DELETE_CHARACTER = "DELETECHARACTER";
	public static final String STARTGAME = "STARTGAME";

    //private Text text;

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
        setupMenu();
		// initText();
		// setupButtons();
		// initCursor();
		MouseInput.get().setCursorVisible(true);

		rootNode.setLightCombineMode(LightState.OFF);
		rootNode.setRenderQueueMode(Renderer.QUEUE_ORTHO);
		rootNode.updateRenderState();
		rootNode.updateGeometricState(0, true);
	}

    private void setupMenu() {
        try {
            UIManager.setLookAndFeel(new MetalLookAndFeel());    
        } catch (Exception e) {
            // TODO: handle exception
        }
        
//        jmeDesktop.getJDesktop().setBackground(new Color(1, 1, 1, 0.2f));
        JDesktopPane desktopPane = jmeDesktop.getJDesktop();
        ActionPanel actionPanel = new ActionPanel(getInputHandler());
        int x = (desktopPane.getWidth() /2) - (actionPanel.getWidth()/2);
        int y = desktopPane.getHeight() - actionPanel.getHeight();
        actionPanel.setLocation(0,y);
        actionPanel.setSize(desktopPane.getWidth(), 80);
        desktopPane.add(actionPanel);
        
        CharacterListPanel clp = new CharacterListPanel(ClientPlayerData
                .getInstance().getAllCharactersOfPlayer());
        clp.addPropertyChangeListener(CharacterListPanel.CHARACTER_SELECTION_CHANGE, actionPanel);
        clp.setSize(200, desktopPane.getHeight() - (desktopPane.getHeight()/4));
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

	public CharacterSelectionStateHandler getInputHandler() {
		return (CharacterSelectionStateHandler) input;
	}

	public BTextArea getCharacterDescription() {
		return characterDescription;
	}

	public void setCharacterDescription(BTextArea characterDescription) {
		this.characterDescription = characterDescription;
	}

    @Override
    protected void initInputHandler() {
        input = new CharacterSelectionStateHandler(this);
        
    }
}