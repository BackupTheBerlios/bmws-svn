package de.mbws.client.gui.options;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import org.apache.commons.configuration.PropertiesConfiguration;
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
import de.mbws.common.utils.StringUtils;

public class OptionsPanel extends JPanel {

	private static Logger logger = Logger.getLogger(OptionsPanel.class);

	
	private InputHandler inputHandler;

	private DisplayMode[] modes = new DisplayMode[0];
	private JCheckBox enableSoundEffects = new JCheckBox(ValueMapper
			.getText(ClientGlobals.OPTIONS_ENABLE_SOUND));
	private JCheckBox enableMusic = new JCheckBox(ValueMapper
			.getText(ClientGlobals.OPTIONS_ENABLE_MUSIC));
	private JCheckBox useDefaultAccount = new JCheckBox(ValueMapper
			.getText(ClientGlobals.OPTIONS_STORE_ACCOUNT));
	private JTextField accountName = new JTextField();
	private JPasswordField accountPass = new JPasswordField();
	private JCheckBox fullScreen = new JCheckBox("Fullscreen(language!)");
	private JComboBox resolutionCb;
	
	public JScrollPane pane;

	private boolean optionsHaveChanged = false;
	private boolean screenOptionsHaveChanged = false;

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
		enableSoundEffects.setSelected(MBWSClient.mbwsConfiguration.getBoolean(
				ClientGlobals.OPTIONS_ENABLE_SOUND, true));
		enableSoundEffects.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				optionsHaveChanged = true;
			}
		});
		enableMusic.setSelected(MBWSClient.mbwsConfiguration.getBoolean(
				ClientGlobals.OPTIONS_ENABLE_MUSIC, true));
		enableMusic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				optionsHaveChanged = true;
			}
		});

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
		accountName.setText(MBWSClient.mbwsConfiguration.getString(
				ClientGlobals.LOGIN, ""));
		accountPass.setText(MBWSClient.mbwsConfiguration.getString(
				ClientGlobals.PASSWORD, ""));
		if (accountName.getText().trim().equals("")) {
			accountName.setEnabled(false);
			accountPass.setEnabled(false);
			useDefaultAccount.setSelected(false);
		} else {
			useDefaultAccount.setSelected(true);
		}
		useDefaultAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accountName.setEnabled(useDefaultAccount.isSelected());
				accountPass.setEnabled(useDefaultAccount.isSelected());
				accountName.setText("");
				accountPass.setText("");
				optionsHaveChanged = true;
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
		if (optionsHaveChanged) {
			PropertiesConfiguration pc = (PropertiesConfiguration) MBWSClient.mbwsConfiguration;
			pc.setProperty(ClientGlobals.LOGIN, accountName.getText());
            try {
                pc.setProperty(ClientGlobals.PASSWORD, StringUtils.hashAndHex(accountPass.getPassword()) );    
            } catch (NoSuchAlgorithmException e) {
                logger.error("Error during building hash for password", e);
                //should alert the user somehow
            }
			pc.setProperty(ClientGlobals.OPTIONS_ENABLE_SOUND,
					enableSoundEffects.isSelected() ? "true" : "false");
			pc.setProperty(ClientGlobals.OPTIONS_ENABLE_MUSIC, enableMusic
					.isSelected() ? "true" : "false");
			int selectedResolution = resolutionCb.getSelectedIndex();
			if (selectedResolution != -1) {
				pc.setProperty(ClientGlobals.WIDTH, modes[resolutionCb
						.getSelectedIndex()].getWidth());
				pc.setProperty(ClientGlobals.HEIGHT, modes[resolutionCb
						.getSelectedIndex()].getHeight());
				pc.setProperty(ClientGlobals.DEPTH, modes[resolutionCb
						.getSelectedIndex()].getBitsPerPixel());
				pc.setProperty(ClientGlobals.FREQUENCY, modes[resolutionCb
						.getSelectedIndex()].getFrequency());
				pc.setProperty(ClientGlobals.FULLSCREEN, fullScreen
						.isSelected() ? "true" : "false");
			}
			try {
				FileWriter fw = new FileWriter(MBWSClient.propertyFile);
				pc.save(fw);
			} catch (Exception e) {
				logger.error("Error writing propertyfile: ", e);
			}
			if (screenOptionsHaveChanged) {
				// TODO: Do a reboot
				MBWSClient.exit();
			}
		}
		getInputHandler().getState()
				.removeMe((JPanel) cancelButton.getParent());
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
		resolutionCb = new JComboBox(info);
		
		resolutionCb.setSelectedIndex(getSelectedResolution());
		
		//resolutionCb.setPopupVisible(true);
		JPanel p = getDefaultPanel();
		p.add(new JLabel("Resolution(lang)"));
		p.add("tab", resolutionCb);
		p.add("br", fullScreen);
		return p;
	}

//	private JPanel getVideoPanel() {
//		String[] info = new String[modes.length];
//		for (int i = 0; i < modes.length; i++) {
//			info[i] = getResolutionEntry(modes[i]);
//		}
//		resolution = new JList(info);
//		pane = new JScrollPane(resolution);
//		resolution.setSelectedIndex(getSelectedResolution());
//		resolution.addListSelectionListener(new ListSelectionListener() {
//			public void valueChanged(ListSelectionEvent e) {
//				screenOptionsHaveChanged = true;
//				optionsHaveChanged = true;
//			}
//		});
//		//TODO:See if we can get rid of that
//		 // note: the listener added here is only a fix for JDK1.4 - when your app is Java5 you don't need that one
//		pane.getViewport().addChangeListener( new ChangeListener() {
//            public void stateChanged( ChangeEvent e ) {
//            	pane.getViewport().repaint();
//            }
//        } );
//		JPanel p = getDefaultPanel();
//		p.add(new JLabel("Resolution(lang)"));
//		p.add("tab", pane);
//		p.add("br", fullScreen);
//		return p;
//	}

	private int getSelectedResolution() {
		int width = MBWSClient.mbwsConfiguration.getInt(ClientGlobals.WIDTH,
				640);
		int height = MBWSClient.mbwsConfiguration.getInt(ClientGlobals.HEIGHT,
				480);
		int freq = MBWSClient.mbwsConfiguration.getInt(ClientGlobals.FREQUENCY,
				60);
		int depth = MBWSClient.mbwsConfiguration
				.getInt(ClientGlobals.DEPTH, 16);

		// TODO: doesnt work ! why ???
		for (int i = 0; i < modes.length; i++) {
			// System.out.println((modes[i].getWidth() + "" +
			// modes[i].getHeight()
			// + "" + modes[i].getFrequency() + "" + modes[i]
			// .getBitsPerPixel()));
			if ((modes[i].getWidth() == width)
					&& (modes[i].getHeight() == height)
					&& (modes[i].getFrequency() == freq)
					&& (modes[i].getBitsPerPixel() == depth)) {
				return i;
			}
		}
		return -1;
	}

	private String getResolutionEntry(DisplayMode mode) {
		return (mode.getWidth() + " x " + mode.getHeight() + " - "
				+ mode.getFrequency() + "Hz " + mode.getBitsPerPixel() + "bpp");
	}

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
