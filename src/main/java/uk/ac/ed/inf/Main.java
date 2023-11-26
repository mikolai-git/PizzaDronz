package uk.ac.ed.inf;

import uk.ac.ed.inf.ilp.data.LngLat;
import uk.ac.ed.inf.ilp.data.NamedRegion;
import uk.ac.ed.inf.ilp.data.Order;
import uk.ac.ed.inf.ilp.data.Restaurant;

import java.util.List;

/**
 * Hello world!
 *
 */
public class Main {


    public static void main(String[] args){

    /*  ACTUAL CODE
    {
        if (args.length != 2) {
            System.out.println("Usage: java -jar PizzaDronz-1.0-SNAPSHOT.jar <date> <url>");
            System.exit(1);
        }

        String date = args[0];
        String uri = args[1];

     */

    //TESTING TO AVOID COMPILING
    String date = "2023-09-04";
    String uri = "https://ilp-rest.azurewebsites.net";

    //Retrieve data from REST server
    //Initialise REST data retriever
    RestDataRetriever restDataRetriever = new RestDataRetriever();

    //Retrieve central area
    NamedRegion centralArea = restDataRetriever.getCentralArea(uri);

    //Retrieve list of no-fly zones
    NamedRegion[] noFlyZones = restDataRetriever.getNoFlyZones(uri);

    //Retrieve list of restaurants
    Restaurant[] restaurants = restDataRetriever.getRestaurants(uri);

    //Retrieve list of orders
    List<Order> orders = restDataRetriever.getOrders(uri, date);

    //Validate orders
    //Initialise OrderValidator
    OrderValidator orderValidator = new OrderValidator();

    //Loop through each order and set their validation code
    for (Order order : orders) {
        orderValidator.validateOrder(order, restaurants);
    }

    //A* Testing
    Node startNode = new Node(new LngLat(0, 0));
    Node goalNode = new Node(new LngLat(5,5));

    List<LngLat> path = AStar.aStar(startNode, goalNode);
        System.out.println(path);


    }
}
