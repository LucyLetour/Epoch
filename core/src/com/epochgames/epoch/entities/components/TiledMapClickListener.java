package com.epochgames.epoch.entities.components;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.epochgames.epoch.entities.TileMapActor;
import com.epochgames.epoch.util.hexlib.HexHelper;

public class TiledMapClickListener extends ClickListener {
    private TileMapActor actor;

    public TiledMapClickListener(TileMapActor actor) {
        this.actor = actor;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        System.out.println(HexHelper.getCoordinatesFromHexCenter(actor.position) + "has been clicked");
    }
}
