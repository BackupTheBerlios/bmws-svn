package de.mbws.client.gui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import de.mbws.client.Client;
import de.mbws.common.data.AccountData;

public class LoginDialog extends AbstractDialog implements ActionListener {
    
    private Logger logger = Logger.getLogger(LoginDialog.class);
	private javax.swing.JPanel jContentPane = null;
	private JLabel accountNameLabel = null;
	private static JTextField accountNameTextField = null;
	private JLabel passwordLabel = null;
	private static JPasswordField passwordPasswordField = null;
	private static JCheckBox savePasswordCheckBox = null;
	private static JButton loginButton = null;
	private static JButton cancelButton = null;
    
    private Timer timer = null;
    /**
     * @param client
     * @throws HeadlessException
     */
    public LoginDialog(Client client) throws HeadlessException {
        super(client);
    }

	/**
	 * This method initializes this
	 * 
	 */
    protected void initialize() {
		this.setSize(380, 260);
		Toolkit tk = Toolkit.getDefaultToolkit();
	    Dimension screenSize = tk.getScreenSize();   
		int x = (screenSize.width /2) - (this.getWidth()/2); 
		int y = (screenSize.height /2) - (this.getHeight()/2);
	    this.setLocation(x,y);	
		this.setContentPane(getJContentPane());
		this.setTitle(Client.getResourceBundle().getString("de.mwbs.client.loginDialog.title"));
		this.setResizable(false);
		this.setName("accountDialog");
		this.setModal(false);
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			accountNameLabel = new JLabel();
			passwordLabel = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			accountNameLabel.setBounds(30, 30, 130, 20);
			accountNameLabel.setText(Client.getResourceBundle().getString("de.mwbs.client.loginDialog.label.accountname"));
			passwordLabel.setBounds(30, 60, 130, 20);
			passwordLabel.setText(Client.getResourceBundle().getString("de.mwbs.client.loginDialog.label.password"));
			jContentPane.add(accountNameLabel, null);
			jContentPane.add(getAccountNameTextField(), null);
			jContentPane.add(passwordLabel, null);
			jContentPane.add(getPasswordPasswordField(), null);
			jContentPane.add(getSavePasswordCheckBox(), null);
			jContentPane.add(getLoginButton(), null);
			jContentPane.add(getCancelButton(), null);
		}
		return jContentPane;
	}
	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getAccountNameTextField() {
		if (accountNameTextField == null) {
			accountNameTextField = new JTextField();
			accountNameTextField.setBounds(150, 30, 160, 20);
		}
		return accountNameTextField;
	}

    /**
	 * This method initializes jPasswordField	
	 * 	
	 * @return javax.swing.JPasswordField	
	 */    
	private JPasswordField getPasswordPasswordField() {
		if (passwordPasswordField == null) {
			passwordPasswordField = new JPasswordField();
			passwordPasswordField.setBounds(150, 60, 160, 20);
		}
		return passwordPasswordField;
	}

    /**
	 * This method initializes jCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */    
	private JCheckBox getSavePasswordCheckBox() {
		if (savePasswordCheckBox == null) {
			savePasswordCheckBox = new JCheckBox();
			savePasswordCheckBox.setBounds(30, 150, 150, 20);
			savePasswordCheckBox.setText(Client.getResourceBundle().getString("de.mwbs.client.loginDialog.checkbox.savepassword"));
		}
		return savePasswordCheckBox;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getLoginButton() {
		if (loginButton == null) {
            loginButton = new JButton();
            loginButton.setBounds(40, 200, 101, 20);
            loginButton.setText(Client.getResourceBundle().getString("de.mwbs.client.loginDialog.button.login"));
            loginButton.addActionListener(this);
		}
		return loginButton;
	}
    
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.setBounds(160, 200, 102, 20);
			cancelButton.setText(Client.getResourceBundle().getString("de.mwbs.client.loginDialog.button.cancel"));
			cancelButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					getParent().setVisible(false);
                }
			});
		}
		return cancelButton;
	}

    public void actionPerformed(ActionEvent e) {
        try {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            AccountData account = new AccountData();
            account.setPassword(getPasswordPasswordField().getText());
            account.setUsername(getAccountNameTextField().getText());
            Client.getInstance().login(account);
            disableDialog();
            timer = startTimeTask(50000);
        } catch (Exception ex) {
            ex.printStackTrace();
        } 
    }

    @Override
    protected void timerRun() {
        enableDialog();
        //error meldung
        JOptionPane.showMessageDialog(this,"TimeOut: No response from Server.");
    }
    
    public void enableDialog() {
        
        getAccountNameTextField().setEditable(true);
        getPasswordPasswordField().setEditable(true);
        getSavePasswordCheckBox().setEnabled(true);
        getCancelButton().setEnabled(true);
        getLoginButton().setEnabled(true);
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    public void disableDialog() {
        getAccountNameTextField().setEditable(false);
        getPasswordPasswordField().setEditable(false);
        getSavePasswordCheckBox().setEnabled(false);
        getCancelButton().setEnabled(false);
        getLoginButton().setEnabled(false);
        repaint();
    }
    
    public void cancelTimer() {
        if (timer != null) timer.cancel();
    }
}  //  @jve:decl-index=0:visual-constraint="10,10"