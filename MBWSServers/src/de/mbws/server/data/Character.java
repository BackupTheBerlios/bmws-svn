package de.mbws.server.data;

import de.mbws.common.events.data.generated.IntVector3D;
import de.mbws.common.events.data.generated.NetQuaternion;
import de.mbws.server.data.db.generated.CharacterData;
import de.mbws.server.data.db.generated.CharacterStatus;
import de.mbws.server.data.db.generated.CharacterVisualappearance;
import de.mbws.server.data.db.generated.Race;

/**
 * Description: character business object
 * 
 * @author Azarai
 */
public class Character {

    private CharacterData normalData;

    private CharacterStatus currentData;

    private volatile CharacterVisualappearance visualData;

    private NetQuaternion heading;

    private IntVector3D location;

    /**
     * @param charStatus
     * @param charData
     * @param charVisual
     */
    public Character(CharacterStatus charStatus, CharacterData charData, CharacterVisualappearance charVisual) {
        super();
        currentData = charStatus;
        normalData = charData;
        visualData = charVisual;

        location = new IntVector3D();
        location.setX(currentData.getCoordinateX());
        location.setY(currentData.getCoordinateY());
        location.setZ(currentData.getCoordinateZ());
    }

    public void changeVisualItem(int bodyPart, String itemId) {
        // TODO konstanten für bodyparts; sollte uu in commons liegen, und
        // prüfung ob itemType zu bodyPart passt
        // TODO bodyparts definieren.
    }

    public String getCharStatus() {
        return currentData.getCharStatus();
    }

    public IntVector3D getLocation() {
        return location;
    }

    public int getCurrentHealth() {
        return currentData.getCurrentHealth();
    }

    public int getCurrentMana() {
        return currentData.getCurrentMana();
    }

    public int getCurrentStamina() {
        return currentData.getCurrentStamina();
    }

    public String getGamestatus() {
        return currentData.getGamestatus();
    }

    public boolean isPvp() {
        return currentData.isPvp();
    }

    public void setCharStatus(String charStatus) {
        currentData.setCharStatus(charStatus);
    }

    public void setCoordinates(IntVector3D location) {
        this.location = location;
    }

    public int getMapId() {
        return currentData.getMap().getId();
    }

    public void setCurrentHealth(int currentHealth) {
        currentData.setCurrentHealth(currentHealth);
    }

    public void setCurrentMana(int currentMana) {
        currentData.setCurrentMana(currentMana);
    }

    public void setCurrentStamina(int currentStamina) {
        currentData.setCurrentStamina(currentStamina);
    }

    public void setGamestatus(String gamestatus) {
        currentData.setGamestatus(gamestatus);
    }

    public void setPvp(boolean pvp) {
        currentData.setPvp(pvp);
    }

    public short getAge() {
        return normalData.getAge();
    }

    public String getName() {
        return normalData.getCharactername();
    }

    public String getGender() {
        return normalData.getGender();
    }

    public int getHealth() {
        return normalData.getHealth();
    }

    public Long getId() {
        return normalData.getId();
    }

    public int getMana() {
        return normalData.getMana();
    }

    public Race getRace() {
        return normalData.getRace();
    }

    public int getRaceID() {
        return getRace().getId().intValue();
    }

    public int getStamina() {
        return normalData.getStamina();
    }

    public CharacterVisualappearance getVisualData() {
        return visualData;
    }

    public NetQuaternion getHeading() {
        return heading;
    }

    public void setHeading(NetQuaternion heading) {
        this.heading = heading;
    }
}
