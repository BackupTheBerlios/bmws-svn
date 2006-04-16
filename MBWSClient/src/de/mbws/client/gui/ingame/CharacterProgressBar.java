package de.mbws.client.gui.ingame;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

/**
 * CharacterProgressBar keeps track of the different values of a character.
 * For PCs this namely is mana, health, agression and endurance
 * @author Kerim
 *
 */
public class CharacterProgressBar extends JProgressBar {

	private static final Color HEALTH_NORMAL = Color.RED;
	private static final Color HEALTH_POISONED = Color.GREEN;
	private static final Color HEALTH_CURSED = Color.PINK;
	private static final Color MANA = Color.BLUE;
	private static final Color AGRESSION = Color.YELLOW;
	private static final Color ENDURANCE = Color.CYAN;

	public static final int HEALTH_BAR = 0;
	public static final int MANA_BAR = 1;
	public static final int AGRESSION_BAR = 2;
	public static final int ENDURANCE_BAR = 3;

	public static final int CONDITION_HEALTHY = 0;
	public static final int CONDITION_POISONED = 1;
	public static final int CONDITION_CURSED = 2;
	

	public CharacterProgressBar(int min, int max, int value, int type) {
		this(JProgressBar.HORIZONTAL, min, max, value, type);

	}

	public void setCondition(int condition) {
		if (condition == CONDITION_CURSED) {
			setForeground(HEALTH_CURSED);
		} else if (condition == CONDITION_POISONED) {
			setForeground(HEALTH_POISONED);
		} else if (condition == CONDITION_HEALTHY) {
			setForeground(HEALTH_NORMAL);
		}
		// revalidate();
	}

	public CharacterProgressBar(int orient, int min, int max, int value, int type) {
		super(orient, min, max);
		setValue(value);
		setString(""+value+"/"+max);
		if (type == HEALTH_BAR) {
			setForeground(HEALTH_NORMAL);
		} else if (type == MANA_BAR) {
			setForeground(MANA);
		} else if (type == AGRESSION_BAR) {
			setForeground(AGRESSION);
		} else if (type == ENDURANCE_BAR) {
			setForeground(ENDURANCE);
		}
		setBackground(null);
//		setOpaque(false);

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final int max = 3;
		final CharacterProgressBar bar = new CharacterProgressBar(0, max, 0,
				CharacterProgressBar.HEALTH_BAR);
		//bar.setForeground(Color.BLACK);
		frame.getContentPane().add(bar);
		frame.pack();
		frame.setVisible(true);
		// Anzeige in Veränderung
		for (int i = 1; i <= max; i++) {
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
			}
			final int j = i;
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					bar.setValue(j);
					bar.setCondition(j - 1);
				}
			});
		}
	}

}
