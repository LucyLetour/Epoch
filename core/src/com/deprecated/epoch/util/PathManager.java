package com.epochgames.epoch.util;

import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.epochgames.epoch.entities.components.TransformComponent;
import com.epochgames.epoch.util.Pathfinding.Node;
import com.epochgames.epoch.util.hexlib.HexSatelliteData;
import com.epochgames.epoch.util.hexlib.HexagonGridUtil;
import org.hexworks.mixite.core.api.Hexagon;

import java.util.ArrayList;

/**
 * The {@code PathManager} class creates a {@link CatmullRomSpline} that
 * represents the hexagon path given to it
 */
public class PathManager {

    private static ArrayList<Hexagon<HexSatelliteData>> hexagonPath;

    public static CatmullRomSpline<Vector2> catmullRomSpline;

    public static Vector2[] points;
    public static Vector2 output;
    private static float rotation;

    private static boolean initialized;

    public static void init(ArrayList<Node> nodePath) {
        hexagonPath = HexagonGridUtil.nodesToHexGrid(nodePath);
        seedPoints();
        output = new Vector2();
        rotation = 0.0f;
        initialized = true;
    }

    /**
     * Seeds the points for the path based on the hexagon path given
     */
    private static void seedPoints() {
        points = new Vector2[hexagonPath.size() + 2];

        points[0] = new Vector2((float)hexagonPath.get(0).getCenterX(), (float)hexagonPath.get(0).getCenterY());
        for (int i = 0; i < points.length - 2; i++) {
            points[i + 1] = new Vector2((float)hexagonPath.get(i).getCenterX(), (float)hexagonPath.get(i).getCenterY());
            //System.out.println(hexagonPath.get(i).getCubeCoordinate().toAxialKey());
        }
        points[points.length - 1] = new Vector2((float)hexagonPath.get(hexagonPath.size() - 1).getCenterX(), (float)hexagonPath.get(hexagonPath.size() - 1).getCenterY());
        catmullRomSpline = new CatmullRomSpline<>(points, false);
    }

    /**
     * Returns the Spline at a certain point along it, where the alpha is from 0 to 1
     * @param alpha the point from 0 to 1 that represents the percentage along the line to go.
     *              The value is clamped from 0 to 1, meaning no values above 1 or below 0 are
     *              ever used
     * @return a {@link Transform} object
     */
    public static Transform getSplineAtPoint(float alpha, TransformComponent transformComponent) {
        alpha = (float)EpochMath.clamp(alpha, 0.0, 1.0);

        if(alpha == 1.0f) {
            return new Transform(points[points.length - 1], transformComponent.rotation);
        }

        rotation = catmullRomSpline.derivativeAt(output, alpha).angle();
        output = catmullRomSpline.valueAt(output, alpha);

        return new Transform(output, rotation);
    }

    public static boolean isInitialized() {
        return initialized;
    }
}
