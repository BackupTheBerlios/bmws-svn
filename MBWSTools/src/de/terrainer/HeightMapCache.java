package de.terrainer;

import java.awt.Dimension;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.LinkedList;

import de.mbws.client.worldloader.TerrainPersistence;

// TODO write to a tempory directory

public class HeightMapCache {

	private class CacheEntry {
		public int[] sectionMap;
		public int x, y;
		public boolean changed;

		public CacheEntry(int x, int y, int[] sectionMap) {
			this.x = x;
			this.y = y;
			this.sectionMap = sectionMap;
		}
	}

	private int coarseSize;
	private int detailResolution;
	private int realResolution;
	private String worldname = "..\\MBWSClient\\data\\world\\world";
	private LinkedList<CacheEntry> cache;
	private int cacheSize = 20;
	private TerrainPersistence persistence;
	private int[][] coarseMap;
	private int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;

	public HeightMapCache(int size, int detailResolution) {
		this.coarseSize = size;
		this.detailResolution = detailResolution;
		this.cache = new LinkedList<CacheEntry>();
		initWorldMap();

	}

	private void initWorldMap() {
		persistence = new TerrainPersistence(worldname, coarseSize, coarseSize,
				detailResolution + 1);
		realResolution = detailResolution + 1;
		int[] heightfield = new int[realResolution * realResolution];
		// write empty heightfields intially
		for (int x = 0; x < coarseSize + 1; x++) {
			for (int y = 0; y < coarseSize + 1; y++) {
				persistence.writeSection(x, y, heightfield);
			}
		}
		coarseMap = new int[coarseSize + 1][coarseSize + 1];
		persistence.writeWorld();
	}

	/**
	 * Gets the height for the given coordinates on the height map. The height can be either
	 * retrieved from the coarse map or the underlying detail map.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public int getHeight(int x, int y) {
		if (x < 0 || x >= getWidth() || y < 0 || y >= getWidth()) {
			return 0;
		}
		int sectionx = x / detailResolution;
		int sectiony = y / detailResolution;
		int detailx = x % detailResolution;
		int detaily = y % detailResolution;
		// if we can get it from the coarse field, get it there
		if (detailx == 0 && detaily == 0) {
			return coarseMap[sectionx][sectiony];
		}
		else {
			int[] section = getSection(sectionx, sectiony).sectionMap;
			return section[detailx + detaily * realResolution];
		}
	}

	/**
	 * Sets a height value for the given coordinates on the height map.
	 * 
	 * @param x
	 * @param y
	 * @param height
	 */
	public void setHeight(int x, int y, int height) {
		int sectionx = x / detailResolution;
		int sectiony = y / detailResolution;
		int detailx = x % detailResolution;
		int detaily = y % detailResolution;
		// if we can get it from the coarse field, set it there as well
		if (detailx == 0 && detaily == 0) {
			coarseMap[sectionx][sectiony] = height;
		}
		setHeightInSection(height, sectionx, sectiony, detailx, detaily);
		// check seams and set them if necessary
		if (detailx == 0 && sectionx > 0) {
			setHeightInSection(height, sectionx - 1, sectiony, detailResolution, detaily);
		}
		if (detaily == 0 && sectiony > 0) {
			setHeightInSection(height, sectionx, sectiony - 1, detailx, detailResolution);
		}
		if (detailx == 0 && sectionx > 0 && detaily == 0 && sectiony > 0) {
			setHeightInSection(height, sectionx - 1, sectiony - 1, detailResolution,
					detailResolution);
		}
		// set minimum and maximum
		min = (height < min) ? height : min;
		max = (height > max) ? height : max;
	}

	private void setHeightInSection(int height, int sectionx, int sectiony, int detailx, int detaily) {
		CacheEntry entry = getSection(sectionx, sectiony);
		entry.sectionMap[detailx + detaily * realResolution] = height;
		entry.changed = true;
	}

	/**
	 * Retrieves a section from the world. First the section is searched in the cache. If necessary
	 * it will be loaded into the cache and discard an older entry.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public CacheEntry getSection(int x, int y) {
		// first we searche the cache for the section
		Iterator<CacheEntry> it = cache.iterator();
		while (it.hasNext()) {
			CacheEntry entry = it.next();
			if (entry.x == x && entry.y == y) {
				// found entry, move it to beginning of the cache
				it.remove();
				cache.addFirst(entry);
				return entry;
			}
		}
		// if the cache is full, obtain a new slot
		if (cache.size() >= cacheSize) {
			CacheEntry entry = cache.removeLast();
			if (entry.changed) {
				saveSection(entry);
			}
		}
		// load the section into the cache
		int[] sectionMap = loadSection(x, y);
		CacheEntry entry = new CacheEntry(x, y, sectionMap);
		cache.addFirst(entry);
		return entry;
	}

	/**
	 * Flushes the cache and rewrites <em> all </em> sections to fill them with seamings.
	 * 
	 */
	public void flush() {
		for (CacheEntry entry : cache) {
			saveSection(entry);
		}
	}

	private void saveSection(CacheEntry entry) {
		persistence.writeSection(entry.x, entry.y, entry.sectionMap);
		entry.changed = false;
	}

	private int[] loadSection(int sectionx, int sectiony) {
		try {
			FileInputStream fis = new FileInputStream(worldname + "_" + sectionx + "_" + sectiony
					+ ".ter");
			int[] heightMap = new int[realResolution * realResolution];
			byte[] bytes = new byte[4];
			for (int y = 0; y < heightMap.length; y++) {
				fis.read(bytes, 0, 4);
				ByteBuffer buffer = ByteBuffer.wrap(bytes);
				int value = buffer.getInt();
				heightMap[y] = value;
			}
			return heightMap;
		}
		catch (Exception e) {
			// logger.error(e);
			throw new RuntimeException(e);
		}
	}

	public Dimension getDimension() {
		int size = coarseSize*detailResolution;
		return new Dimension(size, size);
	}

	public int getWidth() {
		return coarseSize * detailResolution + 1;
	}

	public int getMaximumHeight() {
		return max;
	}

	public int getMinimumHeight() {
		return min;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Width " + coarseSize + ", " + detailResolution + " : ");
		for (int x = 0; x < getWidth(); x++) {
			sb.append(getHeight(x, 10) + " ");
		}
		sb.append("\n");
		for (int x = 0; x < getWidth(); x++) {
			sb.append(getHeight(10, x) + " ");
		}
		return sb.toString();
	}
}
