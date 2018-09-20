package com.epochgames.epoch.entities.components;

import com.badlogic.ashley.core.Component;

public class TurnComponent implements Component {
    //Leader variants are for entities that need to move before other
    //entities in their class (ex: The mothership moves before the minions)
    public static final int PLAYER_PRIORITY = 0;
    public static final int ALLY_LEADER_PRIORITY = 1;
    public static final int ALLY_PRIORITY = 2;
    public static final int ENEMY_LEADER_PRIORITY = 3;
    public static final int ENEMY_PRIORITY = 4;
    public static final int PIRATE_LEADER_PRIORITY = 4;
    public static final int PIRATE_PRIORITY = 6;
    public static final int OTHER_PRIORITY = 7; //For things like planets, asteroids, etc.

    public int priority = OTHER_PRIORITY;
    public boolean isMyTurn = false;
    public boolean turnFinished = false;
}