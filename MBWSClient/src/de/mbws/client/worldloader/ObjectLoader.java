package de.mbws.client.worldloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.jme.bounding.BoundingBox;
import com.jme.image.Texture;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.state.TextureState;
import com.jme.util.TextureManager;
import com.jmex.model.XMLparser.JmeBinaryReader;
import com.jmex.terrain.TerrainBlock;

public class ObjectLoader {
	private DynamicWorld dynamicWorld;
	private String worldPath;
	private String objectPath;
	private static Logger logger = Logger.getLogger(ObjectLoader.class);

	private class ApplyTextureTask implements Runnable {
		private String texturePath;
		private Spatial spatial;

		public ApplyTextureTask(String texturePath, Spatial applyToObject) {
			this.texturePath = texturePath;
			this.spatial = applyToObject;
		}

		public void run() {
			if (spatial instanceof Node) {
				Node node = (Node) spatial;
				Iterator it = ((Node)node.getChild(0)).getChildren().iterator();
				while (it.hasNext()) {
					Spatial spat = (Spatial) it.next();
					logger.debug("Texture for child: "+spat.getName());
				}
			}
			TextureState ts = dynamicWorld.display.getRenderer().createTextureState();
			logger.debug("load texture "+texturePath);
			Texture texture = TextureManager.loadTexture(texturePath, Texture.MM_LINEAR,
					Texture.FM_LINEAR);
			texture.setWrap(Texture.WM_WRAP_S_WRAP_T);
			// texture.setScale(new Vector3f(20,20,20));
			ts.setTexture(texture);
			// ts.setTexture(TextureManager.loadTexture(sectionPath+".png",
			// Texture.MM_LINEAR,
			// Texture.FM_LINEAR));
			logger.debug("Texture loaded");
			spatial.setRenderState(ts);
			logger.debug("Successfully applied texture "+texturePath);
		}
	}
	
	private class CreateTextureStateTask implements Runnable {
		TextureState textureState;
		public void run() {
			textureState = dynamicWorld.display.getRenderer().createTextureState();
		}
		public TextureState getTextureState() {
			return textureState;
		}
	}

	public TextureState createSyncTextureState() {
		CreateTextureStateTask task = new CreateTextureStateTask();
		SyncTaskQueue.getInstance().executeSynchronously(task);
		return task.getTextureState();
	}

	ObjectLoader(DynamicWorld dynamicTerrain) {
		this.dynamicWorld = dynamicTerrain;
	}

	public void loadWorldDescription(String path) throws SAXException, IOException {
		// TODO add a Schema or DTD validation
		try {
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
					new File(path));
			NamedNodeMap attributes = document.getFirstChild().getAttributes();
			dynamicWorld.sectionColumns = readIntAttribute(attributes, "columns");
			dynamicWorld.sectionRows = readIntAttribute(attributes, "rows");
			dynamicWorld.sectionResolution = readIntAttribute(attributes, "resolution");
			// spatial size, spatial scale...
		}
		catch (ParserConfigurationException e) {
			logger.error("loadWorldDescription() " + e);
		}
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
			FileInputStream fi = new FileInputStream(new File(objectPath + "/model/"
					+ objectName));
			String texturePath = objectPath + "/textures/" + objectName;
			texturePath = texturePath.replaceFirst(".jme", ".jpg");
			JmeBinaryReader jbr = new JmeBinaryReader();
			// jbr.setProperty("texurl", urlOfTexture);
			jbr.setProperty("bound", "box"); // Doesnt work ?
			long time = System.currentTimeMillis();
			objectNode = jbr.loadBinaryFormat(fi);
			SyncTaskQueue.getInstance().executeSynchronously(
					new ApplyTextureTask(texturePath, objectNode));
			logger.info("Time to convert from .jme to SceneGraph:"
					+ (System.currentTimeMillis() - time));
		}
		catch (Exception e) {
			logger.error("loadObject() " + e);
		}
		return objectNode;
	}

	public TerrainBlock loadTerrainBlock(int column, int row) throws IOException {
		String sectionPath = worldPath + "_" + column + "_" + row;
		final int[] heightMap = readIntArrayFromFile(column, row, sectionPath + ".ter");
		Vector3f scale = new Vector3f(dynamicWorld.spatialScale, dynamicWorld.heightScale,
				dynamicWorld.spatialScale);
		Vector3f origin = new Vector3f(column * dynamicWorld.sectionWidth, 0, row
				* dynamicWorld.sectionWidth);
		TerrainBlock terrainBlock = new TerrainBlock("terrain(" + column + ", " + row + ")",
				dynamicWorld.sectionResolution, scale, heightMap, origin, false);

		// TODO use the commented line instead
		// AbstractHeightMap hm = new AbstractHeightMap() {
		// public boolean load() {
		// heightData = heightMap;
		// size = dynamicWorld.sectionResolution;
		// return true;
		// }};
		// hm.load();
		// ProceduralTextureGenerator ptg = new ProceduralTextureGenerator(hm);
		// ptg.addTexture(new ImageIcon("../MBWSClient/data/images/grassc.jpg"), -1000,10, 50);
		// ptg.addTexture(new ImageIcon("../MBWSClient/data/images/stone.jpg"), 30,90, 1000);
		// ptg.createTexture(512);
		// ptg.saveTexture("../MBWSClient/data/world/world_0_0");
		// Texture texture =
		// TextureManager.loadTexture(ptg.getImageIcon().getImage(),Texture.MM_LINEAR,
		// Texture.FM_LINEAR, true);
		//

		SyncTaskQueue.getInstance().enqueue("loadSectionTex" + column + "_" + row,
				new ApplyTextureTask("../MBWSClient/data/images/grassb.png", terrainBlock));
		terrainBlock.setModelBound(new BoundingBox());
		terrainBlock.updateModelBound();
		return terrainBlock;
	}

	private int[] readIntArrayFromFile(int column, int row, String path)
			throws FileNotFoundException, IOException {
		int terrainSize = dynamicWorld.sectionResolution * dynamicWorld.sectionResolution;
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
