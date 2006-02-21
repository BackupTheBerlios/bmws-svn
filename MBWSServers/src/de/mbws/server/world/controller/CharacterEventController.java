package de.mbws.server.world.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import de.mbws.common.Globals;
import de.mbws.common.data.db.generated.CharacterStatus;
import de.mbws.common.data.db.generated.CharacterVisualappearance;
import de.mbws.common.data.db.generated.Characterdata;
import de.mbws.common.events.AbstractGameEvent;
import de.mbws.common.events.CharacterEvent;
import de.mbws.common.events.EventTypes;
import de.mbws.common.events.PCEvent;
import de.mbws.common.events.data.generated.*;
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
			CharacterWorldServerInformation cwsi = (CharacterWorldServerInformation) event
					.getEventData();
			ServerPlayerData spd = new ServerPlayerData();
			Characterdata cdata = CharacterPersistenceManager.getInstance()
					.getCharacterByID(
							IdHelper.removePrefix(cwsi.getCharacter()
									.getCharacterID()));
			spd.setActiveCharacter(cdata);
			getWorldServer().addPlayer(cwsi.getSessionId(), spd);

			CharacterEvent result = new CharacterEvent(cwsi);
			result
					.setEventType(EventTypes.S2S_CHARACTER_NEW_CHARACTER_ENTERS_WORLD_OK);
			result.setPlayer(getWorldServer().getServerBySessionId(
					event.getPlayer().getSessionId()));
			sendEvent(result);
		} else if (event.getEventType() == EventTypes.C2S_CHARACTER_ENTERS_WORLD_REQUEST) {
			CharacterSelection csel = (CharacterSelection) event.getEventData();
			Characterdata cdata = ((ServerPlayerData) event.getPlayer())
					.getActiveCharacter();
			if (IdHelper.removePrefix(csel.getCharacterID()) != cdata.getId()) {
				return;
			}
			CharacterStatus cs = cdata.getCharacterStatus();
			PlayerDetails charDetails = new PlayerDetails();

			CharacterData ocd = new CharacterData();
			ocd.setName(cdata.getCharactername());
			ocd.setGender(cdata.getGender());
			ocd.setCurrentHealth(cs.getCharacterdata().getHealth());
			ocd.setMaxHealth(cdata.getHealth());
			ocd.setRace(cdata.getRace().getId().intValue());
			// TODO: must be set !
			ocd.setPvp((byte) 0);
			ocd.setPredefinedModel(-1);
			VisualAppearance cva = new VisualAppearance();
			ocd.setVisualAppearance(cva);
			// TODO clean up above
			IntVector3D location = new IntVector3D();
			location.setX(cs.getCoordinateX());
			location.setY(cs.getCoordinateY());
			location.setZ(cs.getCoordinateZ());
			ocd.setLocation(location);
			NetQuaternion heading = new NetQuaternion();
			heading.setW(0);
			heading.setX(0);
			heading.setY(0);
			heading.setZ(0);
			ocd.setHeading(heading);

			ocd.setCharacterID(((ServerPlayerData) ce.getPlayer())
					.getActiveCharacterAsObjectID());
			((ServerPlayerData) ce.getPlayer()).setMovementInformation(ocd);

			charDetails.setDescription(getPlayerShortDescription(cdata));
			charDetails.setHeading(heading);
			charDetails.setLocation(location);
			CharacterEvent result = new CharacterEvent(charDetails);
			result.setEventType(EventTypes.S2C_CHARACTER_ENTERS_WORLD);
			result.setPlayer(ce.getPlayer());
			sendEvent(result);

			ArrayList<Integer> receivers = (ArrayList<Integer>) getWorldServer()
					.getSessionIDOfAllPlayers().clone();
			if (receivers.size() > 1) {
				PCEvent oe = new PCEvent(ocd);
				oe.setEventType(EventTypes.S2C_MOVABLE_OBJECT_CREATE);
				receivers.remove(ce.getPlayer().getSessionId());
				oe.setPlayer(ce.getPlayer());
				oe.setRecipients(receivers
						.toArray(new Integer[receivers.size()]));
				sendEvent(oe);
			}

			Map players = getWorldServer().getAllPlayers();
			for (Iterator iter = receivers.iterator(); iter.hasNext();) {
				Integer element = (Integer) iter.next();
				if (!element.equals(ce.getPlayer().getSessionId())) {
					ServerPlayerData spd = (ServerPlayerData) players
							.get(element);
					PCEvent oe = new PCEvent(spd.getMovementInformation());
					oe.setEventType(EventTypes.S2C_MOVABLE_OBJECT_CREATE);
					oe.setPlayer(ce.getPlayer());
					sendEvent(oe);
				}
			}
		}
	}

	private PlayerShortDescription getPlayerShortDescription(Characterdata cdata) {
		PlayerShortDescription csd = new PlayerShortDescription();
		csd.setGender(cdata.getGender());
		csd.setLocation(cdata.getCharacterStatus().getMap().getName());
		csd.setName(cdata.getCharactername());
		csd.setRace(cdata.getRace().getId());
		csd.setCharacterID(Globals.OBJECT_ID_PREFIX_CHARACTER + cdata.getId());
		csd.setVisualAppearance(getCharacterVisualAppearance(cdata
				.getCharacterVisualappearance()));
		return csd;
	}

	private VisualAppearance getCharacterVisualAppearance(
			CharacterVisualappearance cdata) {
		VisualAppearance cva = new VisualAppearance();
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
}