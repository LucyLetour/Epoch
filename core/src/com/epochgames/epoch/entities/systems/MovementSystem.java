package com.epochgames.epoch.entities.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.epochgames.epoch.Epoch;
import com.epochgames.epoch.entities.components.Mappers;
import com.epochgames.epoch.entities.components.MoveComponent;
import com.epochgames.epoch.entities.components.TransformComponent;
import com.epochgames.epoch.entities.components.TurnComponent;
import com.epochgames.epoch.util.HexagonGrid;
import com.epochgames.epoch.util.PathManager;
import com.epochgames.epoch.util.Pathfinding.PathfindingAlgorithm;
import com.epochgames.epoch.util.hexlib.HexagonGridUtil;

import static com.epochgames.epoch.GameManager.SHIP_SPEED;

public class MovementSystem extends IteratingSystem {

    private HexagonGrid hexagonGrid;

    private Epoch game;

    private float current;

    public MovementSystem(Epoch game, HexagonGrid hexagonGrid) {
        super(Family.all(TransformComponent.class, MoveComponent.class, TurnComponent.class).get());
        this.hexagonGrid = hexagonGrid;
        this.game = game;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transformComponent = Mappers.transform.get(entity);
        MoveComponent moveComponent = Mappers.move.get(entity);
        TurnComponent turnComponent = Mappers.turn.get(entity);

        if(moveComponent.shouldMove) {
            PathManager.init(PathfindingAlgorithm.findPath(
                    HexagonGridUtil.hexGridToNodes(),
                    HexagonGridUtil.hexagonToNode(hexagonGrid.hexGrid.getByCubeCoordinate(moveComponent.currentPosition).get()),
                    HexagonGridUtil.hexagonToNode(hexagonGrid.hexGrid.getByCubeCoordinate(moveComponent.nextPosition).get())));
        }

        moveComponent.isMoving = (moveComponent.shouldMove || moveComponent.isMoving) && turnComponent.isMyTurn;
        moveComponent.shouldMove = false;

        if(moveComponent.isMoving) {
            float nextPosX = (float)hexagonGrid.hexGrid.getByCubeCoordinate(moveComponent.nextPosition).get().getCenterX();
            float nextPosY = (float)hexagonGrid.hexGrid.getByCubeCoordinate(moveComponent.nextPosition).get().getCenterY();
            current = calculateEntityMovePercentage(deltaTime);
            transformComponent.position = PathManager.getSplineAtPoint(current, transformComponent).position;
            transformComponent.rotation = PathManager.getSplineAtPoint(current, transformComponent).rotation;

            if(current >= 1) {
                moveComponent.isMoving = false;
                transformComponent.position = new Vector2(nextPosX, nextPosY);
                moveComponent.lastPosition = moveComponent.currentPosition;
                moveComponent.currentPosition = moveComponent.nextPosition;
                moveComponent.nextPosition = null;
                moveComponent.timeMoving = 0.0f;
                current = 0;
            }
        }
    }

    private float calculateEntityMovePercentage(float deltaTime) {
        PathManager.catmullRomSpline.derivativeAt(PathManager.output, current);
        return current += (deltaTime * SHIP_SPEED / PathManager.catmullRomSpline.spanCount) / PathManager.output.len();
    }
}
