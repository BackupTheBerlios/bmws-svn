package de.mbws.client.data;

import java.util.List;

import de.mbws.common.data.AbstractPlayerData;
import de.mbws.common.events.data.generated.PlayerCharacterData;

/**
 * Description: This class is used to keep track of all characters a player has
 * 
 * @author Kerim
 * 
 */
public class ClientPlayerData extends AbstractPlayerData {

	private static ClientPlayerData instance;

	private PlayerCharacterData selectedCharacterData;
	private PlayerObject player;
	private List<PlayerCharacterData> allCharactersOfPlayer;

	public List<PlayerCharacterData> getAllCharactersOfPlayer() {
		return allCharactersOfPlayer;
	}

	public void setAllCharactersOfPlayer(
			List<PlayerCharacterData> allCharactersOfPlayer) {
		this.allCharactersOfPlayer = allCharactersOfPlayer;
	}

	private ClientPlayerData() {
	}

	public static ClientPlayerData getInstance() {
		if (instance == null) {
			instance = new ClientPlayerData();
		}
		return instance;
	}

	public void setPlayer(PlayerObject aPlayer) {
		this.player = aPlayer;
	}

	public PlayerObject getPlayer() {
		return player;
	}

	public PlayerCharacterData getSelectedCharacterData() {
		return selectedCharacterData;
	}

	public void setSelectedCharacterData(PlayerCharacterData selectedCharacterData) {
		this.selectedCharacterData = selectedCharacterData;
	}
}
