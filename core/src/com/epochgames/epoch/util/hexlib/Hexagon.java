package com.epochgames.epoch.util.hexlib;

import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.math.Polygon;
import com.epochgames.epoch.GameManager;

public class Hexagon {
    public OffsetCoord offsetCoord;
    public HexGrid hexGrid;

    public Hexagon(CubeCoord c, HexGrid h) {
        offsetCoord = HexHelper.cubeToEvenR(c);
        hexGrid = h;
    }

    public Hexagon(OffsetCoord o, HexGrid h) {
        offsetCoord = o;
        hexGrid = h;
    }

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
