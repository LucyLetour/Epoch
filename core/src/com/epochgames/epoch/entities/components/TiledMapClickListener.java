package com.epochgames.epoch.entities.components;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.epochgames.epoch.entities.TileMapActor;

public class TiledMapClickListener extends ClickListener {
    private TileMapActor actor;

    public TiledMapClickListener(TileMapActor actor) {
        this.actor = actor;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        System.out.println(actor.cell + "has been clicked");
    }
}
