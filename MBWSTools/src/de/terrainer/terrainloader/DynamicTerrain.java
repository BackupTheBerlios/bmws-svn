package de.terrainer.terrainloader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.scene.Node;
import com.jme.system.DisplaySystem;
import com.jmex.terrain.TerrainBlock;

/**
 * @author Axel Sammet
 */
public class DynamicTerrain extends Node {

	int sectionRows;
	int sectionColumns;
	String worldPath;
	float spatialScale = 5;
	float heightScale = 0.3f;
	int sectionResolution = 129;
	float sectionWidth = spatialScale * (sectionResolution-1);
	float visibilityRadius2 = 9*sectionWidth*sectionWidth;
	float prefetchRadius = 3*sectionWidth;
	float unloadRadius;

	Map<String, TerrainBlock> sectionCache = new HashMap<String, TerrainBlock>();

	TerrainLoader terrainLoader;
	DisplaySystem display;
	
	public DynamicTerrain() {
		super("DynamicTerrain");
	}

	/**
	 * @param pathForWorldDescription
	 *            path to the world description file <b> without </b> file
	 *            extension.
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public void init(DisplaySystem display, String pathForWorldDescription) throws SAXException,
			IOException {
		this.worldPath = pathForWorldDescription;
		this.display = display;
		this.terrainLoader = new TerrainLoader(this);
		terrainLoader.loadWorldDescription(worldPath + ".wld");

		for (int x = 0; x < sectionColumns; x++) {
			for (int y = 0; y < sectionRows; y++) {
				sectionCache.put(x + "_" + y, terrainLoader.loadTerrainBlock(x, y));
			}
		}
		

	}

	private void addVisibleSections(Vector3f position) {
		int xstart = Math.max((int) ((position.x - prefetchRadius) / sectionWidth), 0);
		int xend = Math.min((int) ((position.x + prefetchRadius) / sectionWidth), sectionColumns-1);
		int ystart = Math.max((int) ((position.y - prefetchRadius) / sectionWidth), 0);
		int yend = Math.min((int) ((position.y + prefetchRadius) / sectionWidth), sectionRows-1);
		for (int x = xstart; x < xend; x++) {
			for (int y = ystart; y < yend; y++) {
				Vector3f sectionPosition = new Vector3f(x * sectionWidth + sectionWidth / 2, 0, y
						* sectionWidth + sectionWidth / 2);
				if (isInVisibleRange(position, sectionPosition)) {
					TerrainBlock tb = sectionCache.get(x + "_" + y);
					attachChild(tb);
				}
			}
		}
	}

	private void removeInvisibleSections(Vector3f position) {

	}

	private boolean isInVisibleRange(Vector3f pos1, Vector3f pos2) {
		return (pos1.x * pos1.x - pos2.x * pos2.x + pos1.y * pos1.y - pos2.y * pos2.y) < visibilityRadius2;
	}

	public void update(Camera cam) {
		Vector3f location = cam.getLocation();
		Vector3f viewdir = cam.getDirection();
		
		// identify visible sections
		
		// remove sections from the model, that are not visible anymore
		if (getChildren() != null)
			detachAllChildren();

		// add visible sections, that are still missing in the model
		addVisibleSections(location);

		// remove preloaded terrain

		// preload terrain for cache

	}

	public static void main(String[] args) {
		try {
			DynamicTerrain dt = new DynamicTerrain();
			dt.init(null, "..\\MBWSClient\\data\\world\\world");
		}
		catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
