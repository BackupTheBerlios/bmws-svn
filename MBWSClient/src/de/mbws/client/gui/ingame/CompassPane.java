package de.mbws.client.gui.ingame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

/**
 * A CompassPane is a visualisation of a horizontal orientation done by an arrow that is
 * superimposed on a GrafixPane. Usually, this GrafixPane resembles terrain.
 * @author mafw
 */
public class CompassPane extends JComponent {
    private static final long serialVersionUID = 1L;
    private Orientation direction;
    private GraphixPane terrainPanel;
    private int halfWidth;
    private int halfHeight;
    private Polygon arrow;
    private Color arrowColor;

    public CompassPane(Orientation dir, GraphixPane panel, Color color) {
        direction = dir;
        terrainPanel = panel;
        arrowColor = color;
        add(terrainPanel);
        arrow = new Polygon(new int[]{-5, 0, 5}, new int[]{5, -9, 5}, 3);
        Dimension panelDimension = terrainPanel.getPreferredSize();
        halfWidth = panelDimension.width >> 1;
        halfHeight = panelDimension.height >> 1;
    }

    public Dimension getMinimumSize() {
        return terrainPanel.getMinimumSize();
    }

    public Dimension getMaximumSize() {
        return terrainPanel.getMaximumSize();
    }

    public Dimension getPreferredSize() {
        return terrainPanel.getPreferredSize();
    }

    public void paintComponent(Graphics gfx) {
        super.paintComponent(gfx);
        terrainPanel.paintComponent(gfx);
        Graphics2D g2 = (Graphics2D) gfx.create();
        AffineTransform at = AffineTransform.getTranslateInstance(halfWidth, halfHeight);
        at.rotate(Math.toRadians(direction.getOrientation()));
        g2.setColor(arrowColor);
        g2.fill(at.createTransformedShape(arrow));
        g2.dispose();
    }

    public void updateDisplay() {
        getParent().repaint();
    }
    
    public Dimension getImageDimensions() {
        return terrainPanel.getImageDimensions();
    }

    public Orientation getOrientation() {
        return direction;
    }
}
