package de.mbws.client.gui;

import com.jmex.bui.BButton;
import com.jmex.bui.BLookAndFeel;
import com.jmex.bui.BToggleButton;
import com.jmex.bui.background.BBackground;
import com.jmex.bui.background.TiledBackground;

/**
 * Description:
 * 
 * @author Azarai
 */
public class MenuLookAndFeel extends BLookAndFeel {

    @Override
    public BBackground createButtonBack(int state) {
        String path = "rsrc/textures/";
        int dx = 0, dy = 0;
        switch (state) {
            case BButton.DOWN:
                path += "menu_button_test_down.png";
                dx = -1;
                dy = -1;
                break;
            case BButton.OVER:
                path += "menu_button_test_over.png";
                break;
            case BButton.DISABLED:
                path += "button_disabled.png";
                break;
            case BToggleButton.SELECTED:
                path += "button_down.png";
                break;
            default:
            case BButton.UP:
                path += "menu_button_test_up.png";
                break;
        }
        return new TiledBackground(getResource(path), 5 + dx, 3 + dy, 5 - dx, 3 - dy);
    }

    public static BLookAndFeel getDefaultLookAndFeel() {
        MenuLookAndFeel lnf = new MenuLookAndFeel();
        configureDefaultLookAndFeel(lnf);
        return lnf;
    }
}
