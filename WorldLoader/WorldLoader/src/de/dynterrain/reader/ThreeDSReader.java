package de.dynterrain.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import sun.security.action.GetBooleanAction;


import com.jme.bounding.BoundingBox;
import com.jme.image.Image;
import com.jme.image.Texture;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.TriMesh;
import com.jme.scene.VBOInfo;
import com.jme.scene.state.TextureState;
import com.jme.system.DisplaySystem;
import com.jme.system.JmeException;
import com.jme.util.TextureManager;
import com.jme.util.geom.BufferUtils;

/**
 * <p>
 * A native 3DS-Reader. The 3DS format is only partially implemented. This reader reads the meshes
 * and its textures only and creates a JME node from it. It is prepared for a separation of the
 * reentrant and synchronized tasks of model loading. The latter must be executed from the main
 * thread in JME.
 * </p>
 * To load a model single threaded use:
 * 
 * <pre>
 * Node model = read(path, nodeName);
 * </pre>
 * 
 * To load a model in the background use:
 * 
 * <pre>
 * // some thread
 * ThreeDSReader reader = new ThreeDSReader(path, nodeName);
 * reader.preloadMeshesAndTextures();
 * // JME main thread
 * Node model = reader.completeModel();
 * </pre>
 * 
 * @author Axel Sammet
 */
public class ThreeDSReader {

	private static class Chunk {
		public int type;
		public int length;
		public int startingOffset;
	}

	private static class Material {
		public String name;
		public ColorRGBA color;
		public List objectsThatApplyThisTexture = new ArrayList();
		public Image image;
	}

	private static final int FILE_MAGIC_3DS = 0x4D4D;
	private static final int EDITOR_CHUNK = 0x3D3D;
	private static final int OBJECT_BLOCK = 0x4000;
	private static final int TRIANGULAR_MESH = 0x4100;
	private static final int VERTICES_LIST = 0x4110;
	private static final int FACES_DESCRIPTION = 0x4120;
	private static final int FACES_MATERIAL = 0x4130;
	private static final int FACES_TEXTURE_COORD = 0x4140;
	private static final int MATERIAL = 0xAFFF;
	private static final int MATERIAL_NAME = 0xA000;
	private static final int MATERIAL_TEXTURE_FILE_NAME = 0xA300;
	private static final int MATERIAL_DIFFUSE_COLOR = 0xA020;
	private static final int MATERIAL_TEXTURE_MAP = 0xA200;

	private static Logger logger = Logger.getLogger(ThreeDSReader.class.getName());

	private BinaryFileReader reader;
	private Node objectNode;
	private Vector3f[] vertices;
	private Vector2f[] textureCoordinates;
	private int[] triangles;
	private ArrayList textureBlock;

	private Map materials = new HashMap();
	String path;
	String fileName;
	String nodeName;

	/**
	 * 
	 * @param fileName
	 * @param nodeName
	 * @return
	 * @throws FileNotFoundException
	 */
	public static Node read(String fileName, String nodeName) throws FileNotFoundException {
		ThreeDSReader reader = new ThreeDSReader(fileName, nodeName);
		reader.preloadMeshesAndTextures();
		Node objectNode = reader.completeModel();
		return objectNode;
	}

	/**
	 * 
	 * @param fileName
	 * @param nodeName
	 */
	public ThreeDSReader(String fileName, String nodeName) {
		this.fileName = fileName;
		this.nodeName = nodeName;
	}

