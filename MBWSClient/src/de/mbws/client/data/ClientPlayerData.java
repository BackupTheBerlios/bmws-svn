package de.mbws.client.data;

import java.util.List;

import de.mbws.common.data.AbstractPlayerData;
import de.mbws.common.data.generated.Characterdata;
import de.mbws.common.eventdata.generated.CharacterShortDescription;

/**
 * Description: 
 * @author Kerim
 *
 */
public class ClientPlayerData extends AbstractPlayerData {

	private static ClientPlayerData instance;
	
	private Characterdata characterData;
	private Player player;
	private List<CharacterShortDescription> allCharactersOfPlayer;
	
	
	public List<CharacterShortDescription> getAllCharactersOfPlayer() {
		return allCharactersOfPlayer;
	}
	public void setAllCharactersOfPlayer(
			List<CharacterShortDescription> allCharactersOfPlayer) {
		this.allCharactersOfPlayer = allCharactersOfPlayer;
	}
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
	
	public void setPlayer(Player aPlayer) {
		this.player = aPlayer;
	}
	
	public Player getPlayer() {
		return player;
	}
}
