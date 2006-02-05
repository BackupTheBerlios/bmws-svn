package de.mbws.client.gui.mainmenu;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import com.jme.input.InputHandler;

import se.datadosen.component.RiverLayout;
import de.mbws.client.ValueMapper;
import de.mbws.client.controller.AccountController;
import de.mbws.client.controller.ClientNetworkController;
import de.mbws.client.data.ClientGlobals;
import de.mbws.client.data.ClientPlayerData;
import de.mbws.client.state.handler.MainMenuHandler;
import de.mbws.common.eventdata.generated.AccountData;


/**
 * Description:
 * 
 * @author Azarai
 */
public class AccountPanel extends JPanel {

    private JLabel usernameLabel = null;

    private JTextField usernameTextField = null;

    private JLabel passwordLabel = null;

    private JPasswordField passwordTextField = null;

    private JLabel verifyPasswordLabel = null;

    private JPasswordField verifyPasswordTextField = null;

    private JLabel emailAddressLabel = null;

    private JTextField emailAddressTextField = null;

    private JButton createButton = null;

    private JButton cancelButton = null;

    private JLabel titleLabel = null;

    private InputHandler inputHandler;

    // private Image backgroundImage = null;

    /**
     * This is the default constructor
     */
    public AccountPanel(InputHandler inputHandler) {
        super();
        this.inputHandler = inputHandler;
        initialize();
    }

    private MainMenuHandler getInputHandler() {
        return (MainMenuHandler) inputHandler;
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        // backgroundImage = new
        // ImageIcon("data/images/menu/background.png").getImage();
        titleLabel = new JLabel();
        titleLabel.setText(ValueMapper.getText(ClientGlobals.MENU_BUTTON_CREATE_ACCOUNT));
        emailAddressLabel = new JLabel();
        emailAddressLabel.setText(ValueMapper.getText(ClientGlobals.ACCOUNT_CREATION_LABEL_EMAILADRESS));
        emailAddressLabel.setForeground(Color.WHITE);
        verifyPasswordLabel = new JLabel();
        verifyPasswordLabel.setText(ValueMapper.getText(ClientGlobals.ACCOUNT_CREATION_LABEL_PASSWORDVERIFICATION));
        verifyPasswordLabel.setForeground(Color.WHITE);
        passwordLabel = new JLabel();
        passwordLabel.setText(ValueMapper.getText(ClientGlobals.MENU_LABEL_PASSWORD));
        passwordLabel.setForeground(Color.WHITE);
        usernameLabel = new JLabel();
        usernameLabel.setText(ValueMapper.getText(ClientGlobals.MENU_LABEL_USERNAME));
        usernameLabel.setForeground(Color.WHITE);
        this.setLayout(new RiverLayout());
        // this.setSize(backgroundImage.getWidth(null),
        // backgroundImage.getHeight(null));
        this.setSize(300, 300);
        // this.setOpaque(false);
        this.setBackground(new Color(180, 180, 180));
        this.setBorder(new BevelBorder(BevelBorder.RAISED));
        this.add("center", titleLabel);
        this.add("p left", usernameLabel);
        this.add("tab hfill", getUsernameTextField());

        this.add("br", passwordLabel);
        this.add("tab hfill", getPasswordTextField());

        this.add("br", verifyPasswordLabel);
        this.add("tab hfill", getVerifyPasswordTextField());
        this.add("br", emailAddressLabel);
        this.add("tab hfill", getEmailAddressTextField());

        this.add("p center", getCreateButton());
        this.add("tab", getCancelButton());
        this.setVisible(true);

    }

    /**
     * This method initializes usernameTextField
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getUsernameTextField() {
        if (usernameTextField == null) {
            usernameTextField = new JTextField();
        }
        return usernameTextField;
    }

    /**
     * This method initializes passwordTextField
     * 
     * @return javax.swing.JTextField
     */
    private JPasswordField getPasswordTextField() {
        if (passwordTextField == null) {
            passwordTextField = new JPasswordField();
        }
        return passwordTextField;
    }

    /**
     * This method initializes verifyPasswordTextField
     * 
     * @return javax.swing.JTextField
     */
    private JPasswordField getVerifyPasswordTextField() {
        if (verifyPasswordTextField == null) {
            verifyPasswordTextField = new JPasswordField();
        }
        return verifyPasswordTextField;
    }

    /**
     * This method initializes emailAddressTextField
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getEmailAddressTextField() {
        if (emailAddressTextField == null) {
            emailAddressTextField = new JTextField();
        }
        return emailAddressTextField;
    }

    /**
     * This method initializes createButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getCreateButton() {
        if (createButton == null) {
            createButton = new JButton();
            createButton.setText(ValueMapper.getText(ClientGlobals.ACCOUNT_CREATION_BUTTON_CREATE));
            createButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    String password = String.valueOf(getPasswordTextField().getPassword());
                    String username = getUsernameTextField().getText();
                    String passwordVerification = String.valueOf(getVerifyPasswordTextField().getPassword());
                    String emailAddress = getEmailAddressTextField().getText();

                    
                    if (username != null && password != null && passwordVerification != null && emailAddress != null) {
                        if (password.equals(passwordVerification)) {
                        AccountData account = new AccountData();
                        account.setUserName(username);
                        account.setPassword(password);
                        account.setEmailAddress(emailAddress);
                        try {
                            ClientNetworkController.getInstance().connect("localhost", 5000);
                        } catch (Exception ex) {

                        }
                        ClientNetworkController.getInstance().handleOutgoingEvent(
                                AccountController.getInstance().createRegisterEvent(account, ClientPlayerData.getInstance()));
                        } else {
                            //error
                            getInputHandler().getState().displayError("Passwords are not equal");
                        }
                    }
                }
            });
        }
        return createButton;
    }

    /**
     * This method initializes cancelButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getCancelButton() {
        if (cancelButton == null) {
            cancelButton = new JButton();
            cancelButton.setText(ValueMapper.getText(ClientGlobals.GENERIC_BUTTON_BACK));
            cancelButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    getInputHandler().getState().removeMe((JPanel) cancelButton.getParent());
                }

            });
        }
        return cancelButton;
    }

    // @Override
    // protected void paintComponent(Graphics g) {
    // g.drawImage(backgroundImage, 0, 0, null);
    // super.paintComponent(g);
    // }

}
