package de.terrainer.terrainloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.SAXException;

import com.jme.bounding.BoundingBox;
import com.jme.image.Texture;
import com.jme.math.Vector3f;
import com.jme.scene.state.TextureState;
import com.jme.util.TextureManager;
import com.jmex.terrain.TerrainBlock;

public class TerrainLoader {
	private DynamicTerrain dynamicTerrain;

	TerrainLoader(DynamicTerrain dynamicTerrain) {
		this.dynamicTerrain = dynamicTerrain;
	}

	public void loadWorldDescription(String path) throws SAXException, IOException {
		try {
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
					new File(path));
			NamedNodeMap attributes = document.getFirstChild().getAttributes();
			dynamicTerrain.sectionColumns = readIntAttribute(attributes, "columns");
			dynamicTerrain.sectionRows = readIntAttribute(attributes, "rows");
			// spatial size, spatial scale...
		}
		catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

	}

	public TerrainBlock loadTerrainBlock(int column, int row) throws IOException {
		int terrainSize = dynamicTerrain.sectionResolution*dynamicTerrain.sectionResolution;
		String sectionPath = dynamicTerrain.worldPath + "_" + column + "_" + row;
		byte[] bytes = new byte[terrainSize * 4];
		FileInputStream fis = new FileInputStream(sectionPath + ".ter");
		int nr = 0;
		int readct = 0;
		while ((readct = fis.read(bytes, nr, bytes.length-nr))>0) {
			nr += readct;
		}
		ByteBuffer buffer = ByteBuffer.wrap(bytes);
		int[] heightMap = new int[terrainSize];
		for (int i = 0; i < heightMap.length; i++) {
			heightMap[i] = buffer.getInt();
		}
		Vector3f scale = new Vector3f(dynamicTerrain.spatialScale, dynamicTerrain.heightScale,
				dynamicTerrain.spatialScale);
		Vector3f origin = new Vector3f(column * dynamicTerrain.sectionWidth, 0, row
				* dynamicTerrain.sectionWidth);
		TerrainBlock terrainBlock = new TerrainBlock("terrain(" + column + ", " + row + ")",
				dynamicTerrain.sectionResolution, scale, heightMap, origin, false);
		TextureState ts = dynamicTerrain.display.getRenderer().createTextureState();
		// TODO use the commented line instead
		ts.setTexture(TextureManager.loadTexture("..\\MBWSClient\\data\\images\\grassb.png",
				Texture.MM_LINEAR, Texture.FM_LINEAR));
		// ts.setTexture(TextureManager.loadTexture(sectionPath+".png",
		// Texture.MM_LINEAR,
		// Texture.FM_LINEAR));
		terrainBlock.setRenderState(ts);
		terrainBlock.setModelBound(new BoundingBox());
		terrainBlock.updateModelBound();
		return terrainBlock;
	}

	private static int readIntAttribute(NamedNodeMap attributes, String attrName) {
		int value = Integer.parseInt(attributes.getNamedItem(attrName).getNodeValue());
		System.out.println(attrName + " = " + value);
		return value;
	}

}
