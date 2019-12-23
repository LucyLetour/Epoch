package com.epochgames.epoch.util.hexlib;

import com.badlogic.ashley.core.Entity;
import org.hexworks.mixite.core.api.CubeCoordinate;
import org.hexworks.mixite.core.api.defaults.DefaultSatelliteData;

public class HexSatelliteData extends DefaultSatelliteData {
    private boolean passable;
    private float visibility;
    private Entity entityContained;
    private CubeCoordinate position;

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
        this.passable = entity == null;
    }

    public boolean getPassable() {
        return passable;
    }
}
