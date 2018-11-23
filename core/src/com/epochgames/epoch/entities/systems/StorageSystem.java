package com.epochgames.epoch.entities.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.epochgames.epoch.Epoch;
import com.epochgames.epoch.entities.components.*;
import com.epochgames.epoch.util.EpochMath;
import com.epochgames.epoch.util.HexagonGrid;
import com.epochgames.epoch.util.hexlib.HexSatelliteData;
import org.hexworks.mixite.core.api.Hexagon;

public class StorageSystem extends IteratingSystem {

    private HexagonGrid hexagonGrid;
    private Epoch game;
    private ComponentMapper<MoveComponent> move = Mappers.move;

    public StorageSystem(Epoch game, HexagonGrid hexagonGrid) {
        super(Family.all(MoveComponent.class, StorageComponent.class).get());
        this.hexagonGrid = hexagonGrid;
        this.game = game;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Hexagon<HexSatelliteData> hexagon = hexagonGrid.hexGrid.getByCubeCoordinate(move.get(entity).currentPosition).get();

        if(Mappers.type.has(entity) && Mappers.type.get(entity).type == TypeComponent.PLAYER) {
            game.inGameScreen.playerPos = hexagonGrid.hexGrid.getByPixelCoordinate(Mappers.transform.get(entity).position.x, Mappers.transform.get(entity).position.y).get().getCubeCoordinate();
        }

        if(hexagon.getSatelliteData().isPresent()) {
            hexagon.getSatelliteData().get().setEntityContained(entity);
            if(hexagon.getSatelliteData().get().getVisibility() > 0.0f) {
                Mappers.actionCompleteness.get(entity).actionCompleteness = ActionCompletenessComponent.FULL;
                Mappers.icon.get(entity).alpha = (float)EpochMath.clamp(
                        hexagon.getSatelliteData().get().getVisibility() * 2.0, 0.0, 1.0f);

            }
            else {
                Mappers.actionCompleteness.get(entity).actionCompleteness = ActionCompletenessComponent.NONE;
                Mappers.icon.get(entity).alpha = 0.0f;
            }
        }
        else {
            hexagon.setSatelliteData(new HexSatelliteData(entity, hexagon.getCubeCoordinate()));
        }

        if(!move.get(entity).currentPosition.equals(move.get(entity).lastPosition)) {
            hexagonGrid.hexGrid.getByCubeCoordinate(move.get(entity).lastPosition).get().clearSatelliteData();
        }
    }
}
