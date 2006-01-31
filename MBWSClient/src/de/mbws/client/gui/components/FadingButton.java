package de.mbws.client.gui.components;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

/**
 * Description:
 * 
 * @author Azarai
 */
public class FadingButton extends JButton {
    Timer timer = new Timer();

    /**
     * @param text
     */
    public FadingButton(String iconFileName, Point position, final int duration) {
        super(iconFileName);
        setText("");
        setIcon(new FadingButton.FadingImageIcon(iconFileName));
        setDisabledIcon(getIcon());
        setOpaque(false);
        setBorder(new BevelBorder(BevelBorder.RAISED));
        setContentAreaFilled(false);
        setVerticalAlignment(SwingConstants.CENTER);
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalTextPosition(SwingConstants.CENTER);
        setHorizontalTextPosition(SwingConstants.CENTER);
        setBounds(position.x,position.y,getIcon().getIconWidth(),getIcon().getIconHeight());
        addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                setEnabled(false);
                getIconAsFadingImageIcon().setFading(true);
                int alpha = getIconAsFadingImageIcon().getAlphaChannel();
                int steps = (int)Math.round(duration/(alpha+1));
//                getIconAsFadingImageIcon().setSteps(steps-1);
                
                TimerTask task = new TimerTask() {

                    public void run() {
//                        System.out.println("running");
                        if (getIconAsFadingImageIcon().isFading()) {
                            repaint();
                        } else {
                            this.cancel();
                            setEnabled(true);
                            System.out.println(System.currentTimeMillis());
                        }
                    }
                };
                System.out.println(System.currentTimeMillis());
                int t = (alpha/duration);
                System.out.println(steps);
                timer.schedule(task, 0, steps);
            }
        });
    }

    private FadingImageIcon getIconAsFadingImageIcon() {
        return (FadingImageIcon) getIcon();
    }

    class FadingImageIcon extends ImageIcon {

        private int alphaChannel = 255;
        private int steps = 1;
        
        private boolean fading = false;

        public FadingImageIcon(String filename) {
            super(filename);
        }

        public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
            super.paintIcon(c, g, x, y);
            if (fading) {
                g.setColor(new Color(0, 0, 0, alphaChannel));
                ((Graphics2D) g).fillRect(x, y, getIconWidth(), getIconHeight());
                alphaChannel = alphaChannel - steps;
                if (alphaChannel < 0) {
                    alphaChannel = 255;
                    setFading(false);
                }
            }
        }

        public boolean isFading() {
            return fading;
        }

        public void setFading(boolean fading) {
            this.fading = fading;
        }

        public int getAlphaChannel() {
            return alphaChannel;
        }

        public void setSteps(int steps) {
            this.steps = steps;
        }
    }
}