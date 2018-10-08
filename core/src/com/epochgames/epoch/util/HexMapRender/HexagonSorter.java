package com.epochgames.epoch.util.HexMapRender;

import org.codetome.hexameter.core.api.CubeCoordinate;

import java.util.Comparator;

public class HexagonSorter implements Comparator<CubeCoordinate> {

    @Override
    public int compare(CubeCoordinate c1, CubeCoordinate c2) {
        return (c1.getGridX() + c1.getGridY() + c1.getGridZ()) - (c2.getGridX() + c2.getGridY() + c2.getGridZ());
    }
}
