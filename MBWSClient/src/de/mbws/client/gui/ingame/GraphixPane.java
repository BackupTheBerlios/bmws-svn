package de.mbws.client.gui.ingame;

import java.awt.*;
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
        offsetX = imageWidth;
        offsetY = imageHeight;
    }

    /**
     * Set the new clip region via a shape. Everything within that shape remains
     * visible and is drawn. The shape defaults to a rectangle.
     * 
     * @param shape
     */
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

    /**
     * Return the size of this pan.
     * @return size
     */
    public Dimension getPanSize() {
        return size;
    }

    public void paintComponent(Graphics gfx) {
        super.paintComponent(gfx);
        Graphics2D g2 = (Graphics2D) gfx.create();
        clipperShape.setFrame(0, 0, size.width, size.height);
        g2.setClip(clipperShape);
        g2.setPaint(new TexturePaint(terrainImage, new Rectangle(offsetX,
                offsetY, imageWidth, imageHeight)));
        g2.fillRect(0, 0, size.width, size.height);
        g2.dispose();
    }

    /**
     * Move the pan down and expose new imagery. This will wrap around.
     */
    public void panDown() {
        offsetY = --offsetY % imageHeight;
    }

    /**
     * Move the pan up and expose new imagery. This will wrap around.
     */
    public void panUp() {
        offsetY = ++offsetY % imageHeight;
    }

    /**
     * Move the pan right and expose new imagery. This will wrap around.
     */
    public void panRight() {
        offsetX = --offsetX % imageWidth;
    }

    /**
     * Move the pan left and expose new imagery. This will wrap around.
     */
    public void panLeft() {
        offsetX = ++offsetX % imageWidth;
    }

    /**
     * Force a repaint of this component through its parent. The repaint is be queued.
     */
    public void updateDisplay() {
        getParent().repaint();
    }

    /**
     * Return the size of the image that the pan works on.
     * @return dimension
     */
    public Dimension getImageDimensions() {
        return new Dimension(terrainImage.getWidth(), terrainImage.getHeight());
    }

    /**
     * Return the image the pan works on.
     * @return image
     */
    public BufferedImage getImage() {
        return terrainImage;
    }

    /**
     * Return the current X offset.
     * @return x
     */
    public int getCurrentX() {
        return offsetX;
    }

    /**
     * Return the current Y offset.
     * @return y
     */
    public int getCurrentY() {
        return offsetY;
    }
}
