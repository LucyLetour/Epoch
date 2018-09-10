package com.epochgames.epoch.util.hexlib;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;

public class HexGrid {
    private Array<Array<Hexagon>> grid;
    public int width, height;

    public HexGrid(TiledMap tiledMap) {
        MapProperties properties = tiledMap.getProperties();
        generateGrid(properties.get("height", Integer.class), properties.get("width", Integer.class));
    }

    public void generateGrid(int height, int width) {
        this.height = height;
        this.width = width;

        grid = new Array(width);
        Array<Hexagon> column = new Array<>(height);

        for (int row = 0; row < width; row++) {
            for (int col = 0; col < height; col++) {
                column.add(new Hexagon(new OffsetCoord(row, col)));
            }
            grid.add(column);
            column.clear();
        }

        Gdx.app.debug("Created InGame Hexgrid", "Grid has length of " + this.width + " and a height of " + this.height);
    }

    public Hexagon getHexagon(OffsetCoord offsetCoord) {
        for (Array<Hexagon> column : grid){
            for (Hexagon hexagon : column) {
                if(hexagon.getOffsetCoord().equals(offsetCoord)) {
                    return hexagon;
                }
            }
        }
        Gdx.app.error("Invalid Offset Coordinate", "Looked for a hexagon that doesn't exist and didn't find it");
        return null;
    }
}
