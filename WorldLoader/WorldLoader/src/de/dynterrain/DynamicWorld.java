package de.dynterrain;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

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
 * described in a xml-file and consists of various quadratic sections forming a rectangular world.
 * The quadratic terrains reside in the same directory as the world description. They are loaded
 * dynamically and attached as subnodes, when they are within a predefined visibility radius of the
 * camera.
 * 
 * @author Axel Sammet
 */
public class DynamicWorld extends Node {

	private static Logger logger = Logger.getLogger(DynamicWorld.class.getClass().getName());

	WorldDescription worldDescr;
	float visibilityRadius = -1;
	float prefetchRadius;
	float unloadRadius;
	float attachRadius2;
	float unloadRadius2;
	float prefetchRadius2;
	float skyHeightOffset;

	Set<SectionController.SectionNode> visibleSections = new HashSet<SectionController.SectionNode>();
	ObjectRepository modelRepository;
	ObjectLoader loader;
	SectionController sectionController;
	DetailTextureManager dtm;
	DisplaySystem display;
	Node root;
	Dome skydome;

	public DynamicWorld() {
		super("DynamicWorld");
		// start the async task queue
		AsyncTaskQueue.getInstance();
	}

	/**
	 * @param pathForWorldDescription path to the world description file <b> without </b> file
	 *            extension.
	 * @throws IOException
	 * @throws SAXException
	 */
	public void init(Node root, Camera cam, DisplaySystem display, String pathToWorldDescription,
			String pathToModels) throws SAXException, IOException {
		this.display = display;
		this.root = root;
		loader = new ObjectLoader();
		loader.setObjectPath(pathToModels);
		sectionController = new SectionController(loader, pathToWorldDescription);
		worldDescr = loader.loadWorldDescription(pathToWorldDescription);
		if (visibilityRadius<0)
			setVisibilityRadius(2000);
		preloadAndAddSections(cam.getLocation());
		createSky();
		createFog();
		AsyncTaskQueue.getInstance().waitForEmptyQueue();
		logger.info("DynamicWorld finished initialization.");
	}

	private void createSky() {
		detachChildNamed("Skydome");
		float skyRadius = visibilityRadius * 1.1f;
		skydome = new Dome("Skydome", 5, 24, skyRadius);
		skyHeightOffset = skyRadius / 2;
		skydome.setModelBound(new BoundingSphere());
		skydome.updateModelBound();
		LightState lightState = display.getRenderer().createLightState();
		lightState.setEnabled(true);
		//lightState.setTwoSidedLighting(true);
		lightState.setGlobalAmbient(ColorRGBA.white);
		skydome.setRenderState(lightState);
		skydome.setLightCombineMode(LightState.REPLACE);
		Texture domeTexture = TextureManager.loadTexture("../MBWSClient/data/images/wolken.jpg",
				Texture.MM_LINEAR, Texture.FM_LINEAR);
		TextureState ts = display.getRenderer().createTextureState();
		ts.setTexture(domeTexture);
		skydome.setRenderState(ts);
		//skydome.setTextureCombineMode(TextureState.REPLACE);
		attachChild(skydome);
		updateRenderState();
	}

	private void createFog() {
		FogState fs = display.getRenderer().createFogState();
		fs.setDensity(0.5f);
		fs.setEnabled(true);
		fs.setColor(new ColorRGBA(0.8f, 0.8f, 0.8f, 0.8f));
		fs.setStart(50);
		fs.setEnd(visibilityRadius);
		fs.setDensityFunction(FogState.DF_LINEAR);
		fs.setApplyFunction(FogState.AF_PER_VERTEX);
		root.setRenderState(fs);
	}

	/**
	 * Destroys all background processes initiated by DynamicTerain.
	 * 
	 */
	public void destroy() {
		AsyncTaskQueue.getInstance().shutdownQueueProcessor();
	}

	private void preloadAndAddSections(Vector3f position) {
		float sectionWidth = worldDescr.getSectionWidth();
		int xstart = Math.max((int) ((position.x - prefetchRadius) / sectionWidth), 0);
		int xend = Math.min((int) ((position.x + prefetchRadius) / sectionWidth),
				worldDescr.sectionColumns - 1);
		int ystart = Math.max((int) ((position.z - prefetchRadius) / sectionWidth), 0);
		int yend = Math.min((int) ((position.z + prefetchRadius) / sectionWidth),
				worldDescr.sectionRows - 1);
		for (int col = xstart; col < xend; col++) {
			for (int row = ystart; row < yend; row++) {
				// preloadSection
				Vector3f terrainMidPoint = new Vector3f(col * sectionWidth + sectionWidth / 2, 0,
						row * sectionWidth + sectionWidth / 2);
				preloadSection(position, col, row, terrainMidPoint);
				// addVisibleSection
				addVisibleSection(position, col, row, terrainMidPoint);
			}
		}

	}

