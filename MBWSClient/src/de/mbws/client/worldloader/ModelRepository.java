package de.mbws.client.worldloader;

/**
 * The ModelRepository manages models in the (game) world. It consists of a blue-print repository
 * and an instance repository. This way it ensures, that objects can be reused without the overhead
 * of reloading the whole object.
 * 
 * @author Axel
 */
public class ModelRepository {

	/**
	 * ModelInstance represents one instance of a JME-object.
	 */
	public static class ModelInstance {
		public String name;
		public float pos_x;
		public float pos_y;
		public float pos_z;
		public float scale;

		public ModelInstance(String name, float x, float y, float z, float scale) {
			this.name = name;
			this.pos_x = x;
			this.pos_y = y;
			this.pos_z = z;
			this.scale = scale;
		}
	}
	
	// model instances are added to sections
	
	public void registerInstance(ModelInstance mod) {
		// if model is not in blue-print repository enqueue task to load it
		
		// register instance
	}
}
