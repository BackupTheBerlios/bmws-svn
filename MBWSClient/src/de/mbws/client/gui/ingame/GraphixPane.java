package de.mbws.client.gui.ingame;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

/**
 * A GraphixPane is a Swing component that only shows a portion of an image and allows panning across.
 * Size of the portion and the image are supplied when construction an instance of this type. No
 * restrictions apply to the scrolling so it is done in wrap around mode.
 * @author mafw
 *
 */
public class GraphixPane extends JComponent {
    private static final long serialVersionUID = 1L;
    private BufferedImage terrainImage;
    private Dimension size;
    private int offsetX;
    private int offsetY;

    public GraphixPane(Dimension size, BufferedImage terrainImage) {
        this.terrainImage = terrainImage;
        this.size = size;
    }

    public Dimension getMinimumSize() {
        return size;
    }
    public Dimension getMaximumSize() {
        return size;
    }
    public Dimension getPreferredSize() {
        return size;
    }

    public void paintComponent(Graphics gfx) {
        super.paintComponent(gfx);

        // Ellipse2D ellipse = new Ellipse2D.Double();
        // int width = terrainImage.getWidth();
        // int height = terrainImage.getHeight();
        // ellipse.setFrame(width >> 2, height >> 2, width >> 4, height >> 4);
        // gfx.setClip(ellipse);

        Graphics2D g2 = (Graphics2D) gfx;
        g2.setPaint(new TexturePaint(terrainImage, new Rectangle(offsetX,
                offsetY, terrainImage.getWidth(), terrainImage.getHeight())));
        g2.fillRect(0, 0, size.width, size.height);
    }

    public void panDown() {
        offsetY--;
    }

    public void panUp() {
        offsetY++;
    }

    public void panRight() {
        offsetX--;
    }

    public void panLeft() {
        offsetX++;
    }

    public void updateDisplay() {
        repaint();
    }
    
    public Dimension getImageDimensions() {
        return new Dimension(terrainImage.getWidth(), terrainImage.getHeight());
    }
}
