package com.epochgames.epoch.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class TransformComponent implements Component {
    public Vector2 position = new Vector2(0, 0);
    public float scale = 1.0f;
    public float rotation = 0.0f;
    public float nextRotation = 0.0f;
    public boolean isRotating = false;
    public boolean shouldRotate = false;
    public float timeRotating = 0.0f;
}
