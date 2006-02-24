package de.mbws.client.worldloader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jmex.terrain.TerrainBlock;

import de.mbws.client.worldloader.ObjectRepository.DelayedSpatial;

public class SectionController {
	
	private static Logger logger = Logger.getLogger(SectionController.class);

	String worldPath;
	SyncTaskQueue taskQueue;
	ObjectLoader loader;
	Map<String, Section> sectionCache = new HashMap<String, Section>();

	/**
	 * A Section consists of a terrain and a list of JME-<code>Nodes</code>.
	 */
	private static class Section {
		TerrainBlock terrain;
		List<DelayedSpatial> objects = new LinkedList<DelayedSpatial>();
		boolean complete;
	}

	SectionController(SyncTaskQueue taskQueue, ObjectLoader loader, String worldPath) {
		this.taskQueue = taskQueue;
		this.loader = loader;
		this.worldPath = worldPath;
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

		Section getSection() {
			return sectionCache.get(col + "_" + row);
		}
	}

	/**
	 * Task to load one terrain block.
	 */
	class LoadTerrainBlockTask extends SectionTask implements Runnable {
		public LoadTerrainBlockTask(int col, int row) {
			super(col, row);
		}

		public void run() {
			try {
				getSection().terrain = loader.loadTerrainBlock(col, row);
			}
			catch (IOException e) {
				logger.error(e);
			}
		}
	}

	/**
	 * Task to read the section description. It will enqueue more tasks to load subsequently all
	 * objects.
	 */
	class InitObjectLoadingTask extends SectionTask implements Runnable {
		public InitObjectLoadingTask(int col, int row) {
			super(col, row);
		}

		public void run() {
			List<ObjectDescription> list = loader.loadSectionObjectList(worldPath);
			// enqueue LoadTasks for all objects
			Iterator it = list.iterator();
			while (it.hasNext()) {
				// get object from repository and set instance in Section
				// if not available enqueue object loading and set instance in Section
			}
		}
	}

	/**
	 * Loads a section with all contained objects into the section cache.
	 * 
	 * @param section_x
	 * @param section_y
	 * @param tb
	 */
	void preloadSection(int col, int row) {
		if (!sectionCache.containsKey(col + "_" + row)) {
			sectionCache.put(col + "_" + row, new Section());
			taskQueue.enqueue("loadTB" + col + "_" + row, new LoadTerrainBlockTask(col, row));
		}
	}

	/**
	 * Removes a section from the cache.
	 * 
	 * @param section_x
	 * @param section_y
	 */
	void removeSection(int section_x, int section_y) {

	}

}
