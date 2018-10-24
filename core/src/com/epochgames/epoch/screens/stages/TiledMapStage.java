package com.epochgames.epoch.screens.stages;

import com.badlogic.gdx.Gdx;
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
    public HexagonalGrid<HexSatelliteData> hexGrid;

    public TiledMapStage(HexagonalGrid hexGrid) {
        this.hexGrid = hexGrid;

        createActors();
    }

    private void createActors() {
        int i = 0;
        for(Hexagon<HexSatelliteData> hexagon : hexGrid.getHexagons()) {
            Point centerCoords = new Point((int)hexagon.getCenterX(), (int)hexagon.getCenterY());
            TileMapActor actor = new TileMapActor(centerCoords);
            addActor(actor);
            EventListener eventListener = new TiledMapClickListener(actor, GameManager.getInstance().game);
            actor.addListener(eventListener);
            i++;
        }
        Gdx.app.debug("Created Hex Actors", "Made " + i + " actors in the HexGrid");
    }
}
