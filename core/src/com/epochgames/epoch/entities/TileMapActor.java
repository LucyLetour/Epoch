package com.epochgames.epoch.entities;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.epochgames.epoch.GameManager;
import com.epochgames.epoch.util.hexlib.Point;

public class TileMapActor extends Actor {
    public Point position;

    public TileMapActor(Point position) {
        this.position = position;
    }

    @Override
    public Actor hit(float x, float y, boolean touchable) {
        float q2x = Math.abs(x - position.x);
        float q2y = Math.abs(y - position.y);
        float vertical = GameManager.TILE_HEIGHT / 4;
        float horizontal = GameManager.TILE_WIDTH / 2;
        if(q2x > horizontal || q2y > vertical * 2) {
            return null;
        }
        else if((2 * vertical * horizontal) - (vertical * q2x) - (horizontal * q2y) >= 0) {
            return this;
        }
        return null;
    }
}
