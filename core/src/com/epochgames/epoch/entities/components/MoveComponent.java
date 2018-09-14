package com.epochgames.epoch.entities.components;

import com.badlogic.ashley.core.Component;
import com.epochgames.epoch.util.hexlib.Hexagon;
import com.epochgames.epoch.util.hexlib.OffsetCoord;

public class MoveComponent implements Component {
    public static final float TOTAL_MOVE_TIME = 1.0f;

    public Hexagon currentPosition = new Hexagon(new OffsetCoord(0, 0));
    public Hexagon nextPosition = null;
    public boolean isMoving = false;
    public boolean shouldMove = false;
    public float timeMoving = 0.0f;
}
