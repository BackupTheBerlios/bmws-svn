package de.mbws.server.account.controller;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import de.mbws.common.Globals;
import de.mbws.common.data.generated.CharacterVisualappearance;
import de.mbws.common.data.generated.Characterdata;
import de.mbws.common.eventdata.generated.CharacterDetails;
import de.mbws.common.eventdata.generated.CharacterSelection;
import de.mbws.common.eventdata.generated.CharacterShortDescription;
import de.mbws.common.eventdata.generated.CharacterVisualAppearance;
import de.mbws.common.eventdata.generated.CharacterWorldServerInformation;
import de.mbws.common.eventdata.generated.CharactersOfPlayer;
import de.mbws.common.events.AbstractGameEvent;
import de.mbws.common.events.CharacterEvent;
import de.mbws.common.events.EventTypes;
import de.mbws.common.events.ServerRedirectEvent;
import de.mbws.server.account.AccountServer;
import de.mbws.server.account.persistence.CharacterPersistenceManager;
import de.mbws.server.data.ServerCommunicationData;
import de.mbws.server.utils.IdHelper;

/**
 * Description:
 * 
 * @author Azarai
 */
public class CharacterEventController extends AccountServerBaseEventController {

    /**
     * @param accountServer
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
        if (event.getEventType() == EventTypes.C2S_CHARACTER_RECEIVE_REQUEST) {
            CharacterSelection cs = (CharacterSelection) event.getEventData();
            Characterdata cdata = CharacterPersistenceManager.getInstance().getCharacter(ce.getPlayer().getAccount().getUsername(),
                    IdHelper.removePrefix(cs.getCharacterID()));

            CharacterDetails cd = new CharacterDetails();
            cd.setDescription(getCharacterShortDescription(cdata));
            CharacterEvent result = new CharacterEvent(cd);
            result.setEventType(EventTypes.S2C_CHARACTER_RECEIVE);
            result.setPlayer(ce.getPlayer());
            sendEvent(result);
        } else if (event.getEventType() == EventTypes.C2S_CHARACTER_START_PLAYING_REQUEST) {
            CharacterSelection csel = (CharacterSelection) event.getEventData();
            CharacterWorldServerInformation cwsi = new CharacterWorldServerInformation();
            cwsi.setCharacter(csel);
            cwsi.setSessionId(event.getPlayer().getSessionId());
            CharacterEvent result = new CharacterEvent(cwsi);
            result.setEventType(EventTypes.S2S_CHARACTER_NEW_CHARACTER_ENTERS_WORLD);
            result.setPlayer(getAccountServer().getPlayerBySessionId(getAccountServer().getWorldServerSessionId()));
            sendEvent(result);

            ServerRedirectEvent sre = new ServerRedirectEvent(((ServerCommunicationData) result.getPlayer()).getHostInfo());
            sre.setEventType(EventTypes.S2C_REDIRECT_TO_WORLDSERVER);
            sre.setPlayer(event.getPlayer());
            sendEvent(sre);
        } else if (event.getEventType() == EventTypes.C2S_CHARACTER_LIST_RECEIVE_REQUEST) {
            List chars = CharacterPersistenceManager.getInstance().getAllCharacters(ce.getPlayer().getAccount().getUsername());
            LinkedList<CharacterShortDescription> charsToSend = new LinkedList<CharacterShortDescription>();
            for (Iterator iter = chars.iterator(); iter.hasNext();) {
                Characterdata element = (Characterdata) iter.next();
                charsToSend.add(getCharacterShortDescription(element));
            }
            CharactersOfPlayer cop = new CharactersOfPlayer();
            cop.setDescriptions(charsToSend);
            CharacterEvent result = new CharacterEvent(cop);
            result.setEventType(EventTypes.S2C_CHARACTER_LIST_RECEIVE);
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