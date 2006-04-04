package de.terrainer.texgen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TexGenPanel extends JPanel {
	private static final int SIZE = 128;
	private float[][] texture = new float[SIZE][SIZE];
	private float[][] picture = new float[SIZE][SIZE];
	private Random rand = new Random(System.currentTimeMillis());

	public TexGenPanel() {
		setBorder(BorderFactory.createEtchedBorder());
		setLayout(new BorderLayout());
		add(new TextureView(), BorderLayout.CENTER);
		add(createControls(), BorderLayout.SOUTH);
	}

	public JPanel createControls() {
		JPanel pan = new JPanel();
		JButton newBut = new JButton("new");
		newBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				texture = new float[SIZE][SIZE];
				generate();
				normalize();
				repaint();
			}
		});
		pan.add(newBut);
		return pan;
	}
	
	public void normalize() {
		float max = Float.MIN_VALUE;
		float min = Float.MAX_VALUE;
		for (int y = 0; y < texture.length; y++) {
			for (int x = 0; x < texture[0].length; x++) {
				if (texture[x][y] < min)
					min = texture[x][y];
				if (texture[x][y] > max)
					max = texture[x][y];
			}
		}
		if (max - min == 0)
			return;
		for (int y = 0; y < texture.length; y++) {
			for (int x = 0; x < texture[0].length; x++) {
				picture[x][y] = (texture[x][y] - min) / (max - min);
			}
		}
	}

	public void generate() {
		float t = 0;
		float c = 10;
		int nmax = 15;

		for (int i = 0; i < 500; i++) {

			float A = rand.nextFloat();
			float phi = 2 * (float) Math.PI * rand.nextFloat();
			int d = texture.length;
			
			float kx = (float) (2*Math.PI / d *(rand.nextInt(2*nmax)-nmax));
			float ky = (float) (2*Math.PI / d *(rand.nextInt(2*nmax)-nmax));
			float w = (float) Math.sqrt(kx*kx+ky*ky)*c;
			for (int y = 0; y < texture.length; y++) {
				for (int x = 0; x < texture[0].length; x++) {
					texture[x][y] += A * (float) Math.cos(x * kx + y * ky + w * t + phi);
				}
			}
		}
	}

	private class TextureView extends JComponent {
		@Override
		public void paint(Graphics g) {
			float elementWidth = getSize().width / picture.length;
			float elementHeight = getSize().height / picture[0].length;
			for (int y = 0; y < picture.length; y++) {
				for (int x = 0; x < picture[0].length; x++) {
					float greyVal = picture[x][y];
					g.setColor(new Color(greyVal, greyVal, greyVal));
					g.fillRect(Math.round(x * elementWidth), Math.round(y * elementHeight), Math
							.round(elementWidth), Math.round(elementHeight));
				}
			}
		}
	}

	public static void main(String[] args) {
		JFrame fr = new JFrame("TexGen");
		TexGenPanel tgp = new TexGenPanel();
		fr.add(tgp);
		fr.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		fr.setSize(300, 300);
		fr.setVisible(true);
	}
}
