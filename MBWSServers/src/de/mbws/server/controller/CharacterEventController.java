package de.mbws.server.controller;

import de.mbws.common.eventdata.generated.PlayerInfo;
import de.mbws.common.eventdata.generated.UpdateLocation;
import de.mbws.common.events.AbstractGameEvent;
import de.mbws.common.events.CharacterEvent;
import de.mbws.common.events.EventTypes;
import de.mbws.server.account.AccountServer;

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
            
            PlayerInfo pi = new PlayerInfo();
            pi.setVisualappearance(0);
            UpdateLocation ul = new UpdateLocation();
            ul.setLocationX(1);
            ul.setLocationY(1);
            ul.setLocationZ(1);
            ul.setPlayerID(ce.getPlayer().getSessionId());
            pi.setLocation(ul);
            CharacterEvent result = new CharacterEvent(pi);
            result.setEventType(EventTypes.CHARACTER_RECEIVE);
            result.setPlayer(ce.getPlayer());
            sendEvent(result);
        }
    }
}