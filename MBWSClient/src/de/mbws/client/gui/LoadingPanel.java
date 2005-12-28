package de.mbws.client.gui;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import de.mbws.client.ValueMapper;
import de.mbws.client.data.ClientGlobals;

/**
 * Description: 
 * @author Azarai
 *
 */
public class LoadingPanel extends JPanel {

    private JLabel loadingLabel = null;

    /**
     * This is the default constructor
     */
    public LoadingPanel() {
        super();
        initialize();
    }

    /**
     * This method initializes this
     * 
     */
    private void initialize() {
        this.setSize(150, 100);
        this.setLayout(null);
        this.setBackground(new Color(0, 0, 0, 0.2f));
        ImageIcon ii = new ImageIcon("data/images/loadingAnimation.gif");
        loadingLabel = new JLabel(ii);
        loadingLabel.setFont(loadingLabel.getFont().deriveFont(20F));
        loadingLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        loadingLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
        loadingLabel.setText(ValueMapper.getText(ClientGlobals.LOADING_SCREEN_LABEL) + "...");
        loadingLabel.setSize(this.getSize());
        loadingLabel.setLocation(0,0);
        this.add(loadingLabel, null);
    }

}  //  @jve:decl-index=0:visual-constraint="10,10"
