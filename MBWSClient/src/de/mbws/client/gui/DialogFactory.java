package de.mbws.client.gui;

import de.mbws.client.Client;

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
