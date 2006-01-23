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
		String sectionPath = dynamicTerrain.worldPath + "_" + column + "_"
		+ row;
		TerrainBlock terrainBlock = new TerrainBlock();
		ByteBuffer buffer = ByteBuffer.allocate(dynamicTerrain.terrainSize*4);
		FileInputStream fis = new FileInputStream(sectionPath + ".ter");
		fis.read(buffer.array());
		buffer.flip();
		int[] heightMap = new int[dynamicTerrain.terrainSize];
		for (int i=0; i<heightMap.length; i++) {
			heightMap[i] = buffer.getInt();
		}
		terrainBlock.setHeightMap(heightMap);
		terrainBlock.setStepScale(new Vector3f(dynamicTerrain.spatialScale, dynamicTerrain.heightScale ,dynamicTerrain.spatialScale));
		TextureState ts = dynamicTerrain.display.getRenderer().createTextureState();
		// TODO use the commented line instead
		ts.setTexture(TextureManager.loadTexture("..\\MBWSClient\\data\\images\\grassb.png", Texture.MM_LINEAR,
				Texture.FM_LINEAR));
//		ts.setTexture(TextureManager.loadTexture(sectionPath+".png", Texture.MM_LINEAR,
//				Texture.FM_LINEAR));
		terrainBlock.setRenderState(ts);
		return terrainBlock;
	}

	private static int readIntAttribute(NamedNodeMap attributes, String attrName) {
		int value = Integer.parseInt(attributes.getNamedItem(attrName).getNodeValue());
		System.out.println(attrName + " = " + value);
		return value;
	}

}
