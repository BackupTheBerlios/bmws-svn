package de.dynterrain;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.jme.bounding.BoundingBox;
import com.jme.image.Texture;
import com.jme.math.FastMath;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.TriMesh;
import com.jme.scene.VBOInfo;
import com.jme.scene.batch.TriangleBatch;
import com.jme.scene.state.TextureState;
import com.jme.system.DisplaySystem;
import com.jme.util.geom.BufferUtils;

/**
 * Representation of a terrain as a mesh. Terrain supports level of detail.
 * 
 * @author crypter
 */
public class Terrain extends TriMesh {
	private static final Vector3f E = new Vector3f(1, 0, 0);
	private static final Vector3f N = new Vector3f(0, 1, 0);
	private static final Vector3f W = new Vector3f(-1, 0, 0);
	private static final Vector3f S = new Vector3f(0, -1, 0);

	private int width;
	private int noOfVertices;
	private int noOfTriangles;
	private float lodQuality = 0.3f;
	private boolean[][] lodMatrix;
	int counter;
	int maxDepth;

	public Terrain(int[][] heightData, String name) {
		super("TERRAIN_" + name);
		// this.heightData = heightData;
		init(heightData);
		setLocalScale(new Vector3f(1, 0.1f, 1));
	}

	/**
	 * Constructor for terrain.
	 * 
	 * @param heightField linear array with heightvalues as integer
	 * @param width must be a value of 2^n+1
	 */
	public Terrain(String name, int[] heightField, int width, Vector3f scale, Vector3f origin) {
		super("TERRAIN_" + name);
		int[][] heightMap = new int[width][width];
		for (int i = 0; i < width; i++) {
			System.arraycopy(heightField, i * width, heightMap[i], 0, width);
		}
		init(heightMap);
		setLocalScale(scale);
		setLocalTranslation(origin);
	}

	private void init(int[][] heightData) {
		width = heightData.length;
		int width2 = width;
		while (((width2 >>= 1) & 0xffff) != 0) {
			maxDepth++;
		}
		width = heightData[0].length;
		noOfTriangles = (width) * (width) * 2;
		noOfVertices = width * width;
		TriangleBatch batch = getBatch(0);

		FloatBuffer normalBuffer = BufferUtils.createVector3Buffer(noOfVertices * 3);
		FloatBuffer vertexBuffer = BufferUtils.createVector3Buffer(noOfVertices * 3);
		FloatBuffer textureBuffer = BufferUtils.createVector2Buffer(noOfVertices);
		createVertices(heightData, vertexBuffer);
		createTextureCoordinates(heightData, textureBuffer);
		createNormals(heightData, normalBuffer);
		normalBuffer.flip();
		vertexBuffer.flip();
		setNormalBuffer(0, normalBuffer);
		setVertexBuffer(0, vertexBuffer);
		setTextureBuffer(0, textureBuffer);

		IntBuffer trianglesBuffer = BufferUtils.createIntBuffer(noOfTriangles * 3);
		recreateMesh(null, trianglesBuffer);
		trianglesBuffer.flip();
		trianglesBuffer.compact();
		batch.setIndexBuffer(trianglesBuffer);
		batch.setTriangleQuantity(noOfTriangles);

		buildColors();

		setModelBound(new BoundingBox());
		updateModelBound();

		VBOInfo vbo = new VBOInfo(true);
		batch.setVBOInfo(vbo);
	}

	/**
	 * Updates the level of detail according to the current viewpoint and lodQuality. This method
	 * should be called within the update method of the Game.
	 * 
	 * @param view position of the camera. Use cam.getLocation() for instance.
	 */
	public void update(Vector3f viewpoint) {
		if (counter++ % 1 == 0) {
			IntBuffer trianglesBuffer = BufferUtils.createIntBuffer(noOfTriangles * 3);
			recreateMesh(viewpoint, trianglesBuffer);
			trianglesBuffer.flip();
			trianglesBuffer.compact();
			getBatch(0).setIndexBuffer(trianglesBuffer);
		}
	}

