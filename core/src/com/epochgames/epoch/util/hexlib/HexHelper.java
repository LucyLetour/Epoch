package com.epochgames.epoch.util.hexlib;

import com.epochgames.epoch.GameManager;
import com.epochgames.epoch.entities.components.Mappers;
import com.epochgames.epoch.util.EpochMath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HexHelper {

    public enum HEX_DIRECTION {
        UP_RIGHT,
        UP_LEFT,
        RIGHT,
        LEFT,
        BOTTOM_RIGHT,
        BOTTOM_LEFT
    }

    public static Map<HEX_DIRECTION, int[]> CUBE_DIRECTION;
    static
    {
        CUBE_DIRECTION = new HashMap<>();
        CUBE_DIRECTION.put(HEX_DIRECTION.UP_RIGHT, new int[]{+1, 0, -1});
        CUBE_DIRECTION.put(HEX_DIRECTION.UP_LEFT, new int[]{0, +1, -1});
        CUBE_DIRECTION.put(HEX_DIRECTION.RIGHT, new int[]{+1, -1, 0});
        CUBE_DIRECTION.put(HEX_DIRECTION.LEFT, new int[]{-1, +1, 0});
        CUBE_DIRECTION.put(HEX_DIRECTION.BOTTOM_RIGHT, new int[]{0, -1, +1});
        CUBE_DIRECTION.put(HEX_DIRECTION.BOTTOM_LEFT, new int[]{-1, 0, +1});

    }

    public static OffsetCoord cubeToOddR(CubeCoord c) {
        int column = c.x + (c.z - (c.z & 1)) / 2;
        int row = c.z;
        return new OffsetCoord(column, row);
    }

    public static CubeCoord oddRToCube(OffsetCoord o) {
        int x = o.x - (o.y - (o.y & 1)) / 2;
        int z = o.y;
        int y = -x - z;
        return new CubeCoord(x, y, z);
    }

    public static int calculateDistance(Hexagon a, Hexagon b) {
        CubeCoord ac = oddRToCube(a.offsetCoord);
        CubeCoord bc = oddRToCube(b.offsetCoord);
        return Math.max(Math.max(Math.abs(ac.x - bc.x), Math.abs(ac.y - bc.y)), Math.abs(ac.z - bc.z));
        /*int penalty = ((a.offsetCoord.y % 2 == 0 && b.offsetCoord.y % 2 == 1 && (a.offsetCoord.x < b.offsetCoord.x)) ||
                (b.offsetCoord.y % 2 == 0 && a.offsetCoord.y % 2 == 1 && (b.offsetCoord.x < a.offsetCoord.x))) ? 1 : 0;
        return Math.max(Math.abs(b.offsetCoord.y - a.offsetCoord.y), Math.abs(b.offsetCoord.x - a.offsetCoord.x) + (int)Math.floor(Math.abs(b.offsetCoord.y - a.offsetCoord.y) / 2.0f) + penalty);
    */}

    public static CubeCoord cubeCoordRound(float px, float py, float pz) {
        float x = Math.round(px);
        float y = Math.round(py);
        float z = Math.round(pz);

        float dx = Math.abs(x - px);
        float dy = Math.abs(y - py);
        float dz = Math.abs(z - pz);

        if(dx > dy && dx > dz)
            x = -y - z;
        else if(dy > dz)
            y = -x - z;
        else
            z = -x - y;

        return new CubeCoord((int)x, (int)y, (int)z);
    }

    public static CubeCoord cubeCoordRound(CubeCoord cubeCoord) {
        return cubeCoordRound(cubeCoord.x, cubeCoord.y, cubeCoord.z);
    }

    /**
     * Returns grid coordinates from the center of a hex
     * @param hexCenter
     * @return The coordinates of the corresponding hex on the grid
     */
    public static Point getCoordinatesFromHexCenter(Point hexCenter) {
        float xCoord;
        float yCoord;
        float centerX = hexCenter.x;
        float centerY = hexCenter.y;

        xCoord = (((centerY / GameManager.TILE_HEIGHT) - 0.5f) / 0.75f);

        if(xCoord % 2 == 1) {
            yCoord = (((centerX / GameManager.TILE_WIDTH) - 0.5f) / 1.0f);
        }
        else {
            yCoord = (((centerX / GameManager.TILE_WIDTH) - 1.0f) / 1.0f);
        }

        return new Point((int)xCoord, (int)yCoord);
    }

    public static float hexagonLerp(float start, float end, float alpha) {
        return start + ((end - start) * alpha);
    }

    public static FloatCubeCoord cubeCoordinateLerp(CubeCoord start, CubeCoord end, float alpha) {
        return new FloatCubeCoord(hexagonLerp(start.x, end.x, alpha),
                             hexagonLerp(start.y, end.y, alpha),
                             hexagonLerp(start.z, end.z, alpha));
    }

    public static Hexagon[] hexagonLineDraw(OffsetCoord start, OffsetCoord end) {
        /*start.x += 0.000001;
        end.x   += 0.000001;
        start.y += 0.000002;
        end.x   += 0.000002;
        start.z -= 0.000003;
        end.z   -= 0.000003;

        int distance = calculateDistance(new Hexagon(start), new Hexagon(end));
        Hexagon[] hexLine = new Hexagon[distance];

        System.out.println(distance);

        for (int i = 0; i < distance; i++) {
            hexLine[i] = new Hexagon(cubeCoordRound(cubeCoordinateLerp(start, end, 1.0f / distance * i)));
            System.out.println(hexLine[i].offsetCoord);
        }*/
        OffsetCoord current = new OffsetCoord(start);
        ArrayList<Hexagon> hexLine = new ArrayList<>();

        int dx, dy;

        hexLine.add(new Hexagon(new OffsetCoord(current.x, current.y)));
        while(!current.equals(end)) {
            int changeX = end.x - current.x;
            dy = (int)EpochMath.clamp(end.y - current.y, -1, 1);
            dx = (Math.signum(changeX) > 0 && current.y % 2 == 0) || (Math.signum(changeX) < 0 && current.y % 2 == 1) || dy == 0
                    ? (int)EpochMath.clamp(changeX, -1.0f, 1.0f) : 0;
            current.x += dx;
            current.y += dy;
            hexLine.add(new Hexagon(new OffsetCoord(current)));
            //System.out.println("Added " + current + " to path");
            if(hexLine.size() > 20) {
                break;
            }
        }

        System.out.println("Ended at " + end);
        Hexagon[] h = new Hexagon[hexLine.size()];
        System.out.println("Hex path is " + hexLine.size() + " long");
        System.out.println("The distance is " + calculateDistance(new Hexagon(start), new Hexagon(end)));
        System.out.println(start + ", " + end);
        System.out.println(oddRToCube(start) + ", " +  oddRToCube(end));
        return hexLine.toArray(h);
    }

    /*public static Hexagon[] hexagonLineDraw(OffsetCoord start, OffsetCoord end) {
        float xDelta = (new Hexagon(end).offsetCoord.x - new Hexagon(start).offsetCoord.x) * 2;
        float yDelta = (new Hexagon(end).offsetCoord.y - new Hexagon(start).offsetCoord.y);

        float xSign = Math.signum(xDelta);
        float ySign = Math.signum(yDelta);

        xDelta = Math.abs(xDelta);
        yDelta = Math.abs(yDelta);

        ArrayList<Hexagon> path = new ArrayList<>();
        Hexagon current = new Hexagon(start);
        float epsilon = -2 * xDelta;

        while(!current.offsetCoord.equals(end)) {
            if(epsilon >= 0) {
                current = new Hexagon(new OffsetCoord((int)(current.offsetCoord.x - xSign),
                        (int)(current.offsetCoord.x + ySign)));
                path.add(current);
                epsilon = epsilon - 3 * yDelta - 3 * xDelta;
            }
            else {
                epsilon = epsilon + 3 * yDelta;
                if(epsilon > -xDelta) {
                    current = new Hexagon(new OffsetCoord((int)(current.offsetCoord.x + xSign),
                            (int)(current.offsetCoord.x + ySign)));
                    path.add(current);
                    epsilon = epsilon - 3 * xDelta;
                }
                else {
                    if(epsilon < -3 * xDelta) {
                        current = new Hexagon(new OffsetCoord((int)(current.offsetCoord.x + xSign),
                                (int)(current.offsetCoord.x - ySign)));
                        path.add(current);
                        epsilon = epsilon + 3 * xDelta;
                    }
                    else {
                        current = new Hexagon(new OffsetCoord(0,
                                (int)(current.offsetCoord.x + xSign)));
                        path.add(current);
                        epsilon = epsilon + 3 * yDelta;
                    }
                }
            }

            if(path.size() >= 20) {
                current = new Hexagon(end);
            }
        }

        Hexagon[] h = new Hexagon[path.size()];
        return path.toArray(h);
    }*/
}
