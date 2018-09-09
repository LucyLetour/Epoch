package com.epochgames.epoch.util.hexlib;

public class OffsetCoord {
    int[] coords;

    public OffsetCoord(int col, int row) {
        coords = new int[2];
        coords[0] = col;
        coords[1] = row;
    }

    public OffsetCoord(int[] coords) {
        coords = new int[2];
        this.coords = coords;
    }

    public int[] getCoords() {
        return coords;
    }

    public float[] getCoordsF() {
        return new float[]{(float)coords[0], (float)coords[1]};
    }

    @Override
    public String toString() {
        return (coords[0] + " " + coords[1]);
    }
}
