package de.mbws.client.worldloader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.jme.scene.Node;
import com.jmex.terrain.TerrainBlock;

public class SectionController {
	
	String worldPath;
	SyncTaskQueue taskQueue;
	ObjectLoader loader;
	Map<String, Section> sectionCache = new HashMap<String, Section>();


	/**
	 * A Section consists of a terrain and a list of JME-<code>Nodes</code>.
	 */
	private static class Section {
		TerrainBlock terrain;
		List<Node> objects = new ArrayList<Node>();
		boolean complete;
	}

	SectionController(SyncTaskQueue taskQueue, ObjectLoader loader, String worldPath) {
		this.taskQueue = taskQueue;
		this.loader = loader;
		this.worldPath = worldPath;
	}
	
	abstract class SectionTask {
		int col, row;

		SectionTask(int col, int row) {
			this.col = col;
			this.row = row;
		}
		
		Section getSection() {
			return sectionCache.get(col + "_" + row);
		}
	}
	
	class LoadTerrainBlockTask extends SectionTask implements Runnable {
		public LoadTerrainBlockTask(int col, int row) {
			super(col, row);
		}

		public void run() {
			try {
				getSection().terrain = loader.loadTerrainBlock(col, row);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	class InitObjectLoadingTask extends SectionTask implements Runnable {
		public InitObjectLoadingTask(int col, int row) {
			super(col, row);
		}

		public void run() {
			List<ObjectRepository.ModelInstance> list = loader.loadSectionObjectList(worldPath);
			// enqueue LoadTasks for all objects
			Iterator it = list.iterator();
			while (it.hasNext()) {
				// addInstance
			}
		}
	}
	
	void preloadSection(int col, int row) {
		if (!sectionCache.containsKey(col+"_"+row)) {
			sectionCache.put(col+"_"+row, new Section());
			taskQueue.enqueue("loadTB"+col+"_"+row, new LoadTerrainBlockTask(col, row));
			
			
		}
	}
	
	

}
