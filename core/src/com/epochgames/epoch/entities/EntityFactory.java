package com.epochgames.epoch.entities;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.epochgames.epoch.Program;
import com.epochgames.epoch.entities.components.*;
import com.epochgames.epoch.util.Assets;

/**
 * This is the Big Kahuna since this in the class that will
 * populate our universe with entities. Somehow it'll have to take into account
 * story but hey we'll get there /shrug
 */

public class EntityFactory {
    public static Viewport viewport;
    public static SpriteBatch batch;

    //Boolean to determine whether or not the factory has been initialized
    private static boolean initialized = false;

    //Sets the viewport and batch only if the factory has not been initialized already
    public static void init(Program game) {
        if(!initialized) {
            viewport = game.viewport;
            batch = game.batch;

            initialized = true;
        }
    }

    /**
     * Creates a player at the specified position
     * This should probably take in the Ship the player is piloting
     * @param x The x coordinate of the player based on the hex grid
     * @param y The y coordinate of the player based on the hex grid
     * @return The created entity
     */
    public static Entity createPlayer(int x, int y) {
        Entity player = new Entity();

        ActionCompletenessComponent actionCompletenessComponent = new ActionCompletenessComponent();
        actionCompletenessComponent.actionCompleteness = ActionCompletenessComponent.FULL;

        HealthComponent healthComponent = new HealthComponent();
        //Arbitrary values, will be determined by player's ship
        healthComponent.health = 100;
        healthComponent.armor = 200;

        IconComponent iconComponent = new IconComponent();
        //Again, read off of the ship given
        iconComponent.region = Assets.MANAGER.get(Assets.SHIP_ATLAS_REGIONS.ALACRON.getAtlasRegion());

        InteractableComponent interactableComponent = new InteractableComponent();
        interactableComponent.interactable = true;
        //TODO this
        //interactableComponent.representative = new TileMapActor();

        TransformComponent transformComponent = new TransformComponent();
        //TODO this too
        transformComponent.scale = 1.0f;
        transformComponent.rotation = 0.0f;
        transformComponent.position = new Vector2(x, y);

        TurnComponent turnComponent = new TurnComponent();

        TypeComponent typeComponent = new TypeComponent();
        typeComponent.type = TypeComponent.PLAYER;

        player.add(actionCompletenessComponent).add(healthComponent).add(iconComponent).
                add(interactableComponent).add(transformComponent).add(turnComponent).add(typeComponent);

        return player;
    }

    /**
     * Creates an npc at the specified position
     * This should probably take in the Ship the npc is piloting
     * @param x The x coordinate of the player based on the hex grid
     * @param y The y coordinate of the player based on the hex grid
     * @return The created entity
     */
    public static Entity createNPC(int x, int y) {
        Entity npc = new Entity();

        ActionCompletenessComponent actionCompletenessComponent = new ActionCompletenessComponent();
        //Should get distance from player every turn, if its in view then this becomes true
        actionCompletenessComponent.actionCompleteness = ActionCompletenessComponent.NONE;

        HealthComponent healthComponent = new HealthComponent();
        //Arbitrary values, will be determined by npc's ship
        healthComponent.health = 100;
        healthComponent.armor = 200;

        IconComponent iconComponent = new IconComponent();
        //Again, read off of the ship given
        iconComponent.region = Assets.MANAGER.get(Assets.SHIP_ATLAS_REGIONS.ALACRON.getAtlasRegion());

        InteractableComponent interactableComponent = new InteractableComponent();
        interactableComponent.interactable = true;
        //TODO this
        //interactableComponent.representative = new TileMapActor();

        TransformComponent transformComponent = new TransformComponent();
        //TODO this too
        transformComponent.scale = 1.0f;
        transformComponent.rotation = 0.0f;
        transformComponent.position = new Vector2(x, y);

        TurnComponent turnComponent = new TurnComponent();

        TypeComponent typeComponent = new TypeComponent();
        //TODO Determine this off the ship the NPC is piloting
        typeComponent.type = TypeComponent.NEUTRAL;

        npc.add(actionCompletenessComponent).add(healthComponent).add(iconComponent).
                add(interactableComponent).add(transformComponent).add(turnComponent).add(typeComponent);

        return npc;
    }
}
