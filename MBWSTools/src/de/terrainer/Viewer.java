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
	private DynamicWorld terrain;

	public Viewer() {
		setDialogBehaviour(SimpleGame.NEVER_SHOW_PROPS_DIALOG);
		
	}

	protected void simpleInitGame() {
		// First a hand made terrain
		// createTerrainFromMap();
		terrain = new DynamicWorld();
		rootNode.attachChild(terrain);
		try {
			terrain.init(display, "..\\MBWSClient\\data\\world\\world");
		}
		catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		display.getRenderer().setBackgroundColor(new ColorRGBA(0.5f, 0.5f, 0.5f, 1));
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

		FogState fs = display.getRenderer().createFogState();
		fs.setDensity(0.5f);
		fs.setEnabled(true);
		fs.setColor(new ColorRGBA(0.5f, 0.5f, 0.5f, 0.5f));
		fs.setEnd(1000);
		fs.setStart(500);
		fs.setDensityFunction(FogState.DF_LINEAR);
		fs.setApplyFunction(FogState.AF_PER_VERTEX);
		rootNode.setRenderState(fs);

		rootNode.updateWorldData(0);
	}

	@Override
	protected void simpleUpdate() {
		terrain.update(cam);
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