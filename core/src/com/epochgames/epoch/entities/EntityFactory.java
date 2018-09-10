package com.epochgames.epoch.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.epochgames.epoch.Epoch;
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
    public static void init(Epoch game) {
        if(!initialized) {
            viewport = game.viewport;
            batch = game.batch;

            initialized = true;
        }
    }

    /**
     * Creates a ship at the specified position
     * @param x The x coordinate of the player based on the hex grid
     * @param y The y coordinate of the player based on the hex grid
     * @param ship The ship to create
     * @param isPlayer Whether or not this Entity is a player
     * @return The created entity
     */
    public static Entity createShip(int x, int y, Ship ship, boolean isPlayer) {
        Entity newShip = new Entity();

        ActionCompletenessComponent actionCompletenessComponent = new ActionCompletenessComponent();
        //If not a player, should get distance from player every turn. If the NPC is in view of the player then this becomes true
        actionCompletenessComponent.actionCompleteness = isPlayer ?  ActionCompletenessComponent.FULL : ActionCompletenessComponent.NONE;

        HealthComponent healthComponent = new HealthComponent();
        healthComponent.health = ship.shipMake.getHealth();
        healthComponent.armor = ship.shipMake.getArmor();

        IconComponent iconComponent = new IconComponent();
        iconComponent.region = Assets.MANAGER.get(ship.shipMake.getAtlasRegion());

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
        if(!isPlayer) {
            if(ship.isPirate) {
                typeComponent.type = TypeComponent.ENEMY; //NPC is a pirate
            }
            else {
                typeComponent.type = TypeComponent.NEUTRAL; //NPC is not a pirate
            }
        }
        else {
            typeComponent.type = TypeComponent.PLAYER; //NPC is actually the player
        }

        newShip.add(actionCompletenessComponent).add(healthComponent).add(iconComponent).
                add(interactableComponent).add(transformComponent).add(turnComponent).add(typeComponent);

        return newShip;
    }
}
