package de.mbws.server.data;

import de.mbws.common.data.AbstractPlayerData;
import de.mbws.common.eventdata.generated.WorldObject;

/**
 * Description: 
 * @author Azarai
 *
 */
public class ServerPlayerData extends AbstractPlayerData {

    
    private Long activeCharacter;
    private WorldObject movementInformation;
    
    public Long getActiveCharacter() {
        return activeCharacter;
    }

    public void setActiveCharacter(Long activeCharacter) {
        this.activeCharacter = activeCharacter;
    }

    public WorldObject getMovementInformation() {
        return movementInformation;
    }

    public void setMovementInformation(WorldObject movementInformation) {
        this.movementInformation = movementInformation;
    }
}
