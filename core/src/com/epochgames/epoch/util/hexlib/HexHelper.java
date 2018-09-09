package com.epochgames.epoch.util.hexlib;

import com.badlogic.gdx.math.Interpolation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HexHelper {

    public enum GRID_ORIENTATION {
        POINTY,
        FLAT;
    }

    public enum GRID_SIZE {
        OPEN_SPACE,
        PLANETARY_ORBIT,
        LARGE_PLANET,
        SMALL_PLANET,
        ONE;
    }

    public enum HEX_DIRECTION {
        UP_RIGHT,
        UP_LEFT,
        RIGHT,
        LEFT,
        BOTTOM_RIGHT,
        BOTTOM_LEFT;
    }

    public static Map<HEX_DIRECTION, int[]> CUBE_DIRECTION;
    static
    {
        CUBE_DIRECTION = new HashMap<HEX_DIRECTION, int[]>();
        CUBE_DIRECTION.put(HEX_DIRECTION.UP_RIGHT, new int[]{+1, 0, -1});
        CUBE_DIRECTION.put(HEX_DIRECTION.UP_LEFT, new int[]{0, +1, -1});
        CUBE_DIRECTION.put(HEX_DIRECTION.RIGHT, new int[]{+1, -1, 0});
        CUBE_DIRECTION.put(HEX_DIRECTION.LEFT, new int[]{-1, +1, 0});
        CUBE_DIRECTION.put(HEX_DIRECTION.BOTTOM_RIGHT, new int[]{0, -1, +1});
        CUBE_DIRECTION.put(HEX_DIRECTION.BOTTOM_LEFT, new int[]{-1, 0, +1});

    }

    public static OffsetCoord cubeToEvenR(CubeCoord c) {
        int column = c.getCoords()[0] + (c.getCoords()[2] + (c.getCoords()[2] & 1)) / 2;
        int row = c.getCoords()[2];
        return new OffsetCoord(column, row);
    }

    public static CubeCoord evenRToCube(OffsetCoord o) {
        int x = o.getCoords()[0] - (o.getCoords()[1] + (o.getCoords()[1] & 1)) / 2;
        int z = o.getCoords()[1];
        int y = -x - z;
        return new CubeCoord(x, y, z);
    }

    public static Hexagon generateEmptyNeighbor(Hexagon a, HEX_DIRECTION dir) {
        int[] transform = CUBE_DIRECTION.get(dir);
        return new Hexagon(new CubeCoord(evenRToCube(a.getOffsetCoord()).getCoords()[0] + transform[0],
                evenRToCube(a.getOffsetCoord()).getCoords()[1] + transform[1],
                evenRToCube(a.getOffsetCoord()).getCoords()[2] + transform[2]));
    }

    public static Hexagon getNeighbor(Hexagon a, HEX_DIRECTION dir, HexGrid grid) {
        return grid.getHexagon(generateEmptyNeighbor(a, dir).getOffsetCoord());
    }

    /**
     * Starts upper right and circles clockwise to upper left
     * @param a The hexagon to find the neighbors of
     * @param grid The grid the hexagon is located on
     * @return an array of the hexagon's naighbors. The array is GARUNTEED to have 6 elements,
     * but if the hexagon does not exist, that element will be an <code>OutOfBounds</code> object
     */
    public static Hexagon[] getNeighbors(Hexagon a, HexGrid grid) {
        Hexagon[] neighbors = new Hexagon[6];
        try {
            neighbors[0] = getNeighbor(a, HEX_DIRECTION.UP_RIGHT, grid);
        }
        catch (IndexOutOfBoundsException e) {
            neighbors[0] = new OutOfBounds();
        }

        try {
            neighbors[1] = getNeighbor(a, HEX_DIRECTION.RIGHT, grid);
        }
        catch (IndexOutOfBoundsException e) {
            neighbors[1] = new OutOfBounds();
        }

        try {
            neighbors[2] = getNeighbor(a, HEX_DIRECTION.BOTTOM_RIGHT, grid);
        }
        catch (IndexOutOfBoundsException e) {
            neighbors[2] = new OutOfBounds();
        }

        try {
            neighbors[3] = getNeighbor(a, HEX_DIRECTION.BOTTOM_LEFT, grid);
        }
        catch (IndexOutOfBoundsException e) {
            neighbors[3] = new OutOfBounds();
        }

        try {
            neighbors[4] = getNeighbor(a, HEX_DIRECTION.LEFT, grid);
        }
        catch (IndexOutOfBoundsException e) {
            neighbors[4] = new OutOfBounds();
        }

        try {
            neighbors[5] = getNeighbor(a, HEX_DIRECTION.UP_LEFT, grid);
        }
        catch (IndexOutOfBoundsException e) {
            neighbors[5] = new OutOfBounds();
        }

        return neighbors;
    }


    public static int calculateDistance(Hexagon a, Hexagon b) {
        CubeCoord ac = evenRToCube(a.getOffsetCoord());
        CubeCoord bc = evenRToCube(b.getOffsetCoord());
        return (Math.abs(ac.getCoords()[0] - bc.getCoords()[0]) +
                Math.abs(ac.getCoords()[1] - bc.getCoords()[1]) +
                Math.abs(ac.getCoords()[2] - bc.getCoords()[2])) / 2;
    }

    public static CubeCoord cubeCoordRound(float[] points) {
        float x = Math.round(points[0]);
        float y = Math.round(points[1]);
        float z = Math.round(points[2]);

        float dx = Math.abs(x - points[0]);
        float dy = Math.abs(y - points[1]);
        float dz = Math.abs(z - points[2]);

        if(dx > dy && dx > dz)
            x = -y - z;
        else if(dy > dz)
            y = -x - z;
        else
            z = -x - y;

        return new CubeCoord((int)x, (int)y, (int)z);
    }

    public static float[] cubeLerp(float[] a, float[] b, float delta) {
        return new float[]{Interpolation.linear.apply(a[0], b[0], delta),
                Interpolation.linear.apply(a[1], b[1], delta),
                Interpolation.linear.apply(a[2], b[2], delta)};
    }

    public static Hexagon[] cubeDrawLine(Hexagon a, Hexagon b) {
        int distance = calculateDistance(a, b);
        List<Hexagon> path = new ArrayList<Hexagon>();
        for(int i = 0; i <= distance; i++) {
            path.add(new Hexagon(cubeCoordRound(cubeLerp(a.getOffsetCoord().getCoordsF(), b.getOffsetCoord().getCoordsF(), 1.0f/distance * i))));
        }
        return path.toArray(new Hexagon[path.size()]);
    }

    //TODO Document
    /**
     * Converts EvenR to points
     * Size is x [-1/2 * size, numRows + 1 * size]
     *         y [-1/2 * size, numColumns * size]
     * @param o The Offset Coordinate
     * @param size Size Of The Grid
     * @return X and Y positions, respectively
     */
    public static Point evenRToPixelHexagonCenter(OffsetCoord o, float size, Point offset) {
        float x, y;

        //If row is odd, x = column + 1 (times size)
        //Else x = column + 1/2 (times size)
        if (o.getCoords()[1] % 2 == 0) {
            x = (o.getCoords()[0] + 1.0f) * size + (offset.x);
        }
        else {
            x = (o.getCoords()[0] + 0.5f) * size + (offset.x);
        }

        //0.75 is 3/4 height between each hexagon
        //0.5  is 1/2 height that inherently exists between the top of the first row and their centers
        y = (o.getCoords()[1] * 0.75f * size) + (0.5f * size) + (offset.y);

        return new Point(x, y);
    }

    @Deprecated
    public static float[] evenRToPixel(OffsetCoord o, float size) {
        float x, y;

        //If row is odd, x = column + 1 (times size)
        //Else x = column + 1/2 (times size)
        if (o.getCoords()[1] % 2 == 0) {
            x = o.getCoords()[0] + (1.0f * size);
        }
        else {
            x = o.getCoords()[0] + (0.5f * size);
        }

        //0.75 is 3/4 height between each hexagon
        //0.5  is 1/2 height that inherently exists between the top of the first row and their centers
        y = (o.getCoords()[1] * 0.75f * size) + (0.5f * size);

        return new float[]{x, y};
    }

    public static Point[] evenRToPixelHexagonVertices(OffsetCoord o, float size, Point offset) {
        float x, y;
        Point[] points = new Point[6];
        Point centerPoint = evenRToPixelHexagonCenter(o, size, offset);

        points[0] = new Point(centerPoint.x + (0.00f * size), centerPoint.y + (0.50f * size));
        points[1] = new Point(centerPoint.x + (0.50f * size), centerPoint.y + (0.25f * size));
        points[2] = new Point(centerPoint.x + (0.50f * size), centerPoint.y - (0.25f * size));
        points[3] = new Point(centerPoint.x + (0.00f * size), centerPoint.y - (0.50f * size));
        points[4] = new Point(centerPoint.x - (0.50f * size), centerPoint.y - (0.25f * size));
        points[5] = new Point(centerPoint.x - (0.50f * size), centerPoint.y + (0.25f * size));

        return points;
    }

    /*/**
     * Inefficient as hell, try to avoid use...
     *
     * @param px pixel clicked
     * @param size size of grid
     * @param offset offset of the grid
     * @return an <code>OffsetCoord</code> representing the hex that was clicked
     */
    /*public static OffsetCoord pixelToEvenR(Point px, float size, Point offset, HexGrid grid) {
        Hexagon smallestHex = grid.getHexagon(new OffsetCoord(0, 0));
        float distance = ExtendedMath.distance(HexHelper.evenRToPixelHexagonCenter(smallestHex.getOffsetCoord(), size, offset), px);

        float newDistance;
        OffsetCoord check;

        for(int col = 0; col < grid.getSize(); col++) {
            for(int row = 0; row < grid.getSize(); row++) {
                check = new OffsetCoord(col, row);
                newDistance = ExtendedMath.distance(HexHelper.evenRToPixelHexagonCenter(check, size, offset), px);
                if(newDistance < distance) {
                    distance = newDistance;
                    smallestHex = grid.getHexagon(check);
                }
                if(col == 2 && row == 1) {
                    System.out.println(newDistance);
                }
            }
        }

        System.out.println(distance);
        return smallestHex.getOffsetCoord();
    }*/

    public static CubeCoord cubeCoordAdd(CubeCoord c1, CubeCoord c2) {
        return new CubeCoord(c1.getCoords()[0] + c2.getCoords()[0],
                c1.getCoords()[1] + c2.getCoords()[1],
                c1.getCoords()[2] + c2.getCoords()[2]);
    }

    public static CubeCoord cubeCoordScale(CubeCoord c1, int scale) {
        return new CubeCoord(c1.getCoords()[0] * scale,
                c1.getCoords()[1] * scale,
                c1.getCoords()[2] * scale);
    }
}
