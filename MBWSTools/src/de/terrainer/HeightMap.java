package de.terrainer;

import java.awt.Dimension;
import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.apache.log4j.Logger;

import de.mbws.client.worldloader.ObjectLoader;
import de.mbws.client.worldloader.TerrainPersistence;
import de.mbws.client.worldloader.WorldDescription;

public class HeightMap {
	static Logger logger = Logger.getLogger(HeightMap.class);

	private int[][] heightMap;
	private int maxHeight;
	private int minHeight;
	private int resolution;
	private ObjectLoader loader;

	private HeightMap() {

	}

	public HeightMap(int widthX, int widthY, int resolution) {
		heightMap = new int[widthX][widthY];
		this.resolution = resolution;
	}

	public Dimension getDimension() {
		return new Dimension(heightMap.length, heightMap[0].length);
	}

	public int getHeightAt(int x, int y) {
		if (x < 0 || x >= heightMap.length || y < 0 || y >= heightMap[0].length)
			return 0;
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
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getWidth(); y++) {
				maxHeight = Math.max(maxHeight, getHeightAt(x, y));
				minHeight = Math.min(minHeight, getHeightAt(x, y));
			}
		}
	}

	public int[] getLinearMap() {
		int[] ret = new int[getWidth() * getWidth()];
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getWidth(); y++) {
				ret[x + y * getWidth()] = getHeightAt(x, y);
			}
		}
		return ret;
	}

	public void save() {
		int size = 12;
		TerrainPersistence tp = new TerrainPersistence("..\\MBWSClient\\data\\world\\world", size,
				size);
		// TODO for the timebeing we save one terrain x times
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				tp.addSection(x, y, getLinearMap());
			}
		}
		tp.writeWorld("world", size, size, getWidth());
	}

	public static HeightMap load(String worldPath) {
		ObjectLoader objectLoader = new ObjectLoader();
		try {
			WorldDescription wld = objectLoader.loadWorldDescription(worldPath);
			HeightMap hm = new HeightMap();
			hm.heightMap = new int[wld.sectionColumns][wld.sectionRows];
			for (int row = 0; row < wld.sectionRows; row++) {
				for (int col = 0; col < wld.sectionColumns; col++) {
					hm.heightMap[col][row] = loadOneValue(worldPath, col, row);
				}
			}
			return hm;
		}
		catch (Exception e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
	}

	private static int loadOneValue(String worldname, int col, int row) throws IOException {
		byte[] bytes = new byte[4];
		FileInputStream fis = new FileInputStream(worldname + "_" + col + "_" + row);
		fis.read(bytes, 0, 4);
		ByteBuffer buffer = ByteBuffer.wrap(bytes);
		int value = buffer.getInt();
		return value;
	}

}