	/**
	 * Applys all textures to the according nodes. This method <em> must </em> be called in the JME
	 * main thread.
	 */
	public Node completeModel() {
		Iterator it = materials.values().iterator();
		while (it.hasNext()) {
			Material mat = (Material) it.next();
			Iterator nodesIt = mat.objectsThatApplyThisTexture.iterator();
			while (nodesIt.hasNext()) {
				Spatial spatial = (Spatial) nodesIt.next();
				TextureState ts = DisplaySystem.getDisplaySystem().getRenderer()
						.createTextureState();
				Texture texture = new Texture(ts.getMaxAnisotropic());
				//texture.setCorrection(Texture.CM_PERSPECTIVE);
				texture.setFilter(Texture.FM_LINEAR);
				texture.setImage(mat.image);
				texture.setMipmapState(Texture.MM_LINEAR_LINEAR);
				texture.setWrap(Texture.WM_WRAP_S_WRAP_T);
				ts.setTexture(texture);
				spatial.setRenderState(ts);
			}
		}
		objectNode.updateRenderState();
		return objectNode;
	}

	/**
	 * Returns the preloaded model without textures. This method is <em>threadsafe</em>.
	 * 
	 * @return The preloaded model
	 * @throws FileNotFoundException
	 */
	public Node preloadMeshesAndTextures() throws FileNotFoundException {
		File file = new File(fileName);
		FileInputStream is = new FileInputStream(file);
		this.path = file.getParent();
		reader = new BinaryFileReader(is);
		Chunk main = readChunkHeader();
		if (main.type != FILE_MAGIC_3DS)
			throw new JmeException("Not a 3DS file");
		objectNode = new Node(nodeName);
		readSubChunks(new AbstractChunkReader[] { new EditorChunk() }, main);
		return objectNode;
	}

	private Chunk readChunkHeader() {
		reader.markPos();
		Chunk chunk = new Chunk();
		chunk.startingOffset = reader.getPos();
		chunk.type = reader.readShort();
		chunk.length = reader.readInt();
		return chunk;
	}

	private void skipChunk(Chunk chunk) {
		reader.seekMarkOffset(chunk.length);
	}

	private void readSubChunks(AbstractChunkReader[] chunkReader, Chunk superchunk) {
		do {
			Chunk chunk = readChunkHeader();
			boolean isKnownChunk = false;
			for (int i = 0; i < chunkReader.length; i++) {
				if (chunkReader[i].getType() == chunk.type) {
					logger.info("Reading chunk " + chunkReader[i].getClass().getName()
							+ " of length " + chunk.length);
					chunkReader[i].readChunk(chunk);
					isKnownChunk = true;
					break;
				}
			}
			if (!isKnownChunk) {
				// logger.debug("Skipping chunk " + Integer.toHexString(chunk.type) + " of length "
				// + chunk.length);
				skipChunk(chunk);
			}
		} while (reader.getPos() - superchunk.startingOffset < superchunk.length - 6);
	}

	private static class MaterialUsage {
		public int[] triangles;
		public Material material;
	}

	private static abstract class AbstractChunkReader {
		private int chunkId;

		public AbstractChunkReader(int chunkId) {
			this.chunkId = chunkId;
		}

		public int getType() {
			return chunkId;
		}

		public abstract void readChunk(Chunk chunk);
	}

	private class EditorChunk extends AbstractChunkReader {
		public EditorChunk() {
			super(EDITOR_CHUNK);
		}

		@Override
		public void readChunk(Chunk chunk) {
			readSubChunks(
					new AbstractChunkReader[] { new ObjectBlock(), new MaterialDescription() },
					chunk);
		}
	}

	private class ObjectBlock extends AbstractChunkReader {
		public ObjectBlock() {
			super(OBJECT_BLOCK);
		}

		@Override
		public void readChunk(Chunk chunk) {
			String name = reader.readString();
			logger.info("Reading object '" + name + "'");
			readSubChunks(new AbstractChunkReader[] { new TriangularMesh(name) }, chunk);
		}
	}

	private class TriangularMesh extends AbstractChunkReader {
		String name;

		public TriangularMesh(String name) {
			super(TRIANGULAR_MESH);
			this.name = name;
		}

		@Override
		public void readChunk(Chunk chunk) {
			textureBlock = new ArrayList();
			readSubChunks(new AbstractChunkReader[] { new VerticesList(), new FacesDescription(),
					new FacesMaterial(), new FacesTextureCoordinates() }, chunk);
			buildMeshes(objectNode, name);
		}

