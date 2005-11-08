package de.mbws.server.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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
import de.mbws.server.data.ServerPlayerData;
import de.mbws.server.persistence.CharacterPersistenceManager;

/**
 * Description: 
 * @author Azarai
 *
 */
public class CharacterEventController extends EventController {

    /**
     * @param accountServer
     * @param eventType
     */
    public CharacterEventController(AccountServer accountServer, int eventType) {
        super(accountServer, eventType);
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
            
            //TODO: Kerim, remove this test code later
            
            WorldObject wo2 = new WorldObject();
            IntVector3D location2 = new IntVector3D();
            location2.setX(cs.getCoordinateX());
            location2.setY(cs.getCoordinateY());
            location2.setZ(cs.getCoordinateZ());
            wo2.setLocation(location2);
            
            IntVector3D heading2 = new IntVector3D();
            heading2.setX(0);
            heading2.setY(0);
            heading2.setZ(0);
            wo2.setHeading(heading2);
            
            wo2.setObjectID(ce.getPlayer().getSessionId());
            ObjectEvent oe = new ObjectEvent(wo2);
            oe.setEventType(EventTypes.MOVABLE_OBJECT_CREATE);
            Map m = accountServer.getAllPlayers();
            Set keys = m.keySet();
            ArrayList<Integer> receivers = new ArrayList<Integer>();
            for (Iterator iter = keys.iterator(); iter.hasNext();) {
                Integer key = (Integer) iter.next();
                ServerPlayerData element = (ServerPlayerData) m.get(key);
                if (element.getSessionId() != ce.getPlayer().getSessionId()) {
                    receivers.add(element.getSessionId());
                }
            }
            oe.setPlayer(ce.getPlayer());
            oe.setRecipients(receivers.toArray(new Integer[receivers.size()]));
            sendEvent(oe);
            //TODO: Kerim remove above testcode
        }
    }
}