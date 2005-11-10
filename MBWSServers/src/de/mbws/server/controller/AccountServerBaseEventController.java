package de.mbws.server.controller;

import de.mbws.common.events.AbstractGameEvent;
import de.mbws.server.AbstractTcpServer;
import de.mbws.server.account.AccountServer;

/**
 * Description: 
 * @author Azarai
 *
 */
public class AccountServerBaseEventController extends AbstractEventController {

    /**
     * @param server
     * @param eventType
     */
    public AccountServerBaseEventController(AbstractTcpServer server, int eventType) {
        super(server, eventType);
    }

    /* (non-Javadoc)
     * @see de.mbws.server.controller.AbstractEventController#handleEvent(de.mbws.common.events.AbstractGameEvent)
     */
    @Override
    public void handleEvent(AbstractGameEvent event) {
        // TODO Auto-generated method stub

    }
    
    protected AccountServer getAccountServer() {
        return (AccountServer) server;
    }
}
