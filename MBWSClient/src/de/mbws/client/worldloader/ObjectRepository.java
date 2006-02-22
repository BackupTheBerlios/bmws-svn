package de.mbws.client.worldloader;

import java.util.HashMap;
import java.util.Map;

import com.jme.scene.Node;
import com.jme.scene.Spatial;

/**
 * The ObjectRepository manages objects in the (game) world. It consists of a blue-print repository
 * and an instance repository. This way it ensures, that objects can be reused without the overhead
 * of reloading the whole object.
 * 
 * @author Axel
 */
public class ObjectRepository {

	private Map<String, Blueprint> blueprintMap = new HashMap<String, Blueprint>();
	private ObjectLoader objectLoader;
	private String objectRepositoryPath;
	private SyncTaskQueue taskQueue;

	/**
	 * BluePrint represents the blueprint of a JME-object.
	 */
	private static class Blueprint {
		String name;
		int referenceCount;
		Spatial instance;

		public Blueprint(String name, Spatial instance) {
			this.name = name;
			this.instance = instance;
		}
	}

	private class LoadObjectTask implements Runnable {
		String path;
		String name;

		public LoadObjectTask(String path, String name) {
			this.path = path;
			this.name = name;
		}

		public void run() {
			Node object = objectLoader.loadObject(path, name);
			Blueprint blueprint = new Blueprint(name, object);
			blueprintMap.put(name, blueprint);
		}
	}

	/**
	 * Constructs an ObjectRepository.
	 * 
	 * @param objectLoader The instance of the ObjectLoader will be used to read needed objects,
	 *            which are not yet contained in the repository.
	 * @param taskQueue The TaskQueue which should be used for time consuming load jobs.
	 */
	ObjectRepository(ObjectLoader objectLoader, SyncTaskQueue taskQueue) {
		this.objectLoader = objectLoader;
		this.taskQueue = taskQueue;
	}

	/**
	 * Notifies the ObjectRepository about an object, which might be used in the near future. If it
	 * is not yet loaded a load task is started and a blueprint of this object is put into the
	 * repository.
	 * 
	 * @param descr
	 */
	void preloadObject(ObjectDescription descr) {
		if (!blueprintMap.containsKey(descr.name)) {
			taskQueue.enqueue(descr.name, new LoadObjectTask(objectRepositoryPath, descr.name));
		}
	}

	/**
	 * Creates a clone for a specific object instance using a blueprint from the repository.<p/>
	 * <b>Caution:</b> Use <code>createObjectClone()</code> and <code>destroyObjectClone()</code>
	 * symmetrically to avoid memory leaks.
	 * 
	 * @return A clone of the requested object.
	 */
	Node createObjectClone(ObjectDescription descr) {
		// retrieve the blueprint, then build clone
		Blueprint blueprint = blueprintMap.get(descr.name);
		if (blueprint == null) {
			throw new RuntimeException("Requested object is not (yet?) contained in the ObjectRepository");
		}
		// build clone
			return null;
	}

	/**
	 * Notifies the repository of an object instance, which is not used anymore. If the given
	 * instance is the last one in use, the blueprint is removed from the repository.
	 * 
	 * @param node
	 */
	void destroyObjectClone(Node node) {

	}

}
