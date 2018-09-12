package com.epochgames.epoch.entities.components;

import com.badlogic.ashley.core.Component;
import com.epochgames.epoch.util.hexlib.Hexagon;
import com.epochgames.epoch.util.hexlib.OffsetCoord;

public class MoveComponent implements Component {
    public Hexagon currentPosition = new Hexagon(new OffsetCoord(0, 0));
    public Hexagon nextPosition = null;
    public float alpha = 0.0f;
}
