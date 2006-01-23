package de.mbws.client.gui.ingame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.StyledDocument;

import org.apache.log4j.Logger;

import com.jme.input.InputHandler;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.Node;
import com.jme.scene.state.LightState;
import com.jme.system.DisplaySystem;
import com.jmex.awt.swingui.JMEDesktop;

import de.mbws.client.controller.ChatController;

public class GameDesktop extends Node {

	private static Logger logger = Logger.getLogger(GameDesktop.class);
	private JMEDesktop desktop;
	private JTextField chatTF;
	private JTextPane chatAndMessagesTP;

	private JLabel statusField;

	// private int windows;
	// private static String womCSS;
	// private ChatConverter converter;
	// private ClickListener clickListener;

	public GameDesktop(String name, InputHandler input) {
		this(name, DisplaySystem.getDisplaySystem().getWidth(), DisplaySystem
				.getDisplaySystem().getHeight(), input);
	}

	public GameDesktop(String name, int w, int h, InputHandler input) {
		super(name);
		desktop = new JMEDesktop("desktop", w, h, input);
		attachChild(desktop);
		desktop.setLightCombineMode(LightState.OFF);
		desktop.getJDesktop().setBackground(new Color(1, 1, 1, 0.0f));

		// windows = 0;
		// converter = new ChatConverter();
		// womCSS = defaultCSS();

		desktop.setColorBuffer(null);
		desktop.setDefaultColor(new ColorRGBA(1, 1, 1, 0.5f));
		desktop.setRenderQueueMode(Renderer.QUEUE_ORTHO);

		desktop.getJDesktop().addComponentListener(new ComponentListener() {
			public void componentResized(ComponentEvent e) {
			}

			public void componentMoved(ComponentEvent e) {
			}

			public void componentShown(ComponentEvent e) {
			}

			public void componentHidden(ComponentEvent e) {
			}
		});
		desktop.setVBOInfo(null);
		addChatWindow();
		setFocus("chat");
		ChatController.getInstance().setGameDesktop(this);
	}

	// public boolean isFocusOwner() {
	// return (desktop.getFocusOwner() != null
	// && desktop.getFocusOwner() != desktop.getJDesktop() && desktop
	// .getFocusOwner() != chatTF);
	// }

