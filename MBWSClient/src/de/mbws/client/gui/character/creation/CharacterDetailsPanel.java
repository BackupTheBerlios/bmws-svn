package de.mbws.client.gui.character.creation;

import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.List;

import javax.swing.*;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import de.mbws.client.ConfigurationData;
import de.mbws.client.ValueMapper;
import de.mbws.client.data.ClientGlobals;
import de.mbws.client.data.Race;
import de.mbws.client.state.handler.CharacterCreationStateHandler;

public class CharacterDetailsPanel extends JPanel  {

	private JLabel nameLb;
	private JLabel raceLb;
	private JLabel genderLb;
	private JToggleButton genderMBtn;
	private JToggleButton genderFBtn;
	private JComboBox raceCb;
	private JTextField nameTf;
	private JLabel classLb;
	private JComboBox classCb;
	private JButton createBtn;
	private JButton cancelBtn;
	private JTextArea raceInfoTa;
	private JTextArea classInfoTa;
	private CharacterCreationStateHandler inputHandler;

	/**
	 * Test-Constructor.
	 */
	public CharacterDetailsPanel() {
		this.initialize();
	}

	public CharacterDetailsPanel(CharacterCreationStateHandler handler) {
		inputHandler = handler;
		inputHandler.setControlledPanel(this);
		this.initialize();

	}

	/**
	 * Initialize method.
	 */
	private void initialize() {
		// setBackground(null);
		createLabels();
		createTextFields();
		createButtons();
		createComboBoxes();

		String rowDef = "20dlu,p,20dlu,p,20dlu,p,20dlu,p,20dlu,p,20dlu,p,20dlu,p,20dlu,p,20dlu,p,20dlu,p,20dlu,p,20dlu,p";
		String colDef = "2dlu,left:40dlu,2dlu,left:20dlu,2dlu,right:20dlu,2dlu,right:20dlu,2dlu,right:20dlu,2dlu,right:20dlu,2dlu";

		FormLayout layout = new FormLayout(colDef, rowDef);
		this.setLayout(layout);

		CellConstraints cons = new CellConstraints();

		this.add(nameLb, cons.xywh(2, 1, 1, 1));
		this.add(raceLb, cons.xywh(2, 3, 1, 1));
		this.add(genderLb, cons.xywh(2, 7, 1, 1));
		this.add(genderMBtn, cons.xywh(4, 7, 1, 2));
		this.add(genderFBtn, cons.xywh(6, 7, 1, 2));
		this.add(raceCb, cons.xywh(4, 3, 5, 1));
		this.add(nameTf, cons.xywh(4, 1, 5, 1));
		this.add(classLb, cons.xywh(2, 5, 1, 1));
		this.add(classCb, cons.xywh(4, 5, 5, 1));
		this.add(createBtn, cons.xywh(10, 21, 3, 1));
		this.add(cancelBtn, cons.xywh(2, 21, 2, 1));

		createAndAddTextAreas(cons);
	}

	private void createAndAddTextAreas(CellConstraints cons) {
		JLabel raceInfoLb = new JLabel("tt");// raceLb.getText());
		JLabel classInfoLb = new JLabel(classLb.getText());

		raceInfoTa = new JTextArea();
		raceInfoTa.setEditable(false);
		raceInfoTa.setLineWrap(true);
		JScrollPane sp = new JScrollPane(raceInfoTa);

		classInfoTa = new JTextArea();
		classInfoTa.setEditable(false);
		classInfoTa.setLineWrap(true);
		JScrollPane sp2 = new JScrollPane(classInfoTa);

		this.add(raceInfoLb, cons.xywh(2, 10, 7, 4));
		this.add(classInfoLb, cons.xywh(2, 14, 7, 3));
		this.add(sp, cons.xywh(4, 10, 7, 5));
		this.add(sp2, cons.xywh(4, 14, 7, 5));
	}

