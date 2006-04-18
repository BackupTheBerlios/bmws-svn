package de.mbws.server.data;

import de.mbws.common.Globals;
import de.mbws.common.data.AbstractPlayerData;
import de.mbws.server.data.db.generated.Account;

/**
 * Description:
 * 
 * @author Azarai
 * 
 */
public class ServerPlayerData extends AbstractPlayerData {
    private Account account;
	private Character activeCharacter;


	public Character getActiveCharacter() {
		return activeCharacter;
	}

	public void setActiveCharacter(Character activeCharacter) {
		this.activeCharacter = activeCharacter;
	}

	public String getActiveCharacterAsObjectID() {
		return Globals.OBJECT_ID_PREFIX_CHARACTER + activeCharacter.getId();
	}
    
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}