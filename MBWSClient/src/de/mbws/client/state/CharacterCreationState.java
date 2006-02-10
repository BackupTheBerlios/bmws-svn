/**
 * Copyright 2005 Please see supplied licence for details.
 */
package de.mbws.client.state;

import javax.swing.JDesktopPane;

import org.apache.log4j.Logger;

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

    public void onActivate() {
        display.setTitle("MBWS - Create a new Character");
        super.onActivate();
    }

    private void setupMenu() {
        // jmeDesktop.getJDesktop().setBackground(new Color(1, 1, 1, 0.2f));
        JDesktopPane desktopPane = jmeDesktop.getJDesktop();
//        desktopPane.removeAll();
        ActionPanel actionPanel = new ActionPanel(getInputHandler());
        int x = (desktopPane.getWidth() / 2) - (actionPanel.getWidth() / 2);
        int y = desktopPane.getHeight() - actionPanel.getHeight();
        actionPanel.setLocation(0, y);
        actionPanel.setSize(desktopPane.getWidth(), 80);

        ((CharacterCreationStateHandler) getInputHandler()).addPropertyChangeListener(CharacterCreationStateHandler.CHARACTER_VALIDATION_CHANGE,
                actionPanel);
        desktopPane.add(actionPanel);

        CharacterDetailsPanel cdp = new CharacterDetailsPanel(getInputHandler());
        cdp.addPropertyChangeListener((CharacterCreationStateHandler) getInputHandler());
        cdp.setSize(250, desktopPane.getHeight() - (desktopPane.getHeight() / 3));
        cdp.setLocation(desktopPane.getWidth() - cdp.getWidth(), 0);
        desktopPane.add(cdp);

        desktopPane.repaint();
        desktopPane.revalidate();
    }

    @Override
    protected void initInputHandler() {
        input = new CharacterCreationStateHandler(this);
    }

}