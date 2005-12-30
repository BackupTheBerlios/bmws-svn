/**
 * Copyright 2005 Please see supplied licence for details.
 */
package de.mbws.client.state;

import java.util.logging.Level;

import com.jme.app.GameState;
import com.jme.app.GameStateManager;
import com.jme.image.Texture;
import com.jme.input.MouseInput;
import com.jme.math.Vector3f;
import com.jme.renderer.Renderer;
import com.jme.scene.shape.Quad;
import com.jme.scene.state.LightState;
import com.jme.scene.state.TextureState;
import com.jme.util.LoggingSystem;
import com.jme.util.TextureManager;
import com.jmex.bui.BButton;
import com.jmex.bui.BCheckBox;
import com.jmex.bui.BComboBox;
import com.jmex.bui.BContainer;
import com.jmex.bui.BDecoratedWindow;
import com.jmex.bui.BLabel;
import com.jmex.bui.BLookAndFeel;
import com.jmex.bui.BRootNode;
import com.jmex.bui.BTextArea;
import com.jmex.bui.BTextField;
import com.jmex.bui.BWindow;
import com.jmex.bui.PolledRootNode;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.layout.GroupLayout;
import com.jmex.bui.util.Dimension;

import de.mbws.client.MBWSClient;
import de.mbws.client.controller.CharacterController;
import de.mbws.client.controller.ClientNetworkController;
import de.mbws.client.gui.MenuLookAndFeel;

/**
 * @author Kerim
 */
public class CharacterCreationState extends BaseGameState {

	// /** THE CURSOR NODE WHICH HOLDS THE MOUSE GOTTEN FROM INPUT. */
	// private Node cursor;

	public static final String CREATE_CHARACTER = "CREATECHARACTER";
	public static final String DELETE_CHARACTER = "DELETECHARACTER";
	public static final String STARTGAME = "STARTGAME";

	//private CharacterSelectionStateHandler input;

	BRootNode _root;
	public BWindow characterWindow;
	BContainer cont;

	BWindow controllWindow;
	BContainer controllContainer;
	BComboBox raceCB;
	BCheckBox gender;
	BTextField nameTF;
	
	BLookAndFeel lnf;

	
	public BButton abortBtn;
	public BButton createBtn;

	BTextArea characterDescription;

	public CharacterCreationState(String name) {
		super(name);

//		display = DisplaySystem.getDisplaySystem();
		initGUI();
		initBUIGUI();
		
		MouseInput.get().setCursorVisible(true);

		rootNode.setLightCombineMode(LightState.OFF);
		rootNode.setRenderQueueMode(Renderer.QUEUE_ORTHO);
		rootNode.updateRenderState();
		rootNode.updateGeometricState(0, true);
	}

	private void initBUIGUI() {
		_root = new PolledRootNode(MBWSClient.timer, null);
		rootNode.attachChild(_root);
		lnf = MenuLookAndFeel.getDefaultLookAndFeel();
		characterWindow = new BDecoratedWindow(lnf, null);
		cont = new BContainer(GroupLayout.makeVert(GroupLayout.TOP));
		//fillData();
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

		BContainer nameContainer = new BContainer(GroupLayout.makeHoriz(GroupLayout.LEFT));;
		
		BLabel nameLb = new BLabel("Name: ");
		nameTF = new BTextField("Enter Your name here ");
		nameContainer.add(nameLb);
		nameContainer.add(nameTF);
		cont.add(nameContainer);
		
		BContainer raceContainer = new BContainer(GroupLayout.makeHoriz(GroupLayout.LEFT));;
		BLabel raceLb = new BLabel("Race: ");
		raceCB = new BComboBox();
		//combobox.setPreferredSize(new Dimension(250,50));
		raceCB.addItem("test1");
		raceCB.addItem("test2");
		raceCB.selectItem(0);
		raceContainer.add(raceLb);
		raceContainer.add(raceCB);
		cont.add(raceContainer);
		
		gender = new BCheckBox("Male");
		cont.add(gender);

		abortBtn = new BButton("Delete");//, input, DELETE_CHARACTER);
		abortBtn.addListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				returnToCharacterSelectionState();
			}});
		abortBtn.setPreferredSize(new Dimension(70, 30));
		controllContainer.add(abortBtn);
		

		createBtn = new BButton("Create");//, input, CREATE_CHARACTER);
		createBtn.addListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				//TODO: implement mapping
				byte genderValue = 0;
				if (gender.isChecked()) {
					genderValue = 1;
				}
				byte raceValue  = 0;
				ClientNetworkController.getInstance().handleOutgoingEvent(CharacterController.getInstance()
						.createCreateCharacterEvent(
								nameTF.getText().trim(),genderValue, raceValue)); 
				returnToCharacterSelectionState();
			}});
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
	
	public void returnToCharacterSelectionState() {
		 GameState characterSelection = GameStateManager.getInstance().getChild("characterSelection");
		 characterSelection.setActive(true);
		 GameStateManager.getInstance().deactivateChildNamed("characterCreation");
	}

    @Override
    protected void initInputHandler() {
        
    }
}