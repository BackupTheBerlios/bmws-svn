package de.mbws.client.gui.mainmenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jme.input.InputHandler;

import de.mbws.client.MBWSClient;
import de.mbws.client.ValueMapper;
import de.mbws.client.controller.AccountController;
import de.mbws.client.controller.ClientNetworkController;
import de.mbws.client.data.ClientGlobals;
import de.mbws.client.data.ClientPlayerData;
import de.mbws.client.state.handler.MainMenuHandler;
import de.mbws.common.events.data.generated.AccountData;
import de.mbws.common.utils.StringUtils;

public class AccountPanel extends JPanel implements ActionListener {

	private JLabel emailLb;
	private JLabel passwordLb;
	private JLabel passwordVerificationLb;
	private JLabel loginLb;
	private JTextField loginTf;
	private JTextField emailTf;
	private JPasswordField passwordTf;
	private JButton okBtn;
	private JButton cancelBtn;
	private InputHandler inputHandler;
	private JPasswordField passwordVerificationTf;

	/**
	 * test Constructor.
	 */
	public AccountPanel() {
		this.initialize();
	}

	public AccountPanel(InputHandler inputHandler) {
		super();
		this.inputHandler = inputHandler;
		initialize();
		setSize(640, 480);

	}

	/**
	 * Initialize method.
	 */
	private void initialize() {

		String rowDef = "100dlu,p,20dlu,p,20dlu,p,20dlu,p,20dlu,p,25dlu,p,20dlu,p";
		String colDef = "20dlu,left:40dlu:grow,2dlu,60dlu:grow,2dlu,left:40dlu,20dlu";

		FormLayout layout = new FormLayout(colDef, rowDef);
		this.setLayout(layout);
		// setBackground(Color.GRAY);
		createLabels();
		createTextFields();
		createButtons();

		CellConstraints cons = new CellConstraints();

		add(loginLb, cons.xywh(2, 2, 1, 1));
		add(loginTf, cons.xywh(4, 2, 1, 1));
		add(passwordLb, cons.xywh(2, 4, 1, 1));
		add(passwordTf, cons.xywh(4, 4, 1, 1));
		add(passwordVerificationLb, cons.xywh(2, 5, 1, 1));
		add(passwordVerificationTf, cons.xywh(4, 5, 1, 1));
		add(emailLb, cons.xywh(2, 6, 1, 1));
		add(emailTf, cons.xywh(4, 6, 3, 1));

		add(okBtn, cons.xywh(5, 14, 2, 1));
		add(cancelBtn, cons.xywh(2, 14, 1, 1));
		okBtn.setEnabled(false);
	}

	private void createLabels() {
		loginLb = new JLabel();
		loginLb.setText(ValueMapper.getText(ClientGlobals.MENU_LABEL_USERNAME));

		passwordLb = new JLabel();
		passwordLb.setText(ValueMapper.getText(ClientGlobals.MENU_LABEL_PASSWORD));

		passwordVerificationLb = new JLabel();
		passwordVerificationLb.setText(ValueMapper
				.getText(ClientGlobals.ACCOUNT_CREATION_LABEL_PASSWORDVERIFICATION));

		emailLb = new JLabel();
		emailLb.setText(ValueMapper
				.getText(ClientGlobals.ACCOUNT_CREATION_LABEL_EMAILADRESS));
	}

	private void createTextFields() {
		loginTf = new JTextField();
		loginTf.addActionListener(this);

		passwordTf = new JPasswordField();
		passwordTf.addActionListener(this);

		passwordVerificationTf = new JPasswordField();
		passwordVerificationTf.addActionListener(this);

		emailTf = new JTextField();
		emailTf.addActionListener(this);
	}

	private void createButtons() {
		okBtn = new JButton();
		okBtn.setText(ValueMapper
				.getText(ClientGlobals.CHARACTER_CREATION_BUTTON_CREATE_CHARACTER));
		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AccountData account = new AccountData();
				account.setUserName(loginTf.getText().trim());
				try {
					String pass = new String(passwordTf.getPassword());
					account.setPassword(StringUtils.hashAndHex(pass.trim()));
					account.setEmailAddress(emailTf.getText().trim());

					ClientNetworkController.getInstance().connect(
							MBWSClient.mbwsConfiguration
									.getString(ClientGlobals.ACCOUNT_SERVER_IP),
							MBWSClient.mbwsConfiguration
									.getInt(ClientGlobals.ACCOUNT_SERVER_PORT));
				} catch (Exception ex) {
					// FIXME should handle this
				}
				ClientNetworkController.getInstance().handleOutgoingEvent(
						AccountController.getInstance().createRegisterEvent(account,
								ClientPlayerData.getInstance()));
			}
		});

		cancelBtn = new JButton();
		cancelBtn.setText(ValueMapper.getText(ClientGlobals.GENERIC_BUTTON_BACK));
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getInputHandler().getState().removeMe((JPanel) cancelBtn.getParent());
			}
		});

	}

	private MainMenuHandler getInputHandler() {
		return (MainMenuHandler) inputHandler;
	}

	public void actionPerformed(ActionEvent e) {
		if ((!loginTf.getText().trim().equals(""))
				&& (!emailTf.getText().trim().equals(""))
				&& (passwordTf.getPassword() != null)
				&& (passwordVerificationTf.getPassword() != null)) {
			String p = new String(passwordTf.getPassword());
			String p2 = new String(passwordVerificationTf.getPassword());
			if (p.trim().equals(p2.trim())) {
				okBtn.setEnabled(true);
			} else {
				// TODO: replace String here !
				getInputHandler().getState().displayError("Passwords are not equal");
			}
		} else {
			okBtn.setEnabled(false);
		}
	}

	/**
	 * Test main method.
	 */
	public static void main(String args[]) {

		JFrame test = new JFrame("Test for CreateAccountDialog");
		test.setContentPane(new AccountPanel());
		test.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		test.pack();
		test.setVisible(true);
	}
}
