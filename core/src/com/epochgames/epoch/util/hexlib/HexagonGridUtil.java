package com.epochgames.epoch.util.hexlib;

import com.badlogic.gdx.Gdx;
import com.epochgames.epoch.util.HexagonGrid;
import com.epochgames.epoch.util.Pathfinding.Node;
import org.hexworks.mixite.core.api.CubeCoordinate;
import org.hexworks.mixite.core.api.Hexagon;

import java.util.ArrayList;

public class HexagonGridUtil {
    private static HexagonGrid hexGrid;
    private static boolean initialized;

    public static void init(HexagonGrid hexagonGrid) {
        hexGrid = hexagonGrid;
        initialized = true;
    }

    public static ArrayList<Node> hexGridToNodes() {
        if(initialized) {
            ArrayList<Node> nodes = new ArrayList<>();
            for (Hexagon<HexSatelliteData> h : hexGrid.hexGrid.getHexagons()) {
                nodes.add(new Node(h));
            }
            return nodes;
        }
        Gdx.app.error("Uninitialized class!", "HeaxagonGridUtil was used before it was initialized!");
        return null;
    }

    public static Node hexagonToNode(Hexagon<HexSatelliteData> hexagon) {
        return new Node(hexagon);
    }

    public static Node hexagonToNode(int x, int z) {
        return hexagonToNode(hexGrid.hexGrid.getByCubeCoordinate(CubeCoordinate.fromCoordinates(x, z)).get());
    }

    public static ArrayList<Hexagon<HexSatelliteData>> nodesToHexGrid(ArrayList<Node> nodes) {
        ArrayList<Hexagon<HexSatelliteData>> hexagons = new ArrayList<>();
        for (Node node : nodes) {
            hexagons.add(hexGrid.hexGrid.getByCubeCoordinate(CubeCoordinate.fromCoordinates(node.getxCoord(), node.getzCoord())).get());
        }
        return hexagons;
    }
}
