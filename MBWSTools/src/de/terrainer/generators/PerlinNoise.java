package de.terrainer.generators;

import java.awt.Point;
import java.util.Random;

import de.terrainer.AbstractGenerator;
import de.terrainer.MetaInfo;
import de.terrainer.gui.HeightMapComponent;

public class PerlinNoise extends AbstractGenerator {
    private int maxDepth = 0;
    private Random random = new Random(System.currentTimeMillis());
    private static int FREE = -10;
    private int scale = 1000;
    
    public PerlinNoise(HeightMapComponent hmc) {
        super(hmc);
    }

    @Override
    public void generate() {
        int width = getHeightMap().getWidth();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < width; y++) {
				getHeightMap().setHeight(x, y, FREE);
				if (x == 0 || y == 0 || x == getHeightMap().getWidth() - 1
						|| y == getHeightMap().getWidth() - 1)
					getHeightMap().setHeight(x, y, -50);
			}
		}
        getHeightMap().setHeight(33,33, 100);
        generate(new Point(0, 0), new Point(width+1, 0), new Point(0, width+1),
                new Point(width+1, width+1), 0);
        getHeightMap().init();
    }

    private void generate(Point lu, Point ru, Point ld, Point rd, int depth) {
        Point left = new Point(lu.x, ld.y + (lu.y - ld.y) / 2);
        Point right = new Point(ru.x, ld.y + (lu.y - ld.y) / 2);
        Point up = new Point(lu.x + (ru.x - lu.x) / 2, lu.y);
        Point down = new Point(lu.x + (ru.x - lu.x) / 2, ld.y);
        Point center = new Point(lu.x + (ru.x - lu.x) / 2, ld.y + (lu.y - ld.y) / 2);
        // interpolation
        interpolate(lu, ru, ld, rd, left, depth);
        interpolate(lu, ru, ld, rd, right, depth);
        interpolate(lu, ru, ld, rd, up, depth);
        interpolate(lu, ru, ld, rd, down, depth);
        interpolate(lu, ru, ld, rd, center, depth);
        depth++;
        if (center.x - left.x >= 1 || up.y - center.y >= 1) {
            generate(lu, up, left, center, depth+1);
            generate(up, ru, center, right, depth+1);
            generate(left, center, ld, down, depth+1);
            generate(center, right, down, rd, depth+1);
        }

    }

    /**
     * @param lu
     * @param ru
     * @param ld
     * @param rd
     * @param pt
     */
    private void interpolate(Point lu, Point ru, Point ld, Point rd, Point pt, int depth) {
		if (getHeightMap().getHeightAt(pt) != FREE)
			return;
        double height = 0;
        double norm = 0;
        double invdist = 0;
        invdist = 1 / distance(pt, lu);
        height += invdist * getHeightMap().getHeightAt(lu);
        norm += invdist;
        invdist = 1 / distance(pt, ru);
        height += invdist * getHeightMap().getHeightAt(ru);
        norm += invdist;
        invdist = 1 / distance(pt, ld);
        height += invdist * getHeightMap().getHeightAt(ld);
        norm += invdist;
        invdist = 1 / distance(pt, rd);
        height += invdist * getHeightMap().getHeightAt(rd);
        norm += invdist;
        height /= norm;
        int rand = depth<maxDepth? random.nextInt(scale/(depth+1)) : 0;
        getHeightMap().setHeight(pt.x, pt.y, (int) Math.round(height)+rand);
    }

    private double distance(Point pt1, Point pt2) {
        return Math.sqrt((pt1.x - pt2.x) * (pt1.x - pt2.x) + (pt1.y - pt2.y) * (pt1.y - pt2.y));
    }

    @Override
    public void setMask(int[][] mask) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "PerlinNoise";
    }

    public MetaInfo[] getMetaInfo() {
        // TODO Auto-generated method stub
        return new MetaInfo[0];
    }

}
