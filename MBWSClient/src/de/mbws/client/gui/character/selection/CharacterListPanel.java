package de.mbws.client.gui.character.selection;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import de.mbws.common.eventdata.generated.CharacterData;

/**
 * Description:
 * 
 * @author Azarai
 */
public class CharacterListPanel extends JPanel {

    public static final String CHARACTER_SELECTION_CHANGE = "characterSelectionChange";

    private final static Color buttonBackground = new Color(252, 255, 201);

    List<CharacterData> allCharacters = null;

    private JList characterList = null;

    /**
     * This is the default constructor
     */
    public CharacterListPanel(List<CharacterData> allCharacters) {
        super();
        this.allCharacters = allCharacters;
        initialize();
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        // this.setSize(150, 60);
        this.setLayout(new GridLayout(1, 1));
        this.setBackground(new Color(0, 0, 0, 0.2f));
        this.setBorder(new BevelBorder(BevelBorder.RAISED));
        this.add(getCharacterList(), null);
        for (Iterator iter = allCharacters.iterator(); iter.hasNext();) {
            CharacterData character = (CharacterData) iter.next();
            StringBuffer sb = new StringBuffer();
            sb.append("<html>");
            sb.append(character.getName());
            sb.append("<br>");
            sb.append(character.getLocationdescription());
            sb.append("</html>");
            ((DefaultListModel) getCharacterList().getModel()).addElement(character);
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
                        System.out.println(characterList.getSelectedValue());
                        firePropertyChange(CHARACTER_SELECTION_CHANGE, null, characterList.getSelectedValue());
                    }
                }            
            });
        }
        return characterList;
    }
    public class CharacterRenderer extends DefaultListCellRenderer {
        private Hashtable iconTable = new Hashtable();

        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus) {
            JButton label = new JButton(); 
            if (value instanceof CharacterData) {
                CharacterData character = (CharacterData) value;
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
} // @jve:decl-index=0:visual-constraint="10,10"
