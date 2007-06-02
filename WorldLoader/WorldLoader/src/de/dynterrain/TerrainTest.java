package de.dynterrain;

import com.jme.app.SimpleGame;
import com.jme.image.Texture;
import com.jme.input.KeyBindingManager;
import com.jme.input.KeyInput;
import com.jme.light.DirectionalLight;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.state.CullState;
import com.jme.util.TextureManager;
import com.jmex.terrain.util.AbstractHeightMap;
import com.jmex.terrain.util.MidPointHeightMap;

public class TerrainTest extends SimpleGame {

	private Terrain terrain[];
	private final int SIZE = 512;
	private final int TERRAIN_SIZE = 65;
	int gridWidth = SIZE / (TERRAIN_SIZE - 1) - 1;
	int gridSize = gridWidth*gridWidth;
	private Texture t1;
	private Texture t2;

	@Override
	protected void simpleInitGame() {

		t1 = TextureManager.loadTexture(getClass().getClassLoader()
				.getResource("resource/dirt.jpg"), Texture.MM_LINEAR_LINEAR, Texture.FM_LINEAR);

		t2 = TextureManager.loadTexture(getClass().getClassLoader().getResource(
				"resource/Detail.jpg"), Texture.MM_LINEAR_LINEAR, Texture.FM_LINEAR);

		// int[][] heightData = new int[SIZE][SIZE];
		// for (int y = 0; y < SIZE; y++) {
		// for (int x = 0; x < SIZE; x++) {
		// int x1 = x - SIZE / 2;
		// int y1 = y - SIZE / 2;
		// // double z = Math.cos(Math.sqrt(x1 * x1 + y1 * y1) / 2.0) * 30.0;
		// double z = Math.min(0, Math.max(-300, 1000 - (x1 * x1 + y1 * y1)))
		// + (x1 * x1 + y1 * y1) / 50;
		// heightData[x][y] = (int) (z + 0.5);
		// }
		// }
		// terrain = new Terrain(heightData, "Test");
		AbstractHeightMap hm = new MidPointHeightMap(SIZE, 0.7f);
		hm.load();
		int gridWidth = SIZE / (TERRAIN_SIZE - 1) - 1;
		terrain = new Terrain[gridSize];
		for (int y = 0; y < gridWidth; y++) {
			for (int x = 0; x < gridWidth; x++) {
				int[] heightData = new int[TERRAIN_SIZE * TERRAIN_SIZE];
				for (int i = 0; i < TERRAIN_SIZE; i++) {
					//System.out.println(x + " " + y + " " + i);
					System.arraycopy(hm.getHeightMap(), (y * (TERRAIN_SIZE - 1) + i) * hm.getSize()
							+ x * (TERRAIN_SIZE - 1), heightData, i * TERRAIN_SIZE, TERRAIN_SIZE);
				}
				int index = x+y*gridWidth; 
				terrain[index] = new Terrain(heightData, TERRAIN_SIZE, "test"+x+"_"+y);
				heightData = null;
				float horScale = 1.5f;
				terrain[index].setLocalScale(new Vector3f(horScale, horScale, 0.1f));
				terrain[index].setLocalTranslation(new Vector3f(horScale * (TERRAIN_SIZE - 1) * y,
						horScale * (TERRAIN_SIZE - 1) * x, 0));
				terrain[index].setTextures(t1, t2, 4, display);
				rootNode.attachChild(terrain[index]);
			}
		}
		hm = null;

		DirectionalLight dr = new DirectionalLight();
		dr.setEnabled(true);
		// dr.setDiffuse(new ColorRGBA(0.3f, 0.3f, 0.3f, 1.0f));
		// dr.setAmbient(new ColorRGBA(1.0f, 1.0f, 1.0f, 1.0f));
		dr.setDiffuse(new ColorRGBA(2f, 2f, 2f, 2.0f));
		dr.setAmbient(new ColorRGBA(0.8f, 0.8f, 0.8f, 1.0f));
		dr.setDirection(new Vector3f(1f, 0.1f, 1f));
		lightState.attach(dr);

		CullState cs = display.getRenderer().createCullState();
		cs.setCullMode(CullState.CS_BACK);
		cs.setEnabled(true);
		rootNode.setRenderState(cs);

		rootNode.updateWorldData(0);
		System.out.println(cam.getLocation());

		// key bindings
		KeyBindingManager.getKeyBindingManager().set("lower_lod", KeyInput.KEY_K);
		KeyBindingManager.getKeyBindingManager().set("higher_lod", KeyInput.KEY_H);

	}

	private int counter = 0;
	@Override
	protected void simpleUpdate() {
		terrain[counter].update(cam.getLocation());
		counter = (counter+1) % gridSize;
	}

	public static void main(String[] args) {
		TerrainTest app = new TerrainTest();
		app.setDialogBehaviour(ALWAYS_SHOW_PROPS_DIALOG);
		app.start();
	}

}
