package com.epochgames.epoch.util;

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
}
