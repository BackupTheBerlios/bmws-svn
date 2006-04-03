package de.mbws.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import de.mbws.client.controller.CompassPaneController;
import de.mbws.client.controller.GraphixPaneController;
import de.mbws.client.gui.ingame.CompassPane;
import de.mbws.client.gui.ingame.GraphixPane;
import de.mbws.client.gui.ingame.Orientation;

/**
 * @author mafw
 * 
 */
public class CompassPaneTest {

    private JFrame frame;

    public CompassPaneTest() {
    }

    protected void startApp() {
        Runnable runner = new Runnable() {
            public void run() {
                frame = new JFrame();
                frame.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosed(java.awt.event.WindowEvent evt) {
                        exitForm(evt);
                    }
                });
                frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

                try {
                    File terrainImageFile = new File("data/images/IntroAndMainMenu/Background.jpg");
                    FileImageInputStream terrainFIIS = new FileImageInputStream(terrainImageFile);

                    Orientation orientation = new Orientation();
                    orientation.setOrientation(315);
                    orientation.setStep(4); // speed up rotation
                    GraphixPane gfxPane = new GraphixPane(new Dimension(90, 90), ImageIO.read(terrainFIIS));
                    // gfxPane.setClipper(new Ellipse2D.Double());

                    CompassPane compassPane = new CompassPane(orientation, gfxPane, Color.YELLOW);

                    frame.add(compassPane, BorderLayout.CENTER);
                    frame.addKeyListener(new BoundGraphixPaneControllerExample(gfxPane));
                    frame.addKeyListener(new CompassPaneControllerExample(compassPane));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                frame.setTitle("Terrain Map Viewer - Use keys a, s, d, w and the arrow keys");
                frame.pack();
                // frame.setResizable(false);
                frame.setVisible(true);
            }
        };
        EventQueue.invokeLater(runner);
    }

    public void exitForm(java.awt.event.WindowEvent evt) {
        exitApp(0);
    }

    private void exitApp(final int aResult) {
        int response = JOptionPane.showConfirmDialog(frame, "Beenden?", "Jo", // title
                JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            System.exit(aResult);
        } else {
            frame.setVisible(true);
        }
    }

    public static void main(String s[]) {
        if (true) {
            CompassPaneTest cpt = new CompassPaneTest();
            cpt.startApp();
        }
    }
}

class BoundGraphixPaneControllerExample extends GraphixPaneController implements KeyListener {
    private int pageModifyer;
    private int maxPanX;
    private int minPanX;
    private int maxPanY;
    private int minPanY;

    public BoundGraphixPaneControllerExample(GraphixPane pane) {
        super(pane);
        Dimension imageDim = pane.getImageDimensions();
        maxPanX = imageDim.width - 1;
        maxPanY = imageDim.height - 1;
        minPanX = pane.getPanSize().width;
        minPanY = pane.getPanSize().height;
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        pageModifyer = e.getModifiers() & KeyEvent.SHIFT_MASK;
        for (int p = 0; p < 1 + (pageModifyer * pane.getPreferredSize().width); ++p) {
            int offX = getCurrentX();
            int offY = getCurrentY();
            switch (keyCode) {
            case KeyEvent.VK_D:
                if (offX > minPanX) {
                    panRight();
                }
                break;
            case KeyEvent.VK_A:
                if (offX < maxPanX) {
                    panLeft();
                }
                break;
            case KeyEvent.VK_S:
                if (offY > minPanY) {
                    panDown();
                }
                break;
            case KeyEvent.VK_W:
                if (offY < maxPanY) {
                    panUp();
                }
                break;
            }
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }
}

class CompassPaneControllerExample extends CompassPaneController implements KeyListener {
    private int turnModifyer = 0;

    public CompassPaneControllerExample(CompassPane pane) {
        super(pane);
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        turnModifyer = e.getModifiers() & KeyEvent.SHIFT_MASK;
        for (int p = 0; p < 1 + (turnModifyer * 14); ++p) {
            switch (keyCode) {
            case KeyEvent.VK_RIGHT:
                turnRight();
                break;
            case KeyEvent.VK_LEFT:
                turnLeft();
                break;
            }
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }
}