	private void addVisibleSection(Vector3f position, int col, int row, Vector3f terrainMidPoint) {
		SectionController.SectionNode sectionNode = sectionController.getSection(col, row);
		if (isInRange(position, terrainMidPoint, attachRadius2) && sectionNode != null
				&& !visibleSections.contains(sectionNode)) {
			// if (!sectionCache.containsKey(key)) {
			// taskQueue.waitForTask(key);
			// }
			logger.info("Attach visible section " + sectionNode.getName());
			attachChild(sectionNode);
			visibleSections.add(sectionNode);
		}
	}

	private void preloadSection(Vector3f position, int col, int row, Vector3f terrainMidPoint) {
		if (!sectionController.contains(col, row)) {
			if (isInRange(position, terrainMidPoint, prefetchRadius2)) {
				sectionController.preloadSection(col, row);
			}
		}
	}

	private void removeInvisibleSections(Vector3f position) {
		float sectionWidth = worldDescr.getSectionWidth();
		Iterator<SectionController.SectionNode> it = visibleSections.iterator();
		while (it.hasNext()) {
			SectionController.SectionNode node = it.next();
			if (node.getTerrainBlock() != null) {
				Vector3f terrainMidPoint = new Vector3f(node.getTerrainBlock()
						.getLocalTranslation().x
						+ sectionWidth / 2, 0, node.getTerrainBlock().getLocalTranslation().z
						+ sectionWidth / 2);
				if (!isInRange(terrainMidPoint, position, attachRadius2)) {
					it.remove();
					logger.info("Removing invisible section " + node.getName());
					node.removeFromParent();
				}
			}
		}
	}

	private void unloadDistantSections(Vector3f position) {
		float sectionWidth = worldDescr.getSectionWidth();
		Iterator<SectionController.SectionNode> it = sectionController.sectionIterator();
		while (it.hasNext()) {
			SectionController.SectionNode node = it.next();
			if (node.getTerrainBlock() != null) {
				Vector3f terrainOrig = node.getTerrainBlock().getLocalTranslation();
				Vector3f terrainMidPoint = new Vector3f(terrainOrig.x + sectionWidth / 2, 0,
						terrainOrig.z + sectionWidth / 2);
				if (!isInRange(terrainMidPoint, position, unloadRadius2)) {
					it.remove();
					logger.info("Removing from cache " + node.getName());
				}
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
		long time = System.currentTimeMillis();
		SyncTaskQueue.getInstance().process(15);
		Vector3f location = cam.getLocation();
		skydome.setLocalTranslation(new Vector3f(location.x, location.y - skyHeightOffset,
				location.z));
		unloadDistantSections(location);
		removeInvisibleSections(location);
		preloadAndAddSections(location);
		updateGeometricState(0.0f, true);
		updateRenderState();
		final long limit = 20;
		long cycletime = System.currentTimeMillis() - time;
		if (cycletime > limit) {
			logger.warning("Update overrun limit of " + limit + " ms: " + cycletime + " ms");
		}

	}

	TerrainBlock getTerrainAt(float x, float z) {
		float sectionWidth = worldDescr.getSectionWidth();
		return sectionController.getSection((int) (x / sectionWidth), (int) (z / sectionWidth))
				.getTerrainBlock();
	}

	/**
	 * Determines the height at the given x-z-position. The y-component of the given location vector
	 * will be ignored.
	 * 
	 * @param location
	 * @return
	 */
	public float getHeight(Vector3f location) {
		float sectionWidth = worldDescr.getSectionWidth();
		try {
			float ret = getTerrainAt(location.x, location.z).getHeight(location.x % sectionWidth,
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
			getTerrainAt(location.x, location.z).getSurfaceNormal(location, normal);
		}
		catch (Exception e) {
			// do nothing
		}
		return 1 - normal.y;
	}

	public float getVisibilityRadius() {
		return visibilityRadius;
	}

	public void setVisibilityRadius(float visibilityRadius) {
		if (worldDescr == null) {
			throw new RuntimeException("World not initialized! You have to use init() before using this method.");
		}
		this.visibilityRadius = visibilityRadius;
		prefetchRadius = 1.5f * visibilityRadius;
		unloadRadius = 1.2f * prefetchRadius;
		attachRadius2 = (visibilityRadius + 1.5f * worldDescr.getSectionWidth())
				* (visibilityRadius + 1.5f * worldDescr.getSectionWidth());
		prefetchRadius2 = prefetchRadius * prefetchRadius;
		unloadRadius2 = unloadRadius * unloadRadius;
		createSky();
		createFog();
	}
}
