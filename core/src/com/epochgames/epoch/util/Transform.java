package com.epochgames.epoch.util;

import com.badlogic.gdx.math.Vector2;

public class Transform {
    public Vector2 position;
    public float rotation;

    public Transform(Vector2 position, float rotation) {
        this.position = position;
        this.rotation = rotation;
    }
}
