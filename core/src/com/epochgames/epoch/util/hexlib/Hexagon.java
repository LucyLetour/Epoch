package com.epochgames.epoch.util.hexlib;

public class Hexagon {
    private OffsetCoord offsetCoord;

    public Hexagon(CubeCoord c) {
        offsetCoord = HexHelper.cubeToEvenR(c);
    }

    public Hexagon(OffsetCoord o) {
        offsetCoord = o;
    }

    public OffsetCoord getOffsetCoord() {
        return offsetCoord;
    }

    @Override
    public String toString() {
        return "Hexagon at " + offsetCoord.toString();
    }
}
