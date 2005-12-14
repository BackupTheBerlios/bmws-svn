package de.terrainer;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

public class HeightMapComponent extends JComponent {
	private HeightMap heightMap;

	public void setHeightMap(HeightMap heightMap) {
		this.heightMap = heightMap;
	}

	@Override
	public void paint(Graphics g) {
		if (heightMap == null) {
			g.setColor(Color.RED);
			g.drawString("no height map defined", 30, 30);
			return;
		}
		float cellWidth = ((float) getWidth()) / heightMap.getDimension().width;
		float cellHeight = ((float) getHeight()) / heightMap.getDimension().height;
		for (int x = 0; x < heightMap.getDimension().width; x++) {
			for (int y = 0; y < heightMap.getDimension().height; y++) {
				int max = Math.max(Math.abs(heightMap.getMaximumHeight()), Math.abs(heightMap
						.getMinimumHeight()))+1;
				float relHeight = ((float) heightMap.getHeightAt(x, y))/max;
				Color color;
				if (relHeight > 0)
					color = new Color(relHeight/1.3f, relHeight/2+0.3f, relHeight/1.5f);
				else
					color = new Color(0, 0.5f+relHeight / 2, 1+relHeight);
				g.setColor(color);
				g.fillRect((int) (x * cellWidth), (int) (y * cellHeight), (int) cellWidth + 1,
						(int) cellHeight + 1);
			}
		}
	}
}
