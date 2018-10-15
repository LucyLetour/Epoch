package com.epochgames.epoch.util;

import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.epochgames.epoch.entities.components.TransformComponent;
import com.epochgames.epoch.util.hexlib.HexSatelliteData;
import org.codetome.hexameter.core.api.Hexagon;

import java.util.List;

public class PathManager {

    public static final int PATH_FIDELITY = 20;
    public static final float PATH_AVERAGE_DISTANCE = 0.01f;

    public List<Hexagon> hexagonPath;

    public CatmullRomSpline<Vector2> catmullRomSpline;

    public Vector2[] points;
    public Vector2 output;
    public float rotation;

    public PathManager(List<Hexagon> hexagonPath) {
        this.hexagonPath = hexagonPath;
        seedPoints();
        output = new Vector2();
    }

    public void seedPoints() {
        points = new Vector2[hexagonPath.size() + 2];

        points[0] = new Vector2((float)hexagonPath.get(0).getCenterX(), (float)hexagonPath.get(0).getCenterY());
        for (int i = 0; i < points.length - 2; i++) {
            points[i + 1] = new Vector2((float)hexagonPath.get(i).getCenterX(), (float)hexagonPath.get(i).getCenterY());
            System.out.println(hexagonPath.get(i).getCubeCoordinate().toAxialKey());
        }
        points[points.length - 1] = new Vector2((float)hexagonPath.get(hexagonPath.size() - 1).getCenterX(), (float)hexagonPath.get(hexagonPath.size() - 1).getCenterY());
        catmullRomSpline = new CatmullRomSpline<Vector2>(points, false);
    }

    /**
     *
     * @param alpha
     * @return
     */
    public Transform getSplineAtPoint(float alpha, TransformComponent transformComponent) {
        if(alpha >= 1.0f) {
            return new Transform(points[points.length - 1], transformComponent.rotation);
        }

        rotation = catmullRomSpline.derivativeAt(output, alpha).angle();
        output = catmullRomSpline.valueAt(output, alpha);

        return new Transform(output, rotation);
    }

    public Vector2 derivativeAt(float alpha) {
        return catmullRomSpline.derivativeAt(output, alpha);
    }
}
