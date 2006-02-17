package de.mbws.client.worldloader;

import java.util.HashMap;
import java.util.Map;

import com.jme.scene.Spatial;

/**
 * The ObjectRepository manages objects in the (game) world. It consists of a blue-print repository
 * and an instance repository. This way it ensures, that objects can be reused without the overhead
 * of reloading the whole object.
 * 
 * @author Axel
 */
public class ObjectRepository {

	Map<String, Spatial> blueprintMap = new HashMap<String, Spatial>();

	/**
	 * ModelInstance represents one instance of a JME-object.
	 */
	public static class ModelInstance {
		String name;
		float pos_x;
		float pos_y;
		float pos_z;
		float scale;
		Spatial instance;

		public ModelInstance(String name, float x, float y, float z, float scale) {
			this.name = name;
			this.pos_x = x;
			this.pos_y = y;
			this.pos_z = z;
			this.scale = scale;
		}
		
		public void setSpatial(Spatial spatial) {
			instance = spatial;
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
