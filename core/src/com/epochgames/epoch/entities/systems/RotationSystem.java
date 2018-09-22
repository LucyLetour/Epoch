package com.epochgames.epoch.entities.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Interpolation;
import com.epochgames.epoch.entities.components.Mappers;
import com.epochgames.epoch.entities.components.MoveComponent;
import com.epochgames.epoch.entities.components.TransformComponent;
import com.epochgames.epoch.entities.components.TurnComponent;

public class RotationSystem extends IteratingSystem {

    public RotationSystem() {
        super(Family.one(TransformComponent.class, MoveComponent.class).all(TransformComponent.class, TurnComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transformComponent = Mappers.transform.get(entity);
        MoveComponent moveComponent = Mappers.move.get(entity);
        TurnComponent turnComponent = Mappers.turn.get(entity);

        transformComponent.isRotating = (transformComponent.shouldRotate || transformComponent.isRotating) && turnComponent.isMyTurn;
        transformComponent.shouldRotate = false;

        if(transformComponent.isRotating) {
            Interpolation.linear.apply(transformComponent.rotation, transformComponent.nextRotation,
                    calculateEntityRotatePercentage(deltaTime, transformComponent));
            if(calculateEntityRotatePercentage(deltaTime, transformComponent) == 1.0f) {
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

    public float calculateEntityRotatePercentage(float deltaTime, TransformComponent transformComponent) {
        transformComponent.timeRotating += deltaTime;
        return (transformComponent.timeRotating / MoveComponent.TOTAL_MOVE_TIME);
    }
}