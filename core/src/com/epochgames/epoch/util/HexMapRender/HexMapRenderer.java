package com.epochgames.epoch.util.HexMapRender;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.epochgames.epoch.util.hexlib.HexSatelliteData;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonOrientation;
import org.codetome.hexameter.core.api.HexagonalGrid;
import org.codetome.hexameter.core.api.Point;

import java.util.ArrayList;
import java.util.List;

public class HexMapRenderer {
    public static final int LINE_WIDTH = 5;

    public int mapHeight;
    public int mapWidth;
    public HexagonOrientation orientation;
    public Stagger stagger;
    public HexagonalGrid<HexSatelliteData> hexGrid;

    public static HexMapRenderer instance = new HexMapRenderer();

    public ShapeRenderer renderer;
    public Camera camera;

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
    private void setHexGrid(HexagonalGrid hexGrid, Stagger stagger, Camera camera) {
        this.hexGrid = hexGrid;
        mapHeight = hexGrid.getGridData().getGridHeight();
        mapWidth = hexGrid.getGridData().getGridWidth();
        orientation = hexGrid.getGridData().getOrientation();
        this.stagger = stagger;
        this.renderer = new ShapeRenderer();
        this.camera = camera;
    }

    public void setHexGrid(HexagonalGrid hexGrid, Camera camera) {
        setHexGrid(hexGrid, Stagger.EVEN, camera);
    }

    public void renderHexGrid() {
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);

        for (Hexagon<HexSatelliteData> hexagon :
                hexGrid.getHexagons()) {
            List<Vector2> points = new ArrayList<>();
            for(Point point : hexagon.getPoints()) {
                points.add(toVector2(point));
            }

            renderer.rectLine(points.get(0), points.get(1), LINE_WIDTH);
            renderer.rectLine(points.get(1), points.get(2), LINE_WIDTH);
            renderer.rectLine(points.get(2), points.get(3), LINE_WIDTH);
            renderer.rectLine(points.get(3), points.get(4), LINE_WIDTH);
            renderer.rectLine(points.get(4), points.get(5), LINE_WIDTH);
            renderer.rectLine(points.get(5), points.get(0), LINE_WIDTH);
        }

        renderer.end();
    }

    private Vector2 toVector2(Point point) {
        return new Vector2(Math.round(point.getCoordinateX()), Math.round(point.getCoordinateY()));
    }
}
