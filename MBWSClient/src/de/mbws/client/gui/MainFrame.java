package de.mbws.client.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;

import de.mbws.client.Client;

public class MainFrame extends JFrame {

    private javax.swing.JPanel jContentPane = null;

    private JButton createAccountButton = null;

    private JButton loginButton = null;

    private JButton logoutButton = null;

    Client client = null;

    /**
     * This is the default constructor
     */
    public MainFrame(Client client) {
        super();
        this.client = client;
        initialize();
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(360, 200);
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        int x = (screenSize.width / 2) - (this.getWidth() / 2);
        int y = (screenSize.height / 2) - (this.getHeight() / 2);
        this.setLocation(x, y);
        this.setContentPane(getJContentPane());
        this.setTitle(Client.getResourceBundle().getString("de.mwbs.client.mainframe.title"));
    }

    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    private javax.swing.JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new javax.swing.JPanel();
            jContentPane.setLayout(null);
            jContentPane.setBackground(new java.awt.Color(102, 153, 255));
            jContentPane.add(getCreateAccountButton(), null);
            jContentPane.add(getLoginButton(), null);
            jContentPane.add(getLogoutButton(), null);
        }
        return jContentPane;
    }

    /**
     * This method initializes jButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getCreateAccountButton() {
        if (createAccountButton == null) {
            createAccountButton = new JButton();
            createAccountButton.setBounds(100, 20, 160, 25);
            createAccountButton.setText(Client.getResourceBundle().getString("de.mwbs.client.mainframe.button.createaccount.text"));
            createAccountButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    client.getAccountDialog().setVisible(true);
                }
            });
        }
        return createAccountButton;
    }

    /**
     * This method initializes jButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getLoginButton() {
        if (loginButton == null) {
            loginButton = new JButton();
            loginButton.setBounds(100, 60, 160, 25);
            loginButton.setText(Client.getResourceBundle().getString("de.mwbs.client.mainframe.button.login.text"));
            loginButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    client.getLoginDialog().setVisible(true);
                }
            });
        }
        return loginButton;
    }

    /**
     * This method initializes jButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getLogoutButton() {
        if (logoutButton == null) {
            logoutButton = new JButton();
            logoutButton.setBounds(100, 100, 160, 25);
            logoutButton.setText(Client.getResourceBundle().getString("de.mwbs.client.mainframe.button.logout.text"));
            logoutButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                   client.logout();
                }
            });
        }
        return logoutButton;
    }
} // @jve:decl-index=0:visual-constraint="10,10"
