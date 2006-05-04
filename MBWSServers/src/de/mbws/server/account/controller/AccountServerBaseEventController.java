package de.mbws.server.account.controller;

import de.mbws.server.AbstractTcpServer;
import de.mbws.server.account.AccountServer;
import de.mbws.server.controller.AbstractEventController;

/**
 * Description: 
 * @author Azarai
 *
 */
public abstract class AccountServerBaseEventController extends AbstractEventController {

    /**
     * @param server
     */
    public AccountServerBaseEventController(AbstractTcpServer server) {
        super(server);
    }
   
    protected AccountServer getAccountServer() {
        return (AccountServer) server;
    }

}
