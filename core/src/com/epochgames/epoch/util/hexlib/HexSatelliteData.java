package com.epochgames.epoch.util.hexlib;

import org.codetome.hexameter.core.api.contract.SatelliteData;

public class HexSatelliteData implements SatelliteData {
    public boolean passable;
    //TODO Make the satellite hold the entity the hex contains

    @Override
    public boolean isPassable() {
        return passable;
    }

    @Override
    public void setPassable(boolean passable) {

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
