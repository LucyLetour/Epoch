package com.epochgames.epoch.entities;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TileMapActor extends Actor {
    public TiledMap tiledMap;
    public TiledMapTileLayer tiledLayer;
    public TiledMapTileLayer.Cell cell;

    public TileMapActor(TiledMap tiledMap, TiledMapTileLayer tiledLayer, TiledMapTileLayer.Cell cell) {
        this.tiledMap = tiledMap;
        this.tiledLayer = tiledLayer;
        this.cell = cell;
    }
}
