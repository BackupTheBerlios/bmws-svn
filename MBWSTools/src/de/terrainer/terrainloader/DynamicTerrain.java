package de.terrainer.terrainloader;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.xml.sax.SAXException;

import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.scene.Node;
import com.jme.system.DisplaySystem;
import com.jmex.terrain.TerrainBlock;

/**
 * DynamicTerrain is a super node for the terrain of a world. The world is
 * described in a xml-file and consists of various quadratic terrains forming a
 * rectangular world. The quadratic terrains reside in the same directory as the
 * world description. They are loaded dynamically and attached as subnodes, when
 * they are within a predefined visibility radius of the camera.
 * 
 * @author Axel Sammet
 */
public class DynamicTerrain extends Node {

	int sectionRows;
	int sectionColumns;
	String worldPath;
	float spatialScale = 5;
	float heightScale = 0.3f;
	int sectionResolution = 65;
	float sectionWidth = spatialScale * (sectionResolution - 1);
	float visibilityRadius = 4f * sectionWidth;
	float visibilityRadius2 = visibilityRadius * visibilityRadius;
	float prefetchRadius = 4f * sectionWidth;
	float prefetchRadius2 = prefetchRadius * prefetchRadius;
	float unloadRadius;

	// TODO Check if Sets are sufficient
	Map<String, TerrainBlock> sectionCache = new HashMap<String, TerrainBlock>();
	Set<TerrainBlock> visibleSections = new HashSet<TerrainBlock>();

	TerrainLoader terrainLoader;
	DisplaySystem display;

	public DynamicTerrain() {
		super("DynamicTerrain");
	}

	/**
	 * @param pathForWorldDescription
	 *            path to the world description file <b> without </b> file
	 *            extension.
	 * @throws IOException
	 * @throws SAXException
	 */
	public void init(DisplaySystem display, String pathForWorldDescription) throws SAXException,
			IOException {
		this.worldPath = pathForWorldDescription;
		this.display = display;
		this.terrainLoader = new TerrainLoader(this);
		terrainLoader.loadWorldDescription(worldPath + ".wld");

		// TODO replace by dynamic prefetching
		for (int x = 0; x < sectionColumns; x++) {
			for (int y = 0; y < sectionRows; y++) {
				sectionCache.put(x + "_" + y, terrainLoader.loadTerrainBlock(x, y));
			}
		}

	}

	private void addVisibleSections(Vector3f position) {
		int xstart = Math.max((int) ((position.x - visibilityRadius) / sectionWidth), 0);
		int xend = Math.min((int) ((position.x + visibilityRadius) / sectionWidth),
				sectionColumns - 1);
		int ystart = Math.max((int) ((position.z - visibilityRadius) / sectionWidth), 0);
		int yend = Math.min((int) ((position.z + visibilityRadius) / sectionWidth), sectionRows - 1);
		for (int x = xstart; x < xend; x++) {
			for (int y = ystart; y < yend; y++) {
				TerrainBlock tb = sectionCache.get(x + "_" + y);
				Vector3f terrainMidPoint = new Vector3f(tb.getLocalTranslation().x + sectionWidth
						/ 2, 0, tb.getLocalTranslation().z + sectionWidth / 2);
				if (isInVisibleRange(position, terrainMidPoint) && !visibleSections.contains(tb)) {
					attachChild(tb);
					visibleSections.add(tb);
					// System.err.println("attaching "+terrainMidPoint+" view:
					// "+position);
				}
			}
		}
	}

	private void removeInvisibleSections(Vector3f position) {
		Iterator<TerrainBlock> it = visibleSections.iterator();
		while (it.hasNext()) {
			TerrainBlock tb = it.next();
			Vector3f terrainMidPoint = new Vector3f(tb.getLocalTranslation().x + sectionWidth / 2,
					0, tb.getLocalTranslation().z + sectionWidth / 2);
			if (!isInVisibleRange(terrainMidPoint, position)) {
				it.remove();
				System.err.println("removing " + tb);
				tb.removeFromParent();
				System.err.println("detaching " + terrainMidPoint + " view: " + position);
			}
		}
	}

	private boolean isInVisibleRange(Vector3f pos1, Vector3f pos2) {
		return ((pos1.x - pos2.x) * (pos1.x - pos2.x) + (pos1.z - pos2.z) * (pos1.z - pos2.z)) < visibilityRadius2;
	}

	public void update(Camera cam) {
		Vector3f location = cam.getLocation();
		Vector3f viewdir = cam.getDirection();

		// identify visible sections

		// remove sections from the model, that are not visible anymore
		removeInvisibleSections(location);

		// add visible sections, that are still missing in the model
		addVisibleSections(location);

		// remove preloaded terrain

		// preload terrain for cache

	}

}
