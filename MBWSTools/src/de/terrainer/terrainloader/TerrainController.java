package de.terrainer.terrainloader;

import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.scene.Node;

public class TerrainController {

	public void update(Node root, Camera cam) {
		Vector3f location = cam.getLocation();
		Vector3f viewdir = cam.getDirection();
		// identify visible sections 

		// remove sections from the model, that are not visible anymore
		
		// add visible sections, that are still missing in the model
		
		// preloading...
		
	}
}
