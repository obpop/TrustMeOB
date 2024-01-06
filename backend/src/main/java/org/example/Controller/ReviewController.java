package org.example.Controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.javalin.http.Context;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class ReviewController {
    public static void getReviewForPlace(Context ctx) throws IOException, InterruptedException {

        String pathPlaceName = "";

        String apiKey = "";
        String placeId = "";
        String textSearchUrl = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=grand+hotel+lund&key=" + apiKey;
        double lat = 0;
        double lng = 0;
        double northEastLat = 0;
        double northEastLng = 0;
        double southWestLat = 0;
        double southWestLng = 0;
        String name = "";
        String address = "";

        HttpResponse<String> response;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(textSearchUrl))
                .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        //Get the name of the place
        JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
        if (jsonObject.has("results")){
            JsonArray results = jsonObject.getAsJsonArray("results");

            for (JsonElement result : results){
                JsonObject resultObject = result.getAsJsonObject();
                name = resultObject.get("name").getAsString();
            }

            //Get the coordinates of the place
            lat = results.get(0).getAsJsonObject().get("geometry").getAsJsonObject().get("location").getAsJsonObject().get("lat").getAsDouble();
            lng = results.get(0).getAsJsonObject().get("geometry").getAsJsonObject().get("location").getAsJsonObject().get("lng").getAsDouble();

            //Get the address of the place
            address = results.get(0).getAsJsonObject().get("formatted_address").getAsString();

            //Get the coordinates of the place
            System.out.println("LAT-Google_Map: " + lat);
            System.out.println("LONG-Google_Map: " + lng);

            //Get the northwestern and southwestern coordinates of the place
            northEastLat = results.get(0).getAsJsonObject().get("geometry").getAsJsonObject().get("viewport").getAsJsonObject().get("northeast").getAsJsonObject().get("lat").getAsDouble();
            northEastLng = results.get(0).getAsJsonObject().get("geometry").getAsJsonObject().get("viewport").getAsJsonObject().get("northeast").getAsJsonObject().get("lng").getAsDouble();
            southWestLat = results.get(0).getAsJsonObject().get("geometry").getAsJsonObject().get("viewport").getAsJsonObject().get("southwest").getAsJsonObject().get("lat").getAsDouble();
            southWestLng = results.get(0).getAsJsonObject().get("geometry").getAsJsonObject().get("viewport").getAsJsonObject().get("southwest").getAsJsonObject().get("lng").getAsDouble();


        }

        //Get the place id
        if (jsonObject.has("results")){
            JsonArray results = jsonObject.getAsJsonArray("results");

            for (JsonElement result : results){
                JsonObject resultObject = result.getAsJsonObject();
                placeId = resultObject.get("place_id").getAsString();

                System.out.println("(GoogleAPI) PLACE_ID: " + placeId);
                System.out.println();
            }
        }

        //Get the reviews of the place
        JsonArray reviews = getReviews(ctx, placeId, apiKey, lat, lng);

        MapsController mapsController = new MapsController();
        String map = mapsController.handleMapCreation(new double[]{lat, lng});

        if (response.statusCode() == 200) {
            JsonObject json = new JsonObject();
            JsonObject google = new JsonObject();
            JsonObject foursquare = new JsonObject();


            google.addProperty("name", name);
            google.addProperty("placeId", placeId);
            google.addProperty("address", address);
            google.addProperty("lat", lat);
            google.addProperty("lng", lng);

            google.addProperty("northEastLat", northEastLat);
            google.addProperty("northEastLng", northEastLng);
            google.addProperty("southWestLat", southWestLat);
            google.addProperty("southWestLng", southWestLng);

            google.addProperty("placeId", placeId);
            google.addProperty("map", map);

            google.add("reviews", reviews);

            json.add("google", google);
            json.add("foursquare", foursquare);

            FoursquareAPI.getFoursquarePlaces(ctx, json);

            String jsonString = json.toString();
            System.out.println(jsonString);

            ctx.result(jsonString);

        } else {
            System.out.println("GET: NOT WORKING");
        }

    }

    //This method gets the reviews of the place using the place id
    public static JsonArray getReviews(Context ctx, String placeId, String apiKey, double lat, double lng){
        String detailsUrl = "https://maps.googleapis.com/maps/api/place/details/json?placeid=" + placeId + "&key=" + apiKey;
        JsonArray reviewsJsonArray = new JsonArray();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(detailsUrl))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = null;
        ArrayList<String> reviewsArray = new ArrayList<>();
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
            if (jsonObject.has("result")){
                JsonObject result = jsonObject.getAsJsonObject("result");
                if (result.has("reviews")){
                    JsonArray reviews = result.getAsJsonArray("reviews");
                    for (JsonElement review : reviews){
                        JsonObject reviewObject = review.getAsJsonObject();
                        String reviewText = reviewObject.get("text").getAsString();
                        reviewsArray.add(reviewText);

                    }
                }
            }

            //Get the reviews of the place
            for (String review : reviewsArray){
                reviewsJsonArray.add(review);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return reviewsJsonArray;
    }

}