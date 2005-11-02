package de.mbws.server.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import de.mbws.common.events.AbstractGameEvent;
import de.mbws.common.events.MoveEvent;
import de.mbws.server.account.AccountServer;
import de.mbws.server.data.ServerPlayerData;

/**
 * Description:
 * 
 * @author Azarai
 * 
 */
public class MovementEventController extends EventController {

    /**
     * @param accountServer
     * @param eventType
     */
    public MovementEventController(AccountServer accountServer, int eventType) {
        super(accountServer, eventType);
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
            Map m = accountServer.getAllPlayers();
            Set keys = m.keySet();
            ArrayList<Integer> receivers = new ArrayList<Integer>();
            for (Iterator iter = keys.iterator(); iter.hasNext();) {
                Integer key = (Integer) iter.next();
                ServerPlayerData element = (ServerPlayerData) m.get(key);
                if (element.getSessionId() != me.getPlayer().getSessionId()) {
                    receivers.add(element.getSessionId());
                }
            }
            me.setRecipients(receivers.toArray(new Integer[receivers.size()]));
            sendEvent(me);
        }
    }
}