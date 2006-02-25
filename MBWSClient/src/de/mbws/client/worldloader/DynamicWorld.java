package de.mbws.client.worldloader;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import com.jme.bounding.BoundingSphere;
import com.jme.image.Texture;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Node;
import com.jme.scene.shape.Dome;
import com.jme.scene.state.FogState;
import com.jme.scene.state.LightState;
import com.jme.scene.state.TextureState;
import com.jme.system.DisplaySystem;
import com.jme.util.TextureManager;
import com.jmex.terrain.TerrainBlock;

/**
 * DynamicWorld is a super node for the terrain and static objects of a world. The world is
 * described in a xml-file and consists of various quadratic terrains forming a rectangular world.
 * The quadratic terrains reside in the same directory as the world description. They are loaded
 * dynamically and attached as subnodes, when they are within a predefined visibility radius of the
 * camera.
 * 
 * @author Axel Sammet
 */
public class DynamicWorld extends Node {

	private static Logger logger = Logger.getLogger(DynamicWorld.class);

	int sectionRows;
	int sectionColumns;
	String worldPath;
	float spatialScale = 12;
	float heightScale = 0.3f;
	int sectionResolution = 65;
	float sectionWidth = spatialScale * (sectionResolution - 1);
	float visibilityRadius = 3f * sectionWidth;
	float prefetchRadius = 4.5f * sectionWidth;
	float unloadRadius = 6.f * sectionWidth;
	float visibilityRadius2;
	float unloadRadius2;
	float prefetchRadius2;

	Map<String, TerrainBlock> sectionCache = new HashMap<String, TerrainBlock>();
	Set<TerrainBlock> visibleSections = new HashSet<TerrainBlock>();
	ObjectRepository modelRepository;
	AbstractTaskQueue taskQueue;
	ObjectLoader loader;
	DisplaySystem display;
	Dome skydome;

	public DynamicWorld() {
		super("DynamicWorld");
		taskQueue = new SyncTaskQueue();
	}

	/**
	 * @param pathForWorldDescription path to the world description file <b> without </b> file
	 *            extension.
	 * @throws IOException
	 * @throws SAXException
	 */
	public void init(Node root, DisplaySystem display, String pathForWorldDescription)
			throws SAXException, IOException {
		this.worldPath = pathForWorldDescription;
		this.display = display;
		this.loader = new ObjectLoader(this);
		loader.loadWorldDescription(worldPath + ".wld");
		visibilityRadius2 = visibilityRadius * visibilityRadius;
		prefetchRadius2 = prefetchRadius * prefetchRadius;
		unloadRadius2 = unloadRadius * unloadRadius;

		createSky(root, display);
	}

	private void createSky(Node root, DisplaySystem display) {
		FogState fs = display.getRenderer().createFogState();
		fs.setDensity(0.5f);
		fs.setEnabled(true);
		fs.setColor(new ColorRGBA(0.8f, 0.8f, 0.8f, 0.8f));
		fs.setStart(1000);
		fs.setEnd(2000);
		fs.setDensityFunction(FogState.DF_LINEAR);
		fs.setApplyFunction(FogState.AF_PER_VERTEX);
		root.setRenderState(fs);

		skydome = new Dome("Skydome", 5, 24, 2200);
		skydome.setModelBound(new BoundingSphere());
		skydome.updateModelBound();
        LightState lightState = display.getRenderer().createLightState();
        lightState.setEnabled(false);
        skydome.setRenderState(lightState);
        skydome.setLightCombineMode(LightState.REPLACE);
		Texture domeTexture = TextureManager.loadTexture(
				"..\\MBWSClient\\data\\images\\wolken_16.jpg",
				Texture.MM_LINEAR, Texture.FM_LINEAR);
		TextureState ts = display.getRenderer().createTextureState();
		ts.setTexture(domeTexture);
		skydome.setRenderState(ts);
        skydome.setTextureCombineMode(TextureState.REPLACE);
		attachChild(skydome);
		updateRenderState();
	}

	/**
	 * Destroys all background processes initiated by DynamicTerain.
	 * 
	 * @deprecated Unnecessary at the moment.
	 */
	public void destroy() {
	}

	private void preloadAndAddSections(Vector3f position) {
		int xstart = Math.max((int) ((position.x - prefetchRadius) / sectionWidth), 0);
		int xend = Math.min((int) ((position.x + prefetchRadius) / sectionWidth),
				sectionColumns - 1);
		int ystart = Math.max((int) ((position.z - prefetchRadius) / sectionWidth), 0);
		int yend = Math.min((int) ((position.z + prefetchRadius) / sectionWidth), sectionRows - 1);
		for (int x = xstart; x < xend; x++) {
			for (int y = ystart; y < yend; y++) {
				String key = x + "_" + y;
				// preloadSection
				Vector3f terrainMidPoint = new Vector3f(x * sectionWidth + sectionWidth / 2, 0, y
						* sectionWidth + sectionWidth / 2);
				preloadSection(position, x, y, key, terrainMidPoint);
				// addVisibleSection
				addVisibleSection(position, key, terrainMidPoint);
			}
		}

	}

