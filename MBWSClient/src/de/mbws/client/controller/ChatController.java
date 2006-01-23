package de.mbws.client.controller;

import org.apache.log4j.Logger;

import de.mbws.client.data.ClientPlayerData;
import de.mbws.client.gui.ingame.GameDesktop;
import de.mbws.common.eventdata.generated.ChatData;
import de.mbws.common.events.ChatEvent;
import de.mbws.common.events.EventTypes;

public class ChatController {

	private Logger logger = Logger.getLogger(ChatController.class);
	private static ChatController instance;
	private GameDesktop gameDesktop;
	private String playerName = ClientPlayerData.getInstance()
	.getSelectedCharacterData().getName();

	private ChatController() {
		super();
	}

	public static ChatController getInstance() {
		if (instance == null) {
			instance = new ChatController();
		}
		return instance;
	}

	public void parseChat(String text) {
		switch (text.charAt(0)) {
		case '/':
			logger.info("Command issued");
			// onCommand(line.substring(1));
			break;
		default:
			if (text.length() > 0) {
				logger.info("Saying: " + text);
				System.out.println(text);
				String messages = gameDesktop.getChatAndMessagesTP().getText();
				gameDesktop.getChatAndMessagesTP().setText(
						messages + playerName + " : " + text + "\n");
				ClientNetworkController.getInstance().handleOutgoingEvent(
						createChatEvent(text, EventTypes.CHAT_SAY));
			}
		}
	}

	public ChatEvent createChatEvent(String text, int chatType) {
		ChatData data = new ChatData();
		data.setMessage(text);
		data.setAuthor(playerName);
		ChatEvent event = new ChatEvent(data);
		event.setEventType(chatType);
		return event;
	}

	public void handleEvent(ChatEvent event) {
		if (gameDesktop == null) {
			logger.error("No access to chatwindow !");
			return;
		}
		ChatData data = event.getChatData();
		String messages = gameDesktop.getChatAndMessagesTP().getText();
		gameDesktop.getChatAndMessagesTP().setText(
				messages + data.getAuthor() + " > " + data.getMessage() + "\n");

	}

	public void setGameDesktop(GameDesktop gameDesktop) {
		this.gameDesktop = gameDesktop;
	}

}
