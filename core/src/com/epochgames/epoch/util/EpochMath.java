package com.epochgames.epoch.util;

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

    public static double lerp(double start, double end, double delta) {
        return (1.0 - delta) * start + delta * end;
    }
}
