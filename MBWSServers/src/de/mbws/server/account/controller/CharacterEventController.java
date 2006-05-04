package de.mbws.server.account.controller;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import de.mbws.common.Globals;
import de.mbws.common.MessageKeys;
import de.mbws.common.events.AbstractGameEvent;
import de.mbws.common.events.CharacterEvent;
import de.mbws.common.events.EventTypes;
import de.mbws.common.events.ServerRedirectEvent;
import de.mbws.common.events.data.generated.*;
import de.mbws.server.AbstractTcpServer;
import de.mbws.server.account.persistence.CharacterPersistenceManager;
import de.mbws.server.data.ServerCommunicationData;
import de.mbws.server.data.ServerPlayerData;
import de.mbws.server.data.db.generated.*;
import de.mbws.server.data.db.generated.CharacterData;
import de.mbws.server.exceptions.DuplicateKeyException;

/**
 * Description:
 * 
 * @author Azarai
 */
public class CharacterEventController extends AccountServerBaseEventController {

    private static final Integer[] supportedEventTypes = { EventTypes.C2S_CHARACTER_RECEIVE_REQUEST, EventTypes.C2S_CHARACTER_START_PLAYING_REQUEST,
            EventTypes.C2S_CHARACTER_LIST_RECEIVE_REQUEST, EventTypes.S2S_CHARACTER_NEW_CHARACTER_ENTERS_WORLD_OK,
            EventTypes.C2S_CHARACTER_CREATE_REQUEST };

