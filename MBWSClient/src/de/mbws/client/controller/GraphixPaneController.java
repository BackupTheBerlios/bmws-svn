package de.mbws.client.controller;

import de.mbws.client.gui.ingame.GraphixPane;

/**
 * The GraphixPaneController serves as the base for controllers of GraphixPanes. It provides information
 * about current coordinates and image restrictions to scrolling controllers.
 * @author mafw
 *
 */
public abstract class GraphixPaneController {
    protected GraphixPane pane;

    public GraphixPaneController(GraphixPane pane) {
        this.pane = pane;
    }

    /**
     * Return the current X offset.
     * @return x
     */
    protected int getCurrentX() {
        return pane.getCurrentX();
    }

    /**
     * Return the current Y offset.
     * @return y
     */
    protected int getCurrentY() {
        return pane.getCurrentY();
    }

    /**
     * Move the pan up and repaint. This will wrap.
     */
    protected void panUp() {
        pane.panUp();
        pane.updateDisplay();
    }

    /**
     * Move the pan down and repaint. This will wrap.
     */
    protected void panDown() {
        pane.panDown();
        pane.updateDisplay();
    }

    /**
     * Move the pan left and repaint. This will wrap.
     */
    protected void panLeft() {
        pane.panLeft();
        pane.updateDisplay();
    }

    /**
     * Move the pan right and repaint. This will wrap.
     */
    protected void panRight() {
        pane.panRight();
        pane.updateDisplay();
    }
}
