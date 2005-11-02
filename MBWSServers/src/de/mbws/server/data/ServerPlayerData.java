package de.mbws.server.data;

import de.mbws.common.data.AbstractPlayerData;

/**
 * Description: 
 * @author Azarai
 *
 */
public class ServerPlayerData extends AbstractPlayerData {

    
    private Long activeCharacter;

    public Long getActiveCharacter() {
        return activeCharacter;
    }

    public void setActiveCharacter(Long activeCharacter) {
        this.activeCharacter = activeCharacter;
    }
}
