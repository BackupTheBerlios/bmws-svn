package de.mbws.client.experimental;

import com.jme.image.Texture;
import com.jme.math.FastMath;
import com.jme.math.Vector2f;
import com.jme.renderer.Renderer;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.shape.Quad;
import com.jme.scene.state.LightState;
import com.jme.scene.state.RenderState;
import com.jme.scene.state.TextureState;
import com.jme.system.DisplaySystem;
import com.jme.util.TextureManager;
import com.jme.util.geom.BufferUtils;

public class Compass extends Node {

	private float height;
	private float width;
	private float currentAngle;
	private float targetAngle;
	private Quad compass;
	public Vector2f[] texCoords = new Vector2f[4];

	private static final float defaultHeight = 25;
	private static final float defaultWidth = 75;

	public Compass(String name) {
		super(name);
		width = defaultWidth;
		height = defaultHeight;
		initialize();
	}

	public Compass(String name, float w, float h) {
		super(name);
		width = w;
		height = h;
		initialize();
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	protected void initialize() {
		targetAngle = 0;
		currentAngle = 0;

		DisplaySystem display = DisplaySystem.getDisplaySystem();

		compass = new Quad("compassQUAD", width, height);
		compass.setTextureCombineMode(TextureState.REPLACE);
		compass.setRenderQueueMode(Renderer.QUEUE_ORTHO);
		compass.setVBOInfo(null);

		LightState ls = display.getRenderer().createLightState();
		ls.setEnabled(false);
		setRenderState(ls);
		setLightCombineMode(LightState.REPLACE);
		setTextureCombineMode(TextureState.REPLACE);
		setCullMode(Spatial.CULL_NEVER);
		attachChild(compass);
		Texture texture = TextureManager.loadTexture("data/images/compass.png",
				Texture.MM_LINEAR, Texture.FM_LINEAR);
		texture.setWrap(Texture.WM_WRAP_S_CLAMP_T);
		TextureState ts = (TextureState) compass
				.getRenderState(RenderState.RS_TEXTURE);
		if (ts == null) {
			ts = DisplaySystem.getDisplaySystem().getRenderer()
					.createTextureState();
		}

		setTexCoords(0);
		// Initialize the texture state
		ts.setTexture(texture, 0);
		ts.setEnabled(true);
		ts.apply();

		compass.setRenderState(ts);
	}

	//TODO: Should we change Spatial constants ?
//	public int getType() {
//		return (Spatial.COMPASS);
//	}

	protected void setTexCoords(float shift) {
		texCoords[0] = new Vector2f(shift, 1);
		texCoords[1] = new Vector2f(shift, 0);
		texCoords[2] = new Vector2f(shift + 0.375f, 0);
		texCoords[3] = new Vector2f(shift + 0.375f, 1);
		compass.setTextureBuffer(BufferUtils.createFloatBuffer(texCoords));
	}

	/**
	 * update texture move
	 * 
	 * @param d
	 */
	public void updateGeometricState(float time, boolean initiator) {
		float diff = targetAngle - currentAngle;
		if (FastMath.abs(diff) > 180.0f)
			diff = FastMath.sign(diff) * (FastMath.abs(diff) - 360f);
		float d = currentAngle + (diff * time);
		setTexCoords(d / 360f - 0.125f);
		currentAngle = d;
		super.updateGeometricState(time, initiator);
	}

	/**
	 * sets the direction the compass is facing. Should be the same the character is facing
	 * @param d direction in degrees where zero is north, 90 is east, ...
	 */
	public void setAngle(float d) {
		targetAngle = d;
	}
}
