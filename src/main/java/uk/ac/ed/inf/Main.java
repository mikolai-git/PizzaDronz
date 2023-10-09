package uk.ac.ed.inf;

import uk.ac.ed.inf.ilp.data.LngLat;
import uk.ac.ed.inf.ilp.data.NamedRegion;

/**
 * Hello world!
 *
 */
public class Main
{
    public static void main( String[] args )
    {
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

        LngLat [] regionVertices = {point1, point2, point3, point4};

        NamedRegion testNamedRegion = new NamedRegion("testRegion", regionVertices);

        //testing the functions
        double distanceToTest = testLngLatHandler.distanceTo(point1, point3);
        System.out.println("distanceTo: " + distanceToTest);

        boolean isCloseToTest = testLngLatHandler.isCloseTo(point1, point1);
        System.out.println("isCloseTo: " + isCloseToTest);

        boolean isInRegionTest = testLngLatHandler.isInRegion(point1, testNamedRegion);
        System.out.println("isInRegion: " + isInRegionTest);

    }
}
