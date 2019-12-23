package com.epochgames.epoch.util;

import com.badlogic.gdx.math.Vector2;

/**
 * A class that holds a {@code Vector2} position (in pixels) and a rotation
 * (as a {@code float})
 */
public class Transform {
    public Vector2 position;
    public float rotation;

    public Transform(Vector2 position, float rotation) {
        this.position = position;
        this.rotation = rotation;
    }
}
