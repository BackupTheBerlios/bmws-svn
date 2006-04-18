package de.mbws.server.world.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import de.mbws.common.events.AbstractGameEvent;
import de.mbws.common.events.CharacterEvent;
import de.mbws.common.events.EventTypes;
import de.mbws.common.events.PCEvent;
import de.mbws.common.events.data.generated.CharacterSelection;
import de.mbws.common.events.data.generated.CharacterWorldServerInformation;
import de.mbws.common.events.data.generated.NetQuaternion;
import de.mbws.common.events.data.generated.PlayerCharacterDetails;
import de.mbws.server.account.persistence.CharacterPersistenceManager;
import de.mbws.server.data.Character;
import de.mbws.server.data.CharacterHelper;
import de.mbws.server.data.ServerPlayerData;
import de.mbws.server.data.db.generated.CharacterData;
import de.mbws.server.utils.IdHelper;
import de.mbws.server.world.WorldServer;

/**
 * Description:
 * 
 * @author Azarai
 */
public class CharacterEventController extends WorldServerBaseEventController {

    public CharacterEventController(WorldServer worldServer) {
        super(worldServer);
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.mbws.server.controller.EventController#handleEvent(de.mbws.common.events.AbstractGameEvent)
     */
    @Override
    public void handleEvent(AbstractGameEvent event) {
        CharacterEvent ce = (CharacterEvent) event;
        if (event.getEventType() == EventTypes.S2S_CHARACTER_NEW_CHARACTER_ENTERS_WORLD) {
            CharacterWorldServerInformation cwsi = (CharacterWorldServerInformation) event.getEventData();
            ServerPlayerData spd = new ServerPlayerData();
            CharacterData cdata = CharacterPersistenceManager.getInstance().getCharacterByID(
                    IdHelper.removePrefix(cwsi.getCharacter().getCharacterID()));
            Character newCharacter = new Character(cdata.getCharacterStatus(), cdata, cdata.getCharacterVisualappearance());
            spd.setActiveCharacter(newCharacter);
            getWorldServer().addPlayer(cwsi.getSessionId(), spd);

            CharacterEvent result = new CharacterEvent(cwsi);
            result.setEventType(EventTypes.S2S_CHARACTER_NEW_CHARACTER_ENTERS_WORLD_OK);
            result.setPlayer(getWorldServer().getServerBySessionId(event.getPlayer().getSessionId()));
            sendEvent(result);
        } else if (event.getEventType() == EventTypes.C2S_CHARACTER_ENTERS_WORLD_REQUEST) {
            CharacterSelection csel = (CharacterSelection) event.getEventData();
            Character newCharacter = ((ServerPlayerData) event.getPlayer()).getActiveCharacter();
            if (IdHelper.removePrefix(csel.getCharacterID()) != newCharacter.getId()) {
                return;
            }

            PlayerCharacterDetails charDetails = new PlayerCharacterDetails();

            NetQuaternion heading = new NetQuaternion();
            // FIXME define columns in table
            heading.setW(0);
            heading.setX(0);
            heading.setY(0);
            heading.setZ(0);
            newCharacter.setHeading(heading);
            
            de.mbws.common.events.data.generated.CharacterData ocd = CharacterHelper.getCharacterData(newCharacter);

            charDetails.setDescription(CharacterHelper.getPlayerCharacterShortDescription(newCharacter));
            charDetails.setHeading(heading);
            charDetails.setLocation(ocd.getLocation());
            CharacterEvent result = new CharacterEvent(charDetails);
            result.setEventType(EventTypes.S2C_CHARACTER_ENTERS_WORLD);
            result.setPlayer(ce.getPlayer());
            sendEvent(result);

            // inform other players about current one
            ArrayList<Integer> receivers = (ArrayList<Integer>) getWorldServer().getSessionIDOfAllPlayers().clone();
            if (receivers.size() > 1) {
                PCEvent oe = new PCEvent(ocd);
                oe.setEventType(EventTypes.S2C_MOVABLE_OBJECT_CREATE);
                receivers.remove(ce.getPlayer().getSessionId());
                oe.setPlayer(ce.getPlayer());
                oe.setRecipients(receivers.toArray(new Integer[receivers.size()]));
                sendEvent(oe);
            }
            // inform current player about the others
            Map players = getWorldServer().getAllPlayers();
            for (Iterator iter = receivers.iterator(); iter.hasNext();) {
                Integer element = (Integer) iter.next();
                if (!element.equals(ce.getPlayer().getSessionId())) {
                    ServerPlayerData spd = (ServerPlayerData) players.get(element);
                    PCEvent oe = new PCEvent(CharacterHelper.getCharacterData(spd.getActiveCharacter()));
                    oe.setEventType(EventTypes.S2C_MOVABLE_OBJECT_CREATE);
                    oe.setPlayer(ce.getPlayer());
                    sendEvent(oe);
                }
            }
        }
    }
}