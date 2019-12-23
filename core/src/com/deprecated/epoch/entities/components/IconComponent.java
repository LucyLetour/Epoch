package com.epochgames.epoch.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Holds the icon and alpha value of an {@link Entity}
 */
public class IconComponent implements Component {
    public TextureRegion region = null;
    public float alpha = 0.0f;
}