		private void buildMeshes(Node node, String name) {
			Iterator it = textureBlock.iterator();
			while (it.hasNext()) {
				MaterialUsage usedMaterial = (MaterialUsage) it.next();
				Material material = usedMaterial.material;
				TriMesh mesh = new TriMesh(name+"_"+material.name);
				int[] usedTriangles = usedMaterial.triangles;
				int noOfTriangles = usedTriangles.length;
				int noOfVertices = noOfTriangles*3;
				IntBuffer trianglesBuffer = BufferUtils.createIntBuffer(noOfVertices);
				FloatBuffer normalBuffer = BufferUtils.createVector3Buffer(noOfVertices);
				FloatBuffer vertexBuffer = BufferUtils.createVector3Buffer(noOfVertices);
				FloatBuffer textureBuffer = BufferUtils.createVector2Buffer(noOfVertices);
				int vertInd = 0;
				for (int triInd = 0; triInd < noOfTriangles; triInd++) {
					// create a new triangle and read indices of the according vertices
					trianglesBuffer.put(vertInd++).put(vertInd++).put(vertInd++);
					int t1 = triangles[usedTriangles[triInd]*3];
					int t2 = triangles[usedTriangles[triInd]*3+1];
					int t3 = triangles[usedTriangles[triInd]*3+2];
					logger.info("Building triangle "+t1+" => "+t2+" => "+t3);

					// create 3 vertices per triangles, do not reuse vertices for other triangles
					vertexBuffer.put(vertices[t1].x).put(vertices[t1].y).put(vertices[t1].z);
					vertexBuffer.put(vertices[t2].x).put(vertices[t2].y).put(vertices[t2].z);
					vertexBuffer.put(vertices[t3].x).put(vertices[t3].y).put(vertices[t3].z);
					// set texture coordinates
					if (textureCoordinates != null) {
						textureBuffer.put(textureCoordinates[t1].x).put(textureCoordinates[t1].y)
								.put(textureCoordinates[t2].x).put(textureCoordinates[t2].y)
								.put(textureCoordinates[t3].x).put(textureCoordinates[t3].y);
					}
					// calculate normal
					Vector3f vec2 = vertices[t1].subtract(vertices[t2]);
					Vector3f vec1 = vertices[t3].subtract(vertices[t2]);
					Vector3f norm = vec2.cross(vec1).normalize();
					for (int j = 0; j < 3; j++) {
						normalBuffer.put(norm.x).put(norm.y).put(norm.z);
					}
				}
				trianglesBuffer.flip();
				normalBuffer.flip();
				vertexBuffer.flip();
				mesh.setIndexBuffer(0, trianglesBuffer);
				mesh.setNormalBuffer(0, normalBuffer);
				mesh.setVertexBuffer(0, vertexBuffer);
				mesh.setTextureBuffer(0, textureBuffer);
				mesh.setModelBound(new BoundingBox());
				mesh.updateModelBound();
				VBOInfo vboInfo = new VBOInfo(true);
				mesh.getBatch(0).setVBOInfo(vboInfo);
				material.objectsThatApplyThisTexture.add(mesh);
				node.attachChild(mesh);
			}
			vertices = null;
			textureCoordinates = null;
		}
	}

	private class VerticesList extends AbstractChunkReader {
		public VerticesList() {
			super(VERTICES_LIST);
		}

		@Override
		public void readChunk(Chunk chunk) {
			int noOfVertices = reader.readShort();
			logger.info("Reading " + noOfVertices + " vertices");
			vertices = new Vector3f[noOfVertices];
			for (int i = 0; i < noOfVertices; i++) {
				float x = reader.readFloat();
				float y = reader.readFloat();
				float z = reader.readFloat();
				vertices[i] = new Vector3f(x, y, z);
				// logger.debug("Read vertex (" + x + ", " + y + ", " + z + ")");
			}
		}
	}

