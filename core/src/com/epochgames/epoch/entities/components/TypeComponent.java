package com.epochgames.epoch.entities.components;

import com.badlogic.ashley.core.Component;

public class TypeComponent implements Component {
    public static final int PLAYER = 0;
    public static final int ALLY = 1;
    public static final int ENEMY = 2;
    public static final int NEUTRAL = 3;
    public static final int OTHER = 4;

    public int type = OTHER;
}
