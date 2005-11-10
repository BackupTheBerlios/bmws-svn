package de.mbws.server.account.controller;

import java.util.ArrayList;

import de.mbws.common.data.generated.CharacterStatus;
import de.mbws.common.data.generated.Characterdata;
import de.mbws.common.eventdata.generated.IntVector3D;
import de.mbws.common.eventdata.generated.PlayerInfo;
import de.mbws.common.eventdata.generated.WorldObject;
import de.mbws.common.events.AbstractGameEvent;
import de.mbws.common.events.CharacterEvent;
import de.mbws.common.events.EventTypes;
import de.mbws.common.events.ObjectEvent;
import de.mbws.server.account.AccountServer;
import de.mbws.server.account.persistence.CharacterPersistenceManager;
import de.mbws.server.data.ServerPlayerData;

/**
 * Description: 
 * @author Azarai
 *
 */
public class CharacterEventController extends AccountServerBaseEventController {

    /**
     * @param accountServer
     * @param eventType
     */
    public CharacterEventController(AccountServer accountServer) {
        super(accountServer);
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see de.mbws.server.controller.EventController#handleEvent(de.mbws.common.events.AbstractGameEvent)
     */
    @Override
    public void handleEvent(AbstractGameEvent event) {
        if (event.getEventType() == EventTypes.CHARACTER_RECEIVE_REQUEST) {
            CharacterEvent ce = (CharacterEvent) event;
            
            Characterdata cdata = CharacterPersistenceManager.getInstance().getCharacter(ce.getPlayer().getAccount().getUsername());

            CharacterStatus cs = cdata.getCharacterStatus();
            ((ServerPlayerData) ce.getPlayer()).setActiveCharacter(cdata.getId());
            PlayerInfo pi = new PlayerInfo();
            pi.setVisualappearance(cdata.getCharacterVisualappearance().getHeight());
            WorldObject wo = new WorldObject();
            IntVector3D location = new IntVector3D();
            location.setX(cs.getCoordinateX());
            location.setY(cs.getCoordinateY());
            location.setZ(cs.getCoordinateZ());
            wo.setLocation(location);
            
            IntVector3D heading = new IntVector3D();
            heading.setX(0);
            heading.setY(0);
            heading.setZ(0);
            wo.setHeading(heading);
            
            wo.setObjectID(ce.getPlayer().getSessionId());
            pi.setObject(wo);
            CharacterEvent result = new CharacterEvent(pi);
            result.setEventType(EventTypes.CHARACTER_RECEIVE);
            result.setPlayer(ce.getPlayer());
            sendEvent(result);

            ArrayList<Integer> receivers = (ArrayList<Integer>) getAccountServer().getSessionIDOfAllPlayers().clone();
            if (receivers.size() > 1) {
                ObjectEvent oe = new ObjectEvent(wo);
                oe.setEventType(EventTypes.MOVABLE_OBJECT_CREATE);
                
                receivers.remove(ce.getPlayer().getSessionId());
                oe.setPlayer(ce.getPlayer());
                oe.setRecipients(receivers.toArray(new Integer[receivers.size()]));
                sendEvent(oe);
            }
        }
    }
}