    /**
     * @param server
     */
    public CharacterEventController(AbstractTcpServer server) {
        super(server);
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.mbws.server.controller.EventController#handleEvent(de.mbws.common.events.AbstractGameEvent)
     */
    @Override
    public void handleEvent(AbstractGameEvent event) {
        CharacterEvent ce = (CharacterEvent) event;
        // TODO: Kerim 26.12.2005 CharacterReceiveRequest not used anymore !
        if (event.getEventType() == EventTypes.C2S_CHARACTER_RECEIVE_REQUEST) {
            // CharacterSelection cs = (CharacterSelection)
            // event.getEventData();
            // Characterdata cdata =
            // CharacterPersistenceManager.getInstance().getCharacter(ce.getPlayer().getAccount().getUsername(),
            // IdHelper.removePrefix(cs.getCharacterID()));
            //
            // CharacterDetails cd = new CharacterDetails();
            // cd.setDescription(getCharacterData(cdata));
            // CharacterEvent result = new CharacterEvent(cd);
            // result.setEventType(EventTypes.S2C_CHARACTER_RECEIVE);
            // result.setPlayer(ce.getPlayer());
            // sendEvent(result);
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
            List chars = CharacterPersistenceManager.getInstance().getAllCharacters(((ServerPlayerData) ce.getPlayer()).getAccount().getUsername());
            LinkedList<PlayerCharacterData> charsToSend = new LinkedList<PlayerCharacterData>();
            for (Iterator iter = chars.iterator(); iter.hasNext();) {
                CharacterData element = (CharacterData) iter.next();
                charsToSend.add(getPlayerCharacterData(element));
            }
            CharactersOfPlayer cop = new CharactersOfPlayer();
            cop.setCharactersOfPlayer(charsToSend);
            CharacterEvent result = new CharacterEvent(cop);
            result.setEventType(EventTypes.S2C_CHARACTER_LIST_RECEIVE);
            result.setPlayer(ce.getPlayer());
            sendEvent(result);
        } else if (event.getEventType() == EventTypes.S2S_CHARACTER_NEW_CHARACTER_ENTERS_WORLD_OK) {
            CharacterWorldServerInformation cwsi = (CharacterWorldServerInformation) event.getEventData();
            ServerRedirectEvent sre = new ServerRedirectEvent(((ServerCommunicationData) event.getPlayer()).getHostInfo());
            sre.setEventType(EventTypes.S2C_REDIRECT_TO_WORLDSERVER);
            sre.setPlayer(getAccountServer().getPlayerBySessionId(cwsi.getSessionId()));
            sendEvent(sre);
        } else if (event.getEventType() == EventTypes.C2S_CHARACTER_CREATE_REQUEST) {
//          FIXME cleanup, seems to be doubled code
            ServerPlayerData spd = (ServerPlayerData) event.getPlayer();
            CreateCharacter character = (CreateCharacter) event.getEventData();
            CharacterData cdata = new CharacterData();
            cdata.setCharactername(character.getName());
            cdata.setGender(String.valueOf(character.getGender()));
            Race r = CharacterPersistenceManager.getInstance().getRace(character.getRace());
            cdata.setRace(r);
            CharacterStatus status = new CharacterStatus();
            status.setCharacterdata(cdata);
            status.setCoordinateX(1);
            status.setCoordinateY(1);
            status.setCoordinateZ(1);
            status.setCharStatus("A");
            status.setPvp(false);
            status.setGamestatus("0");
            CharacterVisualappearance cva = new CharacterVisualappearance();
            cva.setCharacterdata(cdata);
            cdata.setCharacterVisualappearance(cva);

            Map m = CharacterPersistenceManager.getInstance().getMap(1);
            status.setMap(m);
            cdata.setCharacterStatus(status);
            cdata.setAccount(spd.getAccount());
            try {
                CharacterPersistenceManager.getInstance().createCharacter(cdata);
            } catch (DuplicateKeyException e) {
                SystemErrorData sed = new SystemErrorData();
                sed.setReason(MessageKeys.CHARACTERNAME_DUPLICATE);
                CharacterEvent ae = new CharacterEvent(sed);
                ae.setPlayer(event.getPlayer());
                ae.setEventType(EventTypes.S2C_CHARACTER_CREATE_FAIL);
                sendEvent(ae);
            } catch (Exception e) {
                // ignore for now
            }

            CharacterEvent result = new CharacterEvent();
            result.setEventType(EventTypes.S2C_CHARACTER_CREATE_OK);
            result.setPlayer(spd);
            sendEvent(result);
        }
    }

    //FIXME cleanup, seems to be doubled code
    private PlayerCharacterData getPlayerCharacterData(CharacterData cdata) {
        PlayerCharacterData playerData = new PlayerCharacterData();
        playerData.setGender(cdata.getGender().charAt(0));
        playerData.setName(cdata.getCharactername());
        playerData.setRace(cdata.getRace().getId());
        playerData.setAge(cdata.getAge());
        playerData.setCharacterID(Globals.OBJECT_ID_PREFIX_CHARACTER + cdata.getId());

        de.mbws.common.events.data.generated.PlayerCharacterStatus clientPlayerStatus = new de.mbws.common.events.data.generated.PlayerCharacterStatus();
        CharacterStatus serverCharacterStatus = cdata.getCharacterStatus();
        clientPlayerStatus.setGamestatus(serverCharacterStatus.getGamestatus());
        clientPlayerStatus.setPvp(String.valueOf(serverCharacterStatus.isPvp()));

        // TODO: Kerim 26.12.2005: should we store heading/location in status or
        // in data ?
        IntVector3D location = new IntVector3D();
        location.setX(serverCharacterStatus.getCoordinateX());
        location.setY(serverCharacterStatus.getCoordinateY());
        location.setZ(serverCharacterStatus.getCoordinateZ());
        clientPlayerStatus.setLocation(location);
        playerData.setLocation(location);

        // TODO: Kerim 26.12.2005: fix this and put real values in it
        NetQuaternion heading = new NetQuaternion();
        heading.setW(0);
        heading.setX(0);
        heading.setY(0);
        heading.setZ(0);
        clientPlayerStatus.setHeading(heading);
        playerData.setHeading(heading);

        PlayerCharacterAttributes clientNormalValues = new PlayerCharacterAttributes();
        clientNormalValues.setHealth(cdata.getHealth());
        clientNormalValues.setMana(cdata.getMana());
        clientNormalValues.setStamina(cdata.getStamina());
        playerData.setNormalValues(clientNormalValues);

        PlayerCharacterAttributes clientcurrentValues = new PlayerCharacterAttributes();
        clientcurrentValues.setHealth(serverCharacterStatus.getCurrentHealth());

        clientcurrentValues.setMana(serverCharacterStatus.getCurrentMana());
        clientcurrentValues.setStamina(serverCharacterStatus.getCurrentStamina());
        clientPlayerStatus.setCurrentAttributes(clientcurrentValues);

        playerData.setStatus(clientPlayerStatus);
        playerData.setVisualAppearance(getCharacterVisualAppearance(cdata.getCharacterVisualappearance()));
        playerData.setLocationdescription(cdata.getCharacterStatus().getMap().getName());

        return playerData;
    }

    private CharacterVisualAppearance getCharacterVisualAppearance(CharacterVisualappearance cdata) {
        CharacterVisualAppearance cva = new CharacterVisualAppearance();
        // cva.setFaceType(cdata.getFaceType());
        // cva.setHairColor(cdata.getHairColor())
        // cva.setHairFacial(cdata.getHairFacial())
        // cva.setHairStyle(cdata.getHairStyle())
        // cva.setHeight(cdata.getHeight())
        // cva.setItemBelt(cdata.getItemBelt())
        // cva.setItemBoots(cdata.get)
        // cva.setItemBracers()
        // cva.setItemCape()
        // cva.setItemChest()
        // cva.setItemGloves()
        // cva.setItemHandLeft()
        // cva.setItemHandRight()
        // cva.setItemHead()
        // cva.setItemLegs()
        // cva.setItemShirt()
        // cva.setItemShoulders()
        try {
            BeanUtils.copyProperties(cva, cdata);
        } catch (Exception e) {
            // TODO: handle exception
        }

        return cva;
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