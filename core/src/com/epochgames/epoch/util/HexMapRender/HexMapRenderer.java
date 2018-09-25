package com.epochgames.epoch.util.HexMapRender;

import com.badlogic.gdx.graphics.Texture;
import com.epochgames.epoch.Epoch;
import com.epochgames.epoch.util.hexlib.CubeCoord;
import com.epochgames.epoch.util.hexlib.HexHelper;
import com.epochgames.epoch.util.hexlib.HexSatelliteData;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonOrientation;
import org.codetome.hexameter.core.api.HexagonalGrid;

public class HexMapRenderer {

    public HexagonOrientation orientation;
    public Stagger stagger;
    public HexagonalGrid<HexSatelliteData> hexGrid;
    public Texture hexTexture;

    public static HexMapRenderer instance = new HexMapRenderer();

    public Epoch game;

    public enum Stagger {
        EVEN,
        ODD
    }

    private HexMapRenderer () {}

    /**
     * This is private, because as of now there is no way to change the stagger
     * of a Hexameter grid. The stagger can only be even,  so the {@code setHexGrid(HexagonGrid hexGrid)}
     * methods defaults to
     * @param hexGrid
     * @param stagger
     */
    private void setHexGrid(HexagonalGrid hexGrid, Stagger stagger, Epoch game, Texture hexTexture) {
        this.hexGrid = hexGrid;
        orientation = hexGrid.getGridData().getOrientation();
        this.stagger = stagger;
        this.game = game;
        this.hexTexture = hexTexture;
    }

    public void setHexGrid(HexagonalGrid hexGrid, Epoch game, Texture hexTexture) {
        setHexGrid(hexGrid, Stagger.EVEN, game, hexTexture);
    }

    public void renderHexGrid() {
        for (Hexagon<HexSatelliteData> hexagon : hexGrid.getHexagons()) {
            game.batch.draw(hexTexture, (float)hexagon.getCenterX() - (hexTexture.getWidth() / 2),
                    (float)hexagon.getCenterY() - (hexTexture.getHeight() / 2));
        }
    }
}
