package com.epochgames.epoch.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Array;
import com.epochgames.epoch.GameManager;

/**
 * Contains the queue of actions an {@link Entity} is set
 * to perform
 */
public class ActionQueueComponent implements Component {
    public Array<GameManager.Actions> queuedActions = new Array<>();
}
