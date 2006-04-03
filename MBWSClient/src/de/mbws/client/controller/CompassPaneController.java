package de.mbws.client.controller;

import de.mbws.client.gui.ingame.CompassPane;
import de.mbws.client.gui.ingame.Orientation;

/**
 * @author mafw
 * 
 */
public class CompassPaneController {
    protected CompassPane pane;
    protected Orientation orientation;

    public CompassPaneController(CompassPane pane) {
        this.pane = pane;
        orientation = pane.getOrientation();
    }

    protected void turnLeft() {
        orientation.turnLeft();
        pane.updateDisplay();
    }

    protected void turnRight() {
        orientation.turnRight();
        pane.updateDisplay();
    }

    protected void turnLeft(double degree) {
        orientation.turnLeft(degree);
        pane.updateDisplay();
    }

    protected void turnRight(double degree) {
        orientation.turnRight(degree);
        pane.updateDisplay();
    }

    
}
