package com.epochgames.epoch.util.HexMapRender;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.epochgames.epoch.Epoch;
import com.epochgames.epoch.GameManager;
import com.epochgames.epoch.util.HexagonGrid;
import com.epochgames.epoch.util.hexlib.HexSatelliteData;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonOrientation;

import java.util.Set;

public class HexMapRenderer {

    public HexagonOrientation orientation;
    public Stagger stagger;
    public HexagonGrid hexGrid;
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
    private void setHexGrid(HexagonGrid hexGrid, Stagger stagger, Epoch game, Texture hexTexture) {
        this.hexGrid = hexGrid;
        orientation = hexGrid.hexGrid.getGridData().getOrientation();
        this.stagger = stagger;
        this.game = game;
        this.hexTexture = hexTexture;
    }

    public void setHexGrid(HexagonGrid hexGrid, Epoch game, Texture hexTexture) {
        setHexGrid(hexGrid, Stagger.EVEN, game, hexTexture);
    }

    public void renderHexGrid() {
        Sprite hexagonSprite = new Sprite(hexTexture);
        Hexagon<HexSatelliteData> playerHexagon = hexGrid.hexGrid.getByCubeCoordinate(game.inGameScreen.playerPos).get();
        float alpha;
        for (Hexagon<HexSatelliteData> hexagon : (Set<Hexagon>)hexGrid.hexCalculator.calculateMovementRangeFrom(playerHexagon, GameManager.PLAYER_VIEW_RANGE)) {
            hexagonSprite.setPosition((float)hexagon.getCenterX() - (hexagonSprite.getWidth() / 2), (float)hexagon.getCenterY() - (hexagonSprite.getHeight() / 2));
            if(GameManager.getInstance().checkTileVisibilty(hexagon.getCubeCoordinate())) {
                alpha = 1.0f - ((float) game.inGameScreen.hexagonGrid.hexCalculator.calculateDistanceBetween(playerHexagon, hexagon) / (float) GameManager.PLAYER_VIEW_RANGE);
            }
            else {
                alpha = 0.0f;
            }

            if(hexagon.getSatelliteData().isPresent()) {
                hexagon.getSatelliteData().get().setVisibility(alpha);
            }
            else {
                hexagon.setSatelliteData(new HexSatelliteData(null, hexagon.getCubeCoordinate()));
                hexagon.getSatelliteData().get().setVisibility(alpha);
            }
            hexagonSprite.setAlpha(alpha);
            hexagonSprite.draw(game.batch);
        }
    }
}
