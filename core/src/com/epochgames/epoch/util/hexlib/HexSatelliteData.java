package com.epochgames.epoch.util.hexlib;

import com.badlogic.ashley.core.Entity;
import com.epochgames.epoch.GameManager;
import org.codetome.hexameter.core.api.CubeCoordinate;
import org.codetome.hexameter.core.api.contract.SatelliteData;
import org.codetome.hexameter.core.api.defaults.DefaultSatelliteData;

public class HexSatelliteData extends DefaultSatelliteData {
    public boolean passable;
    public float visibility;
    public Entity entityContained;
    public CubeCoordinate position;

    public HexSatelliteData(Entity entityContained, CubeCoordinate position) {
        setEntityContained(entityContained);
        this.position = position;
    }

    public float getVisibility() {
        return visibility;
    }

    public void setVisibility(float visibility) {
        this.visibility = visibility;
    }

    public Entity getEntityContained() {
        return entityContained;
    }

    public void setEntityContained(Entity entity) {
        this.entityContained = entity;
        this.passable = false;
    }
}
