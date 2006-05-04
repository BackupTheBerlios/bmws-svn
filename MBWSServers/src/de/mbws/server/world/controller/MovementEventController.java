package de.mbws.server.world.controller;

import java.util.ArrayList;

import de.mbws.common.events.AbstractGameEvent;
import de.mbws.common.events.EventTypes;
import de.mbws.common.events.MoveEvent;
import de.mbws.server.AbstractTcpServer;
import de.mbws.server.data.ServerPlayerData;

/**
 * Description:
 * 
 * @author Azarai
 */
public class MovementEventController extends WorldServerBaseEventController {
    private static final Integer[] supportedEventTypes = { EventTypes.MOVEMENT_START_WALK, EventTypes.MOVEMENT_STOP_WALK,
            EventTypes.MOVEMENT_START_RUN, EventTypes.MOVEMENT_STOP_RUN, EventTypes.MOVEMENT_START_TURN_LEFT, EventTypes.MOVEMENT_START_TURN_RIGHT,
            EventTypes.MOVEMENT_STOP_TURN, EventTypes.MOVEMENT_START_WALK_BACKWARDS };

    /**
     * @param server
     */
    public MovementEventController(AbstractTcpServer server) {
        super(server);
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
                // TODO: TEST (below)
                me.getMoveData().setObjectID(spd.getActiveCharacterAsObjectID());
                // TODO: remove/change above
                me.setRecipients(receivers.toArray(new Integer[receivers.size()]));
                sendEvent(me);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.mbws.server.controller.AbstractEventController#getSupportedEventTypes()
     */
    @Override
    public Integer[] getSupportedEventTypes() {
        return supportedEventTypes;
    }
}