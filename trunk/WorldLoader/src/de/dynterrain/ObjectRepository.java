package de.dynterrain;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


import com.jme.math.Vector3f;
import com.jme.renderer.CloneCreator;
import com.jme.scene.Node;
import com.jme.scene.SharedMesh;
import com.jme.scene.Spatial;

/**
 * The ObjectRepository manages objects in the (game) world. It consists of a blueprint repository
 * and an instance repository. This way it ensures, that objects can be reused without the overhead
 * of reloading the whole object.
 * 
 * @author Axel
 */
public class ObjectRepository {

	private static Logger logger = Logger.getLogger(ObjectRepository.class.getName());
	private static long uid = 0;

	private Map<String, Blueprint> blueprintMap = new HashMap<String, Blueprint>();
	private ObjectLoader objectLoader;

	/**
	 * BluePrint represents the blueprint of a JME-object.
	 */
	private static class Blueprint {
		String name;
		int referenceCount;
		CloneCreator cloneCreator; // TODO use SharedMesh
		Spatial bluePrint;
		volatile boolean completed;

		Blueprint(String name) {
			this.name = name;
		}

		void createCloneCreator(Spatial spatial) {
			bluePrint = spatial;
			sharedMesh = new SharedMesh(bluePrint);
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

	private class CreateCloneFactoryTask implements Runnable {
		String name;

		public CreateCloneFactoryTask(String name) {
			this.name = name;
		}

		public void run() {
			Node object = objectLoader.loadObject(name);
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
			long time = System.currentTimeMillis();
			Blueprint blueprint = blueprintMap.get(descr.name);
			Spatial spatial;
			spatial = blueprint.cloneCreator.createCopy();
			spatial.setName(descr.name + "_" + uid++);
			// TODO set rotation ...
			spatial.setLocalTranslation(new Vector3f(descr.x
					+ section.getTerrainBlock().getLocalTranslation().x, descr.y, descr.z
					+ section.getTerrainBlock().getLocalTranslation().z));
			spatial.setLocalScale(descr.scale);
			spatial.rotateUpTo(new Vector3f(descr.rot_x, descr.rot_y, descr.rot_z));
			blueprint.referenceCount++;
			section.attachChild(spatial);
			section.complete = true;
			logger.info("Created clone for " + descr.name + " in "
					+ (System.currentTimeMillis() - time) + " ms");
		}
	}

	/**
	 * Constructs an ObjectRepository.
	 * 
	 * @param objectLoader The instance of the ObjectLoader will be used to read needed objects,
	 *            which are not yet contained in the repository.
	 */
	ObjectRepository(ObjectLoader objectLoader) {
		this.objectLoader = objectLoader;
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
			AsyncTaskQueue.getInstance().enqueue("blueprint_" + descr.name,
					new CreateCloneFactoryTask(descr.name));
		}
		// enqueue task to create a clone (the blueprint entry will be processed first and the
		// factory will be finished when this taks begins
		AsyncTaskQueue.getInstance().enqueue("instance_" + descr.name + uid++,
				new CreateCloneTask(descr, section));
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
			logger.severe("Cannot destroy unknown object '" + name + "'");
		}
	}

}
