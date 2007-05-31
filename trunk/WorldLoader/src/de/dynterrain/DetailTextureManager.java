package de.dynterrain;

import com.jme.image.Texture;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.renderer.Renderer;
import com.jme.scene.state.RenderState;
import com.jme.scene.state.TextureState;
import com.jme.util.TextureManager;
import com.jmex.terrain.TerrainBlock;

public class DetailTextureManager {
	Texture texture;

	public DetailTextureManager() {
		texture = TextureManager.loadTexture("data/images/Detail.jpg", Texture.MM_LINEAR,
				Texture.FM_LINEAR);
		texture.setScale(new Vector3f(10, 10, 10));

//		texture.setCorrection(Texture.CM_PERSPECTIVE);
//		texture.setFilter(Texture.FM_LINEAR);
//		texture.setMipmapState(Texture.MM_LINEAR_LINEAR);
		texture.setWrap(Texture.WM_WRAP_S_WRAP_T);

	}

	public void update(Camera cam, DynamicWorld wld) {
		wld.setRenderQueueMode(Renderer.QUEUE_OPAQUE);
		TerrainBlock tb = wld.getTerrainAt(cam.getLocation().x, cam.getLocation().z);
		TextureState ts = (TextureState) tb.getRenderState(RenderState.RS_TEXTURE);
		texture.setTranslation(new Vector3f(0, 0, 0));
		if (ts.getTexture(1) == null) {
			Texture t2 = ts.getTexture(0);
			tb.setDetailTexture(1, 1);
			ts.setTexture(texture, 1);
			texture.setApply(Texture.AM_COMBINE);
			texture.setCombineFuncRGB(Texture.ACF_ADD_SIGNED);
			texture.setCombineSrc0RGB(Texture.ACS_TEXTURE);
			texture.setCombineOp0RGB(Texture.ACO_SRC_COLOR);
			texture.setCombineSrc1RGB(Texture.ACS_PREVIOUS);
			texture.setCombineOp1RGB(Texture.ACO_SRC_COLOR);
			//texture.setCombineScaleRGB(1);

			t2.setScale(new Vector3f(1,1,1));
			t2.setApply(Texture.AM_COMBINE);
			t2.setCombineFuncRGB(Texture.ACF_MODULATE);
			t2.setCombineSrc0RGB(Texture.ACS_TEXTURE);
			t2.setCombineOp0RGB(Texture.ACO_SRC_COLOR);
			t2.setCombineSrc1RGB(Texture.ACS_PRIMARY_COLOR);
			t2.setCombineOp1RGB(Texture.ACO_SRC_COLOR);
			//t2.setCombineScaleRGB(2);
			ts.setTexture(t2, 0);

		}
	}
}
