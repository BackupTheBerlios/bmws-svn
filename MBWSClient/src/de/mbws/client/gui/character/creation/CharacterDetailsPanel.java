package de.mbws.client.gui.character.creation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import se.datadosen.component.RiverLayout;
import de.mbws.client.ConfigurationData;
import de.mbws.client.ValueMapper;
import de.mbws.client.data.ClientGlobals;
import de.mbws.client.data.Race;
import de.mbws.client.state.handler.BaseInputHandler;
import de.mbws.client.state.handler.CharacterSelectionStateHandler;

/**
 * Description:
 * 
 * @author Azarai
 */
public class CharacterDetailsPanel extends JPanel implements PropertyChangeListener {

    private static Logger logger = Logger.getLogger(CharacterDetailsPanel.class);

    private final static Color buttonBackground = new Color(178, 20, 0); // new

    // Color(252,
    // 255,
    // 201);

    private JLabel headerLabel = null;

    private JPanel headerPanel = null;

    BaseInputHandler inputHandler;

    private JPanel contentPanel = null;

    private JLabel nameLabel = null;

    private JTextField nameTextField = null;

    private JLabel genderLabel = null;

    private JCheckBox genderCheckbox = null;

    private JLabel raceLabel = null;

    private JComboBox raceComboBox = null;

    private JComboBox classTemplateComboBox = null;

    private JLabel classTemplateLabel = null;

    private JTextArea infoTextArea = null;

    public static final String CHARACTER_GENDER_CHANGE = "characterGenderChange";

    public static final String CHARACTER_RACE_CHANGE = "characterRaceChange";

    public static final String CHARACTER_CLASSTEMPLATE_CHANGE = "characterClassTemplateChange";

    /**
     * This is the default constructor
     */
    public CharacterDetailsPanel(BaseInputHandler inputHandler) {
        super();
        this.inputHandler = inputHandler;
        initialize();

    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {

        this.setSize(150, 160);
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(0, 0, 0, 0.2f));
        this.setBorder(new BevelBorder(BevelBorder.RAISED));
        this.add(getHeaderPanel(), java.awt.BorderLayout.NORTH);
        this.add(getContentPanel(), java.awt.BorderLayout.CENTER);
        this.addPropertyChangeListener(CHARACTER_GENDER_CHANGE, this);
        this.addPropertyChangeListener(CHARACTER_RACE_CHANGE, this);
    }

    /**
     * This method initializes headerPanel
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getHeaderPanel() {
        if (headerPanel == null) {
            headerPanel = new JPanel();
            headerPanel.setBackground(new Color(0, 0, 0, 0.6f));
            headerLabel = new JLabel();
            headerLabel.setForeground(Color.WHITE);
            headerLabel.setText(ValueMapper.getText(ClientGlobals.CHARACTER_SELECTION_LABEL_MENUHEADER));
            headerPanel.add(headerLabel);
        }
        return headerPanel;
    }

    private CharacterSelectionStateHandler getInputHandler() {
        return (CharacterSelectionStateHandler) inputHandler;
    }

    /**
     * This method initializes contentPanel
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getContentPanel() {
        if (contentPanel == null) {
            contentPanel = new JPanel();
            contentPanel.setBackground(new Color(0, 0, 0, 0.2f));
            contentPanel.setLayout(new RiverLayout());
            nameLabel = new JLabel();
            nameLabel.setText("Name");
            genderLabel = new JLabel();
            genderLabel.setText("Gender");
            raceLabel = new JLabel();
            raceLabel.setText("Race");
            classTemplateLabel = new JLabel();
            classTemplateLabel.setText("Class Template");
            contentPanel.add("p left", nameLabel);
            contentPanel.add("tab hfill", getNameTextField());
            contentPanel.add("br", genderLabel);
            contentPanel.add("tab", getGenderToggleButton());
            contentPanel.add("br", raceLabel);
            contentPanel.add("tab hfill", getRaceComboBox());
            contentPanel.add("br", classTemplateLabel);
            contentPanel.add("tab hfill", getClassTemplateComboBox());
            contentPanel.add("br hfill vfill", getInfoTextArea());
        }
        return contentPanel;
    }

    /**
     * This method initializes nameTextField
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getNameTextField() {
        if (nameTextField == null) {
            nameTextField = new JTextField();
            nameTextField.setInputVerifier(new InputVerifier() {

                @Override
                public boolean verify(JComponent input) {
                    // TODO Auto-generated method stub
                    return false;
                }

                @Override
                public boolean shouldYieldFocus(JComponent input) {
                    boolean isValid = super.shouldYieldFocus(input);
                    if (isValid) {
                        nameLabel.setForeground(Color.WHITE);
                        nameTextField.setBackground(null);
                    } else {
                        nameLabel.setForeground(Color.red);
                        nameTextField.setBackground(Color.red);
                    }
                    return true;
                }

            });
        }
        return nameTextField;
    }

    /**
     * This method initializes genderToggleButton
     * 
     * @return javax.swing.JToggleButton
     */
    private JCheckBox getGenderToggleButton() {
        if (genderCheckbox == null) {
            genderCheckbox = new JCheckBox();
            genderCheckbox.setIcon(new ImageIcon("data/images/menu/icon_minigender_male.gif"));
            genderCheckbox.setSelectedIcon(new ImageIcon("data/images/menu/icon_minigender_female.gif"));
            genderCheckbox.setContentAreaFilled(false);
            genderCheckbox.setBorder(null);
            genderCheckbox.addChangeListener(new ChangeListener() {
                private boolean oldState = false;

                public void stateChanged(ChangeEvent event) {
                    if (oldState != genderCheckbox.isSelected()) {
                        oldState = genderCheckbox.isSelected();
                        if (genderCheckbox.isSelected()) {
                            firePropertyChange(CHARACTER_GENDER_CHANGE, null, "FEMALE");
                        } else {
                            firePropertyChange(CHARACTER_GENDER_CHANGE, null, "MALE");
                        }
                    }
                }
            });
        }
        return genderCheckbox;
    }

