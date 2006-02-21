package de.mbws.client.data;

import java.util.List;

import de.mbws.common.data.AbstractPlayerData;
import de.mbws.common.events.data.generated.PlayerData;


/**
 * Description: 
 * @author Kerim
 *
 */
public class ClientPlayerData extends AbstractPlayerData {

	private static ClientPlayerData instance;
	
	private PlayerData selectedCharacterData;
	private Player player;
	private List<PlayerData> allCharactersOfPlayer;
	
	
	public List<PlayerData> getAllCharactersOfPlayer() {
		return allCharactersOfPlayer;
	}
	public void setAllCharactersOfPlayer(
			List<PlayerData> allCharactersOfPlayer) {
		this.allCharactersOfPlayer = allCharactersOfPlayer;
	}
	private ClientPlayerData() {}
	public static ClientPlayerData getInstance() {
		if (instance == null) {
			instance = new ClientPlayerData();
		}
		return instance;
	}
	
	public void setPlayer(Player aPlayer) {
		this.player = aPlayer;
	}
	
	public Player getPlayer() {
		return player;
	}
	public PlayerData getSelectedCharacterData() {
		return selectedCharacterData;
	}
	public void setSelectedCharacterData(PlayerData selectedCharacterData) {
		this.selectedCharacterData = selectedCharacterData;
	}
}
