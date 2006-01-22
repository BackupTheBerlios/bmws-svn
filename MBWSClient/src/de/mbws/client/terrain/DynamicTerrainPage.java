/**
 * 
 */
package de.mbws.client.terrain;

import java.io.File;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.state.RenderState;
import com.jmex.terrain.TerrainBlock;
import com.jmex.terrain.util.AbstractHeightMap;
import com.jmex.terrain.util.RawHeightMap;

public class DynamicTerrainPage extends Node {

	private static Logger logger = Logger.getLogger(DynamicTerrainPage.class);

	// Tiles are stored in a Hashmap IN the HashMap uIndexedTiles
	// xIndexedTiles contains the y Terrainblocks for each X
	private HashMap<Integer, CachedTerrainBlock> vIndexedTile = new HashMap();
	private HashMap<Integer, HashMap<Integer, CachedTerrainBlock>> uIndexedTiles = new HashMap();

	private TerrainBlock currentBlock;
	// size of the tiles
	private int tileSize;

	// Number of tiles the page currently has loaded and can load as a max
	private int numberOfCachedTiles;
	private int maxNumberOfCachedTiles = 4;

	// fixed string for all terrainfiles
	private String terrainName;

	// switch for global clod
	private boolean clod;

	// stepscale for the terrains as a global value
	private Vector3f stepScale;

	/**
	 * 
	 */
	public DynamicTerrainPage() {
		super();
	}

	/**
	 * @param name
	 *            name of the node
	 */
	public DynamicTerrainPage(String name, int aTileSize, Vector3f aStepScale) {
		super(name);
		terrainName = "src/resources/" + name;
		tileSize = aTileSize;
		stepScale = aStepScale;
	}

	/**
	 * retrieves the terrainheight at a GLOBAL location. Will first determine
	 * the terrain tile that should be used and then the local coordinate in it
	 * that corresponds to the global one
	 * 
	 * @param location
	 *            global location in the world
	 * @return height value at that position
	 */
	private float getHeight(Vector2f location) {
		Vector2f tileIndex = mapLocationToTile(location);
		HashMap<Integer, CachedTerrainBlock> vTile = uIndexedTiles
				.get(tileIndex.x);
		TerrainBlock tb = vTile.get(tileIndex.y);
		float realLocationX = location.x % tileSize;
		float realLocationZ = location.y % tileSize;
		return tb.getHeight(realLocationX, realLocationZ);
	}

	/**
	 * Sets a new current terrainblock. Loads a new terrainblock (if its not
	 * already in the hashmap) depending on the location
	 * 
	 * @param location
	 *            the global location in the world
	 */
	public void setOrAttachBlock(Vector2f location) {
		if (numberOfCachedTiles >= maxNumberOfCachedTiles) {
			// TODO: delete one or more tiles (based on time and coordinates
			// (distance) ?)
		}
		Vector2f tileIndex = mapLocationToTile(location);
		vIndexedTile = uIndexedTiles.get((int) tileIndex.x);
		if (vIndexedTile != null) {
			TerrainBlock tb = vIndexedTile.get((int) tileIndex.y);
			if (tb != null) {
				currentBlock = tb;
				return;
			}
		}
		AbstractHeightMap heightMap = loadHeightMap((int) tileIndex.x,
				(int) tileIndex.y);
		if (heightMap != null) {

			CachedTerrainBlock tb = new CachedTerrainBlock(""
					+ (int) location.x + (int) location.y, tileSize, stepScale,
					heightMap.getHeightMap(), new Vector3f(0, 0, 0), clod);
			tb.setTimeBlockWasCached(System.currentTimeMillis());
			Vector3f loc3d = new Vector3f(location.x, 0, location.y);
			tb.setLocalTranslation(loc3d);
			if (vIndexedTile == null) {
				vIndexedTile = new HashMap();
			}
			vIndexedTile.put((int) tileIndex.y, tb);
			uIndexedTiles.put((int) tileIndex.x, vIndexedTile);
			currentBlock = tb;
			fixNormals((int) tileIndex.x, (int) tileIndex.y);
			attachChild(tb);
			numberOfCachedTiles++;
		} else {
			logger.error("Terrain couldnt be loaded for location: "
					+ location.x + " " + location.y);
		}
	}

	/**
	 * maps the global position to a tile that corresponds to that position
	 * 
	 * @param loc
	 * @return
	 */
	private Vector2f mapLocationToTile(Vector2f loc) {
		float u = loc.x / tileSize + 1;
		float v = loc.y / tileSize + 1;
		Vector2f uvTile = new Vector2f(u, v);
		return uvTile;
	}

	public void fixNormals(int u, int v) {

		TerrainBlock tb = currentBlock;

		TerrainBlock right = findRightBlock(u, v);
		TerrainBlock down = findDownBlock(u, v);
		int tbSize = tb.getSize();
		if (right != null) {
			float[] normData = new float[3];
			for (int y = 0; y < tbSize; y++) {
				int index1 = ((y + 1) * tbSize) - 1;
				int index2 = (y * tbSize);
//				right.getNormalBuffer().position(index2 * 3);
//				right.getNormalBuffer().get(normData);
//				tb.getNormalBuffer().position(index1 * 3);
//				tb.getNormalBuffer().put(normData);
				tb.getNormalBuffer().position(index2 * 3);
				tb.getNormalBuffer().get(normData);
				right.getNormalBuffer().position(index1 * 3);
				right.getNormalBuffer().put(normData);
			
			}
			if (right.getVBOInfo() != null)
				right.getVBOInfo().setVBONormalID(-1);
		}
		if (down != null) {
			int rowStart = ((tbSize - 1) * tbSize);
			float[] normData = new float[3];
			for (int z = 0; z < tbSize; z++) {
				int index1 = rowStart + z;
				int index2 = z;
				
				tb.getNormalBuffer().position(index2 * 3);
				tb.getNormalBuffer().get(normData);
				down.getNormalBuffer().position(index1 * 3);
				down.getNormalBuffer().put(normData);
				
				
//				down.getNormalBuffer().position(index2 * 3);
//				down.getNormalBuffer().get(normData);
//				tb.getNormalBuffer().position(index1 * 3);
//				tb.getNormalBuffer().put(normData);
			}
			if (down.getVBOInfo() != null)
				down.getVBOInfo().setVBONormalID(-1);
		}
		if (tb.getVBOInfo() != null)
			tb.getVBOInfo().setVBONormalID(-1);
	}

	private TerrainBlock findRightBlock(int u, int v) {
		TerrainBlock tb=null;
		if (u < 1) {
			return null;
		}
		HashMap<Integer, CachedTerrainBlock> vTile = uIndexedTiles.get(u-1);
		if (vTile != null) {
			tb = vTile.get((v));
		}
		return tb;
	}
	
	private TerrainBlock findDownBlock(int u, int v) {
		TerrainBlock tb=null;
		if (v < 1) {
			return null;
		}
		HashMap<Integer, CachedTerrainBlock> vTile = uIndexedTiles.get(u);
		if (vTile != null) {
			tb = vTile.get((v-1));
		}
		return tb;
	}

	private AbstractHeightMap loadHeightMap(int u, int v) {
		RawHeightMap rawMap = new RawHeightMap(getTerrainFileName(u, v),
				tileSize);
		if (!rawMap.load()) {
			return null;
		}
		return rawMap;
	}

	private String getTerrainFileName(int u, int v) {
		return (new File(terrainName + u + "_" + v + ".raw").getAbsolutePath());
	}

	public RenderState setRenderState(RenderState rs) {
		return currentBlock.setRenderState(rs);
	}
}
