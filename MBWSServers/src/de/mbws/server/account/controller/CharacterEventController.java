package de.mbws.server.account.controller;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import de.mbws.common.Globals;
import de.mbws.common.data.db.CharacterStatus;
import de.mbws.common.data.db.CharacterVisualappearance;
import de.mbws.common.data.db.Characterdata;
import de.mbws.common.eventdata.generated.CharacterData;
import de.mbws.common.eventdata.generated.CharacterSelection;
import de.mbws.common.eventdata.generated.CharacterValues;
import de.mbws.common.eventdata.generated.CharacterVisualAppearance;
import de.mbws.common.eventdata.generated.CharacterWorldServerInformation;
import de.mbws.common.eventdata.generated.CharactersOfPlayer;
import de.mbws.common.eventdata.generated.IntVector3D;
import de.mbws.common.eventdata.generated.NetQuaternion;
import de.mbws.common.events.AbstractGameEvent;
import de.mbws.common.events.CharacterEvent;
import de.mbws.common.events.EventTypes;
import de.mbws.common.events.ServerRedirectEvent;
import de.mbws.server.account.AccountServer;
import de.mbws.server.account.persistence.CharacterPersistenceManager;
import de.mbws.server.data.ServerCommunicationData;

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
        //TODO: Kerim 26.12.2005 CharacterReceiveRequest not used anymore !
        if (event.getEventType() == EventTypes.C2S_CHARACTER_RECEIVE_REQUEST) {
//            CharacterSelection cs = (CharacterSelection) event.getEventData();
//            Characterdata cdata = CharacterPersistenceManager.getInstance().getCharacter(ce.getPlayer().getAccount().getUsername(),
//                    IdHelper.removePrefix(cs.getCharacterID()));
//
//            CharacterDetails cd = new CharacterDetails();
//            cd.setDescription(getCharacterData(cdata));
//            CharacterEvent result = new CharacterEvent(cd);
//            result.setEventType(EventTypes.S2C_CHARACTER_RECEIVE);
//            result.setPlayer(ce.getPlayer());
//            sendEvent(result);
        } else if (event.getEventType() == EventTypes.C2S_CHARACTER_START_PLAYING_REQUEST) {
            CharacterSelection csel = (CharacterSelection) event.getEventData();
            CharacterWorldServerInformation cwsi = new CharacterWorldServerInformation();
            cwsi.setCharacter(csel);
            cwsi.setSessionId(event.getPlayer().getSessionId());
            CharacterEvent result = new CharacterEvent(cwsi);
            result.setEventType(EventTypes.S2S_CHARACTER_NEW_CHARACTER_ENTERS_WORLD);
            result.setPlayer(getAccountServer().getPlayerBySessionId(getAccountServer().getWorldServerSessionId()));
            sendEvent(result);
        } else if (event.getEventType() == EventTypes.C2S_CHARACTER_LIST_RECEIVE_REQUEST) {
            List chars = CharacterPersistenceManager.getInstance().getAllCharacters(ce.getPlayer().getAccount().getUsername());
            LinkedList<CharacterData> charsToSend = new LinkedList<CharacterData>();
            for (Iterator iter = chars.iterator(); iter.hasNext();) {
                Characterdata element = (Characterdata) iter.next();
                charsToSend.add(getCharacterData(element));
            }
            CharactersOfPlayer cop = new CharactersOfPlayer();
            cop.setCharactersOfPlayer(charsToSend);
            CharacterEvent result = new CharacterEvent(cop);
            result.setEventType(EventTypes.S2C_CHARACTER_LIST_RECEIVE);
            result.setPlayer(ce.getPlayer());
            sendEvent(result);
        } else if (event.getEventType() == EventTypes.S2S_CHARACTER_NEW_CHARACTER_ENTERS_WORLD_OK) {
            CharacterWorldServerInformation cwsi =  (CharacterWorldServerInformation) event.getEventData();
            ServerRedirectEvent sre = new ServerRedirectEvent(((ServerCommunicationData) event.getPlayer()).getHostInfo());
            sre.setEventType(EventTypes.S2C_REDIRECT_TO_WORLDSERVER);
            sre.setPlayer(getAccountServer().getPlayerBySessionId(cwsi.getSessionId()));
            sendEvent(sre);
        }
    }
    
    
    private CharacterData getCharacterData(Characterdata cdata) {
    	CharacterData characterData = new CharacterData();
        characterData.setGender(cdata.getGender());
        characterData.setName(cdata.getCharactername());
        characterData.setRace(cdata.getRace().getId());
        characterData.setAge(cdata.getAge());
        characterData.setCharacterID(Globals.OBJECT_ID_PREFIX_CHARACTER + cdata.getId());
        
        de.mbws.common.eventdata.generated.CharacterStatus clientCharacterStatus = new de.mbws.common.eventdata.generated.CharacterStatus();
        CharacterStatus serverCharacterStatus = cdata.getCharacterStatus();
        clientCharacterStatus.setCharstatus(serverCharacterStatus.getCharstatus());;
        clientCharacterStatus.setFreexp(serverCharacterStatus.getFreexp());
        clientCharacterStatus.setGamestatus(serverCharacterStatus.getGamestatus());
        clientCharacterStatus.setPvp(serverCharacterStatus.getPvp());
   
//TODO: Kerim 26.12.2005: should we store heading/location in status or in data ?        
        IntVector3D location = new IntVector3D();
        location.setX(serverCharacterStatus.getCoordinateX());
        location.setY(serverCharacterStatus.getCoordinateY());
        location.setZ(serverCharacterStatus.getCoordinateZ());
        clientCharacterStatus.setLocation(location);
        characterData.setLocation(location);
        
//TODO: Kerim 26.12.2005: fix this and put real values in it
        NetQuaternion heading = new NetQuaternion();
        heading.setW(0);
        heading.setX(0);
        heading.setY(0);
        heading.setZ(0);
        clientCharacterStatus.setHeading(heading);
        characterData.setHeading(heading);
        
        CharacterValues clientNormalValues = new CharacterValues();
        clientNormalValues.setConstitution(cdata.getConstitution());
        clientNormalValues.setDexterity(cdata.getDexterity());
        clientNormalValues.setHealth(cdata.getHealth());
        clientNormalValues.setIntelligence(cdata.getIntelligence());
        clientNormalValues.setMana(cdata.getMana());
        clientNormalValues.setStamina(cdata.getStamina());
        clientNormalValues.setStrength(cdata.getStrength());
        characterData.setNormalValues(clientNormalValues);
        
        CharacterValues clientcurrentValues = new CharacterValues();
        clientcurrentValues.setConstitution(serverCharacterStatus.getCurrentconstitution());
        clientcurrentValues.setDexterity(serverCharacterStatus.getCurrentdexterity());
        clientcurrentValues.setHealth(serverCharacterStatus.getCurrenthealth());
        clientcurrentValues.setIntelligence(serverCharacterStatus.getCurrentinteligence());
        clientcurrentValues.setMana(serverCharacterStatus.getCurrentmana());
        clientcurrentValues.setStamina(serverCharacterStatus.getCurrentstamina());
        clientcurrentValues.setStrength(serverCharacterStatus.getCurrentstrength());
        clientCharacterStatus.setCurrentValues(clientcurrentValues);
        
        characterData.setStatus(clientCharacterStatus);
        characterData.setVisualAppearance(getCharacterVisualAppearance(cdata.getCharacterVisualappearance()));
        characterData.setLocationdescription(cdata.getCharacterStatus().getMap().getName());
        
        return characterData;
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