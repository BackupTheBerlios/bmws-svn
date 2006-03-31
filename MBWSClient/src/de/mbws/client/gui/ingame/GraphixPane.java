package de.mbws.client.gui.ingame;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

/**
 * A GraphixPane is a Swing component that only shows a portion of an image and
 * allows panning across. Size of the portion and the image are supplied when
 * construction an instance of this type. No restrictions apply to the scrolling
 * so it is done in wrap around mode.
 * 
 * @author mafw
 * 
 */
public class GraphixPane extends JComponent {
    private static final long serialVersionUID = 1L;

    private BufferedImage terrainImage;
    private int imageWidth;
    private int imageHeight;

    private Dimension size;
    private int offsetX;
    private int offsetY;
    
    private RectangularShape clipperShape;

    public GraphixPane(Dimension size, BufferedImage terrainImage) {
        this.terrainImage = terrainImage;
        this.size = size;
        clipperShape = new Rectangle2D.Double();
        imageWidth = terrainImage.getWidth();
        imageHeight = terrainImage.getHeight();
    }

    public void setClipper(RectangularShape shape) {
        clipperShape = shape;
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
        Graphics2D g2 = (Graphics2D) gfx.create();
        clipperShape.setFrame(0, 0, size.width, size.height);
        g2.setClip(clipperShape);
        g2.setPaint(new TexturePaint(terrainImage, new Rectangle(offsetX, offsetY, imageWidth, imageHeight)));
        g2.fillRect(0, 0, size.width, size.height);
        g2.dispose();
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
        // repaint();
        getParent().repaint();
    }

    public Dimension getImageDimensions() {
        return new Dimension(terrainImage.getWidth(), terrainImage.getHeight());
    }

    public BufferedImage getImage() {
        return terrainImage;
    }
}
