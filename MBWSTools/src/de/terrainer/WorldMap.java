package de.terrainer;

public class WorldMap {
	private String name;
	private int width;
	private int height;
	private int sectionResolution;
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSectionResolution() {
		return sectionResolution;
	}
	public void setSectionResolution(int sectionResolution) {
		this.sectionResolution = sectionResolution;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}

}
