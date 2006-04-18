package de.mbws.server.data;

import org.apache.commons.beanutils.BeanUtils;

import de.mbws.common.Globals;
import de.mbws.common.events.data.generated.CharacterData;
import de.mbws.common.events.data.generated.CharacterVisualAppearance;
import de.mbws.common.events.data.generated.PlayerCharacterShortDescription;
import de.mbws.server.data.db.generated.CharacterVisualappearance;

/**
 * Description:
 * 
 * @author Azarai
 */
public class CharacterHelper {

    /**
     * creates a PlayerCharacterShortDescription payload for the given character object
     * @param character
     * @return PlayerCharacterShortDescription
     */
    public static PlayerCharacterShortDescription getPlayerCharacterShortDescription(Character character) {
        PlayerCharacterShortDescription csd = new PlayerCharacterShortDescription();
        csd.setGender(character.getGender().charAt(0));
        csd.setMap(character.getMapId());
        csd.setName(character.getName());
        csd.setRace(character.getRaceID());
        csd.setCharacterID(Globals.OBJECT_ID_PREFIX_CHARACTER + character.getId());
        csd.setVisualAppearance(getCharacterVisualAppearance(character.getVisualData()));
        return csd;
    }

    private static CharacterVisualAppearance getCharacterVisualAppearance(CharacterVisualappearance cdata) {
        CharacterVisualAppearance cva = new CharacterVisualAppearance();
        try {
            BeanUtils.copyProperties(cva, cdata);
        } catch (Exception e) {
            // TODO: handle exception
        }

        return cva;
    }

    /**
     * creates a CharacterData payload for the given character object
     * @param character
     * @return CharacterData
     */
    public static CharacterData getCharacterData(Character character) {
        de.mbws.common.events.data.generated.CharacterData ocd = new de.mbws.common.events.data.generated.CharacterData();
        ocd.setName(character.getName());
        ocd.setGender(character.getGender().charAt(0));

        ocd.setRace(character.getRaceID());
        if (character.isPvp()) {
            ocd.setPvp(Globals.ON);
        } else {
            ocd.setPvp(Globals.OFF);
        }
        CharacterVisualAppearance cva = new CharacterVisualAppearance();
        ocd.setVisualAppearance(cva);
        ocd.setLocation(character.getLocation());
        ocd.setHeading(character.getHeading());

        ocd.setCharacterID(Globals.OBJECT_ID_PREFIX_CHARACTER + character.getId());
        return ocd;
    }
}