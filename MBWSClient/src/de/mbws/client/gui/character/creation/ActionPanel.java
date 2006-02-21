package de.mbws.client.gui.character.creation;

import java.awt.Color;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import com.jme.input.InputHandler;

import de.mbws.client.ValueMapper;
import de.mbws.client.controller.CharacterController;
import de.mbws.client.controller.ClientNetworkController;
import de.mbws.client.data.ClientGlobals;
import de.mbws.client.state.handler.BaseInputHandler;
import de.mbws.client.state.handler.CharacterCreationStateHandler;

/**
 * Description:
 * 
 * @author Azarai
 */
public class ActionPanel extends JPanel implements PropertyChangeListener {

    private JButton createCharacterButton = null;

    private final static Color buttonBackground = new Color(155, 0, 7);

    InputHandler inputHandler;

    private JPanel buttonPanel = null;

    private JPanel rightPanel = null;

    private JButton backButton = null;

    /**
     * This is the default constructor
     */
    public ActionPanel(InputHandler inputHandler) {
        super();
        this.inputHandler = inputHandler;
        initialize();
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        this.setOpaque(false);
        // this.setBackground(new Color(0, 0, 0, 0.2f));
        this.setSize(400, 40);
        this.setLayout(new GridLayout(1, 3, 0, 0));
        this.add(new JLabel(""));
        this.add(getButtonPanel(), null);
        this.add(getRightPanel(), null);
    }

    /**
     * This method initializes startGameButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getCreateCharacterButton() {
        if (createCharacterButton == null) {
            createCharacterButton = new JButton();
            createCharacterButton.setEnabled(false);
            createCharacterButton.setText(ValueMapper.getText(ClientGlobals.CHARACTER_CREATION_BUTTON_CREATE_CHARACTER));
            createCharacterButton.setBackground(buttonBackground);
            createCharacterButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    Integer i = new Integer(getInputHandler().getCharacterData().getRace());
                    ClientNetworkController.getInstance().handleOutgoingEvent(
                            CharacterController.getInstance().createCreateCharacterEvent(getInputHandler().getCharacterData().getName(),
                                    getInputHandler().getCharacterData().getGender(), i.byteValue()));
                }
            });
            createCharacterButton.setSize(createCharacterButton.getPreferredSize());
        }
        return createCharacterButton;
    }

    private CharacterCreationStateHandler getInputHandler() {
        return (CharacterCreationStateHandler) inputHandler;
    }

    public void propertyChange(PropertyChangeEvent evt) {

        Boolean value = (Boolean) evt.getNewValue();
        if (value.booleanValue()) {
            createCharacterButton.setEnabled(true);
        } else {
            createCharacterButton.setEnabled(false);
        }
        getParent().repaint();
    }

    /**
     * This method initializes buttonPanel
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getButtonPanel() {
        if (buttonPanel == null) {
            buttonPanel = new JPanel();
            buttonPanel.setOpaque(false);
            buttonPanel.add(getCreateCharacterButton(), null);
        }
        return buttonPanel;
    }

    /**
     * This method initializes rightPanel
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getRightPanel() {
        if (rightPanel == null) {
            rightPanel = new JPanel();
            SpringLayout l = new SpringLayout();
            l.putConstraint(SpringLayout.EAST, getExitButton(), 5, SpringLayout.EAST, rightPanel);
            l.putConstraint(SpringLayout.NORTH, getExitButton(), 5, SpringLayout.NORTH, rightPanel);
            rightPanel.setLayout(l);
            rightPanel.setOpaque(false);
            rightPanel.add(getExitButton());
        }
        return rightPanel;
    }

    /**
     * This method initializes exitButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getExitButton() {
        if (backButton == null) {
            backButton = new JButton();
            backButton.setText(ValueMapper.getText(ClientGlobals.GENERIC_BUTTON_BACK));
            backButton.setBackground(buttonBackground);
            backButton.setSize(backButton.getPreferredSize());
            backButton.setMaximumSize(backButton.getPreferredSize());
            backButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    getInputHandler().requestStateSwitch(BaseInputHandler.GAMESTATE_CHARACTER_SELECTION);
                }
            });
        }
        return backButton;
    }
} // @jve:decl-index=0:visual-constraint="10,10"