	private void createNormals(int[][] heightData, FloatBuffer normalBuffer) {
		// normals for the vertices
		for (int y = 0; y < width; y++) {
			for (int x = 0; x < width; x++) {
				Vector3f normal = new Vector3f(0, 0, 0);
				// add normals for the eight neighbours
				if (x < width - 1) {
					Vector3f vec = new Vector3f(1, 0, heightData[x + 1][y] - heightData[x][y]);
					normal = normal.add(N.cross(vec));
				}
				if (y < width - 1) {
					Vector3f vec = new Vector3f(0, 1, heightData[x][y + 1] - heightData[x][y]);
					normal = normal.add(W.cross(vec));
				}
				if (x > 0) {
					Vector3f vec = new Vector3f(-1, 0, heightData[x - 1][y] - heightData[x][y]);
					normal = normal.add(S.cross(vec));
				}
				if (y > 0) {
					Vector3f vec = new Vector3f(0, -1, heightData[x][y - 1] - heightData[x][y]);
					normal = normal.add(E.cross(vec));
				}
				normal = normal.normalize();
				normalBuffer.put(normal.y).put(normal.z).put(normal.x);
			}
		}
	}

	private void createVertices(int[][] heightData, FloatBuffer vertexBuffer) {
		for (int y = 0; y < width; y++) {
			for (int x = 0; x < width; x++) {
				vertexBuffer.put(y).put(heightData[x][y]).put(x);
			}
		}
	}

	private void createTextureCoordinates(int[][] heightData, FloatBuffer textureBuffer) {
		for (int y = 0; y < width; y++) {
			for (int x = 0; x < width; x++) {
				textureBuffer.put(((float) x) / width).put(((float) y) / width);
			}
		}
	}

	private int getVertexIndex(int x, int y) {
		int currentPoint = y * width + x;
		return currentPoint;
	}

	private float getVertexHeight(int x, int y) {
		return getBatch(0).getVertexBuffer().get(getVertexIndex(x, y) * 3 + 1);
	}

	public boolean getLodMatrix(int x, int y) {
		return lodMatrix[x][y];
	}

	public void setLodMatrix(int x, int y, boolean value) {
		lodMatrix[x][y] = value;
	}

	/**
	 * Attaches the given terrain at a side. Attached terrains share their edges and will adapt the
	 * lod without cracks.
	 * 
	 * @param attach
	 * @param attachingSide
	 */
	public void attachTerrain(Terrain attach, int attachingSide) {
		// TODO
	}

	/**
	 * Recreates the mesh.
	 * 
	 * @param viewpoint
	 * @param trianglesBuffer
	 */
	public void recreateMesh(Vector3f viewpoint, IntBuffer trianglesBuffer) {
		long time0 = System.currentTimeMillis();
		lodMatrix = new boolean[width][width];
		for (int depth = maxDepth * 2; depth > 1; depth--) {
			createLODMatrix(viewpoint, 0, 0, width - 1, 0, 0, width - 1, 0, depth);
			createLODMatrix(viewpoint, width - 1, width - 1, 0, width - 1, width - 1, 0, 0, depth);
		}
		createTrianglesRecursive(trianglesBuffer, 0, 0, width - 1, 0, 0, width - 1);
		createTrianglesRecursive(trianglesBuffer, width - 1, width - 1, 0, width - 1, width - 1, 0);

		lodMatrix = null;
		long time = (System.currentTimeMillis() - time0);
		if (time > 20) {
			System.out.println("lod evaluation for " + width + "x" + width + " took "
					+ (System.currentTimeMillis() - time0) + " ms");
		}
	}

	private boolean createLODMatrix(Vector3f viewpoint, int headx, int heady, int x1, int y1,
			int x2, int y2, int depth, int maxDepth) {
		int basex = (x2 - x1) / 2 + x1;
		int basey = (y2 - y1) / 2 + y1;
		if (depth == maxDepth) {
			return false;
		}
		// check if we want to recurse
		float height1 = getVertexHeight(x1, y1);
		float height2 = getVertexHeight(x2, y2);
		float heightBase = getVertexHeight(basex, basey);

		boolean subdivide = getLodMatrix(basex, basey); // might be already set
		subdivide |= calcLodNecessity(viewpoint, height1, height2, basex, basey, heightBase);
		// recurse into two subtriangles
		subdivide |= createLODMatrix(viewpoint, basex, basey, x2, y2, headx, heady, depth + 1,
				maxDepth);
		subdivide |= createLODMatrix(viewpoint, basex, basey, headx, heady, x1, y1, depth + 1,
				maxDepth);
		setLodMatrix(basex, basey, subdivide);
		return subdivide;
	}

