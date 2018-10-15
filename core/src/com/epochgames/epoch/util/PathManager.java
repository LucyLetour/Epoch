package com.epochgames.epoch.util;

import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.epochgames.epoch.util.hexlib.HexSatelliteData;
import org.codetome.hexameter.core.api.Hexagon;

import java.util.List;

public class PathManager {

    public static final int PATH_FIDELITY = 20;
    public static final float PATH_AVERAGE_DISTANCE = 0.01f;

    public List<Hexagon<HexSatelliteData>> hexagonPath;

    public CatmullRomSpline<Vector2> catmullRomSpline;

    public Vector2[] points;
    public Vector2[] controlPoints;
    public Vector2 output;
    public Vector2 temp;

    //public ShapeRenderer renderer;

    public PathManager(List<Hexagon<HexSatelliteData>> hexagonPath) {
        this.hexagonPath = hexagonPath;
        seedPoints();
        output = new Vector2();
        temp = new Vector2();
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
    public Transform getSplineAtPoint(float alpha) {
        float pointInterval = 1.0f / points.length;
        int subPoint = (int)Math.floor(alpha / pointInterval);
        float subAlpha = (alpha % pointInterval) / pointInterval;
        boolean isEasyPath = false;
        Vector2 startPoint;
        Vector2 endPoint;

        if(alpha >= 1.0f) {
            return new Transform(points[points.length - 1], 0.0f);
        }
        /*
        if(subPoint == 0) {
            startPoint = points[0];
            isEasyPath = true;
        }
        else {
            startPoint = EpochMath.averageVector(points[subPoint - 1], points[subPoint]);
        }

        if(subPoint == points.length - 1) {
            endPoint = points[subPoint];
            isEasyPath = true;
        }
        else {
            endPoint = EpochMath.averageVector(points[subPoint], points[subPoint + 1]);
        }

        System.out.println("" + startPoint + '\n' + endPoint);
        */
        //if(!isEasyPath) {
            output = catmullRomSpline.valueAt(output, alpha);
        //}
        /*else {
            output.x = Interpolation.linear.apply(startPoint.x, endPoint.x, subAlpha);
            output.y = Interpolation.linear.apply(startPoint.y, endPoint.y, subAlpha);
        }*/

        return new Transform(output, 0.0f);
    }
}
