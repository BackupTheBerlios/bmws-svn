package de.mbws.client.gui.character.selection;

import java.awt.Color;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.jme.input.InputHandler;

import de.mbws.client.controller.CharacterController;
import de.mbws.client.controller.ClientNetworkController;
import de.mbws.client.state.handler.CharacterSelectionStateHandler;
import de.mbws.common.eventdata.generated.CharacterData;

/**
 * Description: 
 * @author Azarai
 *
 */
public class ActionPanel extends JPanel implements PropertyChangeListener{

    private JButton startGameButton = null;
    private final static Color buttonBackground = new Color(155, 0, 7);
    private JLabel characterNameLabel = null;
    InputHandler inputHandler;
    CharacterData selectedCharacter = null;
    private JPanel buttonPanel = null;
    
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
//        this.setBackground(new Color(0, 0, 0, 0.2f));
        characterNameLabel = new JLabel();
        characterNameLabel.setForeground(Color.ORANGE);
        characterNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        characterNameLabel.setVerticalAlignment(SwingConstants.CENTER);
        characterNameLabel.setText("");
        characterNameLabel.setFont(characterNameLabel.getFont().deriveFont(30F));
        this.setSize(200, 80);
        this.setLayout(new GridLayout(2,1, 0, 0));
        this.add(characterNameLabel, null);
        this.add(getButtonPanel(), null);
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
            startGameButton.setText("Enter World");
            startGameButton.setBackground(buttonBackground);
            startGameButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    ClientNetworkController.getInstance()
                    .handleOutgoingEvent(
                            CharacterController.getInstance()
                                    .createCharacterStartPlayingEvent(
                                            selectedCharacter.getCharacterID()));
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
        selectedCharacter = (CharacterData) evt.getNewValue();
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
}  //  @jve:decl-index=0:visual-constraint="10,10"
