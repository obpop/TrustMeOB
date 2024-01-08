package org.example.Controller;

import com.google.gson.*;
import io.javalin.http.Context;
import org.example.ConfigLoader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class ReviewController {
    private static final String API_KEY_GOOGLE;

    static {
        ConfigLoader loaderGoogle = new ConfigLoader();
        API_KEY_GOOGLE = loaderGoogle.getApiKey("google.api.key");
    }

    private static final String API_KEY_AI;
    static {
        ConfigLoader loaderAI = new ConfigLoader();
        API_KEY_AI = loaderAI.getApiKey("openai.api.key");
    }

    public static void getReviewForPlace(Context ctx) throws IOException, InterruptedException {
        String jsonBody = ctx.body();
        JsonObject nameObject = JsonParser.parseString(jsonBody).getAsJsonObject();
        String companyName = nameObject.get("name").getAsString();
        companyName = formatSpacesToURL(companyName);

        System.out.println("COMPANY NAME: " + companyName);

        // String apiKey = "";
        String placeId = "";
        String textSearchUrl = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=" + companyName + "&key=" + API_KEY_GOOGLE;
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
        if (jsonObject.has("results")) {
            JsonArray results = jsonObject.getAsJsonArray("results");

            for (JsonElement result : results) {
                JsonObject resultObject = result.getAsJsonObject();
                name = resultObject.get("name").getAsString();
            }

            //Get the coordinates of the place
            lat = results.get(0).getAsJsonObject().get("geometry").getAsJsonObject().get("location").getAsJsonObject().get("lat").getAsDouble();
            lng = results.get(0).getAsJsonObject().get("geometry").getAsJsonObject().get("location").getAsJsonObject().get("lng").getAsDouble();

            //Get the address of the place
            address = results.get(0).getAsJsonObject().get("formatted_address").getAsString();


            //Get the northwestern and southwestern coordinates of the place
            northEastLat = results.get(0).getAsJsonObject().get("geometry").getAsJsonObject().get("viewport").getAsJsonObject().get("northeast").getAsJsonObject().get("lat").getAsDouble();
            northEastLng = results.get(0).getAsJsonObject().get("geometry").getAsJsonObject().get("viewport").getAsJsonObject().get("northeast").getAsJsonObject().get("lng").getAsDouble();
            southWestLat = results.get(0).getAsJsonObject().get("geometry").getAsJsonObject().get("viewport").getAsJsonObject().get("southwest").getAsJsonObject().get("lat").getAsDouble();
            southWestLng = results.get(0).getAsJsonObject().get("geometry").getAsJsonObject().get("viewport").getAsJsonObject().get("southwest").getAsJsonObject().get("lng").getAsDouble();
        }

        //Get the place id
        if (jsonObject.has("results")) {
            JsonArray results = jsonObject.getAsJsonArray("results");

            for (JsonElement result : results) {
                JsonObject resultObject = result.getAsJsonObject();
                placeId = resultObject.get("place_id").getAsString();

                System.out.println("(GoogleAPI) PLACE_ID: " + placeId);
                System.out.println();
            }
        }

        //Get the reviews of the place
        JsonArray reviews = getReviews(ctx, placeId, API_KEY_GOOGLE, lat, lng);

        //OpenAI sending of reviews
        ArrayList<String> openAIResponse = new ArrayList<>();
        StringBuilder reviewsString = new StringBuilder();

        for (JsonElement review : reviews) {
            String reviewText = review.getAsString();
            openAIResponse.add(reviewText);
            reviewsString.append(reviewText).append(" ");
        }


        ArrayList<String> AIReview = getAIReviews(openAIResponse);


        MapsController mapsController = new MapsController();
        String map = mapsController.handleMapCreation(new double[]{lat, lng});

        if (response.statusCode() == 200) {
            JsonObject json = new JsonObject();
            JsonObject google = new JsonObject();
            JsonObject foursquare = new JsonObject();
            JsonObject openAI = new JsonObject();


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

            //OpenAI properties
            openAI.addProperty("strengths", AIReview.get(0));
            openAI.addProperty("weaknesses", AIReview.get(1));
            openAI.addProperty("action_points", AIReview.get(2));

            json.add("google", google);
            json.add("foursquare", foursquare);
            json.add("openAI", openAI);


            FoursquareAPI.getFoursquarePlaces(ctx, json);


            String jsonString = json.toString();
            System.out.println(jsonString);

            ctx.result(jsonString);

        } else {
            System.out.println("GET: NOT WORKING");
        }

    }

    //This method gets the reviews of the place using the place id
    public static JsonArray getReviews(Context ctx, String placeId, String API_KEY, double lat, double lng) {
        String detailsUrl = "https://maps.googleapis.com/maps/api/place/details/json?placeid=" + placeId + "&key=" + API_KEY;
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
            if (jsonObject.has("result")) {
                JsonObject result = jsonObject.getAsJsonObject("result");
                if (result.has("reviews")) {
                    JsonArray reviews = result.getAsJsonArray("reviews");
                    for (JsonElement review : reviews) {
                        JsonObject reviewObject = review.getAsJsonObject();
                        String reviewText = reviewObject.get("text").getAsString();
                        reviewsArray.add(reviewText);

                    }
                }
            }

            //Get the reviews of the place
            for (String review : reviewsArray) {
                reviewsJsonArray.add(review);
            }


        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return reviewsJsonArray;
    }

    public static String chatGPT(String message) {
        String url = "https://api.openai.com/v1/chat/completions";
        String model = "gpt-3.5-turbo";

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + API_KEY_AI)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + message.replace("\n", "") + "\"}]}"))
                .build();


        try{
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String responseBody = response.body();
            JsonObject object = JsonParser.parseString(responseBody).getAsJsonObject();

            System.out.println(object.getAsJsonObject().toString());

            String stringResponse = object.getAsJsonObject().get("choices").getAsJsonArray().get(0).getAsJsonObject().get("message").getAsJsonObject().get("content").getAsString();

            return stringResponse;

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static String extractContentFromResponse(String response) {
        int startMarker = response.indexOf("content") + 11; // Marker for where the content starts.
        int endMarker = response.indexOf("\"", startMarker); // Marker for where the content ends.
        return response.substring(startMarker, endMarker); // Returns the substring containing only the response.
    }

    public static ArrayList<String> getAIReviews(ArrayList<String> reviews) {
        ArrayList<String> AIReviews = new ArrayList<>();
        StringBuilder reviewString = new StringBuilder();

        for (String review : reviews) {
            reviewString.append(review).append(" ");
        }

        String promptOne = "Based on the following reviews, write 5 strengths this business has. Write only the 5 points, with the format of number, nothing else: ";
        String promptTwo = "Based on the reviews, write 5 weaknesses this business has. Write only the 5 points, with the format, nothing else: ";
        String promptThree = "Based on the reviews, write 5 action points to improve the business. Write only the 5 points, with the format, nothing else: ";

        AIReviews.add(chatGPT(promptOne + reviewString));
        AIReviews.add(chatGPT(promptTwo + reviewString));
        AIReviews.add(chatGPT(promptThree + reviewString));

        return AIReviews;
    }

    public static String formatSpacesToURL(String string){
        return string.replace(" ", "%20");
    }

}