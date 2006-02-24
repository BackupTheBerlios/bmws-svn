package de.terrainer;

import java.io.IOException;

import org.xml.sax.SAXException;

import com.jme.app.SimpleGame;
import com.jme.light.DirectionalLight;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.state.CullState;
import com.jme.scene.state.FogState;

import de.mbws.client.worldloader.DynamicWorld;

/**
 */
public class Viewer extends SimpleGame {
	private DynamicWorld world;

	public Viewer() {
		setDialogBehaviour(SimpleGame.NEVER_SHOW_PROPS_DIALOG);

	}

	protected void simpleInitGame() {
		// First a hand made terrain
		// createTerrainFromMap();
		world = new DynamicWorld();
		rootNode.attachChild(world);
		try {
			world.init(rootNode, display, "..\\MBWSClient\\data\\world\\world");
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		display.getRenderer().setBackgroundColor(
				new ColorRGBA(0.8f, 0.8f, 0.8f, 1));
		DirectionalLight dr = new DirectionalLight();
		dr.setEnabled(true);
		dr.setDiffuse(new ColorRGBA(0.3f, 0.3f, 0.3f, 1.0f));
		dr.setAmbient(new ColorRGBA(1.0f, 1.0f, 1.0f, 1.0f));
		dr.setDirection(new Vector3f(0.5f, -0.5f, 0));
		lightState.attach(dr);

		CullState cs = display.getRenderer().createCullState();
		cs.setCullMode(CullState.CS_BACK);
		cs.setEnabled(true);
		rootNode.setRenderState(cs);

		rootNode.updateWorldData(0);
		
		cam.setFrustumFar(2000);
	}

	@Override
	protected void simpleUpdate() {
//		System.err.println("height for (" + cam.getLocation().x + ","
//				+ cam.getLocation().z + ") => "
//				+ terrain.getHeight(cam.getLocation()));
		world.update(cam);
	}

	@Override
	protected void initSystem() {
		properties.set("FULLSCREEN", "false");
		super.initSystem();
	}

	public void showViewer() {
		start();
	}

	@Override
	protected void quit() {
		if (display != null) {
			display.close();
		}
	}

}