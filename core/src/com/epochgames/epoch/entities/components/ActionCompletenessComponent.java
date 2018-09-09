package com.epochgames.epoch.entities.components;

import com.badlogic.ashley.core.Component;

/**
 * Determines the completeness of the action done by this entity
 * If full, the entity will show animations for moving/attacking/etc.
 * Else, the entity will do it instantaneously
 */
public class ActionCompletenessComponent implements Component {
    public static final int FULL = 0;
    public static final int NONE = 1;

    public int actionCompleteness = 0;
}
