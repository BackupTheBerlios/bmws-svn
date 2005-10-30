package de.mbws.server.controller;

import de.mbws.common.data.generated.CharacterStatus;
import de.mbws.common.data.generated.Characterdata;
import de.mbws.common.eventdata.generated.IntVector3D;
import de.mbws.common.eventdata.generated.PlayerInfo;
import de.mbws.common.eventdata.generated.WorldObject;
import de.mbws.common.events.AbstractGameEvent;
import de.mbws.common.events.CharacterEvent;
import de.mbws.common.events.EventTypes;
import de.mbws.server.account.AccountServer;
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
            
            PlayerInfo pi = new PlayerInfo();
            pi.setVisualappearance(cdata.getCharacterVisualappearance().getHeight());
            WorldObject wo = new WorldObject();
            IntVector3D location = new IntVector3D();
            location.setLocationX(cs.getCoordinateX());
            location.setLocationY(cs.getCoordinateY());
            location.setLocationZ(cs.getCoordinateZ());
            wo.setLocation(location);
            
            IntVector3D heading = new IntVector3D();
            heading.setLocationX(0);
            heading.setLocationY(0);
            heading.setLocationZ(0);
            wo.setHeading(heading);
            
            wo.setObjectID(ce.getPlayer().getSessionId());
            pi.setObject(wo);
            CharacterEvent result = new CharacterEvent(pi);
            result.setEventType(EventTypes.CHARACTER_RECEIVE);
            result.setPlayer(ce.getPlayer());
            sendEvent(result);
        }
    }
}