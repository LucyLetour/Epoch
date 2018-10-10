package com.epochgames.epoch.util;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.epochgames.epoch.util.hexlib.HexSatelliteData;
import org.codetome.hexameter.core.api.Hexagon;

import java.util.List;

public class PathManager {

    public List<Hexagon<HexSatelliteData>> hexagonPath;

    public Vector2[] points;
    public Vector2 output = new Vector2();

    public ShapeRenderer renderer;

    public PathManager(List<Hexagon<HexSatelliteData>> hexagonPath) {
        this.hexagonPath = hexagonPath;
        seedPoints();
        renderer = new ShapeRenderer();
    }

    public void seedPoints() {
        points = new Vector2[hexagonPath.size()];

        for (int i = 0; i < points.length; i++) {
            points[i] = new Vector2((float)hexagonPath.get(i).getCenterX(), (float)hexagonPath.get(i).getCenterY());
            System.out.println(hexagonPath.get(i).getCubeCoordinate().toAxialKey());
        }
    }

    public Vector2 getSplineAtPoint(float alpha) {
        Vector2 temp = new Vector2();
        CatmullRomSpline.calculate(output, alpha, points, false, temp);
        return output;
    }
}
