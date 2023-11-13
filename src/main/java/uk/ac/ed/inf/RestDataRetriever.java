package uk.ac.ed.inf;
import com.google.gson.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import uk.ac.ed.inf.ilp.data.NamedRegion;
import uk.ac.ed.inf.ilp.data.Order;
import uk.ac.ed.inf.ilp.data.Restaurant;
import uk.ac.ed.inf.ilp.gsonUtils.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RestDataRetriever {


    /**
     *
     * @param uri the uri of the REST server
     * @return the CentralArea object containing its name and list of vertices
     */
    public NamedRegion getCentralArea(String uri) {

        uri = uri + "/centralArea"; //Set the endpoint for central area

        // Create an HttpClient and HttpGet object
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(uri);

        try {
            HttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                //jsonString contains JSON object
                String jsonString = EntityUtils.toString(response.getEntity());

                //Deserialise JSON String
                Gson gson = new Gson();
                return gson.fromJson(jsonString, NamedRegion.class);


            } else {
                System.err.println("HTTP Request failed with status code: " + statusCode);
                return null;

            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     *
     * @param uri the uri of the REST server
     * @return array of no-fly zones
     **/
    public NamedRegion[] getNoFlyZones(String uri) {

        uri = uri + "/noFlyZones"; //Set the endpoint for central area

        // Create an HttpClient and HttpGet object
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(uri);

        try {
            HttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                //jsonString contains JSON object
                String jsonString = EntityUtils.toString(response.getEntity());

                //Deserialise JSON String
                Gson gson = new Gson();
                return gson.fromJson(jsonString, NamedRegion[].class);


            } else {
                System.err.println("HTTP Request failed with status code: " + statusCode);
                return null;

            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     *
     * @param uri the uri of the REST server
     * @return array of participating restaurants
     */
    public Restaurant[] getRestaurants(String uri) {

        uri = uri + "/restaurants"; //Set the endpoint for central area

        // Create an HttpClient and HttpGet object
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(uri);

        try {
            HttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                //jsonString contains JSON object
                String jsonString = EntityUtils.toString(response.getEntity());

                //Deserialise JSON String
                Gson gson = new Gson();
                return gson.fromJson(jsonString, Restaurant[].class);


            } else {
                System.err.println("HTTP Request failed with status code: " + statusCode);
                return null;

            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static List<Order> getOrders(String uri, String date) {
        uri = uri + "/orders"; // Set the endpoint for central area

        // Create a Gson instance with the custom serializer and deserializer
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .create();

        // Create an HttpClient and HttpGet object
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(uri);

        try {
            HttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                // jsonString contains JSON object
                String jsonString = EntityUtils.toString(response.getEntity());

                // Parse JSON data
                JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

                // Filter objects with the specified date
                List<JsonElement> filteredOrders = new ArrayList<>();
                for (JsonElement element : jsonArray) {
                    JsonObject orderObject = element.getAsJsonObject();

                    // Check if the key "orderDate" exists and the value is not null
                    if (orderObject.has("orderDate")) {
                        try {
                            JsonElement orderDateElement = orderObject.get("orderDate");

                            // Check if the value is not null
                            if (!orderDateElement.isJsonNull()) {
                                String orderDateString = orderDateElement.getAsString();

                                // Parse the date string into LocalDate
                                LocalDate orderDate = LocalDate.parse(orderDateString, DATE_FORMATTER);

                                // Check if the parsed date matches the desired date
                                if (orderDate.toString().equals(date)) {
                                    filteredOrders.add(element);
                                }
                            }
                        } catch (Exception e) {
                            // Print any exception that occurs during parsing
                            System.err.println("Error parsing order date: " + e.getMessage());
                        }
                    }
                }

                // Convert the filtered orders to Java objects
                List<Order> orders = new ArrayList<>();
                for (JsonElement element : filteredOrders) {
                    try {
                        Order order = gson.fromJson(element, Order.class);
                        orders.add(order);
                    } catch (Exception e) {
                        // Print any exception that occurs during object conversion
                        System.err.println("Error converting to Order object: " + e.getMessage());
                    }
                }

                return orders;
            } else {
                System.err.println("HTTP Request failed with status code: " + statusCode);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            HttpClientUtils.closeQuietly(httpClient);
        }
    }
}
