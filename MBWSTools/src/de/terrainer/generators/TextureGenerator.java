package de.terrainer.generators;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import de.terrainer.AbstractGenerator;
import de.terrainer.HeightMapCache;
import de.terrainer.MetaInfo;
import de.terrainer.gui.HeightMapComponent;

public class TextureGenerator extends AbstractGenerator {
	private int resolutionPerField = 4;
	private Random rand = new Random(System.currentTimeMillis());

	public TextureGenerator(HeightMapComponent hmc) {
		super(hmc);
	}

	public void generate() {
		HeightMapCache heightMap = getHeightMap();
		int picWidth = (heightMap.getDimension().width - 1) * resolutionPerField;
		BufferedImage img = new BufferedImage(picWidth, picWidth, BufferedImage.TYPE_INT_RGB);
		Graphics gr = img.getGraphics();
		for (int x = 0; x < picWidth; x++) {
			for (int y = 0; y < picWidth; y++) {
				gr.setColor(new Color(rand.nextFloat() / 3, 0.3f + rand.nextFloat() / 3f,
						rand.nextFloat() / 4f));
				gr.drawRect(x, y, 1, 1);
			}
		}
		try {
			ImageIO.write(img, "png", new File("test.png"));
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setMask(int[][] mask) {
		// TODO Auto-generated method stub

	}

	public String getName() {
		// TODO Auto-generated method stub
		return "Texture Generator";
	}

	public MetaInfo[] getMetaInfo() {
		// TODO Auto-generated method stub
		return null;
	}

}
