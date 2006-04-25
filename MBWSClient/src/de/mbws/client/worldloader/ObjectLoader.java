package de.mbws.client.worldloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.jme.bounding.BoundingBox;
import com.jme.image.Image;
import com.jme.image.Texture;
import com.jme.math.FastMath;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.state.TextureState;
import com.jme.system.DisplaySystem;
import com.jme.util.TextureManager;
import com.jmex.model.XMLparser.JmeBinaryReader;
import com.jmex.terrain.TerrainBlock;

import de.mbws.client.worldloader.reader.ThreeDSReader;

public class ObjectLoader {
	private static Logger logger = Logger.getLogger(ObjectLoader.class);

	private String worldPath;
	private String objectPath;
	private WorldDescription worldDescription;

	private class ApplyTextureTask implements Runnable {
		private Image textureImage;
		private Spatial spatial;

		public ApplyTextureTask(Image texturePath, Spatial applyToObject) {
			this.textureImage = texturePath;
			this.spatial = applyToObject;
		}

		public void run() {
			TextureState ts = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
			logger.debug("apply texture " + textureImage);
			Texture texture = new Texture(ts.getMaxAnisotropic());
			texture.setCorrection(Texture.CM_PERSPECTIVE);
			texture.setFilter(Texture.FM_LINEAR);
			texture.setImage(textureImage);
			texture.setMipmapState(Texture.MM_LINEAR_LINEAR);
			texture.setWrap(Texture.WM_WRAP_S_WRAP_T);
			// texture.setScale(new Vector3f(5,5,5));
			ts.setTexture(texture);
			spatial.setRenderState(ts);
			spatial.updateRenderState();
			logger.debug("Successfully applied texture " + textureImage);
		}
	}

	private class CompleteModelTask implements Runnable {
		ThreeDSReader reader;

		public CompleteModelTask(ThreeDSReader reader) {
			this.reader = reader;
		}

		public void run() {
			reader.completeModel();
		}
	}

