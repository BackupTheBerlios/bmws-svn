package de.mbws.client.terrain;

import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jmex.terrain.TerrainBlock;

public class CachedTerrainBlock extends TerrainBlock {
	private long timeBlockWasCached;
	

	public CachedTerrainBlock() {
		super();
	}

	public CachedTerrainBlock(String name) {
		super(name);
	}

	public CachedTerrainBlock(String name, int size, Vector3f stepScale,
			int[] heightMap, Vector3f origin, boolean clod) {
		super(name, size, stepScale, heightMap, origin, clod);
	}

	public CachedTerrainBlock(String name, int size, Vector3f stepScale,
			int[] heightMap, Vector3f origin, boolean clod, int totalSize,
			Vector2f offset, int offsetAmount) {
		super(name, size, stepScale, heightMap, origin, clod, totalSize,
				offset, offsetAmount);
	}

	public long getTimeBlockWasCached() {
		return timeBlockWasCached;
	}

	public void setTimeBlockWasCached(long timeBlockWasCached) {
		this.timeBlockWasCached = timeBlockWasCached;
	}

}
