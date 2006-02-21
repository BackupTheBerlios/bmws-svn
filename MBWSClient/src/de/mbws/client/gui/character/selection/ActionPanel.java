package de.mbws.client.gui.character.selection;

import java.awt.Color;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;

import com.jme.input.InputHandler;

import de.mbws.client.ValueMapper;
import de.mbws.client.controller.AccountController;
import de.mbws.client.controller.CharacterController;
import de.mbws.client.controller.ClientNetworkController;
import de.mbws.client.data.ClientGlobals;
import de.mbws.client.gui.LoadingPanel;
import de.mbws.client.state.handler.CharacterSelectionStateHandler;
import de.mbws.common.events.data.generated.PlayerData;

/**
 * Description:
 * 
 * @author Azarai
 */
public class ActionPanel extends JPanel implements PropertyChangeListener {

    private JButton startGameButton = null;

    private final static Color buttonBackground = new Color(155, 0, 7);

    private JLabel characterNameLabel = null;

    InputHandler inputHandler;

    PlayerData selectedCharacter = null;

    private JPanel buttonPanel = null;

    private JPanel rightPanel = null;

    private JButton exitButton = null;

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
        characterNameLabel = new JLabel();
        characterNameLabel.setForeground(Color.ORANGE);
        characterNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        characterNameLabel.setVerticalAlignment(SwingConstants.CENTER);
        characterNameLabel.setText("");
        characterNameLabel.setFont(characterNameLabel.getFont().deriveFont(30F));
        this.setSize(400, 80);
        this.setLayout(new GridLayout(2, 3, 0, 0));
        this.add(new JLabel(""));
        this.add(characterNameLabel, null);
        this.add(new JLabel(""));
        this.add(new JLabel(""));
        this.add(getButtonPanel(), null);
        this.add(getRightPanel(), null);
    }

    /**
     * This method initializes startGameButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getStartGameButton() {
        if (startGameButton == null) {
            startGameButton = new JButton();
            startGameButton.setEnabled(false);
            startGameButton.setText(ValueMapper.getText(ClientGlobals.CHARACTER_SELECTION_BUTTON_ENTER_WORLD));
            startGameButton.setBackground(buttonBackground);
            startGameButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    getInputHandler().getState().showComponentCenteredOnScreen(new LoadingPanel());
                    ClientNetworkController.getInstance().handleOutgoingEvent(
                            CharacterController.getInstance().createCharacterStartPlayingEvent(selectedCharacter.getCharacterID()));
                }
            });
            startGameButton.setSize(startGameButton.getPreferredSize());
        }
        return startGameButton;
    }

    private CharacterSelectionStateHandler getInputHandler() {
        return (CharacterSelectionStateHandler) inputHandler;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        selectedCharacter = (PlayerData) evt.getNewValue();
        characterNameLabel.setText(selectedCharacter.getName());
        startGameButton.setEnabled(true);
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
            buttonPanel.add(getStartGameButton(), null);
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
        if (exitButton == null) {
            exitButton = new JButton();
            exitButton.setText(ValueMapper.getText(ClientGlobals.CHARACTER_SELECTION_BUTTON_LOGOUT));
            exitButton.setBackground(buttonBackground);
            exitButton.setSize(exitButton.getPreferredSize());
            exitButton.setMaximumSize(exitButton.getPreferredSize());
            exitButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    ClientNetworkController.getInstance().handleOutgoingEvent(
                            AccountController.getInstance().createLogoutEvent());
                }
            });
        }
        return exitButton;
    }
} // @jve:decl-index=0:visual-constraint="10,10"
