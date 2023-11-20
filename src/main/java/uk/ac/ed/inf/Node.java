package uk.ac.ed.inf;

import uk.ac.ed.inf.ilp.data.LngLat;

import java.util.List;

public class Node {
    private LngLat lngLat;
    private boolean isNoFlyZone;
    private List<Node> neighbours;

    public Node(LngLat lngLat) {
        this.lngLat = lngLat;
    }

    public void setIsNoFlyZone(boolean noFlyZone) {
        isNoFlyZone = noFlyZone;
    }
}
