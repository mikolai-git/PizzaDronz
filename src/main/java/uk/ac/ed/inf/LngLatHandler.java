package uk.ac.ed.inf;

import uk.ac.ed.inf.ilp.data.LngLat;
import uk.ac.ed.inf.ilp.data.NamedRegion;
import uk.ac.ed.inf.ilp.interfaces.LngLatHandling;

public class LngLatHandler implements LngLatHandling {
    public double distanceTo(LngLat startPosition, LngLat endPosition) {
        return 0;
    }

    public boolean isCloseTo(LngLat startPosition, LngLat otherPosition) {
        return false;
    }

    public boolean isInCentralArea(LngLat point, NamedRegion centralArea) {
        return LngLatHandling.super.isInCentralArea(point, centralArea);
    }

    public boolean isInRegion(LngLat position, NamedRegion region) {
        return false;
    }

    public LngLat nextPosition(LngLat startPosition, double angle) {
        return null;
    }
}
