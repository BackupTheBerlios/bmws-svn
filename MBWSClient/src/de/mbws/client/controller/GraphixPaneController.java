package de.mbws.client.controller;

import java.awt.Dimension;

import de.mbws.client.gui.ingame.GraphixPane;

/**
 * The GraphixPaneController serves as the base for controllers of GraphixPanes. It provides information
 * about current coordinates and image restrictions to scrolling controllers.
 * @author mafw
 *
 */
public abstract class GraphixPaneController {
    protected GraphixPane pane;
    protected int offsetX;
    protected int offsetY;
    protected int maxPanX;
    protected int maxPanY;

    public GraphixPaneController(GraphixPane pane) {
        this.pane = pane;
        Dimension imageDim = pane.getImageDimensions();
        maxPanX = imageDim.width - pane.getPreferredSize().width;
        maxPanY = imageDim.height - pane.getPreferredSize().height;
        offsetX = maxPanX;
        offsetY = maxPanY;
    }

    protected void panUp() {
        offsetY++;
        pane.panUp();
        pane.updateDisplay();
    }

    protected void panDown() {
        offsetY--;
        pane.panDown();
        pane.updateDisplay();
    }

    protected void panLeft() {
        offsetX++;
        pane.panLeft();
        pane.updateDisplay();
    }

    protected void panRight() {
        offsetX--;
        pane.panRight();
        pane.updateDisplay();
    }
}
