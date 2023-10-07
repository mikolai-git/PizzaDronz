package uk.ac.ed.inf;

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
    }
}
