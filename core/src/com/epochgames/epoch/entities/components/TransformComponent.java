package com.epochgames.epoch.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.epochgames.epoch.util.hexlib.Hexagon;
import com.epochgames.epoch.util.hexlib.OffsetCoord;

public class TransformComponent implements Component {
    public Vector2 position = new Vector2(0, 0);
    public float rotation = 0.0f;
    public float scale = 1.0f;
}
