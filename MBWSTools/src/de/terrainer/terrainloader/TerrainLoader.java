package de.terrainer.terrainloader;

import java.net.URL;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;

public class TerrainLoader {
	
	public void loadWorld(URL url) {
		try {
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(url.openStream());
			NamedNodeMap attributes = document.getAttributes();
			attributes.getNamedItem("columns");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
