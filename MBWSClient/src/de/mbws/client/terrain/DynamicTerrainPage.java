/**
 * 
 */
package de.mbws.client.terrain;

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

	// // Tiles
	// private int u;
	// private int v;

	// Tiles are stored in a Hashmap IN the HashMap uIndexedTiles
	// xIndexedTiles contains the y Terrainblocks for each X
	private HashMap<Integer, TerrainBlock> yTile = new HashMap();
	private HashMap<Integer, HashMap<Integer, TerrainBlock>> uIndexedTiles = new HashMap();

	private TerrainBlock currentBlock;
	// size of the tiles
	private int tileSize;

	private String terrainName;

	private boolean clod;
	

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
		terrainName = "c:/programmierung/projekte/mbwsclient/src/resources/"+name;
		tileSize = aTileSize;
		stepScale = aStepScale;
	}

	// TODO: Make sense here !

	private float getHeight(Vector2f location) {
		Vector2f tileIndex = mapLocationToTile(location);
		HashMap<Integer, TerrainBlock> vTile = uIndexedTiles.get(tileIndex.x);
		TerrainBlock tb = vTile.get(tileIndex.y);
		float realLocationX = location.x % tileSize;
		float realLocationZ = location.y % tileSize;
		return tb.getHeight(realLocationX, realLocationZ);
	}

	public void attachBlock(Vector2f location) {
		Vector2f tileIndex = mapLocationToTile(location);
		AbstractHeightMap heightMap = loadHeightMap((int) tileIndex.x,
				(int) tileIndex.y);
		if (heightMap != null) {

			TerrainBlock tb = new TerrainBlock("" + (int) location.x
					+ (int) location.y, tileSize, stepScale, heightMap
					.getHeightMap(), new Vector3f(0, 0, 0), clod);
			Vector3f loc3d = new Vector3f(location.x,0,location.y);
			tb.setLocalTranslation(loc3d);
			HashMap<Integer, TerrainBlock> yTile = uIndexedTiles
					.get((int) tileIndex.x);
			if (yTile == null) {
				yTile = new HashMap();
			}
			yTile.put((int) tileIndex.y, tb);
			uIndexedTiles.put((int) tileIndex.x, yTile);
			currentBlock = tb;
			attachChild(tb);
		} else {
			logger.error("Terrain couldnt be loaded for location: "
					+ location.x + " " + location.y);
		}
	}

	private Vector2f mapLocationToTile(Vector2f loc) {
		float u = loc.x / tileSize + 1;
		float v = loc.y / tileSize + 1;
		Vector2f uvTile = new Vector2f(u, v);
		return uvTile;
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
		return (terrainName + u +"_"+v+".raw");
	}

	public RenderState setRenderState (RenderState rs) {
		return currentBlock.setRenderState(rs);
	}
}
