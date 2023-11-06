package uk.ac.ed.inf;

import uk.ac.ed.inf.ilp.constant.OrderValidationCode;
import uk.ac.ed.inf.ilp.data.Order;
import uk.ac.ed.inf.ilp.data.Pizza;
import uk.ac.ed.inf.ilp.data.Restaurant;
import uk.ac.ed.inf.ilp.interfaces.OrderValidation;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
public class OrderValidator implements OrderValidation {

    public Order validateOrder(Order orderToValidate, Restaurant[] definedRestaurants) {

        // Initialise validation code
        orderToValidate.setOrderValidationCode(OrderValidationCode.UNDEFINED);

        // Validate card number
        if (validateCardNumberError(orderToValidate.getCreditCardInformation().getCreditCardNumber())){
            orderToValidate.setOrderValidationCode(OrderValidationCode.CARD_NUMBER_INVALID);
            return orderToValidate;
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

        return orderToValidate;
    }
    // Error functions return true if error was detected, i.e. if validation failed
    private boolean validateCardNumberError(String cardNumber){

        return cardNumber.length() != 16;

    }

    private boolean validateExpiryDateError(String expiryDate){

        // Define a regular expression pattern for "mm/yy" format
        String pattern = "^(0[1-9]|1[0-2])/\\d{2}$";


        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        LocalDate date = LocalDate.parse(expiryDate, formatter);

        return date.isBefore(currentDate) || !expiryDate.matches(pattern);
    }

    private boolean validateCvvError(String cvv){

        return cvv.length() != 3;

    }

    private boolean validateOrderTotalError(int orderTotal, Pizza[] pizzas){

        var totalCount = 0;

        for (Pizza pizza : pizzas) {
            totalCount += pizza.priceInPence();
        }

        return orderTotal != totalCount;

    }

    private boolean validatePizzaNotDefinedError(Pizza[] pizzas, Restaurant[] restaurants){
        int totalLength = 0;

       for (Restaurant restaurant : restaurants){
            totalLength += restaurant.menu().length;
       }

       Pizza[] definedPizzas = {};
       int currentIndex = 0;

        for (Restaurant restaurant : restaurants){
            System.arraycopy(restaurant.menu(), 0, definedPizzas, currentIndex, restaurant.menu().length);
            currentIndex += restaurant.menu().length;
        }

        for (Pizza pizza : pizzas){
            boolean isDefined = false;
            for (Pizza definedPizza : definedPizzas){

                // Will set isDefined to true if pizza is in definedPizzas
                if (definedPizza == pizza){
                    isDefined = true;
                }
            }
            if (!isDefined){
                return true;
            }
        }
        return false;
    }

    private boolean validateMaxPizzaCountError(Pizza[] pizzas){

        if (!(pizzas.length > 0 && pizzas.length <= 4)){
            return true;
        }
        else{
            return false;
        }

    }

    private boolean validateMultipleRestaurantsError(Pizza[] pizzas, Restaurant[] restaurants){

        // Create array of menus
        Pizza[][] menus = new Pizza[restaurants.length][];

        for (int i = 0; i < restaurants.length; i++){
            menus[i] = restaurants[i].menu();
        }

        Pizza[] currentMenu = null;

        // Establish current menu
        for (Pizza[] menu : menus){
            for (Pizza pizza : menu){
                if(pizza.name().equals(pizzas[0].name())){
                    currentMenu = menu;
                    break;
                }
            }
        }

        // find out if all pizzas in order come from one menu, if not return error
        for (Pizza pizza : pizzas){
            boolean found = false;
            assert currentMenu != null;
            for (Pizza p : currentMenu){
                if (pizza.name().equals(p.name())){
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    private boolean validateRestaurantClosedError(Order order, Restaurant[] restaurants){
        LocalDate dateOfOrder = order.getOrderDate();

        // Create array of menus
        Pizza[][] menus = new Pizza[restaurants.length][];

        for (int i = 0; i < restaurants.length; i++){
            menus[i] = restaurants[i].menu();
        }

        // Establish restaurant
        int currentRestaurantPos = 0;
        for (int i = 0; i < menus.length; i++){
            for (int j = 0; j < menus[i].length; j++){
                Pizza[] menu = menus[i];
                Pizza pizza = menu[j];

                if(pizza.name().equals(menu[i].name())){
                    currentRestaurantPos= i;
                    break;
                }
            }
        }

        Restaurant currentRestaurant = restaurants[currentRestaurantPos];

        // Retrieve open days of restaurant

        DayOfWeek [] openDays = currentRestaurant.openingDays();

        boolean isOpen = false;
        // Check if day of order is within open days
        for (DayOfWeek openDay : openDays){
            if (openDay == dateOfOrder.getDayOfWeek()){
                isOpen = true;
                break;
            }
        }

        return isOpen;

    }


}
