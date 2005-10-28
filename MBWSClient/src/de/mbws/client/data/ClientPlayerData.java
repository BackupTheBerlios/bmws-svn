package de.mbws.client.data;

import de.mbws.common.data.AbstractPlayerData;

/**
 * Description: 
 * @author Azarai
 *
 */
public class ClientPlayerData extends AbstractPlayerData {

	private static ClientPlayerData instance;
	
	private ClientPlayerData() {}
	public static ClientPlayerData getInstance() {
		if (instance == null) {
			instance = new ClientPlayerData();
		}
		return instance;
	}
}
