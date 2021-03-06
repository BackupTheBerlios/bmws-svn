package de.terrainer.generators;

import java.awt.Point;

import de.terrainer.AbstractGenerator;
import de.terrainer.HeightMapCache;
import de.terrainer.MetaInfo;
import de.terrainer.TerrainerGUI;
import de.terrainer.gui.HeightMapComponent;

public class RandomMidpointDisplacement extends AbstractGenerator {
	private int steepness = 250;
	private int radius = 3;
	private int keepAveraging = 50;
	private int scaleAveraging = 50;

	public RandomMidpointDisplacement(HeightMapComponent hmc) {
		super(hmc);
	}

	public void generate() {
		HeightMapCache heightMap = getHeightMap();
		for (int x = 0; x < heightMap.getWidth(); x++) {
			for (int y = 0; y < heightMap.getWidth(); y++) {
				heightMap.setHeight(x, y, Integer.MIN_VALUE);
				if (x == 0 || y == 0 || x == heightMap.getWidth() - 1
						|| y == heightMap.getWidth() - 1)
					heightMap.setHeight(x, y, 0);
			}
		}

		generate1(0, 0, heightMap.getWidth() - 1, steepness, 1);
		average();
		heightMap.flush();
	}

	public void generate1(int x, int y, int width, int asteepness, int depth) {
		if (width < 1)
			return;
		// asteepness = width * asteepness / 100;
		asteepness = width * steepness / 100;
		// steepness = steepfactor/(depth);
		// if (depth>6)
		// steepness = 1;
		int halfwidth = width / 2;
		Point south = new Point(x + halfwidth, y + width);
		Point north = new Point(x + halfwidth, y);
		Point west = new Point(x, y + halfwidth);
		Point east = new Point(x + width, y + halfwidth);
		Point center = new Point(x + halfwidth, y + halfwidth);
		Point southwest = new Point(x, y + width);
		Point southeast = new Point(x + width, y + width);
		Point northwest = new Point(x, y);
		Point northeast = new Point(x + width, y);
		generateRandomizedAverageHeight(center, southwest, northeast, southwest, northeast,
				asteepness);
		generateRandomizedAverageHeight(north, northwest, northeast, northwest, northeast,
				asteepness);
		generateRandomizedAverageHeight(west, southwest, northwest, southwest, northwest,
				asteepness);
		generateRandomizedAverageHeight(south, southwest, southeast, southwest, southeast,
				asteepness);
		generateRandomizedAverageHeight(east, southeast, northeast, southeast, northeast,
				asteepness);
		// recurse
		generate1(x, y, halfwidth, asteepness, depth + 1);
		generate1(x + halfwidth, y, halfwidth, asteepness, depth + 1);
		generate1(x, y + halfwidth, halfwidth, asteepness, depth + 1);
		generate1(x + halfwidth, y + halfwidth, halfwidth, asteepness, depth + 1);
	}

	private void generateRandomizedAverageHeight(Point pt, Point ref1, Point ref2, Point ref3,
			Point ref4, int steepness) {
		HeightMapCache heightMap = getHeightMap();
		if (heightMap.getHeight(pt.x, pt.y) != Integer.MIN_VALUE)
			return;
		int average = (heightMap.getHeight(ref1.x, ref1.y) + heightMap.getHeight(ref2.x, ref2.y)
				+ heightMap.getHeight(ref3.x, ref3.y) + heightMap.getHeight(ref4.x, ref4.y)) / 4;
		int height = average + (steepness > 0 ? TerrainerGUI.random.nextInt() % steepness : 0);
		heightMap.setHeight(pt.x, pt.y, height);
	}

	private void average() {
		HeightMapCache heightMap = getHeightMap();
		// TODO better with FFT and gaussian shape
		for (int x = 1; x < heightMap.getWidth() - 1; x++) {
			for (int y = 1; y < heightMap.getWidth() - 1; y++) {
				int radius2 = radius;
				float weight = 1;
				if (heightMap.getHeight(x, y) > keepAveraging)
					// radius2 = Math.min(0, radius - (heightMap.getHeight(x, y) - keepAveraging)
					// / (radius * scaleAveraging));
					weight = ((float) scaleAveraging) / (heightMap.getHeight(x, y) - keepAveraging+scaleAveraging);
				float count = 0;
				float val = 0;
				for (int x1 = x - radius2; x1 <= x + radius2; x1++) {
					for (int y1 = y - radius2; y1 <= y + radius2; y1++) {
						if (x1 == x && y1 == y) {
							count += 1;
							val += heightMap.getHeight(x1, y1);
						}
						else {
							count += weight;
							val += weight * heightMap.getHeight(x1, y1);
							
						}
					}
				}
				if (count > 0)
					heightMap.setHeight(x, y, (int) Math.round(val / count));
			}
		}
	}

	public void setMask(int[][] mask) {
		// TODO Auto-generated method stub

	}

	public MetaInfo[] getMetaInfo() {
		MetaInfo[] mi = new MetaInfo[4];
		mi[0] = new MetaInfo();
		mi[0].displayName = "Steepness";
		mi[0].codeName = "Steepness";
		mi[1] = new MetaInfo();
		mi[1].displayName = "Radius";
		mi[1].codeName = "Radius";
		mi[2] = new MetaInfo();
		mi[2].displayName = "Keep avg. until";
		mi[2].codeName = "KeepAveraging";
		mi[3] = new MetaInfo();
		mi[3].displayName = "Reduce avg. every";
		mi[3].codeName = "ScaleAveraging";
		return mi;
	}

	public String getName() {
		return "RandomMidpointDisplacement";
	}

	public int getSteepness() {
		return steepness;
	}

	public void setSteepness(int steepness) {
		this.steepness = steepness;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public void setKeepAveraging(int keepAveraging) {
		this.keepAveraging = keepAveraging;
	}

	public void setScaleAveraging(int scaleAveraging) {
		this.scaleAveraging = scaleAveraging;
	}

	public int getKeepAveraging() {
		return keepAveraging;
	}

	public int getScaleAveraging() {
		return scaleAveraging;
	}

}
