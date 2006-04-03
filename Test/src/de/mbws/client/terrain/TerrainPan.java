package de.mbws.client.terrain;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import de.mbws.client.controller.GraphixPaneController;
import de.mbws.client.gui.ingame.GraphixPane;

public class TerrainPan {
    private JFrame frame;

    public TerrainPan() {
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
                Container pane = frame.getContentPane();

                try {
                    File file = new File("data/images/IntroAndMainMenu/Background.jpg");
                    FileImageInputStream fiis = new FileImageInputStream(file);
                    GraphixPane gfxPane = new GraphixPane(new Dimension(50, 50),
                            ImageIO.read(fiis));
                    pane.add(gfxPane, BorderLayout.CENTER);
                    frame.addKeyListener(new BoundPanControllerExample(gfxPane));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                frame.setTitle("Terrain Map Viewer");
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
            TerrainPan tm = new TerrainPan();
            tm.startApp();
        }
    }
}


class BoundPanControllerExample extends GraphixPaneController implements KeyListener {
    private int pageModifyer = 0;
    private int maxPanX;
    private int maxPanY;

    public BoundPanControllerExample(GraphixPane pane) {
        super(pane);
        Dimension imageDim = pane.getImageDimensions();
        maxPanX = imageDim.width - pane.getPanSize().width;
        maxPanY = imageDim.height - pane.getPanSize().height;
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        pageModifyer = e.getModifiers() & KeyEvent.SHIFT_MASK;
        for (int p = 0; p < 1 + (pageModifyer * pane.getPreferredSize().width); ++p) {
            int offX = getCurrentX();
            int offY = getCurrentY();
            switch (keyCode) {
            case KeyEvent.VK_RIGHT:
                if (offX > 0) {
                    panRight();
                }
                break;
            case KeyEvent.VK_LEFT:
                if (offX < maxPanX) {
                    panLeft();
                }
                break;
            case KeyEvent.VK_DOWN:
                if (offY > 0) {
                    panDown();
                }
                break;
            case KeyEvent.VK_UP: 
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
