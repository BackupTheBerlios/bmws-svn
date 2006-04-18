package de.mbws.server.world.controller;

import java.util.ArrayList;

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
            spd.getActiveCharacter().setHeading(me.getMoveData().getHeading());
            spd.getActiveCharacter().setCoordinates(me.getMoveData().getLocation());
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