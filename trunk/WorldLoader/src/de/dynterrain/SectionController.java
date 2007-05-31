package de.dynterrain;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;



import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jmex.terrain.TerrainBlock;

public class SectionController {

	private static Logger logger = Logger.getLogger(SectionController.class.getName());

	private String worldPath;
	private ObjectLoader loader;
	private Map<String, SectionNode> sectionCache = new HashMap<String, SectionNode>();
	private ObjectRepository objectRepository;

	/**
	 * A Section consists of a terrain and a list of <code>DelayedSpatials</code>.
	 */
	class SectionNode extends Node {
		boolean complete;
		TerrainBlock terrain;

		protected SectionNode(String name) {
			super(name);
		}

		protected void addTerrain(TerrainBlock tb) {
			terrain = tb;
			attachChild(tb);
		}

		@Override
		protected void finalize() throws Throwable {
			Iterator it = getChildren().iterator();
			while (it.hasNext()) {
				objectRepository.destroyObjectClone((Spatial) it.next());
			}
			super.finalize();
		}

		TerrainBlock getTerrainBlock() {
			return terrain;
		}
	}

	SectionController(ObjectLoader loader, String worldPath) {
		this.loader = loader;
		this.worldPath = worldPath;
		objectRepository = new ObjectRepository(loader);
	}

	/**
	 * An abstract super class for tasks which work with a denoted section.
	 */
	private abstract class SectionTask {
		int col, row;

		SectionTask(int col, int row) {
			this.col = col;
			this.row = row;
		}

		SectionNode getSection() {
			return sectionCache.get(col + "_" + row);
		}
	}

	/**
	 * Task to load one terrain block.
	 */
	private class LoadTerrainBlockTask extends SectionTask implements Runnable {
		public LoadTerrainBlockTask(int col, int row) {
			super(col, row);
		}

		public void run() {
			try {
				getSection().addTerrain(loader.loadTerrainBlock(col, row));
			}
			catch (IOException e) {
				logger.severe("LoadTerrainBlockTask "+e);
			}
		}
	}

	/**
	 * Task to create an spatial
	 */
	private class CreateSpatialTask extends SectionTask implements Runnable {
		ObjectDescription descr;

		CreateSpatialTask(int col, int row, ObjectDescription descr) {
			super(col, row);
			this.descr = descr;
		}

		public void run() {
			objectRepository.addObjectClone(getSection(), descr);
		}
	}

	/**
	 * Task to read the section description. It will enqueue more tasks to create subsequently all
	 * objects.
	 */
	private class CreateSectionObjectsTask extends SectionTask implements Runnable {
		CreateSectionObjectsTask(int col, int row) {
			super(col, row);
		}

		public void run() {
			List<ObjectDescription> list = loader.loadSectionObjectList(worldPath+"_"+key(col, row)+".xml");
			// enqueue LoadTasks for all objects
			int number = 0;
			Iterator<ObjectDescription> it = list.iterator();
			while (it.hasNext()) {
				AsyncTaskQueue.getInstance().enqueue("LoadObject" + col + "_" + row + "_" + number,
						new CreateSpatialTask(col, row, it.next()));
				number++;
			}
		}
	}

	private String key(int col, int row) {
		return col + "_" + row;
	}

	/**
	 * Loads a section with all contained objects into the section cache.
	 * 
	 * @param col
	 * @param row
	 * @param tb
	 */
	void preloadSection(int col, int row) {
		if (!sectionCache.containsKey(key(col, row))) {
			sectionCache.put(key(col, row), new SectionNode(key(col, row)));
			AsyncTaskQueue.getInstance().enqueue("loadTB" + key(col, row), new LoadTerrainBlockTask(col, row));
			AsyncTaskQueue.getInstance().enqueue("loadSectionObjects" + key(col, row), new CreateSectionObjectsTask(
					col, row));
		}
	}

	/**
	 * Removes a section from the cache.
	 * 
	 * @param col
	 * @param row
	 */
	void removeSection(int col, int row) {
		sectionCache.remove(key(col, row));
	}

	SectionNode getSection(int col, int row) {
		return sectionCache.get(key(col, row));
	}

	Iterator<SectionNode> sectionIterator() {
		return sectionCache.values().iterator();
	}

	boolean contains(int col, int row) {
		return sectionCache.containsKey(key(col, row));
	}
}
