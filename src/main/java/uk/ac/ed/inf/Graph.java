package uk.ac.ed.inf;

import uk.ac.ed.inf.ilp.data.LngLat;
import uk.ac.ed.inf.ilp.data.Restaurant;
import uk.ac.ed.inf.ilp.interfaces.LngLatHandling;

import java.util.*;


public class Graph {
    double moveDistance;
    List<Node> nodes;
    Restaurant[] restaurants;
    LngLat widthHeight;

    LngLatHandler lngLatHandler = new LngLatHandler();

    public Graph(double moveDistance, Restaurant[] restaurants){
        this.nodes = new ArrayList<>(); {
        this.moveDistance = moveDistance;
        this.restaurants = restaurants;

        }
    }

    /*
    private LngLat findGraphSize(Restaurant[] restaurants) {

        List<Double> xs = new ArrayList<>();
        List<Double> ys = new ArrayList<>();


        for (Restaurant restaurant : restaurants){
            xs.add(restaurant.location().lng());
            ys.add(restaurant.location().lat());
        }

        Collections.sort(xs);
        Collections.sort(ys);

        LngLat xMin = new LngLat(xs.get(0), 0);
        LngLat xMax = new LngLat(xs.get(xs.size() - 1), 0);
        LngLat yMin = new LngLat(0, ys.get(0));
        LngLat yMax = new LngLat(0, ys.get(ys.size() - 1));

        double width = lngLatHandler.distanceTo(xMin, xMax);
        double height = lngLatHandler.distanceTo(yMin, yMax);

        return new LngLat(width, height);

    }

     */
}
