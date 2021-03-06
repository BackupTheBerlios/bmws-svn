package de.mbws.client.gui.ingame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.jme.input.InputHandler;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.Node;
import com.jme.scene.state.LightState;
import com.jme.system.DisplaySystem;
import com.jmex.awt.swingui.JMEDesktop;

import de.mbws.client.ValueMapper;
import de.mbws.client.controller.ChatController;

public class GameDesktop extends Node {

	private static Logger logger = Logger.getLogger(GameDesktop.class);

	private JMEDesktop desktop;

	private ChatWindow chatWindow;
	private StatsAndInteractionPanel statsWindow;
	private GroupLifeStatsWindow groupWindow;
	private List allWindows = new ArrayList();

	private JLabel statusField;

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
		addGroupWindow();
		addMenuButton();
		setFocus("chat");
		ChatController.getInstance().setChatWindow(chatWindow);
	}

	public boolean isFocusOwner() {
		return (desktop.getFocusOwner() != null
				&& desktop.getFocusOwner() != desktop.getJDesktop() && checkFocusInWindows());
		// (desktop
		// .getFocusOwner().equals(chatWindow.getChatTf()) || desktop);
	}

	/**
	 * used to determine if the focus resides in any Window
	 * 
	 * @return
	 */
	private boolean checkFocusInWindows() {
		for (int i = 0; i < allWindows.size(); i++) {
			if (desktop.getFocusOwner().equals(allWindows)) {
				return true;
			}
		}
		return false;
	}

	public void setFocus(String field) {
		final JComponent focussed;
		if (field.equals("chat"))
			focussed = chatWindow.getChatTf();
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
		chatWindow = new ChatWindow(ValueMapper.getText("chat"));
		chatWindow.setLocation(0, desktopPane.getHeight() - 300);
		chatWindow.setPreferredSize(new Dimension(450, 230));
		chatWindow.setVisible(true);
		chatWindow.pack();
		desktopPane.add(chatWindow);
		desktopPane.repaint();
		desktopPane.revalidate();
	}

	protected void addGroupWindow() {
		JDesktopPane desktopPane = desktop.getJDesktop();
		groupWindow = new GroupLifeStatsWindow(ValueMapper
				.getText("groupstatsframe.title"), 0, new CharacterLifeInfo[0]);
		groupWindow.setLocation(desktopPane.getWidth() - 150, desktopPane
				.getHeight() - 300);
		groupWindow.setPreferredSize(new Dimension(150, 230));
		groupWindow.setVisible(true);
		groupWindow.pack();
		desktopPane.add(groupWindow);
		desktopPane.repaint();
		desktopPane.revalidate();
	}

	protected void addMenuButton() {
		JDesktopPane desktopPane = desktop.getJDesktop();
		MenuPanel mPanel = new MenuPanel(this);
		mPanel.setLocation(5, desktopPane.getHeight() - 35);// - 100);
		// mPanel.setPreferredSize(new Dimension(150, 50));
		// mPanel.setVisible(true);
		// mPanel.pack();
		desktopPane.add(mPanel, Integer.MAX_VALUE);
		desktopPane.repaint();
		desktopPane.revalidate();
	}

	protected void displayStatsAndInteractionPanel(
			CharacterLifeInfo selectedCharacter) {
		if (statsWindow != null) {
			statsWindow.setVisible(false);
			statsWindow = null;
		}
		JDesktopPane desktopPane = desktop.getJDesktop();
		statsWindow = new StatsAndInteractionPanel(selectedCharacter);
		statsWindow.setLocation(0, desktopPane.getHeight() - 300);
		statsWindow.setPreferredSize(new Dimension(450, 230));
		statsWindow.setVisible(true);
		desktopPane.add(statsWindow);
		desktopPane.repaint();
		desktopPane.revalidate();
	}

	public StatsAndInteractionPanel getStatsWindow() {
		return statsWindow;
	}

	public ChatWindow getChatWindow() {
		return chatWindow;
	}

	public JMEDesktop getDesktop() {
		return desktop;
	}

	public GroupLifeStatsWindow getGroupWindow() {
		return groupWindow;
	}

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

}