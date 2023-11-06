package uk.ac.ed.inf;

import uk.ac.ed.inf.ilp.data.LngLat;
import uk.ac.ed.inf.ilp.data.NamedRegion;
import uk.ac.ed.inf.ilp.data.Restaurant;
import uk.ac.ed.inf.ilp.data.Pizza;
import uk.ac.ed.inf.ilp.data.Order;
import uk.ac.ed.inf.ilp.data.CreditCardInformation;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * Hello world!
 *
 */
public class Main
{
    public static void main( String[] args )
    {

        // LngLatHandler testing
        System.out.println( "Hello World!" );

        OrderValidator testOrderValidator = new OrderValidator();
        System.out.println("testOrderValidator Instantiated");

        LngLatHandler testLngLatHandler = new LngLatHandler();
        System.out.println("testLngLatHandler Instantiated");

        //test variables
        LngLat point1 = new LngLat(-5.192473, 60.526233);
        LngLat point2 = new LngLat(-5.992473, 50.942627);
        LngLat point3 = new LngLat(5.192473, 50.816933);
        LngLat point4 = new LngLat(5.992473, 60.146233);

        LngLat pointIn = new LngLat(-4.192473, 55.526233);
        LngLat pointOut = new LngLat(-6.192473, 65.526233);

        LngLat easyPoint = new LngLat(10, -10);

        LngLat [] regionVertices = {point1, point2, point3, point4};

        NamedRegion testNamedRegion = new NamedRegion("testRegion", regionVertices);


        // Order validator testing
        DayOfWeek [] monToWed = {DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY};
        DayOfWeek [] weekdays = {DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY};

        Pizza margherita = new Pizza("Margherita", 600);
        Pizza parmigiana = new Pizza("Parmigiana", 800);

        Pizza meaty = new Pizza("Meaty", 700);
        Pizza veggie = new Pizza("Veggie", 900);

        Pizza [] pizzaPostoMenu = {margherita, parmigiana};
        Pizza [] testMenu2 = {meaty, veggie};

        Restaurant testRestaurant1 = new Restaurant("Pizza Posto", pointIn, monToWed, pizzaPostoMenu);
        Restaurant testRestaurant2 = new Restaurant("Restaurant 2", pointIn, weekdays, testMenu2);

        Restaurant [] ourRestaurants = {testRestaurant1, testRestaurant2};

        LocalDate today = LocalDate.of(2023, 10, 10);

        CreditCardInformation testCardValid = new CreditCardInformation("4844446683665696", "09/2028", "596");

        Order testOrder1 = new Order("19514FE0", today, 1400, pizzaPostoMenu, testCardValid);



        //testing the functions
        double distanceToTest = testLngLatHandler.distanceTo(point1, point3);
        System.out.println("distanceTo: " + distanceToTest);

        boolean isCloseToTest = testLngLatHandler.isCloseTo(point1, point1);
        System.out.println("isCloseTo: " + isCloseToTest);

        boolean isInRegionTest = testLngLatHandler.isInRegion(point1, testNamedRegion);
        System.out.println("isInRegion: " + isInRegionTest);

        LngLat nextPosTest = testLngLatHandler.nextPosition(easyPoint, 45);
        System.out.println("nextPos: " + nextPosTest);


        System.out.println(testOrder1.getOrderValidationCode());
        testOrder1 = testOrderValidator.validateOrder(testOrder1, ourRestaurants);
        System.out.println(testOrder1.getOrderValidationCode());


    }
}
