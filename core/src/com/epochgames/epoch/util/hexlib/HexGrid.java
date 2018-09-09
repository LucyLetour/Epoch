package com.epochgames.epoch.util.hexlib;

import java.awt.*;

public class HexGrid {
    private HexHelper.GRID_ORIENTATION orientation;
    private Hexagon[][] grid;
    private int tiles;

    public HexGrid(HexHelper.GRID_ORIENTATION o, HexHelper.GRID_SIZE s) {
        orientation = o;
        generateGrid(s);
    }

    public void generateGrid(HexHelper.GRID_SIZE s) {
        int tiles;

        switch (s) {
            case OPEN_SPACE:
                tiles = 90;
                break;
            case PLANETARY_ORBIT:
                tiles = 11;
                break;
            default:
                tiles = 1;
        }

        grid = new Hexagon[tiles][tiles];

        for (int i = 0; i < tiles; i++) {
            for (int j = 0; j < tiles; j++) {
                grid[i][j] = new Hexagon(new OffsetCoord(i, j));
            }
        }

        this.tiles = tiles;
    }


    public void drawGrid(Graphics2D g, float size, Point offset) {
        Point[] points;

        for (Hexagon[] row: grid) {
            for (Hexagon h : row) {

                //Gets the coordinates of vertices of the hexagon located at the given coordinate
                points = HexHelper.evenRToPixelHexagonVertices(h.getOffsetCoord(), size, offset);
                int[] xPoints = new int[]{(int)points[0].x, (int)points[1].x, (int)points[2].x, (int)points[3].x, (int)points[4].x, (int)points[5].x};
                int[] yPoints = new int[]{(int)points[0].y, (int)points[1].y, (int)points[2].y, (int)points[3].y, (int)points[4].y, (int)points[5].y};
                g.drawPolygon(xPoints, yPoints, 6);
                /*g.drawString(h.getOffsetCoord().coords[0] + ", " + h.getOffsetCoord().coords[1],
                        HexHelper.evenRToPixelHexagonCenter(h.getOffsetCoord(), size, offset).x,
                        HexHelper.evenRToPixelHexagonCenter(h.getOffsetCoord(), size, offset).y);*/
            }
        }
    }

    public Hexagon getHexagon(OffsetCoord o) {
        return grid[o.getCoords()[0]][o.getCoords()[1]];
    }

    public Hexagon[][] getGrid() {
        return grid;
    }

    public int getSize() {
        return tiles;
    }

    @Override
    public String toString() {
        String out = "";
        for (int i = 0; i < tiles; i++) {
            for (int j = 0; j < tiles; j++) {
                out += grid[i][j] + " ";
            }
            out += "\n";
        }
        return out;
    }
}
