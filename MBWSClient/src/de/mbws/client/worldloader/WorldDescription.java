package de.mbws.client.worldloader;

public class WorldDescription {
	public int sectionRows;
	public int sectionColumns;
	public float spatialScale = 12;
	public float heightScale = 0.5f;
	public int sectionResolution = 65;
	
	public float getSectionWidth() {
		return spatialScale * (sectionResolution-1);
	}
}
