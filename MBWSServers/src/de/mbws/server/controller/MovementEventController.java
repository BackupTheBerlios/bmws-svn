package de.mbws.server.controller;

import java.util.ArrayList;
import java.util.Map;

import de.mbws.common.events.AbstractGameEvent;
import de.mbws.common.events.MoveEvent;
import de.mbws.server.account.AccountServer;
import de.mbws.server.account.controller.AccountServerBaseEventController;

/**
 * Description:
 * 
 * @author Azarai
 * 
 */
public class MovementEventController extends AccountServerBaseEventController {

    /**
     * @param accountServer
     * @param eventType
     */
    public MovementEventController(AccountServer accountServer) {
        super(accountServer);
        // TODO Auto-generated constructor stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.mbws.server.controller.EventController#handleEvent(de.mbws.common.events.AbstractGameEvent)
     */
    @Override
    public void handleEvent(AbstractGameEvent event) {
        if (event instanceof MoveEvent) {
            MoveEvent me = (MoveEvent) event;
            Map m = getAccountServer().getAllPlayers();
            ArrayList<Integer> receivers = (ArrayList<Integer>) getAccountServer().getSessionIDOfAllPlayers().clone();
            if (receivers.size() > 1) {
                receivers.remove(me.getPlayer().getSessionId());
                //TODO: TEST (below)
                me.getMoveData().setObjectID(me.getPlayer().getSessionId());
                //TODO: remove/change above
                me.setRecipients(receivers.toArray(new Integer[receivers.size()]));
                sendEvent(me);
            }
        }
    }
}