package de.mbws.client.worldloader;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jme.math.Vector3f;
import com.jme.renderer.CloneCreator;
import com.jme.scene.Node;
import com.jme.scene.Spatial;

/**
 * The ObjectRepository manages objects in the (game) world. It consists of a blueprint repository
 * and an instance repository. This way it ensures, that objects can be reused without the overhead
 * of reloading the whole object.
 * 
 * @author Axel
 */
public class ObjectRepository {

	private static Logger logger = Logger.getLogger(ObjectRepository.class);
	private static long uid = 0;

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
		CloneCreator cloneCreator;
		volatile boolean completed;

		Blueprint(String name) {
			this.name = name;
		}

		void createCloneCreator(Spatial spatial) {
			this.cloneCreator = new CloneCreator(spatial);
			cloneCreator.addProperty("vertices");
			cloneCreator.addProperty("normals");
			cloneCreator.addProperty("colors");
			cloneCreator.addProperty("texcoords");
			cloneCreator.addProperty("indices");
			cloneCreator.addProperty("vboinfo");
		}

		void setCompleted() {
			completed = true;
		}

		boolean isCompleted() {
			return completed;
		}
	}

	public static class DelayedSpatial {
		volatile boolean complete;
		Spatial spatial;
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
			Blueprint blueprint = blueprintMap.get(name);
			blueprint.createCloneCreator(object);
			blueprint.setCompleted();
			logger.info("Created blueprint for object " + name);
		}
	}

	private class CreateCloneTask implements Runnable {
		ObjectDescription descr;
		SectionController.SectionNode section;

		CreateCloneTask(ObjectDescription descr, SectionController.SectionNode sn) {
			this.descr = descr;
			this.section = sn;
		}

		public void run() {
			Blueprint blueprint = blueprintMap.get(descr.name);
			Spatial spatial;
			spatial = blueprint.cloneCreator.createCopy();
			spatial.setName(descr.name + "_" + uid++);
			// TODO set rotation ...
			spatial.setLocalTranslation(new Vector3f(descr.x, descr.y, descr.z));
			spatial.setLocalScale(descr.scale);
			blueprint.referenceCount++;
			section.attachChild(spatial);
			section.complete = true;
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
	 * Creates a clone for a specific object instance using a blueprint from the repository. The
	 * created Spatial will be added to the SectionNode asynchronously. If the object is requested
	 * for the first time a blueprint (CloneCreator) is loaded first and afterwards the clone is
	 * created. <p/> <b>Caution:</b> Use <code>addObjectClone()</code> and
	 * <code>destroyObjectClone()</code> symmetrically to avoid memory leaks.
	 */
	void addObjectClone(SectionController.SectionNode section, ObjectDescription descr) {
		// first check for a blueprint
		if (!blueprintMap.containsKey(descr.name)) {
			blueprintMap.put(descr.name, new Blueprint(descr.name));
			taskQueue.enqueue("blueprint_" + descr.name, new LoadObjectTask(objectRepositoryPath,
					descr.name));
		}
		// enqueue task to create a clone (the blueprint entry will be processed first and the
		// factory will be finished when this taks begins
		taskQueue.enqueue("instance_" + descr.name, new CreateCloneTask(descr, section));
	}

	/**
	 * Notifies the repository of an object instance, which is not used anymore. If the given
	 * instance is the last one in use, the blueprint is removed from the repository.
	 * 
	 * @param node
	 */
	void destroyObjectClone(Spatial node) {
		String name = node.getName().substring(0, node.getName().indexOf('_'));
		Blueprint blueprint = blueprintMap.get(name);
		if (blueprint != null) {
			blueprint.referenceCount--;
			if (blueprint.referenceCount <= 0) {
				blueprintMap.remove(name);
				logger.info("Removed blueprint for object " + name);
			}
		}
		else {
			logger.error("Cannot destroy unknown object '" + name + "'");
		}
	}

	public String getObjectRepositoryPath() {
		return objectRepositoryPath;
	}

	public void setObjectRepositoryPath(String objectRepositoryPath) {
		this.objectRepositoryPath = objectRepositoryPath;
	}

}
