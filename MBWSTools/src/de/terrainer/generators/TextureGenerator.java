package de.terrainer.generators;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import de.terrainer.AbstractGenerator;
import de.terrainer.HeightMap;
import de.terrainer.MetaInfo;

public class TextureGenerator extends AbstractGenerator {
	private int resolutionPerField = 4;
	private Random rand = new Random(System.currentTimeMillis());

	public TextureGenerator(HeightMap heightMap) {
		super(heightMap);
	}

	@Override
	public void generate() {
		int picWidth = (heightMap.getWidth() - 1) * resolutionPerField;
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

	@Override
	public void setMask(int[][] mask) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Texture Generator";
	}

	public MetaInfo[] getMetaInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		(new TextureGenerator(new HeightMap(129, 129))).generate();
	}
}
