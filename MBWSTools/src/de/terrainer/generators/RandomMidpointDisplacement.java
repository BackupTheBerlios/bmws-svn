package de.terrainer.generators;

import java.awt.Point;

import de.terrainer.AbstractGenerator;
import de.terrainer.HeightMap;
import de.terrainer.TerrainerGUI;

public class RandomMidpointDisplacement extends AbstractGenerator {
	private int steepfactor;
	
	public RandomMidpointDisplacement(HeightMap heightMap) {
		super(heightMap);
	}

	public void generate() {
		steepfactor = 50;
		for (int x = 0; x < heightMap.getWidth(); x++) {
			for (int y = 0; y < heightMap.getWidth(); y++) {
				heightMap.setHeight(x, y, Integer.MIN_VALUE);
				if (x==0||y==0||x==heightMap.getWidth()-1||y==heightMap.getWidth()-1)
					heightMap.setHeight(x,y,-10);
			}
		}
		
		generate1(0, 0, heightMap.getWidth() - 1, 300, 1);
		//average(3);
		heightMap.init();
	}

	public void generate1(int x, int y, int width, int steepness, int depth) {
		if (width <= 1)
			return;
		steepness = width*steepfactor/100;
//		steepness = steepfactor/(depth);
//		if (depth>6)
//			steepness = 1;
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
		if (TerrainerGUI.random.nextInt(2)>0)
			generateRandomizedAverageHeight(center, southeast, northwest, southeast, northwest,
				steepness);
		else
			generateRandomizedAverageHeight(center, southwest, northeast, southwest, northeast,
					steepness);
		generateRandomizedAverageHeight(north, northwest, northeast, northwest, northeast,
				steepness);
		generateRandomizedAverageHeight(west, southwest, northwest, southwest, northwest, steepness);
		generateRandomizedAverageHeight(south, southwest, southeast, southwest, southeast,
				steepness);
		generateRandomizedAverageHeight(east, southeast, northeast, southeast, northeast, steepness);
		// recurse
		generate1(x, y, halfwidth, steepness, depth+1);
		generate1(x + halfwidth, y, halfwidth, steepness, depth+1);
		generate1(x, y + halfwidth, halfwidth, steepness, depth+1);
		generate1(x + halfwidth, y + halfwidth, halfwidth, steepness, depth+1);
	}

	private void generateRandomizedAverageHeight(Point pt, Point ref1, Point ref2, Point ref3,
			Point ref4, int steepness) {
		if (heightMap.getHeightAt(pt)!=Integer.MIN_VALUE) 
			return;
		int average = (heightMap.getHeightAt(ref1) + heightMap.getHeightAt(ref2) + heightMap.getHeightAt(ref3) + heightMap.getHeightAt(ref4)) / 4;
		int height = average + (steepness > 0 ? TerrainerGUI.random.nextInt()%steepness : 0);
		heightMap.setHeight(pt,height);
	}


	public void setMask(int[][] mask) {
		// TODO Auto-generated method stub
		
	}

	public GeneratorProperty[] getProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		return "RandomMidpointDisplacement";
	}

}
