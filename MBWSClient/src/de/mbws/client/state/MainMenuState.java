/**
 * Copyright 2005 Please see supplied licence for details.
 */
package de.mbws.client.state;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.logging.Level;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JTextField;

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
import com.jmex.bui.BButton;
import com.jmex.bui.BContainer;
import com.jmex.bui.BDecoratedWindow;
import com.jmex.bui.BLabel;
import com.jmex.bui.BLookAndFeel;
import com.jmex.bui.BPopupWindow;
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
import de.mbws.client.controller.AccountController;
import de.mbws.client.controller.ClientNetworkController;
import de.mbws.client.gui.MenuLookAndFeel;
import de.mbws.client.state.handler.MainMenuHandler;
import de.mbws.common.data.AccountData;

/**
 * @author Kerim
 */
public class MainMenuState extends BaseGameState {

    // /** THE CURSOR NODE WHICH HOLDS THE MOUSE GOTTEN FROM INPUT. */
    // private Node cursor;

    private Text text;

    // private int musicID;

    BTextField loginTF;

    BTextField passwordTF;

    BRootNode _root;

    BWindow window;

    BLookAndFeel lnf;

    BPopupWindow infoWindow;

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
        setupButtons();
        // initCursor();
        MouseInput.get().setCursorVisible(true);
        setupMenu();

        rootNode.setLightCombineMode(LightState.OFF);
        rootNode.setRenderQueueMode(Renderer.QUEUE_ORTHO);
        rootNode.updateRenderState();
        rootNode.updateGeometricState(0.0f, true);
    }

    private void setupMenu() {
        jmeDesktop.getJDesktop().setBackground(new Color(1, 1, 1, 0.2f));
        final JDesktopPane desktopPane = jmeDesktop.getJDesktop();
//        final JPanel p = new JPanel(new GridBagLayout());
//        //GridBagLayout gb = new GridBagLayout();
//        GridBagConstraints gbc = new GridBagConstraints();
//        p.setLocation(0,100);//display.getWidth() / 2 - window.getWidth() / 2, display.getHeight() / 2 - window.getHeight() / 2);
        //desktopPane.add(p,);
        desktopPane.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        final JLabel loginLabel = new JLabel ("Login");
        final JTextField loginTF = new JTextField("sack");
        final JLabel passwordLabel = new JLabel ("Password");
        final JTextField passwordTF = new JTextField("sack");
       
        
        gbc.gridx=0;
        desktopPane.add(loginLabel,gbc);
        gbc.gridx=1;
        desktopPane.add(loginTF,gbc);
        gbc.gridy+=1;
        gbc.gridx=0;
        desktopPane.add(passwordLabel,gbc);
        gbc.gridx=1;
        desktopPane.add(passwordTF,gbc);
        gbc.gridy+=1;
        gbc.gridx=0;
        final JButton loginButton = new JButton("Login");
        desktopPane.add(loginButton,gbc);
       // loginButton.setLocation(0, 100);
        loginButton.setSize(loginButton.getPreferredSize());
//        button3.addActionListener(new java.awt.event.ActionListener() {
//
//            public void actionPerformed(java.awt.event.ActionEvent e) {
//                System.out.println("Suck my dick!!! Dreck 3D");
//            }
//        });
        
//        final JButton button3 = new JButton("S.M.D");
//        button3.setLocation(0, 100);
//        button3.setSize(button3.getPreferredSize());
//        button3.addActionListener(new java.awt.event.ActionListener() {
//
//            public void actionPerformed(java.awt.event.ActionEvent e) {
//                System.out.println("Suck my dick!!! Dreck 3D");
//            }
//        });

     //   desktopPane.add(loginButton,gbc);
        desktopPane.repaint();
        desktopPane.revalidate();
    }

    private void setupButtons() {
        _root = new PolledRootNode(MBWSClient.timer, input);// FixedLogicalRateClient.timer,
        // input);
        rootNode.attachChild(_root);
        lnf = MenuLookAndFeel.getDefaultLookAndFeel();
        window = new BDecoratedWindow(lnf, null);

        BContainer cont = new BContainer(GroupLayout.makeVert(GroupLayout.CENTER));
        BContainer loginContainer = new BContainer(GroupLayout.makeHoriz(GroupLayout.LEFT));
        BContainer passwordContainer = new BContainer(GroupLayout.makeHoriz(GroupLayout.LEFT));
        BLabel loginLabel = new BLabel("Login");
        BLabel passwordLabel = new BLabel("Pass");
        loginTF = new BTextField("sack");// Settings.getInstance().getLogin());
        loginTF.setPreferredSize(new Dimension(100, 30));
        passwordTF = new BTextField("sack");// Settings.getInstance().getPassword());
        passwordTF.setPreferredSize(new Dimension(100, 30));
        BButton login = new BButton("Login");
        BButton account = new BButton("Create Account");
        BButton options = new BButton("Options");
        login.setPreferredSize(new Dimension(200, 25));
        account.setPreferredSize(new Dimension(200, 25));
        options.setPreferredSize(new Dimension(200, 25));

        loginContainer.add(loginLabel);
        loginContainer.add(loginTF);
        passwordContainer.add(passwordLabel);
        passwordContainer.add(passwordTF);
        cont.add(loginContainer);
        cont.add(passwordContainer);
        cont.add(login);
        cont.add(account);
        cont.add(options);

        window.add(cont, BorderLayout.CENTER);

        window.setSize(200, 250);
        window.setLocation(display.getWidth() / 2 - window.getWidth() / 2, display.getHeight() / 2 - window.getHeight() / 2);
        _root.addWindow(window);
        loginTF.requestFocus();
        login.addListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String password = passwordTF.getText();
                String login = loginTF.getText();
                if (login != null && password != null) {
                    getInputHandler().login(login, password);
                    // SoundSystem.stopStream(musicID);
                }
            }
        });
        account.addListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String password = passwordTF.getText();
                String login = loginTF.getText();
                if (login != null && password != null) {
                    AccountData account = new AccountData();
                    account.setUsername(login);
                    account.setPassword(password);
                    account.setPasswordConfirmation(password);
                    // TODO correct that
                    ClientNetworkController.getInstance().handleOutgoingEvent(AccountController.getInstance().createRegisterEvent(account, null));
                    getInputHandler().login(login, password);
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
        infoWindow = new BPopupWindow(window, new BorderLayout());
        infoWindow.add(new BLabel(info), BorderLayout.CENTER);
        BButton button = new BButton("OK");
        button.addListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                infoWindow.dismiss();
            }

        });
        infoWindow.add(button, BorderLayout.SOUTH);
        infoWindow.setLocation(display.getWidth() / 2 - window.getWidth() / 2, display.getHeight() / 2 - window.getHeight() / 2);
        _root.addWindow(infoWindow);

    }

    public MainMenuHandler getInputHandler() {
        return (MainMenuHandler) input;
    }

    @Override
    protected void initInputHandler() {
        input = new MainMenuHandler(this);
    }
}