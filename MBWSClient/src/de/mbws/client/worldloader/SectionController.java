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

	private String worldPath;
	private SyncTaskQueue taskQueue;
	private ObjectLoader loader;
	private Map<String, Section> sectionCache = new HashMap<String, Section>();
	private ObjectRepository objectRepository;

	/**
	 * A Section consists of a terrain and a list of <code>DelayedSpatials</code>.
	 */
	private class Section {
		TerrainBlock terrain;
		List<DelayedSpatial> objects = new LinkedList<DelayedSpatial>();
		boolean complete;

		@Override
		protected void finalize() throws Throwable {
			Iterator<DelayedSpatial> it = objects.iterator();
			while (it.hasNext()) {
				objectRepository.destroyObjectClone(it.next().spatial);
			}
			super.finalize();
		}
	}

	SectionController(SyncTaskQueue taskQueue, ObjectLoader loader, String worldPath) {
		this.taskQueue = taskQueue;
		this.loader = loader;
		this.worldPath = worldPath;
		objectRepository = new ObjectRepository(loader, taskQueue);
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
	private class LoadTerrainBlockTask extends SectionTask implements Runnable {
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
	 * Task to create an spatial 
	 */
	private class CreateSpatialTask extends SectionTask implements Runnable {
		ObjectDescription descr;

		CreateSpatialTask(int col, int row, ObjectDescription descr) {
			super(col, row);
			this.descr = descr;
		}

		public void run() {
			getSection().objects.add(objectRepository.createObjectClone(descr));
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
			List<ObjectDescription> list = loader.loadSectionObjectList(worldPath);
			// enqueue LoadTasks for all objects
			int number = 0;
			Iterator<ObjectDescription> it = list.iterator();
			while (it.hasNext()) {
				taskQueue.enqueue("LoadObject" + col + "_" + row + "_" + number, new CreateSpatialTask(
						col, row, it.next()));
				number++;
			}
		}
	}

	/**
	 * Loads a section with all contained objects into the section cache.
	 * 
	 * @param col
	 * @param row
	 * @param tb
	 */
	void preloadSection(int col, int row) {
		if (!sectionCache.containsKey(col + "_" + row)) {
			sectionCache.put(col + "_" + row, new Section());
			taskQueue.enqueue("loadTB" + col + "_" + row, new LoadTerrainBlockTask(col, row));
			taskQueue.enqueue("loadSectionObjects" + col + "_" + row, new CreateSectionObjectsTask(
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
		sectionCache.remove(col + "_" + row);
	}

}
