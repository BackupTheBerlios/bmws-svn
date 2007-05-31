package de.dynterrain;

public class WorldDescription {
	public int sectionRows;
	public int sectionColumns;
	public float spatialScale = 10;
	public float heightScale = 0.5f;
	public int sectionResolution = 65;
	
	public float getSectionWidth() {
		return spatialScale * (sectionResolution-1);
	}
}
