package uk.ac.ed.inf;

import uk.ac.ed.inf.ilp.constant.OrderValidationCode;
import uk.ac.ed.inf.ilp.data.Order;
import uk.ac.ed.inf.ilp.data.Pizza;
import uk.ac.ed.inf.ilp.data.Restaurant;
import uk.ac.ed.inf.ilp.interfaces.OrderValidation;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.YearMonth;
public class OrderValidator implements OrderValidation {

    public Order validateOrder(Order orderToValidate, Restaurant[] definedRestaurants) {

        // Initialise validation code
        orderToValidate.setOrderValidationCode(OrderValidationCode.UNDEFINED);

        // Validate card number
        if (validateCardNumberError(orderToValidate.getCreditCardInformation().getCreditCardNumber())){
            orderToValidate.setOrderValidationCode(OrderValidationCode.CARD_NUMBER_INVALID);
        }

        // Validate expiry date
        if (validateExpiryDateError(orderToValidate.getCreditCardInformation().getCreditCardExpiry())){
            orderToValidate.setOrderValidationCode(OrderValidationCode.EXPIRY_DATE_INVALID);
        }

        // Validate CVV
        if (validateCvvError(orderToValidate.getCreditCardInformation().getCvv())){
            orderToValidate.setOrderValidationCode(OrderValidationCode.CVV_INVALID);
        }

        // Validate order total
        if (validateOrderTotalError(orderToValidate.getPriceTotalInPence(), orderToValidate.getPizzasInOrder())){
            orderToValidate.setOrderValidationCode(OrderValidationCode.TOTAL_INCORRECT);
        }

        // Validate pizza not defined
        if(validatePizzaNotDefinedError(orderToValidate.getPizzasInOrder(), definedRestaurants)){
            orderToValidate.setOrderValidationCode(OrderValidationCode.PIZZA_NOT_DEFINED);
        }

        // Validate max pizza count
        if(validateMaxPizzaCountError(orderToValidate.getPizzasInOrder())){
            orderToValidate.setOrderValidationCode(OrderValidationCode.MAX_PIZZA_COUNT_EXCEEDED);
        }

        // Validate all from one menu
        if(validateMultipleRestaurantsError(orderToValidate.getPizzasInOrder(), definedRestaurants)){
            orderToValidate.setOrderValidationCode(OrderValidationCode.PIZZA_FROM_MULTIPLE_RESTAURANTS);
        }

        // Validate restaurant open
        if(validateRestaurantClosedError(orderToValidate, definedRestaurants)){
            orderToValidate.setOrderValidationCode(OrderValidationCode.RESTAURANT_CLOSED);
        }

        if(orderToValidate.getOrderValidationCode() == OrderValidationCode.UNDEFINED){
            orderToValidate.setOrderValidationCode(OrderValidationCode.NO_ERROR);
        }
        return orderToValidate;
    }

    // Error functions return true if error was detected, i.e. if validation failed
    private boolean validateCardNumberError(String cardNumber){

        return cardNumber.length() != 16;

    }

    private boolean validateExpiryDateError(String expiryDate) {
        // Define a regular expression pattern for "MM/yy" format
        String pattern = "^(0[1-9]|1[0-2])/\\d{2}$";

        // Check if the month in the expiry date is valid (not over 12)
        int month = Integer.parseInt(expiryDate.split("/")[0]);
        if (month > 12) {
            // Invalid month, return false or handle the error accordingly
            return true;
        }

        // Use YearMonth to parse the month and year without a specific day
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        YearMonth expiryYearMonth = YearMonth.parse(expiryDate, formatter);

        // Get the current month and year
        YearMonth currentYearMonth = YearMonth.now();

        return expiryYearMonth.isBefore(currentYearMonth) || !expiryDate.matches(pattern);
    }

    private boolean validateCvvError(String cvv){

        return cvv.length() != 3;

    }

    private boolean validateOrderTotalError(int orderTotal, Pizza[] pizzas){

        var totalCount = 0;

        for (Pizza pizza : pizzas) {
            totalCount += pizza.priceInPence();
        }

        //Add delivery fee
        totalCount += 100;

        return orderTotal != totalCount;

    }

    private boolean validatePizzaNotDefinedError(Pizza[] pizzas, Restaurant[] restaurants) {
        int totalPizzas = 0;

        // Calculate the total number of pizzas
        for (Restaurant restaurant : restaurants) {
            totalPizzas += restaurant.menu().length;
        }

        // Initialize the definedPizzas array with the calculated size
        Pizza[] definedPizzas = new Pizza[totalPizzas];

        int currentIndex = 0;

        for (Restaurant restaurant : restaurants) {
            System.arraycopy(restaurant.menu(), 0, definedPizzas, currentIndex, restaurant.menu().length);
            currentIndex += restaurant.menu().length;
        }

        for (Pizza pizza : pizzas) {
            boolean isDefined = false;
            for (Pizza definedPizza : definedPizzas) {

                // Will set isDefined to true if pizza is in definedPizzas
                if (definedPizza.name().equals(pizza.name())) {
                    isDefined = true;
                    break;
                }
            }
            if (!isDefined) {
                return true;
            }
        }
        return false;
    }

    private boolean validateMaxPizzaCountError(Pizza[] pizzas){

        return !(pizzas.length > 0 && pizzas.length <= 4);

    }

    private boolean validateMultipleRestaurantsError(Pizza[] pizzas, Restaurant[] restaurants) {
        int numberOfRestaurants = 0;

        for (Restaurant restaurant : restaurants) {
            boolean foundInCurrentRestaurant = false;

            for (Pizza pizza : pizzas) {
                for (Pizza p : restaurant.menu()) {
                    if (pizza.name().equals(p.name())) {
                        foundInCurrentRestaurant = true;
                        break;
                    }
                }

                if (foundInCurrentRestaurant) {
                    break;
                }
            }

            if (foundInCurrentRestaurant) {
                numberOfRestaurants++;
                if (numberOfRestaurants > 1) {
                    return true; // Found in more than one restaurant
                }
            }
        }

        return false; // Found in only one restaurant or no restaurants
    }



    private boolean validateRestaurantClosedError(Order order, Restaurant[] restaurants){
        LocalDate dateOfOrder = order.getOrderDate();

        // Create array of menus
        Pizza[][] menus = new Pizza[restaurants.length][];

        for (int i = 0; i < restaurants.length; i++){
            menus[i] = restaurants[i].menu();
        }

        // Establish restaurant
        int currentRestaurantPos = -1; // Initialize to an invalid value
        for (int i = 0; i < menus.length; i++) {
            for (int j = 0; j < menus[i].length; j++) {
                Pizza pizza = menus[i][j];
                if (pizza.name().equals(order.getPizzasInOrder()[0].name())) { // Assuming orderPizzaName is the name of the pizza in the order
                    currentRestaurantPos = i;
                    break;
                }
            }
            if (currentRestaurantPos != -1) {
                break; // Break out of the outer loop once a match is found
            }
        }

        if (currentRestaurantPos != -1) {
            Restaurant currentRestaurant = restaurants[currentRestaurantPos];

            DayOfWeek[] openDays = currentRestaurant.openingDays();

            for (DayOfWeek openDay : openDays) {
                if (openDay == dateOfOrder.getDayOfWeek()) {
                    return false;
                }
            }
        } else {
            // Handle the case when the pizza is not found in any menu
            return true;
        }
        return true;

    }


}
