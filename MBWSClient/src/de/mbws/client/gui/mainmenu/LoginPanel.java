package de.mbws.client.gui.mainmenu;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.jme.input.InputHandler;

import de.mbws.client.MBWSClient;
import de.mbws.client.ValueMapper;
import de.mbws.client.data.ClientGlobals;
import de.mbws.client.gui.options.OptionsPanel;
import de.mbws.client.state.handler.MainMenuHandler;

/**
 * Description:
 * 
 * @author Azarai
 */
public class LoginPanel extends JPanel {
	private static Logger logger = Logger.getLogger(LoginPanel.class);

	private JLabel usernameLabel = null;

	private JTextField usernameInputField = null;

	private JLabel passwordLabel = null;

	private JPasswordField passwordField = null;

	private JButton loginButton = null;

	private JButton createAccountButton = null;

	private JButton showOptionsButton = null;

	private JButton exitButton = null;

	private InputHandler inputHandler;

	// private final static Color buttonBackground = new Color(155, 0, 7);
	private JLabel versionLabel;

	private JLabel imageLabel = null;

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
			usernameInputField.setText(MBWSClient.mbwsConfiguration.getString(
					"user", ""));
			usernameInputField.setBounds(new java.awt.Rectangle(320, 100, 120,
					20));
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
			passwordField.setText(MBWSClient.mbwsConfiguration.getString(
					"password", ""));
			passwordField.setBounds(new java.awt.Rectangle(320, 130, 120, 20));
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
			loginButton.setBounds(new java.awt.Rectangle(220, 180, 210, 30));
			loginButton.setText(ValueMapper
					.getText(ClientGlobals.MENU_BUTTON_LOGIN));
			loginButton.setContentAreaFilled(false);
			loginButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// getParent().repaint();
					getInputHandler().login(usernameInputField.getText(),
							String.valueOf(passwordField.getPassword()));
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
			createAccountButton.setBounds(new java.awt.Rectangle(220, 230, 210,
					30));
			createAccountButton.setText(ValueMapper
					.getText(ClientGlobals.MENU_BUTTON_CREATE_ACCOUNT));
			createAccountButton.setContentAreaFilled(false);
			createAccountButton
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {

							// setVisible(false);
							getInputHandler()
									.getState()
									.showComponentCenteredOnScreenOnTop(
											new AccountPanel(getInputHandler()));
							// String password = String.valueOf(passwordField
							// .getPassword());
							// String username = usernameInputField.getText();
							// if (username != null && password != null) {
							// AccountData account = new AccountData();
							// account.setUsername(username);
							// account.setPassword(password);
							// account.setPasswordConfirmation(password);
							// account.setEmailaddress("");
							// try {
							// ClientNetworkController.getInstance()
							// .connect("localhost", 5000);
							// } catch (Exception ex) {
							//
							// }
							// ClientNetworkController
							// .getInstance()
							// .handleOutgoingEvent(
							// AccountController
							// .getInstance()
							// .createRegisterEvent(
							// account,
							// ClientPlayerData
							// .getInstance()));
							// }
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
			showOptionsButton.setBounds(new java.awt.Rectangle(220, 280, 210,
					30));
			showOptionsButton.setText(ValueMapper
					.getText(ClientGlobals.MENU_BUTTON_OPTIONS));
			showOptionsButton.setContentAreaFilled(false);
			showOptionsButton
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							logger
									.info("actionPerformed(): showing option panel");
							getInputHandler()
									.getState()
									.showComponentCenteredOnScreenOnTop(
											new OptionsPanel(getInputHandler()));
							// TODO
							// Auto-generated
							// Event stub
							// actionPerformed()
							// getInputHandler().getMyState().displayInfo("blalbalb");
							// //displayInfo("blab");
						}
					});
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
			exitButton.setBounds(new java.awt.Rectangle(220, 330, 210, 30));
			exitButton.setText(ValueMapper
					.getText(ClientGlobals.MENU_BUTTON_EXIT));
			exitButton.setContentAreaFilled(false);
			exitButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					MBWSClient.exit();
				}
			});
		}
		return exitButton;
	}

	/**
	 * @param args
	 */
	// TODO: remove the main method here (Kerim Mansour)
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
		imageLabel = new JLabel(
				new ImageIcon("data/images/menu/background.png"));

		this.setSize(640, 480);
		this.setLayout(null);
		this.setBackground(null);
		this.setBorder(null);
		imageLabel.setBounds(this.getBounds());
		versionLabel = new JLabel();
		versionLabel.setBounds(new java.awt.Rectangle(100, 450, 240, 20));
		versionLabel.setForeground(Color.WHITE);
		versionLabel.setText("Version: 0.1");
		passwordLabel = new JLabel();
		passwordLabel.setForeground(Color.WHITE);
		passwordLabel.setBounds(new java.awt.Rectangle(210, 130, 100, 20));
		passwordLabel.setText(ValueMapper
				.getText(ClientGlobals.MENU_LABEL_PASSWORD));
		usernameLabel = new JLabel();
		usernameLabel.setForeground(Color.WHITE);
		usernameLabel.setBounds(new java.awt.Rectangle(210, 100, 100, 20));
		usernameLabel.setText(ValueMapper
				.getText(ClientGlobals.MENU_LABEL_USERNAME));
		this.add(versionLabel, null);
		this.add(usernameLabel, null);
		this.add(getUsernameInputField(), null);
		this.add(passwordLabel, null);
		this.add(getPasswordField(), null);
		this.add(getLoginButton(), null);
		this.add(getCreateAccountButton(), null);
		this.add(getShowOptionsButton(), null);
		this.add(getExitButton(), null);
		this.add(imageLabel, null);
	}

} // @jve:decl-index=0:visual-constraint="10,10"
