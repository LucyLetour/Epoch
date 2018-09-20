package com.epochgames.epoch.entities.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.epochgames.epoch.GameManager;
import com.epochgames.epoch.entities.components.Mappers;
import com.epochgames.epoch.entities.components.TurnComponent;

import java.util.Comparator;

public class TurnSystem extends SortedIteratingSystem {

    private int currentStateProcessing;
    private long currentTurnProcessing;
    private boolean processing;
    private GameManager gameManager;
    private ComponentMapper<TurnComponent> turnComponent = Mappers.turn;
    ImmutableArray<Entity> entities;

    public TurnSystem(GameManager gameManager) {
        super(Family.all(TurnComponent.class).get(), new PriorityComparator());
        entities = this.getEntities();
        this.gameManager = gameManager;
    }

    /**
     * Assigns Entities whether or not it is their turn. The first check made ensures the system
     * is looking for entities that should be taking their turn, i.e. they line up with the current
     * state and they haven't yet gone this turn. Then, essentially, the system decides if it is
     * processing, meaning an entity is currently doing its turn. If it is, and the entity isn't done,
     * nothing happens. If the entity has finished its turn then we set processing to false to free
     * up the system and end that entity's turn.
     * @param entity The entity being checked
     * @param deltaTime The time since last method call, not used since this is turn based
     */
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        //Turn has been changed, go to the next and reset which state we're processing to 0
        if(gameManager.turnNumber != currentTurnProcessing) {
            currentTurnProcessing = gameManager.turnNumber;
            setupEntitiesForNewTurn();
        }

        currentStateProcessing = gameManager.turnState;

        if(!processing &&
                turnComponent.get(entity).priority == currentStateProcessing &&
                !turnComponent.get(entity).turnFinished) {
            processing = true;
            turnComponent.get(entity).isMyTurn = true;
        }
        else if(processing) {
            if(turnComponent.get(entity).turnFinished) {
                turnComponent.get(entity).isMyTurn = false;
                processing = false;
            }
        }
    }

    private void setupEntitiesForNewTurn() {
        for (Entity entity : entities) {
            turnComponent.get(entity).turnFinished = false;
        }
    }

    private static class PriorityComparator implements Comparator<Entity> {
        private ComponentMapper<TurnComponent> turnComponent = Mappers.turn;
        @Override
        public int compare(Entity e1, Entity e2) {
            return (int)Math.signum(turnComponent.get(e1).priority - turnComponent.get(e2).priority);
        }
    }
}
