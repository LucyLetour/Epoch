package com.epochgames.epoch.util.Pathfinding;

import com.badlogic.gdx.Gdx;
import com.ender.games.epoch.GameManager;
import com.epochgames.epoch.util.hexlib.HexSatelliteData;
import org.hexworks.mixite.core.api.CubeCoordinate;
import org.hexworks.mixite.core.api.Hexagon;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class Node implements Comparable{
    private int xCoord;
    private int yCoord;
    private int zCoord;
    private boolean passable;
    private int priority;

    public Node(Hexagon<HexSatelliteData> hexagon) {
        this.xCoord = hexagon.getGridX();
        this.yCoord = hexagon.getGridY();
        this.zCoord = hexagon.getGridZ();
        this.passable = !hexagon.getSatelliteData().isPresent() || hexagon.getSatelliteData().get().getPassable();
    }

    public ArrayList<Node> getNeighbors(ArrayList<Node> nodes) {
        Collection<Hexagon<HexSatelliteData>> neighbors = GameManager.getInstance().game.inGameScreen.hexagonGrid.hexGrid.getNeighborsOf(
                GameManager.getInstance().game.inGameScreen.hexagonGrid.hexGrid.getByCubeCoordinate(
                        CubeCoordinate.fromCoordinates(xCoord, zCoord)).get()
        );

        ArrayList<Node> out =  new ArrayList<>();
        Node newNode;

        for (Hexagon<HexSatelliteData> h : neighbors) {
            newNode = new Node(h);
            if(nodes.contains(newNode) && newNode.passable)
                out.add(nodes.get(nodes.indexOf(newNode)));
        }

        return out;
    }

    public int getxCoord() {
        return xCoord;
    }

    public int getzCoord() {
        return zCoord;
    }

    public boolean isPassable() {
        return passable;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public boolean equals(Object n) {
        if(n instanceof Node) {
            return ((Node)n).xCoord == this.xCoord && ((Node)n).yCoord == this.yCoord && ((Node)n).zCoord == this.zCoord;
        }
        else {
            Gdx.app.error("Invalid Comparison", "Tried to compare a Node and a " + n.getClass().getSimpleName() + "!");
            return false;
        }
    }

    @Override
    public String toString() {
        return "Node{" +
                "xCoord=" + xCoord +
                ", yCoord=" + yCoord +
                ", zCoord=" + zCoord +
                '}';
    }

    @Override
    public int compareTo(@NotNull Object o) {
        if(o instanceof Node) {
            return this.priority - ((Node) o).priority;
        }
        else {
            Gdx.app.error("Invalid Comparison", "Tried to compare a Node and a " + o.getClass().getSimpleName() + "!");
            return 0;
        }
    }
}
