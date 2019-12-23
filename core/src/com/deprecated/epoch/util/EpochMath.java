package com.epochgames.epoch.util;

import com.badlogic.gdx.math.Vector2;
import com.epochgames.epoch.util.hexlib.Point;

public class EpochMath {
    private EpochMath() {}

    /**
     * Clamps a value between a min and max
     * @param val The value to clamp
     * @param min The minimum value
     * @param max The maximum value
     * @return The clamped value
     */
    public static double clamp(double val, double min, double max) {
        return val <= max ? val > min ? val : min : max;
    }

    public static double distance(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p2.x - p1.x, 2.0f) + Math.pow(p2.y - p1.y, 2.0f));
    }

    public static double distance(Vector2 p1, Vector2 p2) {
        return distance(new Point(p1.x, p1.y), new Point(p2.x, p2.y));
    }

    public static Vector2 averageVector(Vector2... vectors) {
        int xSum = 0;
        int ySum = 0;
        for (Vector2 v : vectors) {
            xSum += v.x;
            ySum += v.y;
        }
        return new Vector2(xSum / vectors.length, ySum / vectors.length);
    }

    public static float Maximum(float... nums) {
        float biggest = nums[0];
        for (float f : nums) {
            if(f > biggest) {
                biggest = f;
            }
        }
        return biggest;
    }
}
