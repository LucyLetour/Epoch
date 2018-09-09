package com.epochgames.epoch.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class InteractableComponent implements Component {
    public boolean interactable = false;
    public Actor representative = null;
}
