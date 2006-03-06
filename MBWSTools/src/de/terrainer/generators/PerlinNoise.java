package de.terrainer.generators;

import java.awt.Point;

import de.terrainer.AbstractGenerator;
import de.terrainer.MetaInfo;
import de.terrainer.gui.HeightMapComponent;

public class PerlinNoise extends AbstractGenerator {
    int maxDepth = 3;

    public PerlinNoise(HeightMapComponent hmc) {
        super(hmc);
    }

    @Override
    public void generate() {
        int width = getHeightMap().getWidth() - 1;
        getHeightMap().setHeight(0,0, 100);
        generate(new Point(0, 0), new Point(width, 0), new Point(0, width),
                new Point(width, width), 0);
        getHeightMap().init();
    }

    private void generate(Point lu, Point ru, Point ld, Point rd, int depth) {
        Point left = new Point(lu.x, ld.y + (lu.y - ld.y) / 2);
        Point right = new Point(ru.x, ld.y + (lu.y - ld.y) / 2);
        Point up = new Point(lu.x + (ru.x - lu.x) / 2, lu.y);
        Point down = new Point(lu.x + (ru.x - lu.x) / 2, ld.y);
        Point center = new Point(lu.x + (ru.x - lu.x) / 2, ld.y + (lu.y - ld.y) / 2);
        // interpolation
        interpolate(lu, ru, ld, rd, left);
        interpolate(lu, ru, ld, rd, right);
        interpolate(lu, ru, ld, rd, up);
        interpolate(lu, ru, ld, rd, down);
        interpolate(lu, ru, ld, rd, center);
        depth++;
        if (center.x - left.x > 1 || up.y - center.y > 1) {
            generate(lu, up, left, center, depth);
            generate(up, ru, center, right, depth);
            generate(left, center, ld, down, depth);
            generate(center, right, down, rd, depth);
        }

    }

    /**
     * @param lu
     * @param ru
     * @param ld
     * @param rd
     * @param pt
     */
    private void interpolate(Point lu, Point ru, Point ld, Point rd, Point pt) {
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
        getHeightMap().setHeight(pt.x, pt.y, (int) height);
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
