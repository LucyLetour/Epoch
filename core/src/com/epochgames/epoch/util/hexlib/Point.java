package com.epochgames.epoch.util.hexlib;

public class Point {
    public float x, y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Point(int x, int y) {
        this.x = (float) x;
        this.y = (float) y;
    }

    public Point(double x, double y) {
        this.x = (float) x;
        this.y = (float) y;
    }

    /**
     * Moves the point a specified x and y
     * @param dx change in the x
     * @param dy change in the y
     * @return the point
     * **Note** The point is changed, the return is simply for convenience
     */
    public Point translate(float dx, float dy) {
        this.x += dx;
        this.y += dy;
        return this;
    }

    @Override
    public String toString() {
        return x + " " + y;
    }
}
