package de.mbws.client.experimental;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import com.jme.bounding.BoundingBox;
import com.jme.image.Texture;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Node;
import com.jme.scene.TriMesh;
import com.jme.scene.shape.Quad;
import com.jme.scene.state.AlphaState;
import com.jme.scene.state.TextureState;
import com.jme.system.DisplaySystem;
import com.jme.util.TextureManager;
import com.jme.util.geom.BufferUtils;

public class Water {

	private float height;
	private float waveHeight = 0;
	private float maxWaveHeight = 0.5f;
	private float periodSpeed = 0.5f;
	private Quad waterQuad;
	private Node rootNode;
	private TriMesh waterMesh;

	public Water(String name, float xExtend, float yExtend, float xTextureSize, float yTectureSize, Vector3f location, Node rootNode) {
		// waterQuad = new Quad(name, x, y);
		// waterQuad.setLocalTranslation(location);
		height = location.y;
		waterMesh = new TriMesh("water");
		// create the vertices
		Vector3f[] vertices = { new Vector3f(0, 0, 0), new Vector3f(xExtend, 0, 0),
				new Vector3f(0, 0, yExtend), new Vector3f(xExtend, 0, yExtend) };

		// create the normals
		Vector3f[] normals = { new Vector3f(0, 1, 0), new Vector3f(0, 1, 0),
				new Vector3f(0, 1, 0), new Vector3f(0, 1, 0), };

		ColorRGBA[] colors = { new ColorRGBA(0, 0, 0.8f, 0.5f),
				new ColorRGBA(0, 0, 0.8f, 0.5f),
				new ColorRGBA(0, 0, 0.8f, 0.5f),
				new ColorRGBA(0, 0, 0.8f, 0.5f), };

		Vector2f[] texCoords = { new Vector2f(0, 0), new Vector2f(xTextureSize, 0),
				new Vector2f(0, yTectureSize), new Vector2f(xTextureSize, yTectureSize) };

		int[] indices = { 0, 1, 2, 1, 2, 3 };

		waterMesh.reconstruct(BufferUtils.createFloatBuffer(vertices),
				BufferUtils.createFloatBuffer(normals), null, BufferUtils
						.createFloatBuffer(texCoords), BufferUtils
						.createIntBuffer(indices));
		waterMesh.setLocalTranslation(location);
		waterMesh.setModelBound(new BoundingBox());
		waterMesh.updateModelBound();

		// Matrix3f localRotate = new Matrix3f();
		// localRotate.fromAxisAngle(new Vector3f(1.0F, 0.0F, 0.0F),
		// -(1 * 0.5f * FastMath.PI));
		// waterQuad.setLocalRotation(localRotate);
		this.rootNode = rootNode;

		URL urlOfTexture;
		try {
			urlOfTexture = new File("data/images/water.jpg").toURL();

			TextureState ts = DisplaySystem.getDisplaySystem().getRenderer()
					.createTextureState();
			Texture texWater = TextureManager.loadTexture(urlOfTexture,
					Texture.MM_LINEAR, Texture.FM_LINEAR);
			texWater.setWrap(Texture.WM_WRAP_S_WRAP_T);
			ts.setTexture(texWater);

			waterMesh.setRenderState(ts);
			//waterQuad.setRenderState(ts);

			// set Additive blending to the water texture
			AlphaState as = DisplaySystem.getDisplaySystem().getRenderer()
					.createAlphaState();
			as.setBlendEnabled(true);
			as.setSrcFunction(AlphaState.DB_ONE);
			as.setDstFunction(AlphaState.DB_ONE);

			waterMesh.setRenderState(as);
			//waterQuad.setRenderState(as);

			rootNode.attachChild(waterMesh);
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
		Vector3f loc = waterMesh.getLocalTranslation();
		waterMesh.setLocalTranslation(new Vector3f(loc.x, height + waveHeight,
				loc.z));
//		Vector3f loc = waterQuad.getLocalTranslation();
//		waterQuad.setLocalTranslation(new Vector3f(loc.x, height + waveHeight,
//				loc.z));
	}

}
