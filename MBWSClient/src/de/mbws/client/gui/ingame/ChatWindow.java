package de.mbws.client.gui.ingame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import org.apache.log4j.Logger;

import de.mbws.client.controller.ChatController;
import de.mbws.client.controller.ClientNetworkController;
import de.mbws.common.events.EventTypes;

public class ChatWindow extends JInternalFrame {

	private static Logger logger = Logger.getLogger(ChatWindow.class);

	private String allMessages = new String();
	private String groupMessages = new String();
	private String whisperMessages = new String();
	private String consoleMessages = new String();
	private JTextField chatTf;

	public ChatWindow(String title) {
		super(title, true, true, true, true);
		add(getTabs(), BorderLayout.CENTER);
		add(createChatTf(), BorderLayout.SOUTH);
		repaint();
		revalidate();
		setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);

	}

	// TODO refcator: use only one method for the panel !
	private JTabbedPane getTabs() {
		JTabbedPane tp = new JTabbedPane();
		// TODO: use constants for tabtitles
		tp.addTab("All", getAllMessagesPanel());
		tp.addTab("Group", getGroupMessagesPanel());
		tp.addTab("Whisper", getWhisperMessagesPanel());
		tp.addTab("Console", getConsoleMessagesPanel());

		return tp;
	}

	public JTextField getChatTf() {
		return chatTf;
	}

	private JTextField createChatTf() {
		chatTf = new JTextField();
		chatTf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.info("got ActionEvent: " + e + ", getText()="
						+ chatTf.getText());
				String input = new String(chatTf.getText()); // copy the
				// string
				if (input != null && input.length() != 0) {
					parseChat(input);
					chatTf.setText("");
				}
			}
		});
		return chatTf;
	}

	private JPanel getAllMessagesPanel() {
		JPanel p = new JPanel(new BorderLayout());
		// p.setOpaque(true);
		// StyledDocument doc = new HTMLDocument();
		p.add(getTextPane(allMessages), BorderLayout.CENTER);
		return p;
	}

	private JPanel getGroupMessagesPanel() {
		JPanel p = new JPanel(new BorderLayout());
		// p.setOpaque(true);
		// StyledDocument doc = new HTMLDocument();
		p.add(getTextPane(groupMessages), BorderLayout.CENTER);
		return p;
	}

	private JPanel getWhisperMessagesPanel() {
		JPanel p = new JPanel(new BorderLayout());
		// p.setOpaque(true);
		// StyledDocument doc = new HTMLDocument();
		p.add(getTextPane(whisperMessages), BorderLayout.CENTER);
		return p;
	}

	private JPanel getConsoleMessagesPanel() {
		JPanel p = new JPanel(new BorderLayout());
		// p.setOpaque(true);
		// StyledDocument doc = new HTMLDocument();
		p.add(getTextPane(consoleMessages), BorderLayout.CENTER);
		return p;
	}

	private JTextPane getTextPane(String message) {
		JTextPane textPane = new JTextPane();
		textPane.setContentType("text/html");
		textPane.setText(message);
		textPane.setEnabled(false);
		return textPane;
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
				updateMessages(ChatController.CHATMESSAGE, text);
				ClientNetworkController.getInstance().handleOutgoingEvent(
						ChatController.getInstance().createChatEvent(text,
								EventTypes.CHAT_SAY));
			}
		}
	}

	private void updateMessages(int messageType, String message) {
		// if (messageType = ChatController.CHATMESSAGE) {
		// addMessage(allMessages, message);
		// }
	}

	// public void addMessage(String text, String attribute) {
	// StyledDocument doc = chatAndMessagesTP.getStyledDocument();
	// try {
	// doc.insertString(doc.getEndPosition().getOffset(), text + "\n", doc
	// .getStyle(attribute));
	// chatAndMessagesTP
	// .setCaretPosition(doc.getEndPosition().getOffset() - 1);
	// } catch (Exception e) {
	// logger.error("addMessage()", e);
	// }
	// }
	//
	// public JTextPane getChatAndMessagesTP() {
	// return chatAndMessagesTP;
	// }
}
