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

public class AccountDialog extends AbstractDialog implements ActionListener {
    
    private Logger logger = Logger.getLogger(AccountDialog.class);
	private javax.swing.JPanel jContentPane = null;
	private JLabel accountNameLabel = null;
	private static JTextField accountNameTextField = null;
	private JLabel emailAddressLabel = null;
	private static JTextField emailAddressTextField = null;
	private JLabel passwordLabel = null;
	private JLabel passwordCheckLabel = null;
	private static JPasswordField passwordPasswordField = null;
	private static JPasswordField passwordCheckPasswordField = null;
	private static JCheckBox savePasswordCheckBox = null;
	private static JButton registerButton = null;
	private static JButton cancelButton = null;
    
    private Timer timer = null;
    /**
     * @param client
     * @throws HeadlessException
     */
    public AccountDialog(Client client) throws HeadlessException {
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
		this.setTitle(Client.getResourceBundle().getString("de.mwbs.client.accountDialog.title"));
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
			emailAddressLabel = new JLabel();
			passwordLabel = new JLabel();
			passwordCheckLabel = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			accountNameLabel.setBounds(30, 30, 130, 20);
			accountNameLabel.setText(Client.getResourceBundle().getString("de.mwbs.client.accountDialog.label.accountname"));
			emailAddressLabel.setBounds(30, 60, 130, 20);
			emailAddressLabel.setText(Client.getResourceBundle().getString("de.mwbs.client.accountDialog.label.emailaddress"));
			passwordLabel.setBounds(30, 90, 130, 20);
			passwordLabel.setText(Client.getResourceBundle().getString("de.mwbs.client.accountDialog.label.password"));
			passwordCheckLabel.setBounds(30, 120, 130, 20);
			passwordCheckLabel.setText(Client.getResourceBundle().getString("de.mwbs.client.accountDialog.label.passwordcheck"));
			jContentPane.add(accountNameLabel, null);
			jContentPane.add(getAccountNameTextField(), null);
			jContentPane.add(emailAddressLabel, null);
			jContentPane.add(getEmailAddressTextField(), null);
			jContentPane.add(passwordLabel, null);
			jContentPane.add(passwordCheckLabel, null);
			jContentPane.add(getPasswordPasswordField(), null);
			jContentPane.add(getPasswordCheckPasswordField(), null);
			jContentPane.add(getSavePasswordCheckBox(), null);
			jContentPane.add(getRegisterButton(), null);
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
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getEmailAddressTextField() {
		if (emailAddressTextField == null) {
			emailAddressTextField = new JTextField();
			emailAddressTextField.setBounds(150, 60, 160, 20);
		}
		return emailAddressTextField;
	}
	/**
	 * This method initializes jPasswordField	
	 * 	
	 * @return javax.swing.JPasswordField	
	 */    
	private JPasswordField getPasswordPasswordField() {
		if (passwordPasswordField == null) {
			passwordPasswordField = new JPasswordField();
			passwordPasswordField.setBounds(150, 90, 160, 20);
		}
		return passwordPasswordField;
	}
	/**
	 * This method initializes jPasswordField	
	 * 	
	 * @return javax.swing.JPasswordField	
	 */    
	private JPasswordField getPasswordCheckPasswordField() {
		if (passwordCheckPasswordField == null) {
			passwordCheckPasswordField = new JPasswordField();
			passwordCheckPasswordField.setBounds(150, 120, 160, 20);
		}
		return passwordCheckPasswordField;
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
			savePasswordCheckBox.setText(Client.getResourceBundle().getString("de.mwbs.client.accountDialog.checkbox.savepassword"));
		}
		return savePasswordCheckBox;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getRegisterButton() {
		if (registerButton == null) {
			registerButton = new JButton();
			registerButton.setBounds(40, 200, 101, 20);
			registerButton.setText(Client.getResourceBundle().getString("de.mwbs.client.accountDialog.button.register"));
			registerButton.addActionListener(this);
		}
		return registerButton;
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
			cancelButton.setText(Client.getResourceBundle().getString("de.mwbs.client.accountDialog.button.cancel"));
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
            account.setEmailaddress(getEmailAddressTextField().getText());
            account.setPasswordConfirmation(getPasswordCheckPasswordField().getText());
            Client.getInstance().register(account);
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
        getPasswordCheckPasswordField().setEditable(true);
        getEmailAddressTextField().setEditable(true);
        getSavePasswordCheckBox().setEnabled(true);
        getCancelButton().setEnabled(true);
        getRegisterButton().setEnabled(true);
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    public void disableDialog() {
        getAccountNameTextField().setEditable(false);
        getPasswordPasswordField().setEditable(false);
        getPasswordCheckPasswordField().setEditable(false);
        getEmailAddressTextField().setEditable(false);
        getSavePasswordCheckBox().setEnabled(false);
        getCancelButton().setEnabled(false);
        getRegisterButton().setEnabled(false);
        repaint();
    }
    
    public void cancelTimer() {
        if (timer != null) timer.cancel();
    }
}  //  @jve:decl-index=0:visual-constraint="10,10"