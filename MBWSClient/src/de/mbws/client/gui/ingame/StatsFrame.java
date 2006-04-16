package de.mbws.client.gui.ingame;
import java.awt.Color;
import java.awt.FlowLayout;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.*;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.button.ClassicButtonShaper;
import org.jvnet.substance.theme.SubstanceEbonyTheme;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class StatsFrame extends JInternalFrame {

	// private JLabel nameLb;
	private CharacterProgressBar lifePb;
	private CharacterProgressBar manaPb;
	private CharacterProgressBar aggressionPb;
	private CharacterProgressBar endurancePb;
	private JLabel imageLabel;
	private boolean character;

	public StatsFrame(boolean isCharacter, String name) {
		super();
		character = isCharacter;
		// initialize();
	}

	public StatsFrame(String name, int currentHealth, int maxHealth,
			int currentMana, int maxMana, int currentAggro, int maxAggro,
			int currentEndurance, int maxEndurance) {
		super(name);
		character = true;
		initialize(name, currentHealth, maxHealth, currentMana, maxMana,
				currentAggro, maxAggro, currentEndurance, maxEndurance);
		setResizable(true);
		setFrameIcon(null);

		this.setBackground(new Color(0, 0, 0, 0.2f));
		// setClosable(true);
		setVisible(true);
	}

	/**
	 * Initialize method.
	 */
	private void initialize(String name, int currentHealth, int maxHealth,
			int currentMana, int maxMana, int currentAggro, int maxAggro,
			int currentEndurance, int maxEndurance) {
		// setBackground(null);
		createAvatar(name);
		createProgressBars(currentHealth, maxHealth, currentMana, maxMana,
				currentAggro, maxAggro, currentEndurance, maxEndurance);

		String rowDef = "10dlu,p,10dlu,p,10dlu,p,10dlu,p";
		String colDef = "2dlu,left:64px,2dlu,left:80dlu:grow,2dlu";

		FormLayout layout = new FormLayout(colDef, rowDef);
		this.setLayout(layout);

		CellConstraints cons = new CellConstraints();

		this.add(imageLabel, cons.xywh(2, 1, 1, 4));
		this.add(lifePb, cons.xywh(4, 1, 1, 1));
		this.add(manaPb, cons.xywh(4, 2, 1, 1));
		if (character) {
			this.add(aggressionPb, cons.xywh(4, 3, 1, 1));
			this.add(endurancePb, cons.xywh(4, 4, 1, 1));
		}
	}

	// TODO: FIX AVATARs
	private void createAvatar(String path) {
		URL image;
		try {
			imageLabel = new JLabel();
			image = new File("./data/images/icons/EmptySlotIcon.jpg").toURL();

			ImageIcon icon = new ImageIcon(image);
			imageLabel.setIcon(icon);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void createProgressBars(int currentHealth, int maxHealth,
			int currentMana, int maxMana, int currentAggro, int maxAggro,
			int currentEndurance, int maxEndurance) {
		lifePb = new CharacterProgressBar(0, maxHealth, currentHealth,
				CharacterProgressBar.HEALTH_BAR);

		if (character) {
			manaPb = new CharacterProgressBar(0, maxMana, currentMana,
					CharacterProgressBar.MANA_BAR);
			aggressionPb = new CharacterProgressBar(0, maxAggro, currentAggro,
					CharacterProgressBar.AGRESSION_BAR);
			endurancePb = new CharacterProgressBar(0, maxEndurance, currentEndurance,
					CharacterProgressBar.ENDURANCE_BAR);
		}
	}

	// private void createLabels(String name) {
	// nameLb = new JLabel(name);
	// }

	public void setStringPainted(boolean shouldPaint) {
		lifePb.setStringPainted(shouldPaint);
		manaPb.setStringPainted(shouldPaint);
		aggressionPb.setStringPainted(shouldPaint);
		endurancePb.setStringPainted(shouldPaint);
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
		frame.add(new StatsFrame("testname", 120, 200, 10, 50, 87, 100, 100, 100));
		frame.add(new JButton("gg"));
		frame.pack();
		frame.setVisible(true);
	}

}
