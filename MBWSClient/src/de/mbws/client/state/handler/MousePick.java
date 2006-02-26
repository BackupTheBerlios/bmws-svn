package de.mbws.client.state.handler;

import com.jme.input.Mouse;
import com.jme.input.MouseInput;
import com.jme.input.action.InputActionEvent;
import com.jme.input.action.MouseInputAction;
import com.jme.intersection.BoundingPickResults;
import com.jme.intersection.PickResults;
import com.jme.math.Ray;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.scene.Node;
import com.jme.system.DisplaySystem;

public class MousePick extends MouseInputAction {

	private Camera camera;
	private Node rootNode;
	private DisplaySystem display;

	// private float intevall = 0f;

	 private Mouse mouse;

	public MousePick(Camera cam, Node node, Mouse aMouse, DisplaySystem aDisplay) {
		super();
		camera = cam;
		rootNode = node;
		mouse = aMouse;
		display = aDisplay;
	}

	@Override
	public void performAction(InputActionEvent evt) {
		// intevall += evt.getTime();
		if (MouseInput.get().isButtonDown(0)) {// && intevall > 0.1f) {
			// intevall = 0;

			Vector2f screenPos = new Vector2f();
			// Get the position that the mouse is pointing to
			screenPos.set(mouse.getHotSpotPosition().x, mouse.getHotSpotPosition().y);
			// Get the world location of that X,Y value
			Vector3f worldCoords = display.getWorldCoordinates(screenPos, 0);
			Ray ray = new Ray(camera.getLocation(), worldCoords
					.subtractLocal(camera.getLocation()));

			PickResults results = new BoundingPickResults();
			results.setCheckDistance(true);
			rootNode.findPick(ray, results);

			if (results.getNumber() > 0) {
				// for (int i = 0; i < results.getNumber(); i++) {
				// System.out.println(results.getPickData(i).getTargetMesh()
				// .getParent().getName());
				//
				//				}
				System.out.println("Last one is "+ findNodeName(results));
				System.out.println("HIT: ");
			}

			results.clear();

		}

	}
	
	private String findNodeName(PickResults results) {
		//state rootNode
	     Node node = results.getPickData(results.getNumber()-1).getTargetMesh().getParent();
		 while (!node.getParent().getName().equals(rootNode.getName())) {
			 node = node.getParent();
		 }
	     return node.getName();
	}

}
