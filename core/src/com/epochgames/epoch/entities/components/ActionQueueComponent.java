package com.epochgames.epoch.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Array;
import com.epochgames.epoch.GameManager;

public class ActionQueueComponent implements Component {
    public Array<GameManager.Actions> queuedActions = new Array<>();
}
