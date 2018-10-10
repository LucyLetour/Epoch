package com.epochgames.epoch.entities.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.epochgames.epoch.entities.components.Mappers;
import com.epochgames.epoch.entities.components.StorageComponent;
import com.epochgames.epoch.util.HexagonGrid;
import com.epochgames.epoch.util.hexlib.HexSatelliteData;

public class StorageSystem extends IteratingSystem {

    HexagonGrid hexagonGrid;
    private ComponentMapper<StorageComponent> storage = Mappers.storage;

    public StorageSystem(HexagonGrid hexagonGrid) {
        super(Family.all(StorageComponent.class).get());
        this.hexagonGrid = hexagonGrid;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        HexSatelliteData satelliteData = hexagonGrid.hexGrid.getByCubeCoordinate(storage.get(entity).cubeCoordinate).get().getSatelliteData().get();
        if(satelliteData.getEntityContained() == null) {

        }
    }
}
