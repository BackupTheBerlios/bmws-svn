package de.mbws.server.account.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.mbws.common.Globals;
import de.mbws.common.data.generated.CharacterStatus;
import de.mbws.common.data.generated.Characterdata;
import de.mbws.common.eventdata.generated.CharacterDetails;
import de.mbws.common.eventdata.generated.CharacterSelection;
import de.mbws.common.eventdata.generated.CharacterShortDescription;
import de.mbws.common.eventdata.generated.CharactersOfPlayer;
import de.mbws.common.eventdata.generated.IntVector3D;
import de.mbws.common.eventdata.generated.NetQuaternion;
import de.mbws.common.eventdata.generated.WorldObject;
import de.mbws.common.events.AbstractGameEvent;
import de.mbws.common.events.CharacterEvent;
import de.mbws.common.events.EventTypes;
import de.mbws.common.events.ObjectEvent;
import de.mbws.server.account.AccountServer;
import de.mbws.server.account.persistence.CharacterPersistenceManager;
import de.mbws.server.data.ServerPlayerData;
import de.mbws.server.utils.IdHelper;

/**
 * Description:
 * 
 * @author Azarai
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

    /*
     * (non-Javadoc)
     * 
     * @see de.mbws.server.controller.EventController#handleEvent(de.mbws.common.events.AbstractGameEvent)
     */
    @Override
    public void handleEvent(AbstractGameEvent event) {
        CharacterEvent ce = (CharacterEvent) event;
        if (event.getEventType() == EventTypes.CHARACTER_RECEIVE_REQUEST) {
            CharacterSelection cs = (CharacterSelection) event.getEventData();
            Characterdata cdata = CharacterPersistenceManager.getInstance().getCharacter(ce.getPlayer().getAccount().getUsername(),
                    IdHelper.removePrefix(cs.getCharacterID()));

            CharacterDetails cd = new CharacterDetails();
            cd.setDescription(getCharacterShortDescription(cdata));
            
            CharacterEvent result = new CharacterEvent(cd);
            result.setEventType(EventTypes.CHARACTER_RECEIVE);
            result.setPlayer(ce.getPlayer());
            sendEvent(result);
        } else if (event.getEventType() == EventTypes.CHARACTER_START_PLAYING_REQUEST) {
            CharacterSelection csel = (CharacterSelection) event.getEventData();
            Characterdata cdata = CharacterPersistenceManager.getInstance().getCharacter(ce.getPlayer().getAccount().getUsername(),
                    IdHelper.removePrefix(csel.getCharacterID()));            
            CharacterStatus cs = cdata.getCharacterStatus();
            ((ServerPlayerData) ce.getPlayer()).setActiveCharacter(cdata.getId());
            CharacterDetails charDetails = new CharacterDetails();
            charDetails.setVisualappearance(cdata.getCharacterVisualappearance().getHeight());
            WorldObject wo = new WorldObject();
            IntVector3D location = new IntVector3D();
            location.setX(cs.getCoordinateX());
            location.setY(cs.getCoordinateY());
            location.setZ(cs.getCoordinateZ());
            wo.setLocation(location);
            // TODO @Jens guck mal hier nach Parameter "W"

            NetQuaternion heading = new NetQuaternion();
            heading.setW(0);
            heading.setX(0);
            heading.setY(0);
            heading.setZ(0);
            wo.setHeading(heading);

            wo.setObjectID(((ServerPlayerData) ce.getPlayer()).getActiveCharacterAsObjectID());
            ((ServerPlayerData) ce.getPlayer()).setMovementInformation(wo);
            
            charDetails.setDescription(getCharacterShortDescription(cdata));
            charDetails.setHeading(heading);
            charDetails.setLocation(location);
            CharacterEvent result = new CharacterEvent(charDetails);
            result.setEventType(EventTypes.CHARACTER_START_PLAYING);
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

            Map players = getAccountServer().getAllPlayers();
            Set keys = players.keySet();
            for (Iterator iter = receivers.iterator(); iter.hasNext();) {
                Integer element = (Integer) iter.next();
                if (!element.equals(ce.getPlayer().getSessionId())) {
                    ServerPlayerData spd = (ServerPlayerData) players.get(element);
                    ObjectEvent oe = new ObjectEvent(spd.getMovementInformation());
                    oe.setEventType(EventTypes.MOVABLE_OBJECT_CREATE);
                    oe.setPlayer(ce.getPlayer());
                    sendEvent(oe);
                }
            }            
        } else if (event.getEventType() == EventTypes.CHARACTER_LIST_RECEIVE_REQUEST) {
            List chars = CharacterPersistenceManager.getInstance().getAllCharacters(ce.getPlayer().getAccount().getUsername());
            LinkedList<CharacterShortDescription> charsToSend = new LinkedList<CharacterShortDescription>();
            for (Iterator iter = chars.iterator(); iter.hasNext();) {
                Characterdata element = (Characterdata) iter.next();
                charsToSend.add(getCharacterShortDescription(element));
            }
            CharactersOfPlayer cop = new CharactersOfPlayer();
            cop.setDescriptions(charsToSend);
            CharacterEvent result = new CharacterEvent(cop);
            result.setEventType(EventTypes.CHARACTER_LIST_RECEIVE);
            result.setPlayer(ce.getPlayer());
            sendEvent(result);
        }
    }
    
    
    private CharacterShortDescription getCharacterShortDescription(Characterdata cdata) {
        CharacterShortDescription csd = new CharacterShortDescription();
        csd.setGender(cdata.getGender());
        csd.setLocation(cdata.getCharacterStatus().getMap().getName());
        csd.setName(cdata.getCharactername());
        csd.setRace(cdata.getRace().getId());
        csd.setCharacterID(Globals.OBJECT_ID_PREFIX_CHARACTER + cdata.getId());
        return csd;
    }
}