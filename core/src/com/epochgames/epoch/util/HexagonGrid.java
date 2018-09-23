package com.epochgames.epoch.util;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.epochgames.epoch.GameManager;
import com.epochgames.epoch.util.hexlib.HexSatelliteData;
import org.codetome.hexameter.core.api.*;

import static org.codetome.hexameter.core.api.HexagonalGridLayout.RECTANGULAR;
import static org.codetome.hexameter.core.api.HexagonOrientation.POINTY_TOP;

public class HexagonGrid {
    public static int GRID_HEIGHT = 90;
    public static int GRID_WIDTH = 90;
    public static HexagonalGridLayout GRID_LAYOUT = RECTANGULAR;
    public static HexagonOrientation ORIENTATION = POINTY_TOP;
    public static double RADIUS = 30;


    public HexagonalGrid<HexSatelliteData> hexGrid;
    public HexagonalGridCalculator hexCalculator;

    public TiledMap tiledMap;

    public HexagonGrid(TiledMap tiledMap) {
        HexagonalGridBuilder builder = new HexagonalGridBuilder()
                .setGridHeight(GRID_HEIGHT)
                .setGridWidth(GRID_WIDTH)
                .setGridLayout(GRID_LAYOUT)
                .setOrientation(ORIENTATION)
                .setRadius(RADIUS);
        hexGrid = builder.build();
        hexCalculator = builder.buildCalculatorFor(hexGrid);
        this.tiledMap = tiledMap;

        RADIUS = GameManager.TILE_WIDTH * (2.0f / 3.0f);
    }
}
