package de.mbws.client.data;

import de.mbws.common.data.AbstractPlayerData;
import de.mbws.common.data.generated.Characterdata;

/**
 * Description: 
 * @author Kerim
 *
 */
public class ClientPlayerData extends AbstractPlayerData {

	private static ClientPlayerData instance;
	
	private Characterdata characterData;
	
	//TODO: Kerim Replace that with a different (private) field
	public String walkingStatus = "stand";
	public String turningStatus = "noTurn";
	
	private ClientPlayerData() {}
	public static ClientPlayerData getInstance() {
		if (instance == null) {
			instance = new ClientPlayerData();
		}
		return instance;
	}
	public Characterdata getCharacterData() {
		return characterData;
	}
	public void setCharacterData(Characterdata characterData) {
		this.characterData = characterData;
	}
}
