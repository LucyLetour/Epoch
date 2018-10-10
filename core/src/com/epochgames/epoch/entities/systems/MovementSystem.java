package com.epochgames.epoch.entities.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.epochgames.epoch.entities.components.Mappers;
import com.epochgames.epoch.entities.components.MoveComponent;
import com.epochgames.epoch.entities.components.TransformComponent;
import com.epochgames.epoch.entities.components.TurnComponent;
import com.epochgames.epoch.util.EpochMath;
import com.epochgames.epoch.util.HexagonGrid;
import com.epochgames.epoch.util.PathManager;
import com.epochgames.epoch.util.hexlib.Point;

public class MovementSystem extends IteratingSystem {

    private HexagonGrid hexagonGrid;
    public PathManager pathManager;

    public float current;

    public MovementSystem(HexagonGrid hexagonGrid) {
        super(Family.all(TransformComponent.class, MoveComponent.class, TurnComponent.class).get());
        this.hexagonGrid = hexagonGrid;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transformComponent = Mappers.transform.get(entity);
        MoveComponent moveComponent = Mappers.move.get(entity);
        TurnComponent turnComponent = Mappers.turn.get(entity);

        if(moveComponent.shouldMove) {
            pathManager = new PathManager(hexagonGrid.hexCalculator.drawLine(
                    hexagonGrid.hexGrid.getByCubeCoordinate(moveComponent.currentPosition).get(),
                    hexagonGrid.hexGrid.getByCubeCoordinate(moveComponent.nextPosition).get()));
        }

        moveComponent.isMoving = (moveComponent.shouldMove || moveComponent.isMoving) && turnComponent.isMyTurn;
        moveComponent.shouldMove = false;

        if(moveComponent.isMoving) {
            float nextPosX = (float)hexagonGrid.hexGrid.getByCubeCoordinate(moveComponent.nextPosition).get().getCenterX();
            float nextPosY = (float)hexagonGrid.hexGrid.getByCubeCoordinate(moveComponent.nextPosition).get().getCenterY();
            current = calculateEntityMovePercentage(deltaTime, moveComponent);
            if(current >= 1) {
                current -= 1;
            }
            transformComponent.position = pathManager.getSplineAtPoint(current);

            //transformComponent.position = pathManager.getSplineAtPoint(calculateEntityMovePercentage(deltaTime, moveComponent));
            //.lerp(new Vector2(nextPosX, nextPosY), calculateEntityMovePercentage(deltaTime, moveComponent));
            if(false) {//calculateEntityMovePercentage(deltaTime, moveComponent) >= 1.0f) {
                moveComponent.isMoving = false;
                transformComponent.position = new Vector2(nextPosX, nextPosY);
                moveComponent.currentPosition = moveComponent.nextPosition;
                moveComponent.nextPosition = null;
                moveComponent.timeMoving = 0.0f;
            }
        }
    }

    public float calculateEntityMovePercentage(float deltaTime, MoveComponent moveComponent) {
        return current += deltaTime * 0.1f;
    }
}