    /**
     * This method initializes raceComboBox
     * 
     * @return javax.swing.JComboBox
     */
    private JComboBox getRaceComboBox() {
        if (raceComboBox == null) {
            DefaultComboBoxModel model = new DefaultComboBoxModel();
            raceComboBox = new JComboBox(model);

            // TODO fixme
            List races = ConfigurationData.getAllRaces();
            for (Iterator iter = races.iterator(); iter.hasNext();) {
                Race race = (Race) iter.next();
                ((DefaultComboBoxModel) raceComboBox.getModel()).addElement(race);
            }
            raceComboBox.setSelectedIndex(-1);
            raceComboBox.setRenderer(new RaceRenderer());
            raceComboBox.addItemListener(new ItemListener() {

                public void itemStateChanged(ItemEvent event) {
                    if (event.getStateChange() == 1) {
                        Race race = (Race) event.getItem();
                        firePropertyChange(CHARACTER_RACE_CHANGE, null, race);
                    }
                }
            });
        }
        return raceComboBox;
    }

    public class RaceRenderer implements ListCellRenderer {

        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = new JLabel();
            if (value instanceof Race) {
                Race race = (Race) value;
                StringBuffer sb = new StringBuffer();
                sb.append(race.getName());
                label.setText(sb.toString());
                label.setHorizontalAlignment(SwingConstants.LEFT);
                label.setBackground(buttonBackground);
            }
            return (label);
        }
    }

    /**
     * This method initializes classTemplateComboBox
     * 
     * @return javax.swing.JComboBox
     */
    private JComboBox getClassTemplateComboBox() {
        if (classTemplateComboBox == null) {
            classTemplateComboBox = new JComboBox();
        }
        return classTemplateComboBox;
    }

    /**
     * This method initializes infoTextArea
     * 
     * @return javax.swing.JTextArea
     */
    private JTextArea getInfoTextArea() {
        if (infoTextArea == null) {
            infoTextArea = new JTextArea();
            infoTextArea.setEditable(false);
            infoTextArea.setLineWrap(true);
        }
        return infoTextArea;
    }

    public void propertyChange(PropertyChangeEvent evt) {

        if (CHARACTER_RACE_CHANGE.equals(evt.getPropertyName())) {
            if (logger.isDebugEnabled()) {
                logger.debug(evt.getNewValue());
            }
            getInfoTextArea().setText(((Race) evt.getNewValue()).getDescription());
            getParent().repaint();
        }
    }

} // @jve:decl-index=0:visual-constraint="10,10"
