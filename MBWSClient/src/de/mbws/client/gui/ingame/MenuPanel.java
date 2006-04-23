package de.mbws.client.gui.ingame;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.button.ClassicButtonShaper;
import org.jvnet.substance.theme.SubstanceEbonyTheme;

import de.mbws.client.ValueMapper;

public class MenuPanel extends JPanel implements ActionListener {

	private final static String CHAT_COMMAND = "chat";
	private final static String ACTIONPANEL_COMMAND = "actionpanel";
	private final static String GROUP_COMMAND = "groupstatsframe.title";
	private JButton menuBtn;
	private GameDesktop gd;
	public MenuPanel(GameDesktop gameDesktop) {
		super();
		gd = gameDesktop;
		initialize();
		setSize(80,30);
	}

	private void initialize() {
		
		final JPopupMenu menu = new JPopupMenu();
    
    // Create and add a menu item
    JMenuItem item = new JMenuItem(ValueMapper.getText(CHAT_COMMAND));
    //item.setAccelerator("c");
    item.setActionCommand(CHAT_COMMAND);
    item.addActionListener(this);
    menu.add(item);
    
    item = new JMenuItem(ValueMapper.getText(ACTIONPANEL_COMMAND));
    //item.setAccelerator(".");
    item.setActionCommand(ACTIONPANEL_COMMAND);
    item.addActionListener(this);
    menu.add(item);
    
    item = new JMenuItem(ValueMapper.getText(GROUP_COMMAND));
    //item.setAccelerator(".");
    item.setActionCommand(GROUP_COMMAND);
    item.addActionListener(this);
    menu.add(item);
    
    
		menuBtn = new JButton(ValueMapper.getText("menu"));   
   
    menuBtn.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent evt) {
//          if (evt.isPopupTrigger()) {
//              menu.show(evt.getComponent(), evt.getX(), evt.getY());
//          }
      }
      public void mouseReleased(MouseEvent evt) {
        //  if (evt.isPopupTrigger()) {
              menu.show(evt.getComponent(), evt.getX(), evt.getY());
         // }
      }
  });
    add(menuBtn);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SubstanceLookAndFeel slf = new SubstanceLookAndFeel();

		SubstanceLookAndFeel.setCurrentButtonShaper(new ClassicButtonShaper());

		try {
			UIManager.setLookAndFeel(slf);
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// MetalLookAndFeel());
		SubstanceLookAndFeel.setCurrentTheme(new SubstanceEbonyTheme());
		JFrame frame = new JFrame();
		frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// StatsFrame
		frame.add(new MenuPanel(null));
		frame.pack();
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(CHAT_COMMAND)) {
			gd.getChatWindow().setVisible(!gd.getChatWindow().isVisible());
		} else if (e.getActionCommand().equals(GROUP_COMMAND)) {
			gd.getGroupWindow().setVisible(!gd.getGroupWindow().isVisible());
		} else if (e.getActionCommand().equals(ACTIONPANEL_COMMAND)) {
			gd.getChatWindow().setVisible(!gd.getChatWindow().isVisible());
		}
		
	}
}
