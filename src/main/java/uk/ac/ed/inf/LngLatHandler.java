package uk.ac.ed.inf;

import uk.ac.ed.inf.ilp.constant.SystemConstants;
import uk.ac.ed.inf.ilp.data.LngLat;
import uk.ac.ed.inf.ilp.data.NamedRegion;
import uk.ac.ed.inf.ilp.interfaces.LngLatHandling;

public class LngLatHandler implements LngLatHandling {
    public double distanceTo(LngLat startPosition, LngLat endPosition) {

        double x1 = startPosition.lng();
        double y1 = startPosition.lat();
        double x2 = endPosition.lng();
        double y2 = endPosition.lat();

        //Return Euclidean distance
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }


    public boolean isCloseTo(LngLat startPosition, LngLat otherPosition) {

        //If points are < DRONE_IS_CLOSE_DISTANCE, returns true, otherwise returns false
        return distanceTo(startPosition, otherPosition) < SystemConstants.DRONE_IS_CLOSE_DISTANCE;
    }

    public boolean isInCentralArea(LngLat point, NamedRegion centralArea) {
        return LngLatHandling.super.isInCentralArea(point, centralArea);
    }

    public boolean isInRegion(LngLat position, NamedRegion region) {
        int numVertices = region.vertices().length;
        int intersectionCount = 0;

        for (int i = 0; i < numVertices; i++) {
            LngLat vertex1 = region.vertices()[i];
            LngLat vertex2 = region.vertices()[(i + 1) % numVertices];

            if (pointOnSegment(position, vertex1, vertex2)) {
                return true; // Point is on the edge of the polygon
            }

            if (position.lat() > Math.min(vertex1.lat(), vertex2.lat()) &&
                position.lat() <= Math.max(vertex1.lat(), vertex2.lat()) &&
                position.lng() <= Math.max(vertex1.lng(), vertex2.lng()) &&
                    vertex1.lat() != vertex2.lat()) {
                double xIntersection = (position.lat() - vertex1.lat()) * (vertex2.lng() - vertex1.lng()) / (vertex2.lat() - vertex1.lat()) + vertex1.lng();

                if (vertex1.lng() == vertex2.lng() || position.lng() <= xIntersection) {
                    intersectionCount++;
                }
            }

        }

        return intersectionCount % 2 == 1;

    }

    // Helper function for isInRegion. Returns true if the point lies on an edge
    public static boolean pointOnSegment(LngLat position, LngLat start, LngLat end) {
        return (position.lng() == start.lng() && position.lat() == start.lat()) ||
                (position.lng() == end.lng() && position.lat() == end.lat()) ||
                (position.lng() >= Math.min(start.lng(), end.lat()) &&
                        position.lng() <= Math.max(start.lng(), end.lng()) &&
                        position.lat() >= Math.min(start.lat(), end.lat()) &&
                        position.lat() <= Math.max(start.lat(), end.lat()));
    }
    // Calculate the lng and lat components of the coordinate shift, then add to startPosition
    public LngLat nextPosition(LngLat startPosition, double angle) {

        double lngComponent = SystemConstants.DRONE_MOVE_DISTANCE * Math.cos(Math.toRadians(angle));
        double latComponent = SystemConstants.DRONE_MOVE_DISTANCE * Math.sin(Math.toRadians(angle));

        return new LngLat(startPosition.lng() + lngComponent, startPosition.lat() + latComponent);

    }
}
