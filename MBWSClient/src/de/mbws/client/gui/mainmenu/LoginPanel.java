package de.mbws.client.gui.mainmenu;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import com.jme.input.InputHandler;

import de.mbws.client.ValueMapper;
import de.mbws.client.controller.AccountController;
import de.mbws.client.controller.ClientNetworkController;
import de.mbws.client.data.ClientGlobals;
import de.mbws.client.data.ClientPlayerData;
import de.mbws.client.state.handler.MainMenuHandler;
import de.mbws.common.data.AccountData;

/**
 * Description:
 * 
 * @author Azarai
 */
public class LoginPanel extends JPanel {

    private JLabel usernameLabel = null;

    private JTextField usernameInputField = null;

    private JLabel passwordLabel = null;

    private JPasswordField passwordField = null;

    private JButton loginButton = null;

    private JButton createAccountButton = null;

    private JButton showOptionsButton = null;

    private JButton exitButton = null;

    private InputHandler inputHandler;

    private final static Color buttonBackground = new Color(155, 0, 7);

    private MainMenuHandler getInputHandler() {
        return (MainMenuHandler) inputHandler;
    }

    /**
     * This method initializes usernameInputField
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getUsernameInputField() {
        if (usernameInputField == null) {
            usernameInputField = new JTextField();
            usernameInputField.setText("sack");
            usernameInputField.setBounds(new java.awt.Rectangle(110,20,131,21));
        }
        return usernameInputField;
    }

    /**
     * This method initializes passwordField
     * 
     * @return javax.swing.JPasswordField
     */
    private JPasswordField getPasswordField() {
        if (passwordField == null) {
            passwordField = new JPasswordField();
            passwordField.setText("sack");
            passwordField.setBounds(new java.awt.Rectangle(110,50,131,21));
        }
        return passwordField;
    }

    /**
     * This method initializes loginButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getLoginButton() {
        if (loginButton == null) {
            loginButton = new JButton();
            loginButton.setBounds(new java.awt.Rectangle(20, 100, 211, 31));
            loginButton.setText(ValueMapper.getText(ClientGlobals.MENU_BUTTON_LOGIN));
            loginButton.setBackground(buttonBackground);
            loginButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
//                    getParent().repaint();
                    getInputHandler().login(usernameInputField.getText(), String.valueOf(passwordField.getPassword()));
                }
            });
        }
        return loginButton;
    }

    /**
     * This method initializes createAccountButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getCreateAccountButton() {
        if (createAccountButton == null) {
            createAccountButton = new JButton();
            createAccountButton.setBounds(new java.awt.Rectangle(20, 150, 211, 31));
            createAccountButton.setText(ValueMapper.getText(ClientGlobals.MENU_BUTTON_CREATE_ACCOUNT));
            createAccountButton.setBackground(buttonBackground);
            createAccountButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    String password = String.valueOf(passwordField.getPassword());
                    String username = usernameInputField.getText();
                    if (username != null && password != null) {
                        AccountData account = new AccountData();
                        account.setUsername(username);
                        account.setPassword(password);
                        account.setPasswordConfirmation(password);
                        ClientNetworkController.getInstance().handleOutgoingEvent(AccountController.getInstance().createRegisterEvent(account, ClientPlayerData.getInstance()));
                        getInputHandler().login(username, password);
                    }
                }
            });
        }
        return createAccountButton;
    }

    /**
     * This method initializes showOptionsButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getShowOptionsButton() {
        if (showOptionsButton == null) {
            showOptionsButton = new JButton();
            showOptionsButton.setBounds(new java.awt.Rectangle(20, 200, 211, 31));
            showOptionsButton.setText(ValueMapper.getText(ClientGlobals.MENU_BUTTON_OPTIONS));
            showOptionsButton.setBackground(buttonBackground);
        }
        return showOptionsButton;
    }

    /**
     * This method initializes exitButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getExitButton() {
        if (exitButton == null) {
            exitButton = new JButton();
            exitButton.setBounds(new java.awt.Rectangle(20, 250, 211, 31));
            exitButton.setText(ValueMapper.getText(ClientGlobals.MENU_BUTTON_EXIT));
            exitButton.setBackground(buttonBackground);
            exitButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    System.exit(0);
                }
            });
        }
        return exitButton;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

    /**
     * This is the default constructor
     */
    public LoginPanel(InputHandler inputHandler) {
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
        this.setSize(250, 300);
        this.setLayout(null);
        this.setBackground(new Color(0, 0, 0, 0.2f));
        this.setBorder(new BevelBorder(BevelBorder.RAISED));
        passwordLabel = new JLabel();
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setBounds(new java.awt.Rectangle(10,50,91,21));
        passwordLabel.setText(ValueMapper.getText(ClientGlobals.MENU_LABEL_PASSWORD));
        usernameLabel = new JLabel();
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setBounds(new java.awt.Rectangle(10,20,91,21));
        usernameLabel.setText(ValueMapper.getText(ClientGlobals.MENU_LABEL_USERNAME));
        this.add(usernameLabel, null);
        this.add(getUsernameInputField(), null);
        this.add(passwordLabel, null);
        this.add(getPasswordField(), null);
        this.add(getLoginButton(), null);
        this.add(getCreateAccountButton(), null);
        this.add(getShowOptionsButton(), null);
        this.add(getExitButton(), null);
    }

} // @jve:decl-index=0:visual-constraint="10,10"
