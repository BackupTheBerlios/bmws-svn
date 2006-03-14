package de.mbws.server.data;

import de.mbws.common.Globals;
import de.mbws.common.data.AbstractPlayerData;
import de.mbws.common.events.data.generated.CharacterData;
import de.mbws.server.data.db.generated.Account;
import de.mbws.server.data.db.generated.Characterdata;

/**
 * Description:
 * 
 * @author Azarai
 * 
 */
public class ServerPlayerData extends AbstractPlayerData {
    private Account account;
	private Characterdata activeCharacter;
	private CharacterData movementInformation;

	public Characterdata getActiveCharacter() {
		return activeCharacter;
	}

	public void setActiveCharacter(Characterdata activeCharacter) {
		this.activeCharacter = activeCharacter;
	}

	public CharacterData getMovementInformation() {
		return movementInformation;
	}

	public void setMovementInformation(CharacterData movementInformation) {
		this.movementInformation = movementInformation;
	}

	public String getActiveCharacterAsObjectID() {
		return Globals.OBJECT_ID_PREFIX_CHARACTER + activeCharacter;
	}
    
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
