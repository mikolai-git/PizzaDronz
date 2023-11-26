package uk.ac.ed.inf;
import uk.ac.ed.inf.ilp.data.LngLat;

import java.util.*;

public class Node implements Comparable<Node> {
    LngLat lngLat;
    Map<LngLat, Double> edges = new HashMap<>();
    double gCost = Double.POSITIVE_INFINITY;
    double hCost = 0;
    double fCost = 0;
    Node parent = null;

    Node(LngLat lngLat) {
        this.lngLat = lngLat;
    }

    void addEdge(LngLat direction, double cost) {
        edges.put(direction, cost);
    }

    @Override
    public int compareTo(Node others) {
        return Double.compare(this.fCost, others.fCost);
    }

}
