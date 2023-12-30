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

        String apiKey = "";
        String placeId = "";
        String textSearchUrl = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=grand+hotel+lund&key=" + apiKey;
        double lat = 0;
        double lng = 0;
        String name = "";

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

            //Get the coordinates of the place
            System.out.println(lat);
            System.out.println(lng);

        }

        //Get the place id
        if (jsonObject.has("results")){
            JsonArray results = jsonObject.getAsJsonArray("results");

            for (JsonElement result : results){
                JsonObject resultObject = result.getAsJsonObject();
                placeId = resultObject.get("place_id").getAsString();

                System.out.println("Place id: " + placeId);
                System.out.println();
            }
        }

        //Get the reviews of the place
        JsonArray reviews = getReviews(ctx, placeId, apiKey, lat, lng);

        MapsController mapsController = new MapsController();
        mapsController.handleMapCreation(new double[]{lat, lng}, ctx);

        if (response.statusCode() == 200) {
            JsonObject json = new JsonObject();
            json.addProperty("name", name);
            json.addProperty("lat", lat);
            json.addProperty("lng", lng);
            json.addProperty("placeId", placeId);
            json.add("reviews", reviews);

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
