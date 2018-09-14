package com.epochgames.epoch.util.hexlib;

import com.epochgames.epoch.GameManager;

public class Hexagon {
    public OffsetCoord offsetCoord;

    public Hexagon(CubeCoord c) {
        offsetCoord = HexHelper.cubeToEvenR(c);
    }

    public Hexagon(OffsetCoord o) {
        offsetCoord = o;
    }

    public Hexagon(Point p) {
        this(new OffsetCoord(p));
    }

    /**
     * Get the screen coordinates of the hex
     * @return The screen coordinates of the center of the hex
     */
    public Point getHexCenter() {
        int xPos = offsetCoord.x;
        int yPos = offsetCoord.y;
        float centerX, centerY;
        //Using height, width = (height / 2) * sqrt(3)
        if(yPos % 2 == 1) {
            centerX = ((xPos * 1.0f) + 0.5f) * GameManager.getInstance().TILE_WIDTH;
        }
        else {
            centerX = ((xPos * 1.0f) + 1.0f) * GameManager.getInstance().TILE_WIDTH;
        }

        centerY = ((yPos * 0.75f) + 0.5f) * GameManager.getInstance().TILE_HEIGHT;

        return new Point(centerX, centerY);
    }

    @Override
    public String toString() {
        return "Hexagon at " + offsetCoord.toString();
    }
}
