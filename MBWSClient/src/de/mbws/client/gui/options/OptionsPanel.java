package de.mbws.client.gui.options;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import org.apache.log4j.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import se.datadosen.component.RiverLayout;

import com.jme.input.InputHandler;

import de.mbws.client.MBWSClient;
import de.mbws.client.ValueMapper;
import de.mbws.client.data.ClientGlobals;
import de.mbws.client.state.handler.MainMenuHandler;

public class OptionsPanel extends JPanel {

	private static Logger logger = Logger.getLogger(OptionsPanel.class);

	// TODO: is the inputhandler needed ?
	private InputHandler inputHandler;

	private DisplayMode[] modes = new DisplayMode[0];
	private JCheckBox enableSoundEffects = new JCheckBox(ValueMapper
			.getText(ClientGlobals.OPTIONS_ENABLE_SOUND));
	private JCheckBox enableMusic = new JCheckBox(ValueMapper
			.getText(ClientGlobals.OPTIONS_ENABLE_MUSIC));

	private JCheckBox useDefaultAccount = new JCheckBox(ValueMapper
			.getText(ClientGlobals.OPTIONS_STORE_ACCOUNT));
	private JTextField accountName = new JTextField();
	private JTextField accountPass = new JTextField();

	private JCheckBox fullScreen;
	private JComboBox resolution;
	private JComboBox colorDepth;
	private JComboBox frequency;
	private JComboBox renderer;

	private JButton cancelButton;

	/**
	 * This is the default constructor
	 */
	public OptionsPanel(InputHandler inputHandler) {
		super();
		this.inputHandler = inputHandler;
		try {
			DisplayMode[] allModes = Display.getAvailableDisplayModes();
			sortAndSetModes(allModes);
		} catch (LWJGLException e) {
			logger.error("Exception trying to get the displaymodes: ", e);
		}
		// TODO: load properties first !!
		loadProperties();
		initialize();
	}

	private void sortAndSetModes(DisplayMode[] allModes) {
		ArrayList tempModes = new ArrayList();
		for (int i = 0; i < allModes.length; i++) {
			// No modes below 16 bpp wanted
			if (allModes[i].getBitsPerPixel() < 16) {
				continue;
			}
			// No modes below a width of 600 wanted
			if (allModes[i].getWidth() < 600) {
				continue;
			}
			// No modes below a height of 480 wanted
			if (allModes[i].getHeight() < 480) {
				continue;
			}
			
			tempModes.add(allModes[i]);
		}
		modes = (DisplayMode[]) tempModes.toArray(modes);
		Arrays.sort(modes, new DisplayModeSorter());
	}

	private void initialize() {
		setLayout(new RiverLayout());
		JTabbedPane tp = new JTabbedPane();
		setSize(400, 300);
		// tp.setSize(600,300);
		// TODO: use constants for tabtitles
		tp.addTab("Video", getVideoPanel());
		tp.addTab("Audio", getAudioPanel());
		tp.addTab("Account", getAccountPanel());
		add("centered hfill vfill", tp);

		cancelButton = new JButton(ValueMapper.getText(ClientGlobals.CANCEL));
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getInputHandler().getState().removeMe(
						(JPanel) cancelButton.getParent());
			}
		});

		JButton okButton = new JButton(ValueMapper
				.getText(ClientGlobals.GENERIC_BUTTON_APPLY));
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				storeProperties();
			}
		});

		add("br", okButton);
		add("tab", cancelButton);
		setBackground(new Color(180, 180, 180));
		setBorder(new BevelBorder(BevelBorder.RAISED));
		setVisible(true);

	}

	private JPanel getAudioPanel() {
		JPanel p = getDefaultPanel();
		p.add("p left", enableSoundEffects);
		p.add("br", enableMusic);
		return p;
	}

	private JPanel getAccountPanel() {
		JLabel accountNameLb = new JLabel(ValueMapper
				.getText(ClientGlobals.MENU_LABEL_USERNAME));
		JLabel accountPassLb = new JLabel(ValueMapper
				.getText(ClientGlobals.MENU_LABEL_PASSWORD));
		accountName.setEnabled(useDefaultAccount.isSelected());
		accountPass.setEnabled(useDefaultAccount.isSelected());

		useDefaultAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accountName.setEnabled(useDefaultAccount.isSelected());
				accountPass.setEnabled(useDefaultAccount.isSelected());
			}
		});

		JPanel p = getDefaultPanel();
		p.add("p left", useDefaultAccount);
		p.add("br", accountNameLb);
		p.add("tab hfill", accountName);
		p.add("br", accountPassLb);
		p.add("tab hfill", accountPass);

		return p;
	}

	private void storeProperties() {

	}

	private void loadProperties() {

	}

	private JPanel getDefaultPanel() {
		JPanel p = new JPanel();
		p.setLayout(new RiverLayout());
		p.setSize(400, 250);
		return p;
	}

	private JPanel getVideoPanel() {
		String[] info = new String[modes.length];
		for (int i = 0; i < modes.length; i++) {
			info[i] = getResolutionEntry(modes[i]);
		}
		resolution = new JComboBox(info);
		resolution.setSelectedItem(MBWSClient.mbwsConfiguration.getInt("WIDTH",
				640)
				+ " x "
				+ MBWSClient.mbwsConfiguration.getInt("HEIGTH", 480)
				+ " - "
				+ MBWSClient.mbwsConfiguration.getInt("FREQUENCY", 60)
				+ "Hz "
				+ MBWSClient.mbwsConfiguration.getInt("DEPTH", 16)
				+ "bpp");
		resolution.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logger.info("resolution changed");
            }
        });
		JPanel p = getDefaultPanel();
		p.add("vfill",resolution);
		return p;
	}

	private String getResolutionEntry(DisplayMode mode) {
		return (mode.getWidth() + " x " + mode.getHeight() + " - "
				+ mode.getFrequency() + "Hz " + mode.getBitsPerPixel() + "bpp");
	}

	// TODO : Kerim mansour: Do we need this ?
	private MainMenuHandler getInputHandler() {
		return (MainMenuHandler) inputHandler;
	}

	/**
	 * Utility class for sorting <code>DisplayMode</code>s. Sorts by
	 * resolution, then bit depth, and then finally refresh rate. Taken directly
	 * from JME (thanks folks !)
	 */
	private class DisplayModeSorter implements Comparator {
		/**
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		public int compare(Object o1, Object o2) {
			DisplayMode a = (DisplayMode) o1;
			DisplayMode b = (DisplayMode) o2;

			// Width
			if (a.getWidth() != b.getWidth())
				return (a.getWidth() > b.getWidth()) ? 1 : -1;
			// Height
			if (a.getHeight() != b.getHeight())
				return (a.getHeight() > b.getHeight()) ? 1 : -1;
			// Bit depth
			if (a.getBitsPerPixel() != b.getBitsPerPixel())
				return (a.getBitsPerPixel() > b.getBitsPerPixel()) ? 1 : -1;
			// Refresh rate
			if (a.getFrequency() != b.getFrequency())
				return (a.getFrequency() > b.getFrequency()) ? 1 : -1;
			// All fields are equal
			return 0;
		}
	}
}
