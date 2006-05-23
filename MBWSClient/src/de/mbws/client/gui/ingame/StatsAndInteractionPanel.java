package de.mbws.client.gui.ingame;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.button.ClassicButtonShaper;
import org.jvnet.substance.theme.SubstanceEbonyTheme;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class StatsAndInteractionPanel extends JPanel implements ActionListener {

	private CharacterProgressBar hpPb;
	private JButton tradeBtn;
	private JButton talkBtn;

	public StatsAndInteractionPanel(CharacterLifeInfo selectedCharacter) {
		super();
		initialize(selectedCharacter);
	}

	private void initialize(CharacterLifeInfo selectedCharacter) {
		int type = selectedCharacter.getType();
		String rowDef = "p,20dlu,p";
		String colDef;
		if (type == CharacterLifeInfo.PLAYERCHARACTER) {
			colDef = "2dlu,left:100dlu:grow,2dlu,left:40dlu,2dlu";
		} else if (type == CharacterLifeInfo.NPC) {
			colDef = "2dlu,left:100dlu:grow,2dlu,left:40dlu,2dlu,left:40dlu,2dlu";
		} else {
			colDef = "2dlu,left:100dlu:grow,2dlu";
		}
		FormLayout layout = new FormLayout(colDef, rowDef);
		setLayout(layout);
		setBackground(Color.BLACK);// (0, 0, 0, 0.2f));
		CellConstraints cons = new CellConstraints();
		createProgressBar(selectedCharacter);
		add(hpPb, cons.xywh(2, 2, 1, 1));
		createAllButtons();
		if (type == CharacterLifeInfo.PLAYERCHARACTER) {
			add(tradeBtn, cons.xywh(4, 2, 1, 1));
		} else if (type == CharacterLifeInfo.NPC) {
			add(tradeBtn, cons.xywh(4, 2, 1, 1));
			add(tradeBtn, cons.xywh(6, 2, 1, 1));
		}
	}

	private void createAllButtons() {
		tradeBtn = new JButton();
		tradeBtn.setText("HandelCHANGE");
		talkBtn = new JButton();
		talkBtn.setText("TALKchange");
	}

	private void createProgressBar(CharacterLifeInfo selectedCharacter) {
		hpPb = new CharacterProgressBar(0, selectedCharacter.getMaxHitPoints(),
				selectedCharacter.getCurrentHitPoints(),
				CharacterProgressBar.HEALTH_BAR);
		hpPb.setString(selectedCharacter.getName());
		hpPb.setStringPainted(selectedCharacter.isShowName());
		hpPb.setBorderPainted(true);
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
		CharacterLifeInfo c = new CharacterLifeInfo(
				CharacterLifeInfo.PLAYERCHARACTER, 80, 100, "sack", false);
		StatsAndInteractionPanel s = new StatsAndInteractionPanel(c);
		frame.add(s);

		frame.pack();
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		System.out.println("huhu");

	}

}
