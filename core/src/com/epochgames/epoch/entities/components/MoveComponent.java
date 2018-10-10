package com.epochgames.epoch.entities.components;

import com.badlogic.ashley.core.Component;
import org.codetome.hexameter.core.api.CubeCoordinate;

public class MoveComponent implements Component {
    public static final float TOTAL_MOVE_TIME = 1.0f;

    public CubeCoordinate currentPosition = CubeCoordinate.fromCoordinates(0, 0);
    public CubeCoordinate nextPosition = null;
    public boolean isMoving = false;
    public boolean shouldMove = false;
    public float timeMoving = 0.0f;
}
