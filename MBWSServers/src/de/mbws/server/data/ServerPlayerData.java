package de.mbws.server.data;

import de.mbws.common.Globals;
import de.mbws.common.data.AbstractPlayerData;
import de.mbws.common.data.db.generated.Characterdata;
import de.mbws.common.events.data.generated.WorldObject;

/**
 * Description: 
 * @author Azarai
 *
 */
public class ServerPlayerData extends AbstractPlayerData {

    
    private Characterdata activeCharacter;
    private WorldObject movementInformation;
    
    public Characterdata getActiveCharacter() {
        return activeCharacter;
    }

    public void setActiveCharacter(Characterdata activeCharacter) {
        this.activeCharacter = activeCharacter;
    }

    public WorldObject getMovementInformation() {
        return movementInformation;
    }

    public void setMovementInformation(WorldObject movementInformation) {
        this.movementInformation = movementInformation;
    }
    
    public String getActiveCharacterAsObjectID() {
        return Globals.OBJECT_ID_PREFIX_CHARACTER + activeCharacter;
    }
}
