package de.mbws.client.gui.mainmenu;

import java.awt.event.*;

import javax.swing.*;

import org.apache.log4j.Logger;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jme.input.InputHandler;

import de.mbws.client.MBWSClient;
import de.mbws.client.ValueMapper;
import de.mbws.client.data.ClientGlobals;
import de.mbws.client.gui.options.OptionsPanel;
import de.mbws.client.state.handler.MainMenuHandler;

public class LoginPanel extends JPanel {
	private static Logger logger = Logger.getLogger(LoginPanel.class);
	private javax.swing.JLabel loginLb;
	private javax.swing.JTextField loginTf;
	private javax.swing.JLabel passwordLb;
	private javax.swing.JPasswordField passwordTf;
	private javax.swing.JButton loginBtn;
	private javax.swing.JButton exitBtn;
	private javax.swing.JButton createAccountBtn;
	private javax.swing.JButton optionBtn;
	private InputHandler inputHandler;

	/**
	 * Constructor.
	 */
	public LoginPanel() {
		initialize();
	}

	public LoginPanel(MainMenuHandler inputHandler2) {
		super();
		inputHandler = inputHandler2;
		initialize();

		setSize(640, 480);
		// setVisible(true);
	}

	/**
	 * Initialize method.
	 */
	private void initialize() {
		// setBackground(SubstanceEbonyTheme.getGray(ThemeKind.DARK)
		// .getForegroundColor());
		// setBackground(Color.GRAY);
		createLabels();
		createTextFields();
		createButtons();
		String rowDef = "60dlu,p,20dlu,p,20dlu,p,20dlu,p,140dlu,p,20dlu";
		String colDef = "2dlu,40dlu:grow,2dlu,40dlu:grow,40dlu:grow,2dlu,left:40dlu,2dlu,40dlu:grow,2dlu";

		FormLayout layout = new FormLayout(colDef, rowDef);
		this.setLayout(layout);

		CellConstraints cons = new CellConstraints();

		this.add(loginLb, cons.xywh(4, 3, 1, 1));
		this.add(loginTf, cons.xywh(5, 3, 1, 1));
		this.add(passwordLb, cons.xywh(4, 5, 1, 1));
		this.add(passwordTf, cons.xywh(5, 5, 1, 1));
		this.add(loginBtn, cons.xywh(5, 7, 1, 1));
		this.add(optionBtn, cons.xywh(5, 11, 1, 1));
		this.add(createAccountBtn, cons.xywh(7, 11, 3, 1));
		this.add(exitBtn, cons.xywh(2, 11, 1, 1));

	}

	private void createLabels() {
		loginLb = new JLabel();
		loginLb.setText(ValueMapper.getText(ClientGlobals.MENU_LABEL_USERNAME));

		passwordLb = new JLabel();
		passwordLb.setText(ValueMapper.getText(ClientGlobals.MENU_LABEL_PASSWORD));
	}

	// TODO: Button behaviour handling in an isValid Method !
	private void createTextFields() {
		loginTf = new JTextField();
		loginTf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (loginTf.getText().trim().equals("")) {
					loginBtn.setEnabled(false);
				} else {
					loginBtn.setEnabled(true);
				}
			}
		});

		passwordTf = new JPasswordField();
		passwordTf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pass = new String(passwordTf.getPassword());
				if (pass.trim().equals(""))
					loginBtn.setEnabled(false);
			}
		});
		passwordTf.addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent e) {
				String pass = new String(passwordTf.getPassword());
				if (pass.trim().equals("")) {
					loginBtn.setEnabled(false);
				} else {
					loginBtn.setEnabled(true);
					if (e.getKeyChar() == KeyEvent.VK_ENTER ||e.getKeyChar() == '\r') {
						login();
					}
				}

			}

			public void keyPressed(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
			}

		});

		loginTf.setText(MBWSClient.mbwsConfiguration.getString(ClientGlobals.LOGIN,
				""));
		passwordTf.setText(MBWSClient.mbwsConfiguration.getString(
				ClientGlobals.PASSWORD, ""));

	}

	private void login() {
		getInputHandler().login(loginTf.getText(),
				String.valueOf(passwordTf.getPassword()));
	}

	private void createButtons() {
		loginBtn = new JButton();
		loginBtn.setText(ValueMapper.getText(ClientGlobals.MENU_BUTTON_LOGIN));
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});

		exitBtn = new JButton();
		exitBtn.setText(ValueMapper.getText(ClientGlobals.MENU_BUTTON_EXIT));
		exitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MBWSClient.exit(ClientGlobals.EXIT_NORMAL);
			}
		});

		createAccountBtn = new JButton();
		createAccountBtn.setText(ValueMapper
				.getText(ClientGlobals.MENU_BUTTON_CREATE_ACCOUNT));
		createAccountBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getInputHandler().getState().showComponentCenteredOnScreenOnTop(
						new AccountPanel(getInputHandler()));
			}
		});

		optionBtn = new JButton();
		optionBtn.setText(ValueMapper.getText(ClientGlobals.MENU_BUTTON_OPTIONS));
		optionBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getInputHandler().getState().showComponentCenteredOnScreenOnLayer(
						new OptionsPanel(getInputHandler()), 10);
			}
		});

	}

	private MainMenuHandler getInputHandler() {
		return (MainMenuHandler) inputHandler;
	}

	/**
	 * Test main method.
	 */
	public static void main(String args[]) {

		JFrame test = new JFrame("Test for LoginDialog");
		test.setContentPane(new LoginPanel());
		test.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		test.pack();
		test.setVisible(true);
	}
}
