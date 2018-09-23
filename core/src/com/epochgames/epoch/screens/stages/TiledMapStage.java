package com.epochgames.epoch.screens.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.epochgames.epoch.GameManager;
import com.epochgames.epoch.entities.TileMapActor;
import com.epochgames.epoch.screens.inputListeners.TiledMapClickListener;
import com.epochgames.epoch.util.hexlib.HexSatelliteData;
import com.epochgames.epoch.util.hexlib.Point;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;

public class TiledMapStage extends Stage {
    public TiledMap tiledMap;
    public HexagonalGrid<HexSatelliteData> hexGrid;

    public TiledMapStage(TiledMap tiledMap, HexagonalGrid hexGrid) {
        this.tiledMap = tiledMap;
        this.hexGrid = hexGrid;

        for(MapLayer layer : tiledMap.getLayers()) {
            TiledMapTileLayer tiledLayer = (TiledMapTileLayer)layer;
            createActorsForLayer(tiledLayer);
        }
    }

    private void createActorsForLayer(TiledMapTileLayer tiledLayer) {
        int i = 0;
        for(Hexagon<HexSatelliteData> hexagon : hexGrid.getHexagons()) {
            TiledMapTileLayer.Cell cell = tiledLayer.getCell(hexagon.getGridX(), hexagon.getGridY());
            Point centerCoords = new Point((int)hexagon.getCenterX(), (int)hexagon.getCenterY());
            TileMapActor actor = new TileMapActor(tiledMap, tiledLayer, cell, centerCoords);
            addActor(actor);
            EventListener eventListener = new TiledMapClickListener(actor, GameManager.getInstance().game);
            actor.addListener(eventListener);
            i++;
        }
        Gdx.app.debug("Created Hex Actors", "Made " + i + " actors in the HexGrid");
    }
}
