package com.epochgames.epoch.util.hexlib;

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

    public static OffsetCoord cubeToEvenR(CubeCoord c) {
        int column = c.x + (c.z + (c.z & 1)) / 2;
        int row = c.z;
        return new OffsetCoord(column, row);
    }

    public static CubeCoord evenRToCube(OffsetCoord o) {
        int x = o.x - (o.y + (o.x & 1)) / 2;
        int z = o.y;
        int y = -x - z;
        return new CubeCoord(x, y, z);
    }

    public static int calculateDistance(Hexagon a, Hexagon b) {
        CubeCoord ac = evenRToCube(a.offsetCoord);
        CubeCoord bc = evenRToCube(b.offsetCoord);
        return (Math.abs(ac.x - bc.x) +
                Math.abs(ac.y - bc.y) +
                Math.abs(ac.z - bc.z)) / 2;
    }

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
}
