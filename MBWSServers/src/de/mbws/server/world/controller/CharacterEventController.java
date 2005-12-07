package de.mbws.server.world.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import de.mbws.common.Globals;
import de.mbws.common.data.generated.CharacterStatus;
import de.mbws.common.data.generated.CharacterVisualappearance;
import de.mbws.common.data.generated.Characterdata;
import de.mbws.common.eventdata.generated.CharacterDetails;
import de.mbws.common.eventdata.generated.CharacterSelection;
import de.mbws.common.eventdata.generated.CharacterShortDescription;
import de.mbws.common.eventdata.generated.CharacterVisualAppearance;
import de.mbws.common.eventdata.generated.CharacterWorldServerInformation;
import de.mbws.common.eventdata.generated.IntVector3D;
import de.mbws.common.eventdata.generated.NetQuaternion;
import de.mbws.common.eventdata.generated.WorldObject;
import de.mbws.common.events.AbstractGameEvent;
import de.mbws.common.events.CharacterEvent;
import de.mbws.common.events.EventTypes;
import de.mbws.common.events.ObjectEvent;
import de.mbws.server.account.persistence.CharacterPersistenceManager;
import de.mbws.server.data.ServerPlayerData;
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
        
        if (event.getEventType() == EventTypes.S2S_CHARACTER_NEW_CHARACTER_ENTERS_WORLD) {
            CharacterWorldServerInformation cwsi = (CharacterWorldServerInformation) event.getEventData();
            ServerPlayerData spd = new ServerPlayerData();
            spd.setActiveCharacter(IdHelper.removePrefix(cwsi.getCharacter().getCharacterID()));
            getWorldServer().addPlayer(cwsi.getSessionId(),spd);
        } else if (event.getEventType() == EventTypes.C2S_CHARACTER_ENTERS_WORLD_REQUEST) {
            CharacterSelection csel = (CharacterSelection) event.getEventData();
            Characterdata cdata = CharacterPersistenceManager.getInstance().getCharacterByID(
                    IdHelper.removePrefix(csel.getCharacterID()));            
            CharacterStatus cs = cdata.getCharacterStatus();
            CharacterDetails charDetails = new CharacterDetails();
            WorldObject wo = new WorldObject();
            IntVector3D location = new IntVector3D();
            location.setX(cs.getCoordinateX());
            location.setY(cs.getCoordinateY());
            location.setZ(cs.getCoordinateZ());
            wo.setLocation(location);
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
            result.setEventType(EventTypes.S2C_CHARACTER_ENTERS_WORLD);
            result.setPlayer(ce.getPlayer());
            sendEvent(result);

            ArrayList<Integer> receivers = (ArrayList<Integer>) getWorldServer().getSessionIDOfAllPlayers().clone();
            if (receivers.size() > 1) {
                ObjectEvent oe = new ObjectEvent(wo);
                oe.setEventType(EventTypes.S2C_MOVABLE_OBJECT_CREATE);
                receivers.remove(ce.getPlayer().getSessionId());
                oe.setPlayer(ce.getPlayer());
                oe.setRecipients(receivers.toArray(new Integer[receivers.size()]));
                sendEvent(oe);
            }

            Map players = getWorldServer().getAllPlayers();
            for (Iterator iter = receivers.iterator(); iter.hasNext();) {
                Integer element = (Integer) iter.next();
                if (!element.equals(ce.getPlayer().getSessionId())) {
                    ServerPlayerData spd = (ServerPlayerData) players.get(element);
                    ObjectEvent oe = new ObjectEvent(spd.getMovementInformation());
                    oe.setEventType(EventTypes.S2C_MOVABLE_OBJECT_CREATE);
                    oe.setPlayer(ce.getPlayer());
                    sendEvent(oe);
                }
            }            
        }
    }
    
    
    private CharacterShortDescription getCharacterShortDescription(Characterdata cdata) {
        CharacterShortDescription csd = new CharacterShortDescription();
        csd.setGender(cdata.getGender());
        csd.setLocation(cdata.getCharacterStatus().getMap().getName());
        csd.setName(cdata.getCharactername());
        csd.setRace(cdata.getRace().getId());
        csd.setCharacterID(Globals.OBJECT_ID_PREFIX_CHARACTER + cdata.getId());
        csd.setVisualAppearance(getCharacterVisualAppearance(cdata.getCharacterVisualappearance()));
        return csd;
    }
    
    private CharacterVisualAppearance getCharacterVisualAppearance(CharacterVisualappearance cdata) {
        CharacterVisualAppearance cva = new CharacterVisualAppearance();
//        cva.setFaceType(cdata.getFaceType());
//        cva.setHairColor(cdata.getHairColor())
//        cva.setHairFacial(cdata.getHairFacial())
//        cva.setHairStyle(cdata.getHairStyle())
//        cva.setHeight(cdata.getHeight())
//        cva.setItemBelt(cdata.getItemBelt())
//        cva.setItemBoots(cdata.get)
//        cva.setItemBracers()
//        cva.setItemCape()
//        cva.setItemChest()
//        cva.setItemGloves()
//        cva.setItemHandLeft()
//        cva.setItemHandRight()
//        cva.setItemHead()
//        cva.setItemLegs()
//        cva.setItemShirt()
//        cva.setItemShoulders()
        try {
            BeanUtils.copyProperties(cva,cdata);    
        } catch (Exception e) {
            // TODO: handle exception
        }
        
        return cva;
    }
}