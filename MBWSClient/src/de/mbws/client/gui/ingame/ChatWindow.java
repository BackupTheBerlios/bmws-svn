package de.mbws.client.gui.ingame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JEditorPane;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import org.apache.log4j.Logger;

import de.mbws.client.controller.ChatController;
import de.mbws.client.controller.ClientNetworkController;
import de.mbws.common.events.EventTypes;

public class ChatWindow extends JInternalFrame {

	private static Logger logger = Logger.getLogger(ChatWindow.class);

	private StyledDocument allMessages = new HTMLDocument();
	private StyledDocument groupMessages = new HTMLDocument();
	private StyledDocument privateMessages = new HTMLDocument();
	private StyledDocument consoleMessages = new HTMLDocument();
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

	private JPanel getAllMessagesPanel() {
		JPanel p = new JPanel(new BorderLayout());
		p.add(getTextPane(allMessages), BorderLayout.CENTER);
		return p;
	}

	private JPanel getGroupMessagesPanel() {
		JPanel p = new JPanel(new BorderLayout());
		p.add(getTextPane(groupMessages), BorderLayout.CENTER);
		return p;
	}

	private JPanel getPrivateMessagesPanel() {
		JPanel p = new JPanel(new BorderLayout());
		p.add(getTextPane(privateMessages), BorderLayout.CENTER);
		return p;
	}

	private JPanel getConsoleMessagesPanel() {
		JPanel p = new JPanel(new BorderLayout());
		p.add(getTextPane(consoleMessages), BorderLayout.CENTER);
		return p;
	}

	private JTextPane getTextPane(StyledDocument doc) {
		JTextPane textPane = new JTextPane();
		textPane.setContentType("text/html");
		GlobalStyleSheet.getInstance().setupStyles(doc);
		textPane.setStyledDocument(doc);
		((HTMLEditorKit) textPane.getEditorKit())
				.setStyleSheet(GlobalStyleSheet.getInstance());
		textPane.setEnabled(false);
		return textPane;
	}
	
	private JEditorPane getEditorPane(StyledDocument doc) {
		JEditorPane textPane = new JTextPane();
		textPane.setContentType("text/html");
		GlobalStyleSheet.getInstance().setupStyles(doc);
		textPane.setDocument(doc);
		((HTMLEditorKit) textPane.getEditorKit())
				.setStyleSheet(GlobalStyleSheet.getInstance());
		textPane.setEnabled(false);
		return textPane;
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
								EventTypes.CHAT_SAY));
			}
		}
	}

	private void onCommand(String command) {
		String text = command.substring(command.indexOf(" ")+1);
		if (command.startsWith("msg ")) {
			logger.info("private message: " + text);
			updateMessages(EventTypes.CHAT_PM, text);
			ClientNetworkController.getInstance().handleOutgoingEvent(
					ChatController.getInstance().createChatEvent(text,
							EventTypes.CHAT_PM));
		} else if (command.startsWith("admin ")) {
			logger.info("Admin Command: " + text);
			updateMessages(EventTypes.CHAT_ADMIN_COMMAND, text);
			ClientNetworkController.getInstance().handleOutgoingEvent(
					ChatController.getInstance().createChatEvent(text,
							EventTypes.CHAT_ADMIN_COMMAND));
		} else if (command.startsWith("grp ")) {
			logger.info("Group message: " + text);
			updateMessages(EventTypes.CHAT_GROUP_SAY, text);
			ClientNetworkController.getInstance().handleOutgoingEvent(
					ChatController.getInstance().createChatEvent(text,
							EventTypes.CHAT_GROUP_SAY));
		} else {
			logger.warn("Message unknown: "+text);
		}
	}

	private void updateMessages(int messageType, String message) {
		if (messageType == EventTypes.CHAT_SAY) {
			addMessage(allMessages, message, GlobalStyleSheet.ALL);
		} else if (messageType == EventTypes.CHAT_PM) {
			addMessage(allMessages, message, GlobalStyleSheet.PM);
			addMessage(privateMessages, message, GlobalStyleSheet.PM);
		} else if (messageType == EventTypes.CHAT_ADMIN_COMMAND) {
			addMessage(allMessages, message, GlobalStyleSheet.ADMIN);
			addMessage(consoleMessages, message, GlobalStyleSheet.ADMIN);
		} else if (messageType == EventTypes.CHAT_GROUP_SAY) {
			addMessage(allMessages, message, GlobalStyleSheet.GROUP);
			addMessage(groupMessages, message, GlobalStyleSheet.GROUP);
		}
	}

	public void addMessage(StyledDocument doc, String text, String attribute) {
		try {
			doc.insertString(doc.getEndPosition().getOffset(), text + "\n", doc
					.getStyle(attribute));
			// chatAndMessagesTP
			// .setCaretPosition(doc.getEndPosition().getOffset() - 1);
		} catch (Exception e) {
			logger.error("addMessage()", e);
		}
	}
	//
	// public JTextPane getChatAndMessagesTP() {
	// return chatAndMessagesTP;
	// }
}
