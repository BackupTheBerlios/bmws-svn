package de.mbws.client.gui.ingame;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.button.ClassicButtonShaper;
import org.jvnet.substance.theme.SubstanceEbonyTheme;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import de.mbws.client.ValueMapper;

public class GroupLifeStatsWindow extends JInternalFrame implements MouseListener {

	// private JLabel nameLb;
	private ArrayList<CharacterProgressBar> hitPointBars = new ArrayList<CharacterProgressBar>();
	private JButton inviteBtn;
	private JButton kickBtn;
	private JButton leaveBtn;
	private static final String INVITE = "groupstatsframe.invite";
	private static final String KICK = "groupstatsframe.kick";
	private static final String LEAVE = "groupstatsframe.leave";

	private int leader;

	public GroupLifeStatsWindow(String title,int groupLeader, CharacterLifeInfo[] groupMembers) {
		super(title);
		sortArray(groupMembers, groupLeader);
		createProgressBars(groupMembers);
		createButtons();
		initialize(groupMembers.length);
		setResizable(true);
		setFrameIcon(null);

		// this.setBackground(new Color(0, 0, 0, 0.2f));
		this.setBackground(new Color(0, 0, 0, 1f));
		setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
		setClosable(true);
//		setVisible(true);
	}

	private void createButtons() {
		inviteBtn = new JButton(ValueMapper.getText(INVITE));
		kickBtn = new JButton(ValueMapper.getText(KICK));
		leaveBtn = new JButton(ValueMapper.getText(LEAVE));
	}

	private void sortArray(CharacterLifeInfo[] groupMembers, int groupLeader) {
		if (groupLeader != 0) {
			CharacterLifeInfo temp = groupMembers[groupLeader];
			groupMembers[groupLeader] = groupMembers[0];
			groupMembers[0] = temp;
		}
		leader = 0;
	}

	/**
	 * Initialize method.
	 */
	private void initialize(int numberOfMembers) {
		StringBuffer rowDef = new StringBuffer();
		for (int i = 0; i < numberOfMembers; i++) {
			rowDef.append("20dlu,p,");
		}
		// 2 more for the 2 buttons
		rowDef.append("20dlu,p,");
		rowDef.append("20dlu,p,");
		rowDef.setLength(rowDef.length() - 1);
		String colDef = "2dlu,center:40dlu:grow,2dlu";

		FormLayout layout = new FormLayout(colDef, rowDef.toString());
		this.setLayout(layout);

		CellConstraints cons = new CellConstraints();
		int i = 0;
		for (i = 0; i < numberOfMembers; i++) {
			add(hitPointBars.get(i), cons.xywh(2, (i * 2) + 1, 1, 1));
		}
		// i+=1;
		add(inviteBtn, cons.xywh(2, (i * 2) + 1, 1, 1));
		i += 1;
		add(kickBtn, cons.xywh(2, (i * 2) + 1, 1, 1));

	}

	private void createProgressBars(CharacterLifeInfo[] groupMembers) {
		for (int i = 0; i < groupMembers.length; i++) {
			CharacterProgressBar hpPb = new CharacterProgressBar(0, groupMembers[i]
					.getMaxHitPoints(), groupMembers[i].getCurrentHitPoints(),
					CharacterProgressBar.HEALTH_BAR);
			hpPb.setString(groupMembers[i].getName());
			hpPb.setStringPainted(true);
			hpPb.setBorderPainted(false);
			hpPb.addMouseListener(this);
			hitPointBars.add(hpPb);
		}

	}
	


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SubstanceLookAndFeel slf = new SubstanceLookAndFeel();

		SubstanceLookAndFeel.setCurrentButtonShaper(new ClassicButtonShaper());
		ValueMapper v = new ValueMapper();
		try {
			UIManager.setLookAndFeel(slf);
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// MetalLookAndFeel());
		SubstanceLookAndFeel.setCurrentTheme(new SubstanceEbonyTheme());
		JFrame frame = new JFrame();
		// frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		int groupLeader = 2;
		CharacterLifeInfo[] groupMembers = new CharacterLifeInfo[3];
		CharacterLifeInfo m = new CharacterLifeInfo(CharacterLifeInfo.PLAYERCHARACTER,59,100,"test",true);
		groupMembers[0] = m;
		m = new CharacterLifeInfo(CharacterLifeInfo.PLAYERCHARACTER,49,100,"test2",true);
		groupMembers[1] = m;
		m = new CharacterLifeInfo(CharacterLifeInfo.PLAYERCHARACTER,19,120,"leader",true);
		groupMembers[2] = m;
		frame.add(new GroupLifeStatsWindow("huhu",groupLeader, groupMembers));
		// frame.add(new JButton("gg"));
		frame.setBackground(Color.BLACK);
		frame.pack();
		frame.setVisible(true);
	}

	public void mouseClicked(MouseEvent e) {
		for (int i = 0; i < hitPointBars.size(); i++) {
			hitPointBars.get(i).setBorderPainted(false);
		}
		((CharacterProgressBar) e.getSource()).setBorderPainted(true);
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

}
