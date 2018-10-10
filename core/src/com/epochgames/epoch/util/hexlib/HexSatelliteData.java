package com.epochgames.epoch.util.hexlib;

import com.badlogic.ashley.core.Entity;
import com.epochgames.epoch.GameManager;
import org.codetome.hexameter.core.api.CubeCoordinate;
import org.codetome.hexameter.core.api.contract.SatelliteData;
import org.codetome.hexameter.core.api.defaults.DefaultSatelliteData;

public class HexSatelliteData extends DefaultSatelliteData {
    public boolean passable;
    public boolean visible;
    public Entity entityContained;
    public CubeCoordinate position;

    public HexSatelliteData(Entity entityContained, CubeCoordinate position) {
        this.entityContained = entityContained;
        this.position = position;
        this.visible = GameManager.getInstance().checkTileVisibilty(position);
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Entity getEntityContained() {
        return entityContained;
    }

    public void setEntityContained(Entity entity) {
        this.entityContained = entity;
    }
}
