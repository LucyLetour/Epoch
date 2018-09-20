package com.epochgames.epoch.util.hexlib;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;

public class HexGrid {
    private Hexagon[][] grid;
    public int width, height;
    public TiledMap tiledMap;

    public HexGrid(TiledMap tiledMap) {
        MapProperties properties = tiledMap.getProperties();
        this.tiledMap = tiledMap;
        generateGrid(properties.get("height", Integer.class), properties.get("width", Integer.class));
    }

    public void generateGrid(int height, int width) {
        this.height = height;
        this.width = width;

        grid = new Hexagon[width][height];

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                grid[row][col] = new Hexagon(new OffsetCoord(row, col));
            }
        }

        Gdx.app.debug("Created InGame Hexgrid", "Grid has length of " + grid.length + " and a height of " + grid[0].length);
    }

    public Hexagon getHexagon(OffsetCoord offsetCoord) {
        Hexagon ret = grid[offsetCoord.x][offsetCoord.y];
        if(ret == null) {
            Gdx.app.error("Invalid Offset Coordinate", "Looked for a hexagon that doesn't exist and didn't find it");
            return null;
        }
       return ret;
    }
}
