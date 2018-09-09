package com.epochgames.epoch.maps;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.epochgames.epoch.util.Assets;

public class OpenSpaceMap extends EpochTiledMap {

    public OpenSpaceMap() {
        super(Assets.MANAGER.get(Assets.TileMaps.OPEN_SPACE));
    }

    @Override
    public void render(OrthographicCamera camera) {
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
    }
}