	public WorldDescription loadWorldDescription(String worldPath) throws SAXException, IOException {
		worldDescription = new WorldDescription();
		this.worldPath = worldPath;
		// TODO add a Schema or DTD validation
		try {
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
					new File(worldPath + ".wld"));
			NamedNodeMap attributes = document.getFirstChild().getAttributes();
			worldDescription.sectionColumns = readIntAttribute(attributes, "columns");
			worldDescription.sectionRows = readIntAttribute(attributes, "rows");
			worldDescription.sectionResolution = readIntAttribute(attributes, "resolution");
			// spatial size, spatial scale...
		}
		catch (ParserConfigurationException e) {
			logger.error("loadWorldDescription() " + e);
		}
		return worldDescription;
	}

	public List<ObjectDescription> loadSectionObjectList(String path) {
		List<ObjectDescription> objectList = new ArrayList<ObjectDescription>();
		try {
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
					new File(path));
			NodeList nodeList = document.getElementsByTagName("Object");
			for (int i = 0; i < nodeList.getLength(); i++) {
				ObjectDescription descr = new ObjectDescription();
				NamedNodeMap objectAttrs = nodeList.item(i).getAttributes();
				descr.name = objectAttrs.getNamedItem("name").getNodeValue();
				descr.x = readFloatAttribute(objectAttrs, "pos_x");
				descr.y = readFloatAttribute(objectAttrs, "pos_y");
				descr.z = readFloatAttribute(objectAttrs, "pos_z");
				descr.rot_x = FastMath.TWO_PI / 360 * readFloatAttribute(objectAttrs, "rot_x");
				descr.rot_y = FastMath.TWO_PI / 360 * readFloatAttribute(objectAttrs, "rot_y");
				descr.rot_z = FastMath.TWO_PI / 360 * readFloatAttribute(objectAttrs, "rot_z");
				descr.scale = readFloatAttribute(objectAttrs, "scale");
				objectList.add(descr);
			}
			logger.debug("Read descriptions for " + nodeList.getLength() + " objects");
		}
		catch (Exception e) {
			logger.error("loadSectionObjectList() " + e);
		}
		return objectList;
	}

	public Node loadObject(String objectName) {
		Node objectNode = null;
		try {
			if (objectName.endsWith(".jme")) {
				FileInputStream fi = new FileInputStream(new File(objectPath + "/model/"
						+ objectName));
				String texturePath = objectPath + "/textures/" + objectName;
				texturePath = texturePath.replaceFirst(".jme", ".jpg");
				JmeBinaryReader jbr = new JmeBinaryReader();
				// jbr.setProperty("texurl", urlOfTexture);
				jbr.setProperty("bound", "box"); // Doesn't work ?
				long time = System.currentTimeMillis();
				objectNode = jbr.loadBinaryFormat(fi);
				Image textureImage = TextureManager.loadImage(new URL("file:" + texturePath), true);
				// SyncTaskQueue.getInstance().enqueue("applyTexForObject"+textureImage,
				SyncTaskQueue.getInstance().executeSynchronously(
						new ApplyTextureTask(textureImage, objectNode));
				logger.info("Time to convert from .jme to SceneGraph:"
						+ (System.currentTimeMillis() - time));
			}
			else if (objectName.toLowerCase().endsWith(".3ds")) {
				ThreeDSReader reader = new ThreeDSReader(objectPath + "/" + objectName, objectName);
				objectNode = reader.preloadMeshesAndTextures();
				SyncTaskQueue.getInstance().executeSynchronously(new CompleteModelTask(reader));
			}
		}
		catch (Exception e) {
			logger.error("loadObject() " + e);
		}
		return objectNode;
	}

	public TerrainBlock loadTerrainBlock(int column, int row) throws IOException {
		String sectionPath = worldPath + "_" + column + "_" + row;
		final int[] heightMap = readIntArrayFromFile(column, row, sectionPath + ".ter");
		Vector3f scale = new Vector3f(worldDescription.spatialScale, worldDescription.heightScale,
				worldDescription.spatialScale);
		Vector3f origin = new Vector3f(column * worldDescription.getSectionWidth(), 0, row
				* worldDescription.getSectionWidth());
		TerrainBlock terrainBlock = new TerrainBlock("terrain(" + column + ", " + row + ")",
				worldDescription.sectionResolution, scale, heightMap, origin, false);

		Image textureImage = TextureManager.loadImage(new URL(
				"file:../MBWSClient/data/images/grassb.png"), false);

		SyncTaskQueue.getInstance().enqueue("applySectionTex" + column + "_" + row,
				new ApplyTextureTask(textureImage, terrainBlock));
		terrainBlock.setModelBound(new BoundingBox());
		terrainBlock.updateModelBound();
		return terrainBlock;
	}

	private int[] readIntArrayFromFile(int column, int row, String path)
			throws FileNotFoundException, IOException {
		int terrainSize = worldDescription.sectionResolution * worldDescription.sectionResolution;
		byte[] bytes = new byte[terrainSize * 4];
		FileInputStream fis = new FileInputStream(path);
		int nr = 0;
		int readct = 0;
		while ((readct = fis.read(bytes, nr, bytes.length - nr)) > 0) {
			nr += readct;
		}
		ByteBuffer buffer = ByteBuffer.wrap(bytes);
		int[] heightMap = new int[terrainSize];
		for (int i = 0; i < heightMap.length; i++) {
			heightMap[i] = buffer.getInt();
		}
		return heightMap;
	}

	private static int readIntAttribute(NamedNodeMap attributes, String attrName) {
		int value = Integer.parseInt(attributes.getNamedItem(attrName).getNodeValue());
		return value;
	}

	private static float readFloatAttribute(NamedNodeMap attributes, String attrName) {
		float value = Float.parseFloat(attributes.getNamedItem(attrName).getNodeValue());
		return value;
	}

	public void setObjectPath(String objectPath) {
		this.objectPath = objectPath;
	}

	public void setWorldPath(String worldPath) {
		this.worldPath = worldPath;
	}

}
