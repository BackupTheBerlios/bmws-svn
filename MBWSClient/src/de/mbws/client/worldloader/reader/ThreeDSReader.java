package de.mbws.client.worldloader.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jme.bounding.BoundingBox;
import com.jme.image.Texture;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.TriMesh;
import com.jme.scene.state.TextureState;
import com.jme.system.DisplaySystem;
import com.jme.system.JmeException;
import com.jme.util.TextureManager;
import com.jme.util.geom.BufferUtils;

public class ThreeDSReader {

	private static class Chunk {
		public int type;
		public int length;
		public int startingOffset;
	}

	private static class Material {
		public String name;
		public ColorRGBA color;
		public String textureName;
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

	private static Logger logger = Logger.getLogger(ThreeDSReader.class);

	private BinaryFileReader reader;
	private Node objectNode;
	private Vector3f[] vertices;
	private Vector2f[] textureCoordinates;
	private Map materials = new HashMap();
	String path;

	public static Node read(String filename, String name) throws FileNotFoundException {
		File file = new File(filename);
		FileInputStream is = new FileInputStream(file);
		return (new ThreeDSReader()).internalRead(is, file.getParent(), name);
	}

	private Node internalRead(InputStream is, String path, String name) {
		this.path = path;
		reader = new BinaryFileReader(is);
		Chunk main = readChunkHeader();
		if (main.type != FILE_MAGIC_3DS)
			throw new JmeException("Not a 3DS file");
		objectNode = new Node(name);
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
					logger.debug("Reading chunk " + chunkReader[i].getClass().getName()
							+ " of length " + chunk.length);
					chunkReader[i].readChunk(chunk);
					isKnownChunk = true;
					break;
				}
			}
			if (!isKnownChunk) {
				logger.debug("Skipping chunk " + Integer.toHexString(chunk.type) + " of length "
						+ chunk.length);
				skipChunk(chunk);
			}
		} while (reader.getPos() - superchunk.startingOffset < superchunk.length - 6);
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
			logger.debug("Reading object '" + name + "'");
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
			TriMesh mesh = new TriMesh(name);
			mesh.setDefaultColor(ColorRGBA.green);
			readSubChunks(new AbstractChunkReader[] { new VerticesList(mesh),
					new FacesDescription(mesh), new FacesMaterial(mesh),
					new FacesTextureCoordinates() }, chunk);
			if (mesh.getVertQuantity() > 0) {
				objectNode.attachChild(mesh);
			}
			mesh.setModelBound(new BoundingBox());
			mesh.updateModelBound();
		}
	}

	private class VerticesList extends AbstractChunkReader {
		TriMesh mesh;

		public VerticesList(TriMesh mesh) {
			super(VERTICES_LIST);
			this.mesh = mesh;
		}

		@Override
		public void readChunk(Chunk chunk) {
			int noOfVertices = reader.readShort();
			logger.debug("Reading " + noOfVertices + " vertices");
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
		TriMesh mesh;

		public FacesDescription(TriMesh mesh) {
			super(FACES_DESCRIPTION);
			this.mesh = mesh;
		}

		@Override
		public void readChunk(Chunk chunk) {
			int noOfTriangles = reader.readShort();
			int noOfVertices = noOfTriangles * 3;
			logger.debug("Reading " + noOfTriangles + " triangles");
			IntBuffer trianglesBuffer = BufferUtils.createIntBuffer(noOfVertices);
			FloatBuffer normalBuffer = BufferUtils.createVector3Buffer(noOfVertices);
			FloatBuffer vertexBuffer = BufferUtils.createVector3Buffer(noOfVertices);
			FloatBuffer colorBuffer = BufferUtils.createColorBuffer(noOfVertices);
			FloatBuffer textureBuffer = BufferUtils.createVector2Buffer(noOfVertices);
			for (int i = 0; i < noOfTriangles; i++) {
				// read indices of the triangle
				int t1 = reader.readShort();
				int t2 = reader.readShort();
				int t3 = reader.readShort();
				reader.readShort(); // properties are ignored for the timebeeing
				// logger.debug("Index " + i + ": " + t1 + ", " + t2 + ", " + t3 + " => "
				// + reader.getPos());
				trianglesBuffer.put(i * 3).put(i * 3 + 1).put(i * 3 + 2);
				// create 3 vertices per triangles, do not reuse vertices for other triangles
				vertexBuffer.put(vertices[t1].x).put(vertices[t1].y).put(vertices[t1].z);
				vertexBuffer.put(vertices[t2].x).put(vertices[t2].y).put(vertices[t2].z);
				vertexBuffer.put(vertices[t3].x).put(vertices[t3].y).put(vertices[t3].z);
				// set color for every vertex
				for (int j = 0; j < 3; j++) {
					colorBuffer.put(1).put(0.0f).put(0.0f).put(1);
				}
				// set texture coordinates
				if (textureCoordinates != null) {
					textureBuffer.put(textureCoordinates[t1].x).put(textureCoordinates[t1].y).put(
							textureCoordinates[t2].x).put(textureCoordinates[t2].y).put(
							textureCoordinates[t3].x).put(textureCoordinates[t3].y);
				}
				// calculate normal
				Vector3f vec2 = vertices[t1].subtract(vertices[t2]);
				Vector3f vec1 = vertices[t3].subtract(vertices[t2]);
				Vector3f norm = vec2.cross(vec1).normalize();
				for (int j = 0; j < 3; j++) {
					normalBuffer.put(norm.x).put(norm.y).put(norm.z);
				}
				// logger.debug("Index " + i + " normal : " + norm.x + ", " + norm.y + ", " + norm.z
				// + " => "
				// + reader.getPos());
			}
			vertices = null;
			textureCoordinates = null;
			trianglesBuffer.flip();
			normalBuffer.flip();
			vertexBuffer.flip();
			colorBuffer.flip();
			mesh.setIndexBuffer(trianglesBuffer);
			mesh.setTriangleQuantity(noOfTriangles);
			mesh.setNormalBuffer(normalBuffer);
			mesh.setVertexBuffer(vertexBuffer);
			mesh.setVertQuantity(3 * noOfTriangles);
			mesh.setTextureBuffer(textureBuffer);
			mesh.setColorBuffer(colorBuffer);
			// mesh.setRandomColors();

			// MaterialState ms =
			// DisplaySystem.getDisplaySystem().getRenderer().createMaterialState();
			// ms.setDiffuse(ColorRGBA.blue);

			// mesh.setRenderState(DisplaySystem.getDisplaySystem().getRenderer().createTextureState());
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

	private class FacesMaterial extends AbstractChunkReader {
		Spatial spatial;

		public FacesMaterial(Spatial spatial) {
			super(FACES_MATERIAL);
			this.spatial = spatial;
		}

		@Override
		public void readChunk(Chunk chunk) {
			String materialName = reader.readString();
			int noOfAssignedTriangles = reader.readShort();
			logger.debug("Material " + materialName + " used for " + noOfAssignedTriangles
					+ " triangles");
			Material material = (Material) materials.get(materialName);
			if (material != null && material.textureName != null) {
				Texture texture = TextureManager.loadTexture(path + File.separator
						+ material.textureName, Texture.MM_LINEAR, Texture.FM_LINEAR);
				TextureState ts = DisplaySystem.getDisplaySystem().getRenderer()
						.createTextureState();
				ts.setTexture(texture);
				spatial.setRenderState(ts);
			}
			// for (int i = 0; i < noOfAssignedTriangles; i++) {
			//
			// }
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
			logger.debug("Definition of material " + material.name);
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
			material.textureName = reader.readString();
			logger.debug("Using texture: " + material.textureName);
		}
	}
}
