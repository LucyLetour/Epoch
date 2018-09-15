package com.epochgames.epoch.entities.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Array;
import com.epochgames.epoch.entities.components.Mappers;
import com.epochgames.epoch.entities.components.MoveComponent;
import com.epochgames.epoch.entities.components.TransformComponent;

public class RotationSystem extends IteratingSystem {

    private Array<Entity> entityProccessor;

    public RotationSystem() {
        super(Family.one(TransformComponent.class, MoveComponent.class).all(TransformComponent.class).get());

        entityProccessor = new Array<>();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        for(Entity entity : entityProccessor) {
            TransformComponent transformComponent = Mappers.transform.get(entity);
            MoveComponent moveComponent = Mappers.move.get(entity);

            transformComponent.isRotating = transformComponent.shouldRotate || transformComponent.isRotating;
            transformComponent.shouldRotate = false;

            if(transformComponent.isRotating) {
                Interpolation.linear.apply(transformComponent.rotation, transformComponent.nextRotation,
                        calculateEntityRotatePercentage(deltaTime, transformComponent));
                if(Math.abs(transformComponent.rotation - transformComponent.nextRotation) < 1.0f) {
                    transformComponent.isRotating = false;
                    transformComponent.rotation = transformComponent.nextRotation;
                    transformComponent.nextRotation = 0.0f;
                    transformComponent.timeRotating = 0.0f;
                    if(moveComponent != null) {
                        moveComponent.shouldMove = true;
                    }
                }
            }
        }

        entityProccessor.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        entityProccessor.add(entity);
    }

    public float calculateEntityRotatePercentage(float deltaTime, TransformComponent transformComponent) {
        transformComponent.timeRotating += deltaTime;
        return (transformComponent.timeRotating / MoveComponent.TOTAL_MOVE_TIME);
    }
}