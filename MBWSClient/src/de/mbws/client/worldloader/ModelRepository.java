package de.mbws.client.worldloader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jme.scene.Node;
import com.jmex.terrain.TerrainBlock;

/**
 * The ModelRepository manages objects in the (game) world. It consists of a blue-print repository
 * and an instance repository. This way it ensures, that objects can be reused without the overhead
 * of reloading the whole object.
 * 
 * @author Axel
 */
public class ModelRepository {

	Map<String, Section> sectionCache = new HashMap<String, Section>();

	/**
	 * A Section consists of a terrain and a list of JME-<code>Nodes</code>.
	 */
	private static class Section {
		TerrainBlock terrain;
		List<Node> objects = new ArrayList<Node>();
	}

	/**
	 * ModelInstance represents one instance of a JME-object.
	 */
	public static class ModelInstance {
		String name;
		float pos_x;
		float pos_y;
		float pos_z;
		float scale;

		public ModelInstance(String name, float x, float y, float z, float scale) {
			this.name = name;
			this.pos_x = x;
			this.pos_y = y;
			this.pos_z = z;
			this.scale = scale;
		}
	}

	/**
	 * Loads a section with all contained objects into the section cache.
	 * 
	 * @param section_x
	 * @param section_y
	 * @param tb
	 */
	void preloadSection(int section_x, int section_y) {
		// TODO loading of the description and loading of the terrain, objects ... has to be split
		// by different load tasks for the queue
	}

	/**
	 * Removes a section from the cache.
	 * 
	 * @param section_x
	 * @param section_y
	 */
	void removeSection(int section_x, int section_y) {

	}

	private void prelaodModelInstance(int section_x, int section_y, ModelInstance mod) {
		// if model is not in blue-print repository enqueue task to load it

		// register instance
	}
}
