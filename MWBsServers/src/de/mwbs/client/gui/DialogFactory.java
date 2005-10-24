package de.mwbs.client.gui;

import de.mwbs.client.Client;

/**
 * Description: 
 * @author azarai
 *
 */
public class DialogFactory {

    public static AbstractDialog createAccountDialog (Client client){
        return new AccountDialog(client);
    }
    
    public static AbstractDialog createLoginDialog (Client client){
        return new LoginDialog(client);
    }    
}
