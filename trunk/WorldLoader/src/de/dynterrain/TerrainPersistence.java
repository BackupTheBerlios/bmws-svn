package de.dynterrain;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.ByteBuffer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TerrainPersistence {
	private int sectionResolution = 128;
	private int sectionColumns = 8;
	private int sectionRows = 8;
	private String worldName;
	private Document document;
	private Element worldElement;
	private int[][][] sections;

	public TerrainPersistence(String worldName, int cols, int rows, int resolution) {
		this.sectionRows = rows;
		this.sectionColumns = cols;
		this.worldName = worldName;
		this.sectionResolution = resolution;
		sections = new int[cols][rows][];
		createWorldDescription();
	}

	private static void write(Document doc, Writer out) {
		try {
			DOMSource domSource = new DOMSource(doc);
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(domSource, new StreamResult(out));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createWorldDescription() {
		try {
			DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			document = docBuilder.newDocument();
			worldElement = document.createElement("World");
			worldElement.setAttribute("columns", Integer.toString(sectionColumns));
			worldElement.setAttribute("rows", Integer.toString(sectionRows));
			worldElement.setAttribute("resolution", Integer.toString(sectionResolution));
			document.appendChild(worldElement);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addSection(int x, int y, int[] heightfield) {
		sections[x][y] = heightfield;
	}

	public void writeSection(int x, int y, int[] heightfield) {
		try {
			ByteBuffer buffer = ByteBuffer.allocate(4 * heightfield.length);
			for (int i = 0; i < heightfield.length; i++) {
				buffer.putInt(heightfield[i]);
			}
			buffer.flip();
			String filename = worldName + "_" + x + "_" + y + ".ter";
			FileOutputStream fo = new FileOutputStream(filename);
			fo.write(buffer.array());
			Element sectionElement = document.createElement("Section");
			sectionElement.setAttribute("terrainFile", filename);
			sectionElement.setAttribute("column", Integer.toString(x));
			sectionElement.setAttribute("row", Integer.toString(y));
			worldElement.appendChild(sectionElement);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void writeWorld() {
		createWorldDescription();
		writeWorldDescription();

	}
	

	private void writeWorldDescription() {
		try {
			FileWriter fw = new FileWriter(worldName + ".wld");
			write(document, fw);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