	public void setFocus(String field) {
		final JComponent focussed;
		if (field.equals("chat"))
			focussed = chatTF;
		else
			focussed = statusField;
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					desktop.setFocusOwner(focussed);
				}
			});
		} catch (Exception e) {
			logger.error("setFocus", e);
		}
	}

	protected void addChatWindow() {
		JDesktopPane desktopPane = desktop.getJDesktop();
		JInternalFrame internalFrame = new JInternalFrame();
		internalFrame.setLayout(new BorderLayout());
		chatTF = new JTextField("huhu");
		chatTF.setPreferredSize(new Dimension(390, 20));
		chatTF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.info("got ActionEvent: " + e + ", getText()="
						+ chatTF.getText());
				String input = new String(chatTF.getText()); // copy the
				// string
				if (input != null && input.length() != 0) {
					ChatController.getInstance().parseChat(input);
					chatTF.setText("");
				}
			}
		});
		chatAndMessagesTP = new JTextPane();
		chatAndMessagesTP.setEditable(false);
		StyledDocument doc = chatAndMessagesTP.getStyledDocument();
		// setupStyles(doc);
		final JScrollPane scroller = new JScrollPane();
		scroller.getViewport().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				scroller.getViewport().repaint();
			}
		});
		scroller.getViewport().add(chatAndMessagesTP);
		internalFrame.setLocation(0, desktopPane.getHeight() - 230);
		internalFrame.add(scroller);
		internalFrame.add(chatTF, BorderLayout.SOUTH);
		internalFrame.setPreferredSize(new Dimension(450, 230));
		internalFrame.setVisible(true);
		internalFrame.setResizable(true);
		internalFrame.setIconifiable(true);
		internalFrame.pack();
		desktopPane.add(internalFrame);
		desktopPane.repaint();
		desktopPane.revalidate();
	}

	public void addMessage(String text, String attribute) {
		StyledDocument doc = chatAndMessagesTP.getStyledDocument();
		try {
			doc.insertString(doc.getEndPosition().getOffset(), text + "\n", doc
					.getStyle(attribute));
			chatAndMessagesTP
					.setCaretPosition(doc.getEndPosition().getOffset() - 1);
		} catch (Exception e) {
			logger.error("addMessage()", e);
		}
	}

	public JTextPane getChatAndMessagesTP() {
		return chatAndMessagesTP;
	}

	// private StyleSheet adaptCSS(StyleSheet css) {
	// StringReader in = new StringReader(womCSS);
	// try {
	// css.loadRules(in, null);
	// } catch (Exception e) {
	// _log.warning(e.toString());
	// }
	// return css;
	// }

	// public ClickListener getDefaultClickListener() {
	// return new ClickListener() {
	// public void onLinkClicked(String command) {
	// }
	//
	// public void onButtonClicked(String command) {
	// }
	// };
	// }

	// public void setClickListener(ClickListener listener) {
	// if (listener == null)
	// clickListener = getDefaultClickListener();
	// else
	// clickListener = listener;
	// }

	// public void setStatusMessage(String txt) {
	// statusField.setText(txt);
	// }
	//
	// protected void addStatusWindow() {
	// JDesktopPane desktopPane = desktop.getJDesktop();
	// JInternalFrame internalFrame = new JInternalFrame();
	// internalFrame.setLayout(new BorderLayout());
	// statusField = new JLabel();
	// statusField.setPreferredSize(new Dimension(350, 20));
	// internalFrame.add(statusField);
	// internalFrame.setLocation(0, desktopPane.getHeight() - 280);
	// internalFrame.setVisible(true);
	// internalFrame.setResizable(true);
	// internalFrame.setIconifiable(true);
	// internalFrame.pack();
	// desktopPane.add(internalFrame);
	// desktopPane.repaint();
	// desktopPane.revalidate();
	// }

	// private void setupStyles(StyledDocument doc) {
	// Style def = StyleContext.getDefaultStyleContext().getStyle(
	// StyleContext.DEFAULT_STYLE);
	// Style st = doc.addStyle("roman", def);
	// st = doc.addStyle("italic", def);
	// StyleConstants.setItalic(st, true);
	// st = doc.addStyle("bold", def);
	// StyleConstants.setBold(st, true);
	// st = doc.addStyle("underline", def);
	// StyleConstants.setUnderline(st, true);
	// st = doc.addStyle("say_all", def);
	// StyleConstants.setForeground(st, Color.white);
	// st = doc.addStyle("say_shout", def);
	// StyleConstants.setForeground(st, Color.orange);
	// st = doc.addStyle("say_ooc", def);
	// StyleConstants.setForeground(st, Color.pink);
	// st = doc.addStyle("say_whisper", def);
	// StyleConstants.setForeground(st, Color.magenta);
	// }

	// public void addWindow(String title, String htmlText) {
	// int x = 100 + (windows * 10);
	// int y = 100 + (windows * 10);
	// String text = converter.toHTML(htmlText);
	// if (title == null)
	// title = converter.getTitle();
	// final JDesktopPane desktopPane = desktop.getJDesktop();
	// final JInternalFrame internalFrame = new JInternalFrame(title);
	// internalFrame.setLocation(x, y);
	// internalFrame.setResizable(false);
	// internalFrame.setClosable(true);
	// JEditorPane edit = new JEditorPane();
	//
	// edit.setEditorKit(new HTMLEditorKit() {
	// private static final long serialVersionUID = -8076716694988890933L;
	//
	// public ViewFactory getViewFactory() {
	// return new HTMLEditorKit.HTMLFactory() {
	//
	// public View create(Element elem) {
	// Object o = elem.getAttributes().getAttribute(
	// StyleConstants.NameAttribute);
	// if (o instanceof HTML.Tag) {
	// HTML.Tag kind = (HTML.Tag) o;
	// if (kind == HTML.Tag.INPUT)
	// return new FormView(elem) {
	// protected void submitData(String data) {
	// AttributeSet attr = getElement()
	// .getAttributes();
	// String src = (String) attr
	// .getAttribute(HTML.Attribute.ACTION);
	// clickListener.onButtonClicked(converter
	// .decodeURL(src, data));
	// }
	//
	// protected void imageSubmit(String data) {
	// AttributeSet attr = getElement()
	// .getAttributes();
	// String src = (String) attr
	// .getAttribute(HTML.Attribute.ACTION);
	// clickListener.onButtonClicked(converter
	// .decodeURL(src, data));
	// }
	// };
	// }
	// return super.create(elem);
	// }
	// };
	// }
	// });
	// edit.setPreferredSize(new Dimension(300, 300));
	// edit.setContentType("text/html");
	// edit.setText(text);
	// edit.setEditable(false);
	// edit.setBackground(new Color(0x48, 0x51, 0x32, 0x3f));
	// // StyleSheet s = ((HTMLEditorKit) edit.getEditorKit()).getStyleSheet();
	// // ((HTMLEditorKit) edit.getEditorKit()).setStyleSheet(adaptCSS(s));
	// // edit.addHyperlinkListener(new MyLinkListener());
	// final JScrollPane scroller = new JScrollPane();
	// scroller.getViewport().addChangeListener(new ChangeListener() {
	// public void stateChanged(ChangeEvent e) {
	// scroller.getViewport().repaint();
	// }
	// });
	// JViewport vp = scroller.getViewport();
	// vp.add(edit);
	// internalFrame.add(scroller);
	// internalFrame.setVisible(true);
	// internalFrame.pack();
	// desktopPane.add(internalFrame);
	// desktopPane.repaint();
	// desktopPane.revalidate();
	// windows++;
	// }

	// class MyLinkListener implements HyperlinkListener {
	// public void hyperlinkUpdate(HyperlinkEvent e) {
	// logger.info("hyperlinkUpdate: " + e.getEventType().toString());
	// if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {
	// clickListener.onLinkClicked(e.getDescription());
	// }
	// }
	// }

	// private String defaultCSS() {
	// return
	// "body {color: #A49E84; background-color: #484132; font-size: 10pt;" +
	// " font-family: verdana,tahoma,helvetica; } " +
	// "body a:link, body a:visited, body a:active { color: yellow;
	// text-decoration: underline; }" +
	// "body a:hover { color: #FFFFFF; text-decoration: none; }" +
	// "input { color: #333333; background-color: #E7E9ED; border : 1px solid
	// #333333; }";
	// }
}