	private class FacesDescription extends AbstractChunkReader {
		public FacesDescription() {
			super(FACES_DESCRIPTION);
		}

		@Override
		public void readChunk(Chunk chunk) {
			int noOfTriangles = reader.readShort();
			triangles = new int[3 * noOfTriangles];
			logger.info("Reading " + noOfTriangles + " triangles");
			for (int i = 0; i < noOfTriangles; i++) {
				for (int j = 0; j < 3; j++) {
					triangles[3*i+j] = reader.readShort();
				}
				reader.readShort(); // properties are ignored for the timebeeing
			}

			/*
			 * int noOfVertices = noOfTriangles * 3; logger.debug("Reading " + noOfTriangles + "
			 * triangles"); IntBuffer trianglesBuffer = BufferUtils.createIntBuffer(noOfVertices);
			 * FloatBuffer normalBuffer = BufferUtils.createVector3Buffer(noOfVertices); FloatBuffer
			 * vertexBuffer = BufferUtils.createVector3Buffer(noOfVertices); // FloatBuffer
			 * colorBuffer = BufferUtils.createColorBuffer(noOfVertices); FloatBuffer textureBuffer =
			 * BufferUtils.createVector2Buffer(noOfVertices); for (int i = 0; i < noOfTriangles;
			 * i++) { // read indices of the triangle int t1 = reader.readShort(); int t2 =
			 * reader.readShort(); int t3 = reader.readShort(); reader.readShort(); // properties
			 * are ignored for the timebeeing // logger.debug("Index " + i + ": " + t1 + ", " + t2 + ", " +
			 * t3 + " => " // + reader.getPos()); // create triangle trianglesBuffer.put(i *
			 * 3).put(i * 3 + 1).put(i * 3 + 2); // create 3 vertices per triangles, do not reuse
			 * vertices for other triangles
			 * vertexBuffer.put(vertices[t1].x).put(vertices[t1].y).put(vertices[t1].z);
			 * vertexBuffer.put(vertices[t2].x).put(vertices[t2].y).put(vertices[t2].z);
			 * vertexBuffer.put(vertices[t3].x).put(vertices[t3].y).put(vertices[t3].z); // // set
			 * color for every vertex // for (int j = 0; j < 3; j++) { //
			 * colorBuffer.put(1).put(0.0f).put(0.0f).put(1); // } // set texture coordinates if
			 * (textureCoordinates != null) {
			 * textureBuffer.put(textureCoordinates[t1].x).put(textureCoordinates[t1].y).put(
			 * textureCoordinates[t2].x).put(textureCoordinates[t2].y).put(
			 * textureCoordinates[t3].x).put(textureCoordinates[t3].y); } // calculate normal
			 * Vector3f vec2 = vertices[t1].subtract(vertices[t2]); Vector3f vec1 =
			 * vertices[t3].subtract(vertices[t2]); Vector3f norm = vec2.cross(vec1).normalize();
			 * for (int j = 0; j < 3; j++) { normalBuffer.put(norm.x).put(norm.y).put(norm.z); } //
			 * logger.debug("Index " + i + " normal : " + norm.x + ", " + norm.y + ", " + norm.z // + " => " // +
			 * reader.getPos()); } vertices = null; textureCoordinates = null;
			 * trianglesBuffer.flip(); normalBuffer.flip(); vertexBuffer.flip(); //
			 * colorBuffer.flip(); mesh.setIndexBuffer(trianglesBuffer);
			 * mesh.setTriangleQuantity(noOfTriangles); mesh.setNormalBuffer(normalBuffer);
			 * mesh.setVertexBuffer(vertexBuffer); mesh.setVertQuantity(3 * noOfTriangles);
			 * mesh.setTextureBuffer(textureBuffer);
			 */
		}
	}

	private class FacesTextureCoordinates extends AbstractChunkReader {
		public FacesTextureCoordinates() {
			super(FACES_TEXTURE_COORD);
		}

