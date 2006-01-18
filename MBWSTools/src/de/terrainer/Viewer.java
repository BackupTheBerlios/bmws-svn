package de.terrainer;

import java.net.URL;

import javax.swing.ImageIcon;

import com.jme.app.SimpleGame;
import com.jme.bounding.BoundingBox;
import com.jme.image.Texture;
import com.jme.math.Vector3f;
import com.jme.scene.state.TextureState;
import com.jme.util.TextureManager;
import com.jmex.terrain.TerrainBlock;
import com.jmex.terrain.util.ImageBasedHeightMap;
import com.jmex.terrain.util.MidPointHeightMap;
import com.jmex.terrain.util.ProceduralTextureGenerator;

/**
 */
public class Viewer extends SimpleGame {
	private int[] map;
	private int length;

	public Viewer(int[] map, int length) {
		this.map = map;
		this.length = length;
	}

	protected void simpleInitGame() {
		// First a hand made terrain
		createTerrainFromMap();
		// generatedHeightMap();

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
		// The map for our terrain. Each value is a height on the terrain
		// Create a terrain block. Our integer height values will
		// scale on the map 2x larger x,
		// and 2x larger z. Our map's origin will be the regular
		// origin, and it won't create an
		// AreaClodMesh from it.
		Vector3f terrainScale = new Vector3f(5, 0.3f, 5);
		TerrainBlock tb = new TerrainBlock("block", length, terrainScale, map,
				new Vector3f(0, 0, 0), false);
		// Add the texture
		// This will be the texture for the terrain.
		URL grass = ClassLoader
				.getSystemResource("grassb.png");
		TextureState ts = display.getRenderer().createTextureState();
		ts.setTexture(TextureManager.loadTexture(grass, Texture.MM_LINEAR,
				Texture.FM_LINEAR));
		tb.setRenderState(ts);

		// Give the terrain a bounding box.
		tb.setModelBound(new BoundingBox());
		tb.updateModelBound();
		// Attach the terrain TriMesh to our rootNode
		rootNode.attachChild(tb);

		
		TerrainBlock tb2 = new TerrainBlock("block", length, terrainScale, map,
				new Vector3f(0, 0, 5*129), false);
		// Add the texture
		// This will be the texture for the terrain.
		URL clouds = ClassLoader
				.getSystemResource("clouds.png");
		TextureState ts2 = display.getRenderer().createTextureState();
		ts2.setTexture(TextureManager.loadTexture(clouds, Texture.MM_LINEAR,
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

	private void update() {
		
		super.update(0.0f);
	}
	
	private void generatedHeightMap() {
		// This will be the texture for the terrain.
		URL grass = ClassLoader
				.getSystemResource("jmetest/data/texture/grassb.png");
		// Use the helper class to create a terrain for us. The
		// terrain will be 64x64
		MidPointHeightMap mph = new MidPointHeightMap(64, 1.5f);
		// Create a terrain block from the created terrain map.
		TerrainBlock tb = new TerrainBlock("midpoint block", mph.getSize(),
				new Vector3f(1, .11f, 1), mph.getHeightMap(), new Vector3f(0,
						-25, 0), false);
		// Add the texture
		TextureState ts = display.getRenderer().createTextureState();
		ts.setTexture(TextureManager.loadTexture(grass, Texture.MM_LINEAR,
				Texture.FM_LINEAR));
		tb.setRenderState(ts);
		// Give the terrain a bounding box.
		tb.setModelBound(new BoundingBox());
		tb.updateModelBound();
		tb.setLocalTranslation(new Vector3f(100, 200, 0));
		// Attach the terrain TriMesh to rootNode
		rootNode.attachChild(tb);
	}

	private void complexTerrain() {
		// This grayscale image will be our terrain
		URL grayscale = ClassLoader
				.getSystemResource("jmetest/data/texture/bubble.jpg");
		// These will be the textures of our terrain.
		URL waterImage = ClassLoader
				.getSystemResource("jmetest/data/texture/water.png");
		URL dirtImage = ClassLoader
				.getSystemResource("jmetest/data/texture/dirt.jpg");
		URL highest = ClassLoader
				.getSystemResource("jmetest/data/texture/highest.jpg");
		// Create an image height map based on the gray scale of our image.
		ImageBasedHeightMap ib = new ImageBasedHeightMap(new ImageIcon(
				grayscale).getImage());
		// Create a terrain block from the image's grey scale
		TerrainBlock tb = new TerrainBlock("image icon", ib.getSize(),
				new Vector3f(.5f, .05f, .5f), ib.getHeightMap(), new Vector3f(
						0, 0, 0), false);
		// Create an object to generate textured terrain from the
		// image based height map.
		ProceduralTextureGenerator pg = new ProceduralTextureGenerator(ib);
		// Look like water from height 0-60 with the strongest
		// "water look" at 30
		pg.addTexture(new ImageIcon(waterImage), 0, 30, 60);
		// Look like dirt from height 40-120 with the strongest
		// "dirt look" at 80
		pg.addTexture(new ImageIcon(dirtImage), 40, 80, 120); // Look like
		// highest (pure
		// white) from
		// height
		// 110-256
		// with the strongest "white look" at 130
		pg.addTexture(new ImageIcon(highest), 110, 130, 256);
		// Tell pg to create a texture from the ImageIcon's it has recieved.
		pg.createTexture(256);
		TextureState ts = display.getRenderer().createTextureState();
		// Load the texture and assign it.
		ts.setTexture(TextureManager.loadTexture(pg.getImageIcon().getImage(),
				Texture.MM_LINEAR_LINEAR, Texture.FM_LINEAR, true));
		tb.setRenderState(ts);
		// Give the terrain a bounding box tb.setModelBound(new BoundingBox());
		tb.updateModelBound();
		// Move the terrain in front of the camera
		tb.setLocalTranslation(new Vector3f(0, 0, -50));
		// Attach the terrain to our rootNode.
		rootNode.attachChild(tb);
	}
}