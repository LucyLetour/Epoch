package com.epochgames.epoch.entities.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.epochgames.epoch.entities.components.Mappers;
import com.epochgames.epoch.entities.components.MoveComponent;
import com.epochgames.epoch.entities.components.TransformComponent;

public class MovementSystem extends IteratingSystem {

    private Array<Entity> entityProccessor;

    public MovementSystem() {
        super(Family.all(TransformComponent.class, MoveComponent.class).get());

        entityProccessor = new Array<>();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        for(Entity entity : entityProccessor) {
            TransformComponent transformComponent = Mappers.transform.get(entity);
            MoveComponent moveComponent = Mappers.move.get(entity);

            moveComponent.isMoving = moveComponent.shouldMove ? true : moveComponent.isMoving;

            if(moveComponent.isMoving) {
                transformComponent.position.x = Interpolation.fade.apply(
                        moveComponent.currentPosition.getHexCenter().x,
                        moveComponent.nextPosition.getHexCenter().x, 0.1f);
                transformComponent.position.y = Interpolation.fade.apply(
                        moveComponent.currentPosition.getHexCenter().y,
                        moveComponent.nextPosition.getHexCenter().y, 0.1f);
            }
            System.out.println(transformComponent.position.x + ", " + transformComponent.position.y);
        }

        entityProccessor.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        entityProccessor.add(entity);
    }
}
