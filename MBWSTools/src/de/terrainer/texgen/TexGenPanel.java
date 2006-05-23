package de.terrainer.texgen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.ImageObserver;
import java.util.Random;

import javax.swing.*;

public class TexGenPanel extends JPanel {
	private static final int SIZE = 128;
	private static final int TRES = 30;
	private static final int ITER = 30;
	private float[][][] texture = new float[SIZE][SIZE][TRES];
	private float[][][] picture = new float[SIZE][SIZE][TRES];
	private Random rand = new Random(System.currentTimeMillis());
	private TextureView texView;

	public TexGenPanel() {
		texView = new TextureView();
		setBorder(BorderFactory.createEtchedBorder());
		setLayout(new BorderLayout());
		add(texView, BorderLayout.CENTER);
		add(createControls(), BorderLayout.SOUTH);
	}

	public JPanel createControls() {
		JPanel pan = new JPanel();
		JButton newBut = new JButton("new");
		newBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				texture = new float[SIZE][SIZE][TRES];
				generate();
				normalize();
				repaint();
			}
		});
		pan.add(newBut);
		JButton animBut = new JButton("animate");
		animBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				animate();
			}
		});
		pan.add(animBut);
		return pan;
	}

	public void normalize() {
		float max = Float.MIN_VALUE;
		float min = Float.MAX_VALUE;
		for (int t = 0; t < TRES; t++) {
			for (int y = 0; y < texture.length; y++) {
				for (int x = 0; x < texture[0].length; x++) {
					if (texture[x][y][t] < min)
						min = texture[x][y][t];
					if (texture[x][y][t] > max)
						max = texture[x][y][t];
				}
			}
		}
		if (max - min == 0)
			return;
		for (int t = 0; t < TRES; t++) {
			for (int y = 0; y < texture.length; y++) {
				for (int x = 0; x < texture[0].length; x++) {
					picture[x][y][t] = (texture[x][y][t] - min) / (max - min);
				}
			}
		}
	}

	public void generate() {
		float c = 10;
		int nmax = 5;

		float A[] = new float[ITER];
		float phi[] = new float[ITER];
		float kx[] = new float[ITER];
		float ky[] = new float[ITER];
		int d = texture.length;

		for (int i = 0; i < ITER; i++) {

			A[i] = rand.nextFloat();
			phi[i] = 2 * (float) Math.PI * rand.nextFloat();

			kx[i] = (float) (2 * Math.PI / d * (rand.nextInt(2 * nmax) - nmax));
			ky[i] = (float) (2 * Math.PI / d * (rand.nextInt(2 * nmax) - nmax));
		}
		for (int ti = 0; ti < TRES; ti++) {
			float t = ti * 4 * (float) Math.PI / TRES;
			for (int i = 0; i < ITER; i++) {
				float w = (float) Math.sqrt(kx[i] * kx[i] + ky[i] * ky[i]) * c;
				for (int y = 0; y < texture.length; y++) {
					for (int x = 0; x < texture[0].length; x++) {
						texture[x][y][ti] += A[i]
								* (float) Math.cos(x * kx[i] + y * ky[i] + w * t + phi[i]);
					}
				}
			}
		}
	}

	private class TextureView extends JComponent {
		@Override
		public void paint(Graphics g) {
			paintTex(g, createBuffer(), 0);
		}

		public void paintTex(Graphics gr, Image background, int t) {
			Graphics g = background.getGraphics();
			float elementWidth = ((float) getSize().width) / picture.length;
			float elementHeight = ((float) getSize().height) / picture[0].length;
			for (int y = 0; y < picture.length; y++) {
				for (int x = 0; x < picture[0].length; x++) {
					float greyVal = picture[x][y][t];
					g.setColor(new Color(greyVal, greyVal, greyVal));
					g.fillRect(Math.round(x * elementWidth), Math.round(y * elementHeight), Math
							.round(elementWidth + 1), Math.round(elementHeight + 1));
				}
			}
			gr.drawImage(background, 0, 0, new ImageObserver() {
				public boolean imageUpdate(Image img, int infoflags, int x, int y, int width,
						int height) {
					return false;
				}

			});
		}

		public Image createBuffer() {
			return createImage(getWidth(), getHeight());
		}
	}

	public void animate() {
		Graphics g = texView.getGraphics();
		Image background = texView.createBuffer();
		for (int i = 0; i < 3; i++) {
			for (int t = 0; t < TRES; t++) {
				texView.paintTex(g, background, t);
				try {
					Thread.sleep(60);
				}
				catch (InterruptedException never) {
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
