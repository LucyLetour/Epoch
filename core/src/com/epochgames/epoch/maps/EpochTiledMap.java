package com.epochgames.epoch.maps;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.HexagonalTiledMapRenderer;

public abstract class EpochTiledMap {
    protected TiledMap tiledMap;
    protected TiledMapRenderer tiledMapRenderer;

    //In units of Tiles
    protected float worldHeight, worldWidth;

    public EpochTiledMap(TiledMap tiledMap) {
        this.tiledMap = tiledMap;
        //TODO fix all this
        tiledMapRenderer = new HexagonalTiledMapRenderer(tiledMap);
    }

    public abstract void render(OrthographicCamera camera);

    public float getWorldHeight() {
        return worldHeight;
    }

    public float getWorldWidth() {
        return worldWidth;
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public TiledMapRenderer getTiledMapRenderer() {
        return tiledMapRenderer;
    }
}
