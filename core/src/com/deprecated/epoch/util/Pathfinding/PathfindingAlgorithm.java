package com.epochgames.epoch.util.Pathfinding;

import java.util.*;

public class PathfindingAlgorithm {
    /**
     * Breadth First Search with early exit.
     * Retrieved from "https://www.redblobgames.com/pathfinding/a-star/introduction.html"
     * @param nodes All of the nodes in the search
     * @param startNode The node to start at
     * @param endNode The node to end at
     * @return The list of nodes, from startNode to endNode
     */
    public static ArrayList<Node> findPath(ArrayList<Node> nodes, Node startNode, Node endNode) {
        PriorityQueue<Node> frontier = new PriorityQueue<>();
        frontier.add(startNode);
        HashMap<Node, Node> cameFrom = new HashMap<>();
        cameFrom.put(startNode, null);
        HashMap<Node, Integer> costSoFar = new HashMap<>();
        costSoFar.put(startNode, 0);

        Node current = null;
        Integer newCost;
        while(!frontier.isEmpty()) {
            current = frontier.remove();

            if(current.equals(endNode)) {
                break;
            }

            for(Node next : current.getNeighbors(nodes)) {
                newCost = costSoFar.get(current) + (next.isPassable() ? 1 : 1000);
                if(!costSoFar.containsKey(next) || newCost < costSoFar.get(next)) {
                    costSoFar.put(next, newCost);
                    next.setPriority(newCost);
                    frontier.add(next);
                    cameFrom.put(next, current);
                }
            }
        }

        ArrayList<Node> path = new ArrayList<>();
        while(!current.equals(startNode)) {
            path.add(current);
            current = cameFrom.get(current);
        }

        path.add(startNode);
        Collections.reverse(path);
        return path;
    }
}
