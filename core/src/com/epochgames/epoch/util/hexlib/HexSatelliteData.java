package com.epochgames.epoch.util.hexlib;

import com.badlogic.ashley.core.Entity;
import org.codetome.hexameter.core.api.contract.SatelliteData;

public class HexSatelliteData implements SatelliteData {
    public boolean passable;
    public boolean visible;
    public Entity entityContained;

    @Override
    public boolean isPassable() {
        return passable;
    }

    @Override
    public void setPassable(boolean passable) {
        this.passable = passable;
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

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public void setOpaque(boolean opaque) {

    }

    @Override
    public double getMovementCost() {
        return 0;
    }

    @Override
    public void setMovementCost(double movementCost) {

    }
}