	private void createComboBoxes() {
		raceCb = new JComboBox(new DefaultComboBoxModel());
		 List races = ConfigurationData.getAllRaces();
		 for (Iterator iter = races.iterator(); iter.hasNext();) {
		 Race race = (Race) iter.next();
		 ((DefaultComboBoxModel) raceCb.getModel()).addElement(race);
		 }
		 raceCb.setSelectedIndex(-1);
		 raceCb.setRenderer(new RaceRenderer());
		raceCb.addItemListener(inputHandler);

		classCb = new JComboBox(new DefaultComboBoxModel());
		// List classes = ConfigurationData.getAllClasses();
		// for (Iterator iter = races.iterator(); iter.hasNext();) {
		// CharacterClass cClass = (CharacterClass) iter.next();
		// ((DefaultComboBoxModel) classCb.getModel()).addElement(cClass);
		// }
		// classCb(-1);
		// classCb.setRenderer(new ClassRenderer());
		classCb.addActionListener(inputHandler);
		classCb.addItemListener(inputHandler);
	}

	// / if (CharacterDetailsPanel.CHARACTER_RACE_CHANGE.equals(evt
	// .getPropertyName())) {
	// if (logger.isDebugEnabled()) {
	// logger.debug(evt.getNewValue());
	// }
	// characterData.setRace(((Race) evt.getNewValue()).getId());
	// } else if (CharacterDetailsPanel.CHARACTER_GENDER_CHANGE.equals(evt
	// .getPropertyName())) {
	// characterData
	// .setGender(((Character) evt.getNewValue()).charValue());
	// } else if (CharacterDetailsPanel.CHARACTER_NAME_CHANGE.equals(evt
	// .getPropertyName())) {
	// characterData.setName((String) evt.getNewValue())
	//			
	public class RaceRenderer implements ListCellRenderer {

		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			JLabel label = new JLabel();
			if (value instanceof Race) {
				Race race = (Race) value;
				StringBuffer sb = new StringBuffer();
				sb.append(race.getName());
				label.setText(sb.toString());
				label.setHorizontalAlignment(SwingConstants.LEFT);
				// label.setBackground(buttonBackground);
			}
			return (label);
		}
	}

	private void createLabels() {
		nameLb = new JLabel();
		nameLb.setText(ValueMapper.getText(ClientGlobals.CHARACTER_NAME));
		raceLb = new JLabel();
		raceLb.setText(ValueMapper.getText(ClientGlobals.CHARACTER_RACE));
		genderLb = new JLabel();
		genderLb.setText(ValueMapper.getText(ClientGlobals.CHARACTER_GENDER));
		classLb = new JLabel();
		classLb.setText(ValueMapper.getText(ClientGlobals.CHARACTER_CLASS));
	}

	private void createTextFields() {
		nameTf = new JTextField();
		nameTf.addKeyListener(inputHandler);
	}

	public void createButtons() {
		ButtonGroup bg = new ButtonGroup();

		genderMBtn = new JToggleButton();
		genderMBtn.setIcon(new ImageIcon(
				"data/images/menu/icon_minigender_male.gif"));
		genderMBtn.setSelected(true);
		genderMBtn.addActionListener(inputHandler);

		genderFBtn = new JToggleButton();
		genderFBtn.setIcon(new ImageIcon(
				"data/images/menu/icon_minigender_female.gif"));
		genderFBtn.addActionListener(inputHandler);

		bg.add(genderMBtn);
		bg.add(genderFBtn);

		createBtn = new JButton();
		createBtn.setText(ValueMapper
				.getText(ClientGlobals.CHARACTER_CREATION_BUTTON_CREATE_CHARACTER));

		createBtn.addActionListener(inputHandler);
		createBtn.setEnabled(false);
		
		cancelBtn = new JButton();
		cancelBtn.setText(ValueMapper.getText(ClientGlobals.GENERIC_BUTTON_BACK));
		cancelBtn.addActionListener(inputHandler);
	}
	

	public JButton getCancelBtn() {
		return cancelBtn;
	}

	public JComboBox getClassCb() {
		return classCb;
	}

	public JTextArea getClassInfoTa() {
		return classInfoTa;
	}

	public JLabel getClassLb() {
		return classLb;
	}

	public JButton getCreateBtn() {
		return createBtn;
	}

	public JToggleButton getGenderFBtn() {
		return genderFBtn;
	}

	public JLabel getGenderLb() {
		return genderLb;
	}

	public JToggleButton getGenderMBtn() {
		return genderMBtn;
	}

	public JLabel getNameLb() {
		return nameLb;
	}

	public JTextField getNameTf() {
		return nameTf;
	}

	public JComboBox getRaceCb() {
		return raceCb;
	}

	public JTextArea getRaceInfoTa() {
		return raceInfoTa;
	}

	public JLabel getRaceLb() {
		return raceLb;
	}

	private CharacterCreationStateHandler getInputHandler() {
		return (CharacterCreationStateHandler) inputHandler;
	}

	/**
	 * Test main method.
	 */
	public static void main(String args[]) {

		JFrame test = new JFrame("Test for CreateCharacterDialog");
		test.setContentPane(new CharacterDetailsPanel());
		test.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		test.pack();
		test.setVisible(true);
	}

}
// import java.awt.BorderLayout;
// import java.awt.Color;
// import java.awt.Component;
// import java.awt.event.FocusEvent;
// import java.awt.event.FocusListener;
// import java.awt.event.ItemEvent;
// import java.awt.event.ItemListener;
// import java.beans.PropertyChangeEvent;
// import java.beans.PropertyChangeListener;
// import java.util.Iterator;
// import java.util.List;
//
// import javax.swing.*;
// import javax.swing.border.BevelBorder;
// import javax.swing.event.ChangeEvent;
// import javax.swing.event.ChangeListener;
//
// import org.apache.log4j.Logger;
//
// import se.datadosen.component.RiverLayout;
// import de.mbws.client.ConfigurationData;
// import de.mbws.client.ValueMapper;
// import de.mbws.client.data.ClientGlobals;
// import de.mbws.client.data.Race;
// import de.mbws.client.state.handler.BaseInputHandler;
// import de.mbws.client.state.handler.CharacterSelectionStateHandler;
// import de.mbws.common.Globals;
//
// /**
// * Description:
// *
// * @author Azarai
// */
// public class CharacterDetailsPanel extends JPanel implements
// PropertyChangeListener {
//
// private static Logger logger = Logger.getLogger(CharacterDetailsPanel.class);
//
// private final static Color buttonBackground = new Color(178, 20, 0); // new
//
// // Color(252,
// // 255,
// // 201);
//
// private JLabel headerLabel = null;
//
// private JPanel headerPanel = null;
//
// BaseInputHandler inputHandler;
//
// private JPanel contentPanel = null;
//
// private JLabel nameLabel = null;
//
// private JTextField nameTextField = null;
//
// private JLabel genderLabel = null;
//
// private JCheckBox genderCheckbox = null;
//
// private JLabel raceLabel = null;
//
// private JComboBox raceComboBox = null;
//
// private JComboBox classTemplateComboBox = null;
//
// private JLabel classTemplateLabel = null;
//
// private JTextArea infoTextArea = null;
//
// public static final String CHARACTER_GENDER_CHANGE = "characterGenderChange";
//
// public static final String CHARACTER_RACE_CHANGE = "characterRaceChange";
//
// public static final String CHARACTER_CLASSTEMPLATE_CHANGE =
// "characterClassTemplateChange";
// public static final String CHARACTER_NAME_CHANGE = "characterNameChange";
//
// /**
// * This is the default constructor
// */
// public CharacterDetailsPanel(BaseInputHandler inputHandler) {
// super();
// this.inputHandler = inputHandler;
// initialize();
//
// }
//
// /**
// * This method initializes this
// *
// * @return void
// */
// private void initialize() {
//
// this.setSize(150, 160);
// this.setLayout(new BorderLayout());
// this.setBackground(new Color(0, 0, 0, 0.2f));
// this.setBorder(new BevelBorder(BevelBorder.RAISED));
// this.add(getHeaderPanel(), java.awt.BorderLayout.NORTH);
// this.add(getContentPanel(), java.awt.BorderLayout.CENTER);
// this.addPropertyChangeListener(CHARACTER_GENDER_CHANGE, this);
// this.addPropertyChangeListener(CHARACTER_RACE_CHANGE, this);
// }
//
// /**
// * This method initializes headerPanel
// *
// * @return javax.swing.JPanel
// */
// private JPanel getHeaderPanel() {
// if (headerPanel == null) {
// headerPanel = new JPanel();
// headerPanel.setBackground(new Color(0, 0, 0, 0.6f));
// headerLabel = new JLabel();
// headerLabel.setForeground(Color.WHITE);
// headerLabel.setText(ValueMapper.getText(ClientGlobals.CHARACTER_SELECTION_LABEL_MENUHEADER));
// headerPanel.add(headerLabel);
// }
// return headerPanel;
// }
//
// private CharacterSelectionStateHandler getInputHandler() {
// return (CharacterSelectionStateHandler) inputHandler;
// }
//
// /**
// * This method initializes contentPanel
// *
// * @return javax.swing.JPanel
// */
// private JPanel getContentPanel() {
// if (contentPanel == null) {
// contentPanel = new JPanel();
// contentPanel.setBackground(new Color(0, 0, 0, 0.2f));
// contentPanel.setLayout(new RiverLayout());
// nameLabel = new JLabel();
// nameLabel.setText("Name");
// genderLabel = new JLabel();
// genderLabel.setText("Gender");
// raceLabel = new JLabel();
// raceLabel.setText("Race");
// classTemplateLabel = new JLabel();
// classTemplateLabel.setText("Class Template");
// contentPanel.add("p left", nameLabel);
// contentPanel.add("tab hfill", getNameTextField());
// contentPanel.add("br", genderLabel);
// contentPanel.add("tab", getGenderToggleButton());
// contentPanel.add("br", raceLabel);
// contentPanel.add("tab hfill", getRaceComboBox());
// contentPanel.add("br", classTemplateLabel);
// contentPanel.add("tab hfill", getClassTemplateComboBox());
// contentPanel.add("br hfill vfill", getInfoTextArea());
// }
// return contentPanel;
// }
//
// /**
// * This method initializes nameTextField
// *
// * @return javax.swing.JTextField
// */
// private JTextField getNameTextField() {
// if (nameTextField == null) {
// nameTextField = new JTextField();
// nameTextField.setInputVerifier(new InputVerifier() {
//
// @Override
// public boolean verify(JComponent input) {
// // TODO Auto-generated method stub
// return false;
// }
//
// @Override
// public boolean shouldYieldFocus(JComponent input) {
// boolean isValid = super.shouldYieldFocus(input);
// if (isValid) {
// nameLabel.setForeground(Color.WHITE);
// nameTextField.setBackground(null);
// } else {
// nameLabel.setForeground(Color.red);
// nameTextField.setBackground(Color.red);
// }
// return true;
// }
//
// });
// //just for testing purpose
// nameTextField.addFocusListener(new FocusListener() {
//            
// public void focusLost(FocusEvent e) {
// String name = getNameTextField().getText();
// if (name != null && !"".equals(name)) {
// firePropertyChange(CHARACTER_NAME_CHANGE, null, name);
// }
// }
//            
// public void focusGained(FocusEvent e) {
// }
//            
// });
// }
// return nameTextField;
// }
//
// /**
// * This method initializes genderToggleButton
// *
// * @return javax.swing.JToggleButton
// */
// private JCheckBox getGenderToggleButton() {
// if (genderCheckbox == null) {
// genderCheckbox = new JCheckBox();
// genderCheckbox.setIcon(new
// ImageIcon("data/images/menu/icon_minigender_male.gif"));
// genderCheckbox.setSelectedIcon(new
// ImageIcon("data/images/menu/icon_minigender_female.gif"));
// genderCheckbox.setContentAreaFilled(false);
// genderCheckbox.setBorder(null);
// genderCheckbox.addChangeListener(new ChangeListener() {
// private boolean oldState = false;
//
// public void stateChanged(ChangeEvent event) {
// if (oldState != genderCheckbox.isSelected()) {
// oldState = genderCheckbox.isSelected();
// if (genderCheckbox.isSelected()) {
// firePropertyChange(CHARACTER_GENDER_CHANGE, null, Globals.GENDER_FEMALE);
// } else {
// firePropertyChange(CHARACTER_GENDER_CHANGE, null, Globals.GENDER_MALE);
// }
// }
// }
// });
// }
// return genderCheckbox;
// }
//
// /**
// * This method initializes raceComboBox
// *
// * @return javax.swing.JComboBox
// */
// private JComboBox getRaceComboBox() {
// if (raceComboBox == null) {
// DefaultComboBoxModel model = new DefaultComboBoxModel();
// raceComboBox = new JComboBox(model);
//
// // TODO fixme
// List races = ConfigurationData.getAllRaces();
// for (Iterator iter = races.iterator(); iter.hasNext();) {
// Race race = (Race) iter.next();
// ((DefaultComboBoxModel) raceComboBox.getModel()).addElement(race);
// }
// raceComboBox.setSelectedIndex(-1);
// raceComboBox.setRenderer(new RaceRenderer());
// raceComboBox.addItemListener(new ItemListener() {
//
// public void itemStateChanged(ItemEvent event) {
// if (event.getStateChange() == 1) {
// Race race = (Race) event.getItem();
// firePropertyChange(CHARACTER_RACE_CHANGE, null, race);
// }
// }
// });
// }
// return raceComboBox;
// }
//
// public class RaceRenderer implements ListCellRenderer {
//
// public Component getListCellRendererComponent(JList list, Object value, int
// index, boolean isSelected, boolean cellHasFocus) {
// JLabel label = new JLabel();
// if (value instanceof Race) {
// Race race = (Race) value;
// StringBuffer sb = new StringBuffer();
// sb.append(race.getName());
// label.setText(sb.toString());
// label.setHorizontalAlignment(SwingConstants.LEFT);
// label.setBackground(buttonBackground);
// }
// return (label);
// }
// }
//
// /**
// * This method initializes classTemplateComboBox
// *
// * @return javax.swing.JComboBox
// */
// private JComboBox getClassTemplateComboBox() {
// if (classTemplateComboBox == null) {
// classTemplateComboBox = new JComboBox();
// }
// return classTemplateComboBox;
// }
//
// /**
// * This method initializes infoTextArea
// *
// * @return javax.swing.JTextArea
// */
// private JTextArea getInfoTextArea() {
// if (infoTextArea == null) {
// infoTextArea = new JTextArea();
// infoTextArea.setEditable(false);
// infoTextArea.setLineWrap(true);
// }
// return infoTextArea;
// }
//
// public void propertyChange(PropertyChangeEvent evt) {
//
// if (CHARACTER_RACE_CHANGE.equals(evt.getPropertyName())) {
// if (logger.isDebugEnabled()) {
// logger.debug(evt.getNewValue());
// }
// getInfoTextArea().setText(((Race) evt.getNewValue()).getDescription());
// getParent().repaint();
// }
// }
//
// } // @jve:decl-index=0:visual-constraint="10,10"
