package com.epochgames.epoch.entities.components;

/**
 * Determines the completeness of the action done by this entity
 * If full, the entity will show animations for moving/attacking/etc.
 * Else, the entity will do it instantaneously
 */
public class ActionCompletenessComponent {
    public static final int FULL = 0;
    public static final int NONE = 1;

    public int actionCompleteness = 0;
}