	private void addVisibleSection(Vector3f position, String key, Vector3f terrainMidPoint) {
		TerrainBlock tb = sectionCache.get(key);
		if (isInRange(position, terrainMidPoint, visibilityRadius2) && tb != null
				&& !visibleSections.contains(tb)) {
			// if (!sectionCache.containsKey(key)) {
			// taskQueue.waitForTask(key);
			// }
			attachChild(tb);
			visibleSections.add(tb);
			// System.err.println("attaching "+terrainMidPoint+" view:
			// "+position);
		}
	}

	private class BlockLoader implements Runnable {
		int x, z;

		public BlockLoader(int x, int z) {
			this.x = x;
			this.z = z;
		}

		public void run() {
			try {
				sectionCache.put(x + "_" + z, loader.loadTerrainBlock(x, z));
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void preloadSection(Vector3f position, int x, int z, String key,
			Vector3f terrainMidPoint) {
		if (!sectionCache.containsKey(key)) {
			if (isInRange(position, terrainMidPoint, prefetchRadius2)) {
				taskQueue.enqueue(key, new BlockLoader(x, z));
			}
		}
	}

	private void removeInvisibleSections(Vector3f position) {
		Iterator<TerrainBlock> it = visibleSections.iterator();
		while (it.hasNext()) {
			TerrainBlock tb = it.next();
			Vector3f terrainMidPoint = new Vector3f(tb.getLocalTranslation().x + sectionWidth / 2,
					0, tb.getLocalTranslation().z + sectionWidth / 2);
			if (!isInRange(terrainMidPoint, position, visibilityRadius2)) {
				it.remove();
				logger.debug("removing from world cache " + tb);
				tb.removeFromParent();
			}
		}
	}

	private void unloadDistantSections(Vector3f position) {
		Iterator<Entry<String, TerrainBlock>> it = sectionCache.entrySet().iterator();
		while (it.hasNext()) {
			Vector3f terrainOrig = it.next().getValue().getLocalTranslation();
			Vector3f terrainMidPoint = new Vector3f(terrainOrig.x + sectionWidth / 2, 0,
					terrainOrig.z + sectionWidth / 2);
			if (!isInRange(terrainMidPoint, position, unloadRadius2)) {
				it.remove();
			}
		}
	}

	private boolean isInRange(Vector3f pos1, Vector3f pos2, float squareOfRange) {
		return ((pos1.x - pos2.x) * (pos1.x - pos2.x) + (pos1.z - pos2.z) * (pos1.z - pos2.z)) < squareOfRange;
	}

	/**
	 * Updates the world. Call this method from within the <code> update()</code>-method of your
	 * <code>BaseGame</code>. For the given camera position all terrain sections within the
	 * preload-radius are loaded from the world description. Then all visible sections are added to
	 * the DynamicTerrain. Vice versa all invisible sections are detached and sections outside the
	 * unloadRadius are left for garbage collection.
	 * 
	 * @param cam
	 */
	public void update(Camera cam) {
		((SyncTaskQueue) taskQueue).process(15);
		Vector3f location = cam.getLocation();
		skydome.setLocalTranslation(new Vector3f(location.x, location.y-1000, location.z));
		unloadDistantSections(location);
		removeInvisibleSections(location);
		preloadAndAddSections(location);
		updateGeometricState(0.0f, true);
		updateRenderState();

	}

	private TerrainBlock getSectionAt(float x, float z) {
		String key = (int) (x / sectionWidth) + "_" + (int) (z / sectionWidth);
		return sectionCache.get(key);
	}

	/**
	 * Determines the height at the given x-z-position. The y-component of the given location vector
	 * will be ignored.
	 * 
	 * @param location
	 * @return
	 */
	public float getHeight(Vector3f location) {
		try {
			float ret = getSectionAt(location.x, location.z).getHeight(location.x % sectionWidth,
					location.z % sectionWidth);
			return ret;
		}
		catch (Exception e) {
			return 0;
		}
	}

	/**
	 * Determines the steepness at the given x-z-position. The y-component of the given location
	 * vector will be ignored. The returned value is 1 - the cosine of the angle between the terrain
	 * and a flat plane.
	 * 
	 * @param location
	 * @return steepness in a value ranging from 0 to 1
	 */
	public float getSteepness(Vector3f location) {
		Vector3f normal = new Vector3f();
		try {
			getSectionAt(location.x, location.z).getSurfaceNormal(location, normal);
		}
		catch (Exception e) {
			// do nothing
		}
		return 1 - normal.y;
	}
}
