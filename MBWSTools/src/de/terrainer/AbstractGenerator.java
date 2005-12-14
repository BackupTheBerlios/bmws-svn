package de.terrainer;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.Action;
import javax.swing.ImageIcon;

public abstract class AbstractGenerator {
	public static final ImageIcon defaultIcon;
	protected HeightMap heightMap;
	public static class GeneratorProperty {
		public String displayName;
		public String setterName;
		// type ???
		// value range
	}
	public class DefaultAction implements Action {
		boolean enabled = true;
		public Object getValue(String key) {
			return null;
		}

		public void putValue(String key, Object value) {
		}

		public void setEnabled(boolean b) {
			enabled = b;
		}

		public boolean isEnabled() {
			return enabled;
		}

		public void addPropertyChangeListener(PropertyChangeListener listener) {
		}

		public void removePropertyChangeListener(PropertyChangeListener listener) {
		}

		public void actionPerformed(ActionEvent e) {
			enabled = false;
			generate();
			enabled = true;
		}
		
	}
	
	static{
		byte[] buffer = new byte[1024];
		InputStream bin =ClassLoader.getSystemClassLoader().getResourceAsStream("de/terrainer/resource/arrow.gif");
		try {
			bin.read(buffer);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		defaultIcon = new ImageIcon(buffer);
	}

	public abstract void generate();
	
	public AbstractGenerator(HeightMap heightMap) {
		this.heightMap = heightMap;
	}

	public abstract void setMask(int[][] mask);

	public abstract GeneratorProperty[] getProperties();

	public abstract String getName();

	public Action getAction() {
		Action action = new DefaultAction();
		return action;
	}
	
	public ImageIcon getIcon() {
		return defaultIcon;
	}

}
