package com.epochgames.epoch.entities.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.epochgames.epoch.entities.components.Mappers;
import com.epochgames.epoch.entities.components.MoveComponent;
import com.epochgames.epoch.entities.components.TransformComponent;
import com.epochgames.epoch.util.EpochMath;
import com.epochgames.epoch.util.hexlib.Point;

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
            moveComponent.shouldMove = false;

            if(moveComponent.isMoving) {
                transformComponent.position.lerp(new Vector2(moveComponent.nextPosition.getHexCenter().x, moveComponent.nextPosition.getHexCenter().y),
                        calculateEntityMovePercentage(deltaTime, moveComponent));
                if(EpochMath.distance(new Point(transformComponent.position.x, transformComponent.position.y),
                        moveComponent.nextPosition.getHexCenter()) < 1.0f) {
                    moveComponent.isMoving = false;
                    transformComponent.position = new Vector2(moveComponent.nextPosition.getHexCenter().x,
                            moveComponent.nextPosition.getHexCenter().y);
                    moveComponent.currentPosition = moveComponent.nextPosition;
                    moveComponent.nextPosition = null;
                    moveComponent.timeMoving = 0.0f;
                }
            }
        }

        entityProccessor.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        entityProccessor.add(entity);
    }

    public float calculateEntityMovePercentage(float deltaTime, MoveComponent moveComponent) {
        moveComponent.timeMoving += deltaTime;
        return (moveComponent.timeMoving / MoveComponent.TOTAL_MOVE_TIME);
    }
}
