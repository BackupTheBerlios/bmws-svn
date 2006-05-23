package de.mbws.client.gui.ingame;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.*;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.button.ClassicButtonShaper;
import org.jvnet.substance.theme.SubstanceEbonyTheme;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class ActionPanel extends JPanel implements ActionListener {

	private JLabel imageLabel;
	private JButton[] actions = new JButton[10];
	private CellConstraints cons = new CellConstraints();
	public ActionPanel() {
		super();
		 initializeBigIcons();
		// initializeSmallIcons();
		//initializeMediumIcons();
		
		addActionAt(1,"test","testcommand");
	}

	/**
 * used to add the buttons to the panel 
 * @param slot starts with 1 and ends with 10
 * @param path
 * @param command
 */
	public void addActionAt(int slot, String path, String command) {
		JButton action = new JButton();
		action.setIcon(getIcon(path));
		action.addActionListener(this);
		action.setActionCommand(command);
		actions[slot - 1] = action;
		add(action, cons.xywh(slot*2, 1, 1, 1));
	}
	
	public void removeActionAt(int slot) {
		JButton b = actions[slot-1];
		if (b!=null) {
			remove(b);
		}
		actions[slot-1] = null;
	}

	private void initializeBigIcons() {
		createBackgroundSlot("");

		String rowDef = "64px,p";
		String colDef = "5px,left:64px,5px,left:64px,5px,left:64px,5px,left:64px,5px,left:64px,5px,left:64px,5px,left:64px,5px,left:64px,5px,left:64px,5px,left:64px,5px";

		FormLayout layout = new FormLayout(colDef, rowDef);
		setLayout(layout);
		setBackground(Color.BLACK);//(0, 0, 0, 0.2f));
		CellConstraints cons = new CellConstraints();

		for (int i = 0; i < 10; i++) {
			createBackgroundSlot("hu");
			add(imageLabel, cons.xywh(2 * (i + 1), 1, 1, 1));
		}
	}

	private void initializeSmallIcons() {
		createBackgroundSlot("");

		String rowDef = "32px,p";
		String colDef = "5px,left:32px,5px,left:32px,5px,left:32px,5px,left:32px,5px,left:32px,5px,left:32px,5px,left:32px,5px,left:32px,5px,left:32px,5px,left:32px,5px";

		FormLayout layout = new FormLayout(colDef, rowDef);
		setLayout(layout);
		setBackground(new Color(0, 0, 0, 0.2f));
	

		for (int i = 0; i < 10; i++) {
			createBackgroundSlot("hu");
			add(imageLabel, cons.xywh(2 * (i + 1), 1, 1, 1));
		}
	}

	private void initializeMediumIcons() {
		createBackgroundSlot("");

		String rowDef = "48px,p";
		String colDef = "5px,left:48px,5px,left:48px,5px,left:48px,5px,left:48px,5px,left:48px,5px,left:48px,5px,left:48px,5px,left:48px,5px,left:48px,5px,left:48px,5px";

		FormLayout layout = new FormLayout(colDef, rowDef);
		setLayout(layout);
		setBackground(new Color(0, 0, 0, 0.2f));
		

		for (int i = 0; i < 10; i++) {
			createBackgroundSlot("hu");
			this.add(imageLabel, cons.xywh(2 * (i + 1), 1, 1, 1));
		}
	}

	// TODO: FIX and replace with ONE Background
	private Icon getIcon(String path) {
		URL image;
		ImageIcon icon = null;
		try {
			image = new File("./data/images/icons/EmptySlotIcon.jpg").toURL();
			icon = new ImageIcon(image);

			// BufferedImage srcImage = new
			// BufferedImage(64,64,BufferedImage.TYPE_INT_RGB);
			// srcImage.set icon.getImage().getSource().
			// // BufferedImage srcImage = (BufferedImage) createImage(icon.getImage()
			// // .getSource());
			// BufferedImage dstImage = new BufferedImage(48, 48, srcImage.getType());
			//
			// RescaleOp rescaleOp = new RescaleOp(0.5f, 1, null);
			// rescaleOp.filter(srcImage, dstImage);
			// icon.setImage(dstImage);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return icon;
	}

	private void createBackgroundSlot(String path) {
		URL image;
		try {
			imageLabel = new JLabel();
			image = new File("./data/images/icons/EmptySlotIcon.jpg").toURL();
			ImageIcon icon = new ImageIcon(image);

			// BufferedImage srcImage = new
			// BufferedImage(64,64,BufferedImage.TYPE_INT_RGB);
			// srcImage.set icon.getImage().getSource().
			// // BufferedImage srcImage = (BufferedImage) createImage(icon.getImage()
			// // .getSource());
			// BufferedImage dstImage = new BufferedImage(48, 48, srcImage.getType());
			//
			// RescaleOp rescaleOp = new RescaleOp(0.5f, 1, null);
			// rescaleOp.filter(srcImage, dstImage);
			// icon.setImage(dstImage);
			imageLabel.setIcon(icon);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
		frame.add(new ActionPanel());
		frame.pack();
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
	System.out.println("huhu");
		
	}

}
