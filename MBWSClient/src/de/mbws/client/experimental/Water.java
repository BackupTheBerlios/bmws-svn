package de.mbws.client.experimental;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import com.jme.image.Texture;
import com.jme.math.FastMath;
import com.jme.math.Matrix3f;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.shape.Quad;
import com.jme.scene.state.AlphaState;
import com.jme.scene.state.TextureState;
import com.jme.system.DisplaySystem;
import com.jme.util.TextureManager;

public class Water {

	private float height;
	private float waveHeight = 0;
	private float maxWaveHeight = 0.5f;
	private float periodSpeed = 0.5f;
	private Quad waterQuad;
	private Node rootNode;

	public Water(String name, float x, float y, Vector3f location,
			 Node rootNode) {
		waterQuad = new Quad(name, x, y);
		waterQuad.setLocalTranslation(location);
		height = location.y;
		Matrix3f localRotate = new Matrix3f();
		localRotate.fromAxisAngle(new Vector3f(1.0F, 0.0F, 0.0F),
				-(1 * 0.5f * FastMath.PI));
		waterQuad.setLocalRotation(localRotate);
		this.rootNode = rootNode;

		URL urlOfTexture;
		try {
			urlOfTexture = new File("data/images/water.jpg").toURL();

			TextureState ts = DisplaySystem.getDisplaySystem().getRenderer()
					.createTextureState();
			Texture waterTexture = TextureManager.loadTexture(urlOfTexture,
					Texture.MM_LINEAR, Texture.FM_LINEAR);
			waterTexture.setWrap(Texture.WM_WRAP_S_WRAP_T);
			ts.setTexture(waterTexture);

			waterQuad.setRenderState(ts);

			// set Additive blending to the water texture
			AlphaState as = DisplaySystem.getDisplaySystem().getRenderer()
					.createAlphaState();
			as.setBlendEnabled(true);
			as.setSrcFunction(AlphaState.DB_ONE);
			as.setDstFunction(AlphaState.DB_ONE);

			waterQuad.setRenderState(as);

			rootNode.attachChild(waterQuad);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public void update(float elapsedTime) {
		waveHeight += periodSpeed * elapsedTime;
		if (waveHeight > maxWaveHeight) {
			waveHeight = maxWaveHeight;
			periodSpeed = -periodSpeed;
		} else if (waveHeight < -maxWaveHeight) {
			waveHeight = -maxWaveHeight;
			periodSpeed = -periodSpeed;
		}
		Vector3f loc = waterQuad.getLocalTranslation();
		waterQuad.setLocalTranslation(new Vector3f(loc.x, height + waveHeight,
				loc.z));
	}

}
