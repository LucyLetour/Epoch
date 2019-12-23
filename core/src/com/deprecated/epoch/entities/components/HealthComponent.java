package com.epochgames.epoch.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

/**
 * Holds the health and armor values of an
 * {@link Entity}
 */
public class HealthComponent implements Component {
    public int health;
    public float armor;
}