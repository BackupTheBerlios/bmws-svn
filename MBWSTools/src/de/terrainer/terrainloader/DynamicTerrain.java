package de.terrainer.terrainloader;

import java.io.IOException;

import org.xml.sax.SAXException;

import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.scene.Node;
import com.jme.system.DisplaySystem;

/**
 * @author Axel Sammet
 */
public class DynamicTerrain extends Node {

	int sectionRows;
	int sectionColumns;
	String worldPath;
	float visibilityRadius;
	float prefetchRadius;
	float unloadRadius;
	float spatialScale = 5;
	float heightScale = 0.1f;
	int terrainSize;
	
	TerrainLoader terrainLoader;
	DisplaySystem display;

	/**
	 * @param pathForWorldDescription
	 *            path to the world description file <b> without </b> file
	 *            extension.
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public void init(DisplaySystem display, String pathForWorldDescription) throws SAXException, IOException {
		this.worldPath = pathForWorldDescription;
		this.display = display;
		this.terrainLoader = new TerrainLoader(this);
		terrainLoader.loadWorldDescription(worldPath + ".wld");
	}

	public void update(Camera cam) {
		Vector3f location = cam.getLocation();
		Vector3f viewdir = cam.getDirection();
		// identify visible sections

		// remove sections from the model, that are not visible anymore

		// add visible sections, that are still missing in the model

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
