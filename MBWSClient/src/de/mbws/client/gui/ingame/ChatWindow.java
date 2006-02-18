package de.mbws.client.gui.ingame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.HTMLDocument;

import org.apache.log4j.Logger;

import de.mbws.client.controller.ChatController;
import de.mbws.client.controller.ClientNetworkController;
import de.mbws.common.events.EventTypes;

public class ChatWindow extends JInternalFrame {

	private static Logger logger = Logger.getLogger(ChatWindow.class);

	private StyledDocument allMessages;// = new HTMLDocument();
	private StyledDocument groupMessages = new HTMLDocument();
	private StyledDocument privateMessages = new HTMLDocument();
	private StyledDocument consoleMessages = new HTMLDocument();
	private JTextField chatTf;

	private JTextPane messagesTP;
	private JTextPane groupMessagesTP;
	private JTextPane privateMessagesTP;
	private JTextPane consoleMessagesTP;

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
		tp.addTab("Private Message", getPrivateMessagesPanel());
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

	private JScrollPane getAllMessagesPanel() {
		messagesTP = new JTextPane();
		allMessages = messagesTP.getStyledDocument();
		GlobalStyleSheet.getInstance().setupStyles(allMessages);
		messagesTP.setEditable(false);
		return addToScrollPane(messagesTP);
	}

	private JScrollPane getGroupMessagesPanel() {
		groupMessagesTP = new JTextPane();
		groupMessages = groupMessagesTP.getStyledDocument();
		GlobalStyleSheet.getInstance().setupStyles(groupMessages);
		groupMessagesTP.setEditable(false);
		return addToScrollPane(groupMessagesTP);
	}

	private JScrollPane getPrivateMessagesPanel() {
		privateMessagesTP = new JTextPane();
		StyledDocument doc = privateMessagesTP.getStyledDocument();
		GlobalStyleSheet.getInstance().setupStyles(doc);
		privateMessagesTP.setEditable(false);
		return addToScrollPane(privateMessagesTP);
	}

	private JScrollPane getConsoleMessagesPanel() {
		consoleMessagesTP = new JTextPane();
		StyledDocument doc = consoleMessagesTP.getStyledDocument();
		GlobalStyleSheet.getInstance().setupStyles(doc);
		consoleMessagesTP.setEditable(false);
		return addToScrollPane(consoleMessagesTP);
	}

	private JScrollPane addToScrollPane(JComponent c) {
		final JScrollPane pane = new JScrollPane();
		pane.getViewport().add(c);
		pane.revalidate();
		// TODO:See if we can get rid of that
		// note: the listener added here is only a fix for JDK1.4 - when your
		// app is Java5 you don't need that one
		pane.getViewport().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				pane.getViewport().repaint();
			}
		});
		return pane;
	}

	public void parseChat(String text) {
		switch (text.charAt(0)) {
		case '/':
			logger.info("Command issued");
			onCommand(text.substring(1));
			break;
		default:
			if (text.length() > 0) {
				logger.info("Saying: " + text);
				updateMessages(EventTypes.CHAT_SAY, text);
				ClientNetworkController.getInstance().handleOutgoingEvent(
						ChatController.getInstance().createChatEvent(text,
								EventTypes.CHAT_SAY, ""));
			}
		}
	}

	private void onCommand(String command) {
		String text = command.substring(command.indexOf(" ") + 1);
		if (command.startsWith("msg ")) {
			logger.info("private message: " + text);
			updateMessages(EventTypes.CHAT_PM, text);
			int indexForMessage = text.indexOf(" ");
			if (indexForMessage > 1) {
				String realMessage = text.substring(indexForMessage + 1);
				String recipient = text.substring(0, indexForMessage);
				logger.info("recipient: " + recipient);
				logger.info("message: " + realMessage);
				ClientNetworkController.getInstance().handleOutgoingEvent(
						ChatController.getInstance().createChatEvent(
								realMessage, EventTypes.CHAT_PM, recipient));
			} else {
				logger.warn("private msg without recipient");
			}
		} else if (command.startsWith("admin ")) {
			logger.info("Admin Command: " + text);
			updateMessages(EventTypes.CHAT_ADMIN_COMMAND, text);
			ClientNetworkController.getInstance().handleOutgoingEvent(
					ChatController.getInstance().createChatEvent(text,
							EventTypes.CHAT_ADMIN_COMMAND, ""));
		} else if (command.startsWith("grp ")) {
			logger.info("Group message: " + text);
			updateMessages(EventTypes.CHAT_GROUP_SAY, text);
			ClientNetworkController.getInstance().handleOutgoingEvent(
					ChatController.getInstance().createChatEvent(text,
							EventTypes.CHAT_GROUP_SAY, ""));
		} else {
			logger.warn("Message unknown: " + text);
		}
	}

	public void updateMessages(int messageType, String message) {
		if (messageType == EventTypes.CHAT_SAY) {
			addMessage(messagesTP, message, GlobalStyleSheet.ALL);
		} else if (messageType == EventTypes.CHAT_PM) {
			addMessage(messagesTP, message, GlobalStyleSheet.PM);
			addMessage(privateMessagesTP, message, GlobalStyleSheet.PM);
		} else if (messageType == EventTypes.CHAT_ADMIN_COMMAND) {
			addMessage(messagesTP, message, GlobalStyleSheet.ADMIN);
			addMessage(consoleMessagesTP, message, GlobalStyleSheet.ADMIN);
		} else if (messageType == EventTypes.CHAT_GROUP_SAY) {
			addMessage(messagesTP, message, GlobalStyleSheet.GROUP);
			addMessage(groupMessagesTP, message, GlobalStyleSheet.GROUP);
		}
	}

	public void addMessage(JTextPane textPane, String text, String attribute) {
		try {
			StyledDocument doc = textPane.getStyledDocument();
			doc.insertString(doc.getEndPosition().getOffset(), text + "\n", doc
					.getStyle(attribute));
			textPane.setCaretPosition(doc.getEndPosition().getOffset() - 1);

		} catch (Exception e) {
			logger.error("addMessage()", e);
		}
	}

}
