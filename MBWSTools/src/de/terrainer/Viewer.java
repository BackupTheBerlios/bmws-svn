package de.terrainer;

import java.io.IOException;

import org.xml.sax.SAXException;

import com.jme.app.SimpleGame;
import com.jme.bounding.BoundingBox;
import com.jme.image.Texture;
import com.jme.math.Vector3f;
import com.jme.scene.state.TextureState;
import com.jme.util.TextureManager;
import com.jmex.terrain.TerrainBlock;

import de.terrainer.terrainloader.DynamicTerrain;

/**
 */
public class Viewer extends SimpleGame {
	private int[] map;
	private int length;
	private DynamicTerrain terrain; 

	public Viewer(int[] map, int length) {
		this.map = map;
		this.length = length;
	}

	protected void simpleInitGame() {
		// First a hand made terrain
		//createTerrainFromMap();
		terrain = new DynamicTerrain();
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
	}

	@Override
	protected void simpleUpdate() {
		terrain.update(cam);
		rootNode.updateGeometricState(0.0f, true);
		rootNode.updateRenderState();
	}

	@Override
	protected void initSystem() {
		properties.set("FULLSCREEN", "false");
		super.initSystem();
	}

	public void showViewer() {
		setDialogBehaviour(SimpleGame.NEVER_SHOW_PROPS_DIALOG);
		start();
	}

	@Override
	protected void quit() {
		if (display != null) {
			display.close();
		}
	}

	private void createTerrainFromMap() {
		try {
		// The map for our terrain. Each value is a height on the terrain
		// Create a terrain block. Our integer height values will
		// scale on the map 2x larger x,
		// and 2x larger z. Our map's origin will be the regular
		// origin, and it won't create an
		// AreaClodMesh from it.
		Vector3f terrainScale = new Vector3f(5, 0.1f, 5);
		TerrainBlock tb = new TerrainBlock("block", length, terrainScale, map,
				new Vector3f(0, 0, 0), false);
		// Add the texture
		// This will be the texture for the terrain.
		TextureState ts = display.getRenderer().createTextureState();
		ts.setTexture(TextureManager.loadTexture("..\\MBWSClient\\data\\images\\grassb.png", Texture.MM_LINEAR,
				Texture.FM_LINEAR));
		tb.setRenderState(ts);

		// Give the terrain a bounding box.
		tb.setModelBound(new BoundingBox());
		tb.updateModelBound();
		// Attach the terrain TriMesh to our rootNode
		rootNode.attachChild(tb);

		
		TerrainBlock tb2 = new TerrainBlock("block", length, terrainScale, map,
				new Vector3f(0, 0, 5*128), false);
		// Add the texture
		// This will be the texture for the terrain.
		TextureState ts2 = display.getRenderer().createTextureState();
		ts2.setTexture(TextureManager.loadTexture("..\\MBWSClient\\data\\images\\clouds.png", Texture.MM_LINEAR,
				Texture.FM_LINEAR));
		tb2.setRenderState(ts2);

		// Give the terrain a bounding box.
		tb2.setModelBound(new BoundingBox());
		tb2.updateModelBound();
		// Attach the terrain TriMesh to our rootNode
		rootNode.attachChild(tb2);

		/*
		FogState fs = display.getRenderer().createFogState();
		fs.setDensity(0.5f);
		fs.setColor(new ColorRGBA(0.5f,0.5f,0.5f,0.5f));
		fs.setEnd(60);
		fs.setStart(40);
		fs.setDensityFunction(FogState.DF_LINEAR);
		fs.setApplyFunction(FogState.AF_PER_VERTEX);
		rootNode.setRenderState(fs);
		*/
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	
	
	}