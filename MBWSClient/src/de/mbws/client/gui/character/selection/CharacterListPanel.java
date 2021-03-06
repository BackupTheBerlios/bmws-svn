package de.mbws.client.gui.character.selection;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;

import de.mbws.client.ValueMapper;
import de.mbws.client.data.ClientGlobals;
import de.mbws.client.state.handler.BaseInputHandler;
import de.mbws.client.state.handler.CharacterSelectionStateHandler;
import de.mbws.common.events.data.generated.PlayerCharacterData;


/**
 * Description:
 * 
 * @author Azarai
 */
public class CharacterListPanel extends JPanel implements
		PropertyChangeListener {

	private static Logger logger = Logger.getLogger(CharacterListPanel.class);
	public static final String CHARACTER_SELECTION_CHANGE = "characterSelectionChange";

	private final static Color buttonBackground = new Color(178, 20, 0); // new
																			// Color(252,
																			// 255,
																			// 201);

	List<PlayerCharacterData> allCharacters = null;

	private JList characterList = null;

	private JPanel buttonPanel = null;

	private JButton createButton = null;

	private JButton deleteButton = null;
	PlayerCharacterData selectedCharacter = null;

	private JLabel headerLabel = null;

	private JPanel headerPanel = null;
	BaseInputHandler inputHandler;

	/**
	 * This is the default constructor
	 */
	public CharacterListPanel(List<PlayerCharacterData> allCharacters,
			BaseInputHandler inputHandler) {
		super();
		this.inputHandler = inputHandler;
		this.allCharacters = allCharacters;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {

		this.setSize(150, 60);
		this.setLayout(new BorderLayout());
		this.setBackground(new Color(0, 0, 0, 0.2f));
		this.setBorder(new BevelBorder(BevelBorder.RAISED));
		this.add(getCharacterList(), BorderLayout.CENTER);
		this.add(getButtonPanel(), BorderLayout.SOUTH);
		this.add(getHeaderPanel(), java.awt.BorderLayout.NORTH);
		addPropertyChangeListener(
				CharacterListPanel.CHARACTER_SELECTION_CHANGE, this);
		for (Iterator iter = allCharacters.iterator(); iter.hasNext();) {
			PlayerCharacterData character = (PlayerCharacterData) iter.next();
			((DefaultListModel) getCharacterList().getModel())
					.addElement(character);
		}
	}

	/**
	 * This method initializes characterList
	 * 
	 * @return javax.swing.JList
	 */
	private JList getCharacterList() {
		if (characterList == null) {
			DefaultListModel model = new DefaultListModel();
			characterList = new JList(model);
			characterList.setOpaque(false);
			characterList.setCellRenderer(new CharacterRenderer());
			characterList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			characterList.addListSelectionListener(new ListSelectionListener() {

				public void valueChanged(ListSelectionEvent event) {
					if (!event.getValueIsAdjusting()) {
						logger.info(characterList.getSelectedValue());
						firePropertyChange(CHARACTER_SELECTION_CHANGE, null,
								characterList.getSelectedValue());
					}
				}
			});
		}
		return characterList;
	}

	public class CharacterRenderer extends DefaultListCellRenderer {
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean hasFocus) {
			JButton label = new JButton();
			if (value instanceof PlayerCharacterData) {
				PlayerCharacterData character = (PlayerCharacterData) value;
				StringBuffer sb = new StringBuffer();
				sb.append("<html>");
				sb.append(character.getName());
				sb.append("<br>");
				sb.append(character.getLocationdescription());
				sb.append("</html>");
				label.setText(sb.toString());
				label.setHorizontalAlignment(SwingConstants.LEFT);
				label.setBackground(buttonBackground);
			}
			return (label);
		}
	}

	/**
	 * This method initializes listPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getButtonPanel() {
		if (buttonPanel == null) {
			buttonPanel = new JPanel();
			buttonPanel.setBackground(new Color(0, 0, 0, 0.4f));
			buttonPanel.setLayout(new FlowLayout());
			buttonPanel.add(getCreateButton(), null);
			buttonPanel.add(getDeleteButton(), null);
		}
		return buttonPanel;
	}

	/**
	 * This method initializes createButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getCreateButton() {
		if (createButton == null) {
			createButton = new JButton();
			createButton
					.setText(ValueMapper
							.getText(ClientGlobals.CHARACTER_SELECTION_BUTTON_CREATE_CHARACTER));
			createButton.setBackground(buttonBackground);
			createButton.setSize(createButton.getPreferredSize());
			createButton.addActionListener(new ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					getInputHandler().requestStateSwitch(
							BaseInputHandler.GAMESTATE_CHARACTER_CREATION);
				}
			});
		}
		return createButton;
	}

	/**
	 * This method initializes deleteButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getDeleteButton() {
		if (deleteButton == null) {
			deleteButton = new JButton();
			deleteButton.setEnabled(false);
			deleteButton
					.setText(ValueMapper
							.getText(ClientGlobals.CHARACTER_SELECTION_BUTTON_DELETE_CHARACTER));
			deleteButton.setBackground(buttonBackground);
			deleteButton.setSize(deleteButton.getPreferredSize());
			deleteButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					logger
							.info("Character deletion request... add confirm box");
				}
			});
		}
		return deleteButton;
	}

	public void propertyChange(PropertyChangeEvent evt) {
		selectedCharacter = (PlayerCharacterData) evt.getNewValue();
		deleteButton.setEnabled(true);
		getParent().repaint();
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
			headerLabel
					.setText(ValueMapper
							.getText(ClientGlobals.CHARACTER_SELECTION_LABEL_MENUHEADER));
			headerPanel.add(headerLabel);
		}
		return headerPanel;
	}

	private CharacterSelectionStateHandler getInputHandler() {
		return (CharacterSelectionStateHandler) inputHandler;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
