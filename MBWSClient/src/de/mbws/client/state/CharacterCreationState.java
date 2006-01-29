/**
 * Copyright 2005 Please see supplied licence for details.
 */
package de.mbws.client.state;

import javax.swing.JDesktopPane;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

import org.apache.log4j.Logger;

import com.jme.app.GameState;
import com.jme.app.GameStateManager;
import com.jme.input.MouseInput;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.state.LightState;

import de.mbws.client.gui.character.creation.ActionPanel;
import de.mbws.client.gui.character.creation.CharacterDetailsPanel;
import de.mbws.client.state.handler.CharacterCreationStateHandler;

/**
 * @author Kerim
 */
public class CharacterCreationState extends BaseGameState {

    private static Logger logger = Logger.getLogger(CharacterCreationState.class);

    // /** THE CURSOR NODE WHICH HOLDS THE MOUSE GOTTEN FROM INPUT. */
    // private Node cursor;

    public CharacterCreationState(String name) {
        super(name);
        display.getRenderer().setBackgroundColor(ColorRGBA.blue);
        // display = DisplaySystem.getDisplaySystem();
        setupMenu();
        

        MouseInput.get().setCursorVisible(true);

        rootNode.setLightCombineMode(LightState.OFF);
        rootNode.setRenderQueueMode(Renderer.QUEUE_ORTHO);
        rootNode.updateRenderState();
        rootNode.updateGeometricState(0, true);
    }

//    private void initBUIGUI() {
//        _root = new PolledRootNode(MBWSClient.timer, null);
//        rootNode.attachChild(_root);
//        lnf = MenuLookAndFeel.getDefaultLookAndFeel();
//        characterWindow = new BDecoratedWindow(lnf, null);
//        cont = new BContainer(GroupLayout.makeVert(GroupLayout.TOP));
//        // fillData();
//        characterDescription = new BTextArea();
//
//        // window.add(new BScrollPane(cont), BorderLayout.WEST);
//        characterWindow.add(cont, BorderLayout.WEST);
//        characterWindow.setSize(250, 250);
//        characterWindow.setLocation(display.getWidth() - 250, display.getHeight() - 250);
//
//        controllWindow = new BDecoratedWindow(lnf, null);
//        controllContainer = new BContainer(GroupLayout.makeHoriz(GroupLayout.CENTER));
//
//        controllWindow.setSize(250, 50);
//        controllWindow.setLocation(display.getWidth() - 250, 0);
//
//        BContainer nameContainer = new BContainer(GroupLayout.makeHoriz(GroupLayout.LEFT));
//        ;
//
//        BLabel nameLb = new BLabel("Name: ");
//        nameTF = new BTextField("Enter Your name here ");
//        nameContainer.add(nameLb);
//        nameContainer.add(nameTF);
//        cont.add(nameContainer);
//
//        BContainer raceContainer = new BContainer(GroupLayout.makeHoriz(GroupLayout.LEFT));
//        ;
//        BLabel raceLb = new BLabel("Race: ");
//        raceCB = new BComboBox();
//        // combobox.setPreferredSize(new Dimension(250,50));
//        raceCB.addItem("test1");
//        raceCB.addItem("test2");
//        raceCB.selectItem(0);
//        raceContainer.add(raceLb);
//        raceContainer.add(raceCB);
//        cont.add(raceContainer);
//
//        gender = new BCheckBox("Male");
//        cont.add(gender);
//
//        abortBtn = new BButton("Delete");// , input, DELETE_CHARACTER);
//        abortBtn.addListener(new ActionListener() {
//            public void actionPerformed(ActionEvent event) {
//                returnToCharacterSelectionState();
//            }
//        });
//        abortBtn.setPreferredSize(new Dimension(70, 30));
//        controllContainer.add(abortBtn);
//
//        createBtn = new BButton("Create");// , input, CREATE_CHARACTER);
//        createBtn.addListener(new ActionListener() {
//            public void actionPerformed(ActionEvent event) {
//                // TODO: implement mapping
//                byte genderValue = 0;
//                if (gender.isChecked()) {
//                    genderValue = 1;
//                }
//                byte raceValue = 0;
//                ClientNetworkController.getInstance().handleOutgoingEvent(
//                        CharacterController.getInstance().createCreateCharacterEvent(nameTF.getText().trim(), genderValue, raceValue));
//                returnToCharacterSelectionState();
//            }
//        });
//        createBtn.setPreferredSize(new Dimension(70, 30));
//        controllContainer.add(createBtn);
//
//        controllWindow.add(controllContainer, BorderLayout.CENTER);
//
//        _root.addWindow(characterWindow);
//        _root.addWindow(controllWindow);
//    }

    public void onActivate() {
        display.setTitle("MBWS - Create a new Character");
        super.onActivate();
    }

    private void setupMenu() {
        try {
            UIManager.setLookAndFeel(new MetalLookAndFeel());
        } catch (Exception e) {
            // TODO: handle exception
        }

        // jmeDesktop.getJDesktop().setBackground(new Color(1, 1, 1, 0.2f));
        JDesktopPane desktopPane = jmeDesktop.getJDesktop();
        desktopPane.removeAll();
        ActionPanel actionPanel = new ActionPanel(getInputHandler());
        int x = (desktopPane.getWidth() / 2) - (actionPanel.getWidth() / 2);
        int y = desktopPane.getHeight() - actionPanel.getHeight();
        actionPanel.setLocation(0, y);
        actionPanel.setSize(desktopPane.getWidth(), 80);
        desktopPane.add(actionPanel);

        CharacterDetailsPanel cdp = new CharacterDetailsPanel(getInputHandler());
        cdp.addPropertyChangeListener((CharacterCreationStateHandler) getInputHandler());
        cdp.setSize(250, desktopPane.getHeight()
                - (desktopPane.getHeight() / 3));
        cdp.setLocation(desktopPane.getWidth() - cdp.getWidth(), 0);
        desktopPane.add(cdp);

        desktopPane.repaint();
        desktopPane.revalidate();
    }

    public void returnToCharacterSelectionState() {
        GameState characterSelection = GameStateManager.getInstance().getChild("characterSelection");
        characterSelection.setActive(true);
        GameStateManager.getInstance().deactivateChildNamed("characterCreation");
    }

    @Override
    protected void initInputHandler() {
        input = new CharacterCreationStateHandler(this);
    }

}