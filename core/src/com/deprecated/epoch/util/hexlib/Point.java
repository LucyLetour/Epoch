package com.epochgames.epoch.util.hexlib;

import com.badlogic.gdx.math.Vector2;

public class Point {
    public float x, y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Moves the point a specified x and y
     * **Note** The point is changed, the return is simply for convenience
     * @param dx change in the x
     * @param dy change in the y
     * @return the point
     */
    public Point translate(float dx, float dy) {
        this.x += dx;
        this.y += dy;
        return this;
    }

    public Vector2 toVector2() {
        return new Vector2(this.x, this.y);
    }

    @Override
    public String toString() {
        return x + " " + y;
    }
}
