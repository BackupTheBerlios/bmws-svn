package de.mbws.server.world.controller;

import java.util.ArrayList;
import java.util.Map;

import de.mbws.common.events.AbstractGameEvent;
import de.mbws.common.events.MoveEvent;
import de.mbws.server.data.ServerPlayerData;
import de.mbws.server.world.WorldServer;

/**
 * Description:
 * 
 * @author Azarai
 * 
 */
public class MovementEventController extends WorldServerBaseEventController {

    /**
     * @param accountServer
     * @param eventType
     */
    public MovementEventController(WorldServer worldServer) {
        super(worldServer);
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
            ServerPlayerData spd = (ServerPlayerData) me.getPlayer();
            spd.getMovementInformation().setHeading(me.getMoveData().getHeading());
            spd.getMovementInformation().setLocation(me.getMoveData().getLocation());
            Map m = getWorldServer().getAllPlayers();
            ArrayList<Integer> receivers = (ArrayList<Integer>) getWorldServer().getSessionIDOfAllPlayers().clone();
            if (receivers.size() > 1) {
                receivers.remove(me.getPlayer().getSessionId());
                //TODO: TEST (below)
                me.getMoveData().setObjectID(spd.getActiveCharacterAsObjectID());
                //TODO: remove/change above
                me.setRecipients(receivers.toArray(new Integer[receivers.size()]));
                sendEvent(me);
            }
        }
    }
}