package de.dynterrain;

import javax.swing.ImageIcon;

import com.jme.app.SimpleGame;
import com.jme.image.Texture;
import com.jme.input.KeyBindingManager;
import com.jme.input.KeyInput;
import com.jme.light.DirectionalLight;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.state.CullState;
import com.jme.scene.state.TextureState;
import com.jme.util.TextureManager;

public class TerrainTest extends SimpleGame {

	private Terrain terrain;
	private final int SIZE = 129;

	@Override
	protected void simpleInitGame() {
		int[][] heightData = new int[SIZE][SIZE];
		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				int x1 = x - SIZE / 2;
				int y1 = y - SIZE / 2;
				 double z = Math.cos(Math.sqrt(x1 * x1 + y1 * y1) / 2.0) * 30.0;
				//double z = Math.min(0, Math.max(-300, 1000 - (x1 * x1 + y1 * y1)))+(x1 * x1 + y1 * y1)/50;
				heightData[x][y] = (int) (z + 0.5);
			}
		}
		terrain = new Terrain(heightData, "Test");
		terrain.setLocalScale(new Vector3f(1, 1, 0.1f));
		ImageIcon grass = new ImageIcon(getClass().getClassLoader().getResource(
				"resource/grassb.png"));
		TextureState ts = display.getRenderer().createTextureState();
		ts.setEnabled(true);

		Texture t1 = TextureManager.loadTexture(grass.getImage(), Texture.MM_LINEAR_LINEAR,
				Texture.FM_LINEAR, true);
		t1.setStoreTexture(true);
		ts.setTexture(t1, 0);

		// Texture t2 = TextureManager.loadTexture(getClass().getClassLoader().getResource(
		// "resource/Detail.jpg"), Texture.MM_LINEAR_LINEAR, Texture.FM_LINEAR);
		//
		// ts.setTexture(t2, 1);
		// t2.setWrap(Texture.WM_WRAP_S_WRAP_T);
		//
		// t1.setApply(Texture.AM_COMBINE);
		// t1.setCombineFuncRGB(Texture.ACF_MODULATE);
		// t1.setCombineSrc0RGB(Texture.ACS_TEXTURE);
		// t1.setCombineOp0RGB(Texture.ACO_SRC_COLOR);
		// t1.setCombineSrc1RGB(Texture.ACS_PRIMARY_COLOR);
		// t1.setCombineOp1RGB(Texture.ACO_SRC_COLOR);
		// t1.setCombineScaleRGB(1.0f);
		// t1.setScale(new Vector3f(2, 2, 2));
		// t1.setWrap(Texture.WM_WRAP_S_WRAP_T);
		//
		// t2.setApply(Texture.AM_COMBINE);
		// t2.setCombineFuncRGB(Texture.ACF_ADD_SIGNED);
		// t2.setCombineSrc0RGB(Texture.ACS_TEXTURE);
		// t2.setCombineOp0RGB(Texture.ACO_SRC_COLOR);
		// t2.setCombineSrc1RGB(Texture.ACS_PREVIOUS);
		// t2.setCombineOp1RGB(Texture.ACO_SRC_COLOR);
		// t2.setCombineScaleRGB(1.0f);
		rootNode.attachChild(terrain);
		rootNode.setRenderState(ts);

		DirectionalLight dr = new DirectionalLight();
		dr.setEnabled(true);
		// dr.setDiffuse(new ColorRGBA(0.3f, 0.3f, 0.3f, 1.0f));
		// dr.setAmbient(new ColorRGBA(1.0f, 1.0f, 1.0f, 1.0f));
		dr.setDiffuse(new ColorRGBA(1f, 1f, 1f, 1.0f));
		dr.setAmbient(new ColorRGBA(0.8f, 0.8f, 0.8f, 1.0f));
		dr.setDirection(new Vector3f(0.5f, -0.5f, 0));
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

	@Override
	protected void simpleUpdate() {
		if (KeyBindingManager.getKeyBindingManager().isValidCommand("lower_lod")) {
			terrain.decreaseLOD();
		}
		if (KeyBindingManager.getKeyBindingManager().isValidCommand("higher_lod")) {
			terrain.increaseLOD();
		}
		terrain.update(cam.getLocation());
	}

	public static void main(String[] args) {
		TerrainTest app = new TerrainTest();
		app.setDialogBehaviour(ALWAYS_SHOW_PROPS_DIALOG);
		app.start();
	}

}
