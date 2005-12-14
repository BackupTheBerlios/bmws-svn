package de.terrainer;

import java.awt.Dimension;
import java.awt.Point;

public class HeightMap {
	int[][] heightMap;
	int maxHeight;
	int minHeight;
	int steepfactor;

	public HeightMap(int widthX, int widthY) {
		heightMap = new int[widthX][widthY];
	}

	public Dimension getDimension() {
		return new Dimension(heightMap.length, heightMap[0].length);
	}

	public int getHeightAt(int x, int y) {
		if (x < 0 || x >= heightMap.length || y < 0 || y >= heightMap[0].length)
			return -50;
		return heightMap[x][y];
	}
	
	public void setHeight(int x, int y, int height) {
		heightMap[x][y] = height;
	}
	
	public void setHeight(Point pt, int height) {
		setHeight(pt.x, pt.y, height);
	}

	public int getHeightAt(Point pt) {
		return getHeightAt(pt.x, pt.y);
	}

	public int getMaximumHeight() {
		return maxHeight;
	}

	public int getMinimumHeight() {
		return minHeight;
	}
	
	public int getWidth() {
		return heightMap[0].length;
	}

	public void init() {
		maxHeight = Integer.MIN_VALUE;
		minHeight = Integer.MAX_VALUE;
		for (int x=0; x<getWidth(); x++) {
			for (int y=0; y<getWidth(); y++) {
				maxHeight = Math.max(maxHeight, getHeightAt(x,y));
				minHeight = Math.min(minHeight, getHeightAt(x,y));
			}
		}
	}

	private void average(int rad) {
		// TODO better with FFT and gaussian shape
		for (int x = 0; x < heightMap.length; x++) {
			for (int y = 0; y < heightMap[0].length; y++) {
				if (heightMap[x][y]>75)
					continue;
				int count =0;
				int val = 0;
				for (int x1 =x-rad; x1<=x+rad; x1++) {
					for (int y1 =y-rad; y1<=y+rad; y1++) {
						count++;
						val += getHeightAt(x1,y1);						
					}					
				}
				heightMap[x][y] = val/count;
			}
		}
		
	}

	public int[] getLinearMap() {
		int[] ret = new int[getWidth()*getWidth()];
		for (int x = 0; x <getWidth(); x++) {
			for (int y = 0; y < getWidth() ; y++) {
				ret[x+y*getWidth()] = getHeightAt(x,y);
			}
		}
		return ret;
	}
}
