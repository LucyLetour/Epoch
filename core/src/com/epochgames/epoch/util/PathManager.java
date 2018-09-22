package com.epochgames.epoch.util;

import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.epochgames.epoch.util.hexlib.Hexagon;

public class PathManager {

    public Hexagon[] hexagonPath;

    public Vector2[] points;
    public Vector2 output = new Vector2();

    public PathManager(Hexagon... hexagonPath) {
        this.hexagonPath = hexagonPath;
    }

    public void seedPoints() {
        points = new Vector2[hexagonPath.length];

        for (int i = 0; i < points.length; i++) {
            points[i] = hexagonPath[i].getHexCenter().toVector2();
        }
    }

    public Vector2 getSplineAtPoint(float alpha) {
        Vector2 temp = new Vector2();
        CatmullRomSpline.calculate(output, alpha, points, false, temp);
        return output;
    }
}
