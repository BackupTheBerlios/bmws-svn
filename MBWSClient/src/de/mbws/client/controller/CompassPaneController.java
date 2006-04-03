package de.mbws.client.controller;

import de.mbws.client.gui.ingame.CompassPane;
import de.mbws.client.gui.ingame.Orientation;

/**
 * A CompassPaneController serves as the base for controllers of CompassPanes. It provides 
 * information about current orientation as well as functinality to change the orientation.
 * @author mafw
 */
public abstract class CompassPaneController {
    protected CompassPane pane;
    protected Orientation orientation;

    public CompassPaneController(CompassPane pane) {
        this.pane = pane;
        orientation = pane.getOrientation();
    }

    /**
     * Turn the orientation left and repaint.
     */
    protected void turnLeft() {
        orientation.turnLeft();
        pane.updateDisplay();
    }

    /**
     * Turn the orientation right and repaint.
     */
    protected void turnRight() {
        orientation.turnRight();
        pane.updateDisplay();
    }

    /**
     * Turn the orientation left by some degree and repaint.
     * @param degree
     */
    protected void turnLeft(double degree) {
        orientation.turnLeft(degree);
        pane.updateDisplay();
    }

    /**
     * Turn the orientation right by some degree and repaint.
     * @param degree
     */
    protected void turnRight(double degree) {
        orientation.turnRight(degree);
        pane.updateDisplay();
    }
}