		@Override
		public void readChunk(Chunk arg0) {
			int noOfVertices = reader.readShort();
			textureCoordinates = new Vector2f[noOfVertices];
			for (int i = 0; i < noOfVertices; i++) {
				float u = reader.readFloat();
				float v = reader.readFloat();
				textureCoordinates[i] = new Vector2f(u, v);
			}

		}
	}

	/**
	 * Sets a material for a complete submodel. Any detailed information about the triangles, which
	 * should use this model is ignored.
	 */
	private class FacesMaterial extends AbstractChunkReader {
		public FacesMaterial() {
			super(FACES_MATERIAL);
		}

		@Override
		public void readChunk(Chunk chunk) {
			String materialName = reader.readString();
			int noOfAssignedTriangles = reader.readShort();
			logger.info("Material " + materialName + " used for " + noOfAssignedTriangles
					+ " triangles");
			MaterialUsage usedMaterial = new MaterialUsage();
			usedMaterial.triangles = new int[noOfAssignedTriangles];
			usedMaterial.material = (Material) materials.get(materialName);
			for (int i = 0; i < noOfAssignedTriangles; i++) {
				usedMaterial.triangles[i] = reader.readShort();
				logger.info("Triangle "+usedMaterial.triangles[i]);
			}
			textureBlock.add(usedMaterial);
			reader.seekMarkOffset(chunk.length);
		}
	}

	/**
	 * The material chunk holds the definition of a material with color and texture information for
	 * later reference from the polygon data.
	 */
	private class MaterialDescription extends AbstractChunkReader {
		public MaterialDescription() {
			super(MATERIAL);
		}

		@Override
		public void readChunk(Chunk chunk) {
			// build a material map for later reference
			Material material = new Material();
			readSubChunks(new AbstractChunkReader[] { new MaterialName(material),
					new MaterialDiffuseColor(material), new MaterialTextureMap(material) }, chunk);
			materials.put(material.name, material);
		}
	}

	/**
	 * Reader for the name of a material definition.
	 */
	private class MaterialName extends AbstractChunkReader {
		Material material;

		public MaterialName(Material material) {
			super(MATERIAL_NAME);
			this.material = material;
		}

		@Override
		public void readChunk(Chunk chunk) {
			material.name = reader.readString();
			logger.info("Definition of material " + material.name);
		}
	}

	/**
	 * Reader for the diffuse color for a material
	 */
	private class MaterialDiffuseColor extends AbstractChunkReader {
		Material material;

		public MaterialDiffuseColor(Material material) {
			super(MATERIAL_DIFFUSE_COLOR);
			this.material = material;
		}

		@Override
		public void readChunk(Chunk chunk) {
			float red = reader.readByte() / 256f;
			float green = reader.readByte() / 256f;
			float blue = reader.readByte() / 256f;
			ColorRGBA col = new ColorRGBA(red, green, blue, 1);
			material.color = col;
			reader.seekMarkOffset(chunk.length);
		}
	}

	private class MaterialTextureMap extends AbstractChunkReader {
		Material material;

		public MaterialTextureMap(Material material) {
			super(MATERIAL_TEXTURE_MAP);
			this.material = material;
		}

		@Override
		public void readChunk(Chunk chunk) {
			readSubChunks(new AbstractChunkReader[] { new MaterialTextureFilename(material) },
					chunk);
		}
	}

	private class MaterialTextureFilename extends AbstractChunkReader {
		Material material;

		public MaterialTextureFilename(Material material) {
			super(MATERIAL_TEXTURE_FILE_NAME);
			this.material = material;
		}

		@Override
		public void readChunk(Chunk chunk) {
			String textureName = reader.readString();
			logger.info("Using texture: " + textureName);
			try {
				material.image = TextureManager.loadImage(new URL("file:" + path + File.separator
						+ textureName), true);
			}
			catch (MalformedURLException e) {
				logger.severe(e.getMessage());
			}
		}
	}
}
