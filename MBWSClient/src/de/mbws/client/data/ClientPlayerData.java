package de.mbws.client.data;

import java.util.List;

import de.mbws.common.data.AbstractPlayerData;
import de.mbws.common.events.data.generated.PlayerCharacterData;


/**
 * Description: 
 * @author Kerim
 *
 */
public class ClientPlayerData extends AbstractPlayerData {

	private static ClientPlayerData instance;
	
	private PlayerCharacterData selectedCharacterData;
	private Player player;
	private List<PlayerCharacterData> allCharactersOfPlayer;
	
	
	public List<PlayerCharacterData> getAllCharactersOfPlayer() {
		return allCharactersOfPlayer;
	}
	public void setAllCharactersOfPlayer(
			List<PlayerCharacterData> allCharactersOfPlayer) {
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
	public PlayerCharacterData getSelectedCharacterData() {
		return selectedCharacterData;
	}
	public void setSelectedCharacterData(PlayerCharacterData selectedCharacterData) {
		this.selectedCharacterData = selectedCharacterData;
	}
}
