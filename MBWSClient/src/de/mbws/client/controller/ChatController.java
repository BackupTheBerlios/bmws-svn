package de.mbws.client.controller;

import org.apache.log4j.Logger;

import de.mbws.client.data.ClientPlayerData;
import de.mbws.client.gui.ingame.ChatWindow;
import de.mbws.common.events.MessageEvent;
import de.mbws.common.events.data.generated.MessageData;

public class ChatController {

	private Logger logger = Logger.getLogger(ChatController.class);
	private static ChatController instance;
	private ChatWindow chatWindow;
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

	public MessageEvent createChatEvent(String text, int chatType, String recipient) {
		MessageData data = new MessageData();
		data.setMessage(text);
		data.setAuthor(playerName);
		data.setRecipient(recipient);
		MessageEvent event = new MessageEvent(data);
		event.setEventType(chatType);
		return event;
	}

	public void handleEvent(MessageEvent event) {
		if (chatWindow == null) {
			logger.error("No access to chatwindow !");
			return;
		}
		MessageData data = event.getMessageData();
		// String messages = chatWindow.getChatAndMessagesTP().getText();
		// chatWindow.getChatAndMessagesTP().setText(
		// messages + data.getAuthor() + " > " + data.getMessage() + "\n");

	}

	public void setChatWindow(ChatWindow chatWindow) {
		this.chatWindow = chatWindow;
	}

}
