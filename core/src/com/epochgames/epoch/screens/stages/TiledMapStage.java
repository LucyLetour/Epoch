package com.epochgames.epoch.screens.stages;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.epochgames.epoch.GameManager;
import com.epochgames.epoch.entities.TileMapActor;
import com.epochgames.epoch.screens.InputListeners.TiledMapClickListener;
import com.epochgames.epoch.util.hexlib.HexGrid;
import com.epochgames.epoch.util.hexlib.OffsetCoord;
import com.epochgames.epoch.util.hexlib.Point;

public class TiledMapStage extends Stage {
    public TiledMap tiledMap;
    public HexGrid hexGrid;

    public TiledMapStage(TiledMap tiledMap, HexGrid hexGrid) {
        this.tiledMap = tiledMap;
        this.hexGrid = hexGrid;

        for(MapLayer layer : tiledMap.getLayers()) {
            TiledMapTileLayer tiledLayer = (TiledMapTileLayer)layer;
            createActorsForLayer(tiledLayer);
        }
    }

    private void createActorsForLayer(TiledMapTileLayer tiledLayer) {
        for (int x = 0; x < tiledLayer.getWidth(); x++) {
            for (int y = 0; y < tiledLayer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = tiledLayer.getCell(x, y);
                Point center = hexGrid.getHexagon(new OffsetCoord(x, y)).getHexCenter();
                TileMapActor actor = new TileMapActor(tiledMap, tiledLayer, cell, center);
                addActor(actor);
                EventListener eventListener = new TiledMapClickListener(actor, GameManager.getInstance().game);
                actor.addListener(eventListener);
            }
        }
    }
}