	private void createTrianglesRecursive(IntBuffer trianglesBuffer, int headx, int heady, int x1,
			int y1, int x2, int y2) {
		int basex = (x2 - x1) / 2 + x1;
		int basey = (y2 - y1) / 2 + y1;
		if (getLodMatrix(basex, basey) && !(basex == x1 && basey == y1)) {
			createTrianglesRecursive(trianglesBuffer, basex, basey, headx, heady, x1, y1);
			createTrianglesRecursive(trianglesBuffer, basex, basey, x2, y2, headx, heady);
		}
		else {
			putTriangle(trianglesBuffer, getVertexIndex(headx, heady), getVertexIndex(x1, y1),
					getVertexIndex(x2, y2));
		}
	}

	private boolean calcLodNecessity(Vector3f viewpoint, float height1, float height2, int splitx,
			int splity, float splitHeight) {
		if (splitx == 0 || splitx == width - 1 || splity == 0 || splity == width - 1) {
			return true;
		}
		float distance;
		if (viewpoint == null) {
			distance = 3;
		}
		else {
			Vector3f localViewpoint = viewpoint.subtract(getLocalTranslation());
			Vector3f meshpoint = getVertex(splitx, splity).mult(getLocalScale());
			distance = distance(localViewpoint, meshpoint);
		}
		return Math.abs((height2 - height1) / 2 + height1 - splitHeight) / distance > lodQuality;
	}

	private Vector3f getVertex(int x, int y) {
		int index = getVertexIndex(x, y) * 3;
		return new Vector3f(getBatch(0).getVertexBuffer().get(index), getBatch(0).getVertexBuffer()
				.get(index + 1), getBatch(0).getVertexBuffer().get(index + 2));
	}

	private float distance(Vector3f v1, Vector3f v2) {
		return FastMath.sqrt((v1.x - v2.x) * (v1.x - v2.x) + (v1.y - v2.y) * (v1.y - v2.y)
				+ (v1.z - v2.z) * (v1.z - v2.z));
	}

	public void setTextures(Texture rough, Texture detail, float detailScale, DisplaySystem display) {
		TextureState ts = display.getRenderer().createTextureState();
		ts.setEnabled(true);

		rough.setApply(Texture.AM_COMBINE);
		rough.setCombineFuncRGB(Texture.ACF_MODULATE);
		rough.setCombineSrc0RGB(Texture.ACS_TEXTURE);
		rough.setCombineOp0RGB(Texture.ACO_SRC_COLOR);
		rough.setCombineSrc1RGB(Texture.ACS_PRIMARY_COLOR);
		rough.setCombineOp1RGB(Texture.ACO_SRC_COLOR);
		rough.setCombineScaleRGB(1.0f);
		rough.setScale(new Vector3f(0.25f, 0.25f, 0.25f));
		rough.setWrap(Texture.WM_WRAP_S_WRAP_T);

		ts.setTexture(rough, 0);

		detail.setApply(Texture.AM_COMBINE);
		detail.setCombineFuncRGB(Texture.ACF_ADD_SIGNED);
		detail.setCombineSrc0RGB(Texture.ACS_TEXTURE1);
		detail.setCombineOp0RGB(Texture.ACO_SRC_COLOR);
		detail.setCombineSrc1RGB(Texture.ACS_PREVIOUS);
		detail.setCombineOp1RGB(Texture.ACO_SRC_COLOR);
		detail.setCombineScaleRGB(1.0f);
		detail.setScale(new Vector3f(detailScale, detailScale, detailScale));
		detail.setWrap(Texture.WM_WRAP_S_WRAP_T);
		copyTextureCoords(0, 0, 1);
		ts.setTexture(detail, 1);
		setRenderState(ts);
	}

	private void putTriangle(IntBuffer trianglesBuffer, int ind1, int ind2, int ind3) {
		if (ind1 < noOfVertices && ind2 < noOfVertices && ind3 < noOfVertices) {
			trianglesBuffer.put(ind1).put(ind2).put(ind3);
		}
	}

	public float getLodQuality() {
		return lodQuality;
	}

	public void setLodQuality(float lodQuality) {
		this.lodQuality = lodQuality;
	}

	public float getHeight(float f, float g) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void getSurfaceNormal(Vector3f location, Vector3f normal) {
		// TODO Auto-generated method stub

	}

	/**
	 * Sets the colors for each vertex to the color white.
	 */
	private void buildColors() {
		setDefaultColor(ColorRGBA.white);
	}
}
