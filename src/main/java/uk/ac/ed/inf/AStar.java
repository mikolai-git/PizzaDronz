package uk.ac.ed.inf;

import uk.ac.ed.inf.ilp.constant.SystemConstants;
import uk.ac.ed.inf.ilp.data.LngLat;
import java.util.*;

public class AStar {

    static LngLatHandler lngLatHandler = new LngLatHandler();

    /***
     *
     * @param node
     * @return list of 16 neighbours in the 16 cardinal compass directions starting at 0 = east
     */
    static List<Node> generateNeighbours(Node node) {

        System.out.println("Generating neighbours for Node:" + node.lngLat);

        List<Node> neighbours = new ArrayList<>();
        for (int direction = 0; direction < 16; direction++) {
            double angle = Math.toRadians(22.5 * direction);
            double newLng = node.lngLat.lng() + SystemConstants.DRONE_MOVE_DISTANCE * Math.cos(angle);
            double newLat = node.lngLat.lat() + SystemConstants.DRONE_MOVE_DISTANCE * Math.sin(angle);
            LngLat newLngLat = new LngLat(newLng, newLat);

            Node newNode = new Node(newLngLat);
            neighbours.add(newNode);
        }
        return neighbours;
    }

    static List<LngLat> aStar(Node start, Node goal) {

        // Priority queue to store nodes in order of their fCost
        PriorityQueue<Node> openSet = new PriorityQueue<>();

        // Set to store nodes that have been visited
        Set<Node> closedSet = new HashSet<>();

        //Initialise costs for the start node
        start.gCost = 0;
        start.hCost = lngLatHandler.distanceTo(start.lngLat, goal.lngLat);
        start.fCost = start.gCost + start.hCost;

        // Add the start node to the open set
        openSet.add(start);

        // Loop until the open set is empty
        while (!openSet.isEmpty()) {

            // Get the node with the lowest fCost from the open set
            Node currentNode = openSet.poll();

            // If the current node is the goal, reconstruct and return the path
            if (lngLatHandler.distanceTo(currentNode.lngLat, goal.lngLat) <= SystemConstants.DRONE_IS_CLOSE_DISTANCE) {

                System.out.println("GOAL REACHED");

                List<LngLat> path = new ArrayList<>();
                // Reconstruct the path by backtracking from the goal to the start
                while (currentNode != null) {
                    path.add(new LngLat(currentNode.lngLat.lng(), currentNode.lngLat.lat()));
                    currentNode = currentNode.parent;
                }
                // Reverse the path to get it from start to goal
                Collections.reverse(path);
                return path;
            }

            // Mark the current node as visited
            closedSet.add(currentNode);

            // Explore neighbors of the current node
            for (Node neighbour : generateNeighbours(currentNode)) {
                // Skip neighbors that have already been visited
                if (closedSet.contains(neighbour)) {
                    continue;
                }

                // Calculate the tentative cost from the start to this neighbor
                double tentativeGCost = currentNode.gCost + lngLatHandler.distanceTo(currentNode.lngLat, neighbour.lngLat);

                // If the tentative cost is lower than the current known cost to the neighbor
                if (tentativeGCost < neighbour.gCost) {
                    // Update the cost in the edges map
                    currentNode.addEdge(new LngLat(neighbour.lngLat.lng(), neighbour.lngLat.lat()), tentativeGCost);
                    // Update the cost and heuristic values for the neighbor
                    neighbour.gCost = tentativeGCost;
                    neighbour.hCost = lngLatHandler.distanceTo(neighbour.lngLat, goal.lngLat);
                    neighbour.fCost = neighbour.gCost + neighbour.hCost;

                    // Set the current node as the parent of the neighbor
                    neighbour.parent = currentNode;

                    // If the neighbor is not in the open set, add it
                    if (!openSet.contains(neighbour)) {
                        openSet.add(neighbour);
                    }


                }

            }

        }
        // If the open set is empty and the goal was not reached, return null (no path found)
        System.out.println("No Path Found");
        return null;

    }
}