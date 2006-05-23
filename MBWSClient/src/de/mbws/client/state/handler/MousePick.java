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

import de.mbws.client.data.AbstractGameObject;
import de.mbws.client.state.OutdoorGameState;

public class MousePick extends MouseInputAction {

	private Camera camera;
	private Node rootNode;
	private DisplaySystem display;
	private OutdoorGameState gameState;

	// private float intevall = 0f;

	private Mouse mouse;

	public MousePick(Camera cam, Node node, Mouse aMouse, DisplaySystem aDisplay,
			OutdoorGameState state) {
		super();
		camera = cam;
		rootNode = node;
		mouse = aMouse;
		display = aDisplay;
		gameState = state;
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
			Ray ray = new Ray(camera.getLocation(), worldCoords.subtractLocal(camera
					.getLocation()));

			PickResults results = new BoundingPickResults();
			results.setCheckDistance(true);
			rootNode.findPick(ray, results);

			if (results.getNumber() > 0) {
				// for (int i = 0; i < results.getNumber(); i++) {
				// System.out.println(results.getPickData(i).getTargetMesh()
				// .getParent().getName());
				//
				// }
				Node nodeHit = findNode(results);

				if (nodeHit != null) {
					gameState.setSelectedObject(nodeHit);
					// OutdoorGameState((AbstractGameObject) nodeHit).setSelected(true);
					System.out.println("Last one is " + nodeHit.getName());
					if (nodeHit.getName() == null) {
						System.out.println("ups, thats bad");
					}
				} else {
					System.out.println("Hit NULL");
				}

			}

			results.clear();

		}

	}

	private Node findNode(PickResults results) {
		// state rootNode
		Node node = results.getPickData(results.getNumber() - 1).getTargetMesh()
				.getParent();
		while (node.getParent() != null) {
			if (node instanceof AbstractGameObject) {
				return node;
			}
			node = node.getParent();
		}
		return null;
	}

}
