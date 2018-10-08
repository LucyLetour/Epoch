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
}
