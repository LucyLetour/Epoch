package com.epochgames.epoch.util.hexlib;

public class CubeCoord {
    int[] coords;

    public CubeCoord(int x, int y, int z) {
        coords = new int[3];
        coords[0] = x;
        coords[1] = y;
        coords[2] = z;
    }

    public CubeCoord(int[] coords) {
        coords = new int[3];
        this.coords = coords;
    }

    public int[] getCoords() {
        return coords;
    }
}
