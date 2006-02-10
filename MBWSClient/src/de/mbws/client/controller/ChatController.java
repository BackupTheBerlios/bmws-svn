package de.mbws.client.controller;

import org.apache.log4j.Logger;

import de.mbws.client.data.ClientPlayerData;
import de.mbws.client.gui.ingame.ChatWindow;
import de.mbws.common.events.ChatEvent;
import de.mbws.common.events.data.generated.ChatData;

public class ChatController {

	private Logger logger = Logger.getLogger(ChatController.class);
	private static ChatController instance;
	private ChatWindow chatWindow;
	private String playerName = ClientPlayerData.getInstance()
			.getSelectedCharacterData().getName();

	public static final int SERVERMESSAGE = 0;
	public static final int ADMINMESSAGE = 1;
	public static final int CHATMESSAGE = 2;
	public static final int GROUPMESSAGE = 3;
	public static final int WHISPERMESSAGE = 4;

	private ChatController() {
		super();
	}

	public static ChatController getInstance() {
		if (instance == null) {
			instance = new ChatController();
		}
		return instance;
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
		if (chatWindow == null) {
			logger.error("No access to chatwindow !");
			return;
		}
		ChatData data = event.getChatData();
		// String messages = chatWindow.getChatAndMessagesTP().getText();
		// chatWindow.getChatAndMessagesTP().setText(
		// messages + data.getAuthor() + " > " + data.getMessage() + "\n");

	}

	public void setChatWindow(ChatWindow chatWindow) {
		this.chatWindow = chatWindow;
	}

}
