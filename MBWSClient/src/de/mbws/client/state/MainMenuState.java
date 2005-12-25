/**
 * Copyright 2005 Please see supplied licence for details.
 */
package de.mbws.client.state;

import java.util.logging.Level;

import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

import com.jme.image.Texture;
import com.jme.input.MouseInput;
import com.jme.math.Vector3f;
import com.jme.renderer.Renderer;
import com.jme.scene.Text;
import com.jme.scene.shape.Quad;
import com.jme.scene.state.LightState;
import com.jme.scene.state.TextureState;
import com.jme.util.LoggingSystem;
import com.jme.util.TextureManager;

import de.mbws.client.gui.mainmenu.LoginPanel;
import de.mbws.client.state.handler.MainMenuHandler;

/**
 * @author Kerim
 */
public class MainMenuState extends BaseGameState {

    // /** THE CURSOR NODE WHICH HOLDS THE MOUSE GOTTEN FROM INPUT. */
    // private Node cursor;

    private Text text;

    // private int musicID;

    public MainMenuState(String name) {
        super(name);
        // SoundSystem.init(null, SoundSystem.OUTPUT_DEFAULT);
        // //TODO: Take "bin" out
        // musicID =
        // SoundSystem.createStream(System.getProperty("user.dir")+File.separator+"bin"+File.separator+"resources"
        // + File.separator
        // + "audio" + File.separator + "music" + File.separator
        // + "intro.ogg", false);
        // if (SoundSystem.isStreamOpened(musicID)) {
        // SoundSystem.playStream(musicID);
        // }
        initGUI();
        initText();
        // initCursor();
       
        MouseInput.get().setCursorVisible(true);
        setupMenu();

        rootNode.setLightCombineMode(LightState.OFF);
        rootNode.setRenderQueueMode(Renderer.QUEUE_ORTHO);
        rootNode.updateRenderState();
        rootNode.updateGeometricState(0.0f, true);
    }

    private void setupMenu() {
        try {
            UIManager.setLookAndFeel(new MetalLookAndFeel());    
        } catch (Exception e) {
            // TODO: handle exception
        }
        
//        jmeDesktop.getJDesktop().setBackground(new Color(1, 1, 1, 0.2f));
        JDesktopPane desktopPane = jmeDesktop.getJDesktop();
        LoginPanel loginPanel = new LoginPanel(getInputHandler());
        int x = (desktopPane.getWidth() /2) - (loginPanel.getWidth()/2);
        int y = (desktopPane.getHeight() /2) - (loginPanel.getHeight()/2);
        loginPanel.setLocation(x,y);
        desktopPane.add(loginPanel);
        desktopPane.repaint();
        desktopPane.revalidate();
    }

    /**
     * @see com.jme.app.StandardGameState#onActivate()
     */
    public void onActivate() {
        display.setTitle("Test Game State System - Menu State");
        super.onActivate();
    }

    // /**
    // * Creates a pretty cursor.
    // */
    // private void initCursor() {
    // Texture texture = TextureManager.loadTexture(MainMenuState.class
    // .getClassLoader()
    // .getResource("rsrc/textures/cursor1.png"),
    // Texture.MM_LINEAR_LINEAR, Texture.FM_LINEAR);
    //
    // TextureState ts = display.getRenderer().createTextureState();
    // ts.setEnabled(true);
    // ts.setTexture(texture);
    //
    // AlphaState alpha = display.getRenderer().createAlphaState();
    // alpha.setBlendEnabled(true);
    // alpha.setSrcFunction(AlphaState.SB_SRC_ALPHA);
    // alpha.setDstFunction(AlphaState.DB_ONE);
    // alpha.setTestEnabled(true);
    // alpha.setTestFunction(AlphaState.TF_GREATER);
    // alpha.setEnabled(true);
    //
    // input.getMouse().setRenderState(ts);
    // //input.getMouse().setRenderState(alpha);
    // input.getMouse().setLocalScale(new Vector3f(1, 1, 1));
    //		
    // cursor = new Node("Cursor");
    // cursor.attachChild(input.getMouse());
    //
    // rootNode.attachChild(cursor);
    // }

    /**
     * Initializes the 2D Background and the Buttons
     */
    private void initGUI() {
        Quad backgroundQuad = new Quad("background");
        backgroundQuad.initialize(display.getWidth(), display.getHeight());
        backgroundQuad.setLocalTranslation((new Vector3f(display.getWidth() / 2, display.getHeight() / 2, 0)));

        LoggingSystem.getLogger().log(Level.INFO, "display size = " + (float) display.getWidth() + " " + (float) display.getHeight());

        TextureState ts = display.getRenderer().createTextureState();
        ts.setTexture(TextureManager.loadTexture(getClass().getClassLoader().getResource("resources/IntroAndMainMenu/Background.jpg"),
                Texture.MM_LINEAR, Texture.FM_LINEAR, ts.getMaxAnisotropic(), true));

        ts.setEnabled(true);
        backgroundQuad.setRenderQueueMode(Renderer.QUEUE_ORTHO);
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

    public void displayInfo(String info) {
        JOptionPane.showMessageDialog(jmeDesktop.getJDesktop(), info);
    }

    public MainMenuHandler getInputHandler() {
        return (MainMenuHandler) input;
    }

    @Override
    protected void initInputHandler() {
        input = new MainMenuHandler(this);
    }
}