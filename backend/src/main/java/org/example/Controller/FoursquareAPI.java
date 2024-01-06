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

public class FoursquareAPI {
    public static void getFoursquareTips(Context ctx, String fsq_id, String name, JsonObject foursquareJson) throws IOException, InterruptedException {
        String API_KEY = "";
        String text = "";
        int counter = 1;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.foursquare.com/v3/places/" + fsq_id + "/tips?limit=5&sort=POPULAR"))
                .header("accept", "application/json")
                .header("Authorization", API_KEY)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response =
                HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            JsonArray resultsArray = JsonParser.parseString(response.body()).getAsJsonArray();
            JsonArray textArray = new JsonArray();

            for (JsonElement resultElement : resultsArray) {
                JsonObject resultObject = resultElement.getAsJsonObject();
                text = resultObject.get("text").getAsString();
                textArray.add(text);

                System.out.println("Review" + counter + ": " + text);
                counter++;
            }
            System.out.println();

            foursquareJson.get("foursquare").getAsJsonObject().addProperty("fsq_id", fsq_id);
            foursquareJson.get("foursquare").getAsJsonObject().addProperty("name", name);
            foursquareJson.get("foursquare").getAsJsonObject().add("texts", textArray);

            //System.out.println(foursquareJson.toString());
            ctx.result(foursquareJson.toString());

        } else {
            System.out.println("GET: NOT WORKING");
        }
    }

    public static void getFoursquarePlaces(Context ctx, JsonObject json) throws IOException, InterruptedException {
        String API_KEY = "";

        //Get the info from ReviewController json

        String name = json.get("google").getAsJsonObject().get("name").getAsString(); //TODO OBS! Grand Hotel Lund

        //  String cc = ""; //TODO OBS! SE
        double northEastLAT = json.get("google").getAsJsonObject().get("northEastLat").getAsDouble(); //55.70521002989272
        double northEastLNG = json.get("google").getAsJsonObject().get("northEastLng").getAsDouble();; //13.19029362989272

        double southWestLAT = json.get("google").getAsJsonObject().get("southWestLat").getAsDouble(); //55.70251037010727
        double southWestLNG = json.get("google").getAsJsonObject().get("southWestLng").getAsDouble(); //13.18759397010728

        String fsq_id = "";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        "https://api.foursquare.com/v3/places/search?query=" + formatName(name) +"&ne=" + northEastLAT + "%2C" + northEastLNG +
                                "&sw=" + southWestLAT + "%2C" + southWestLNG + "&limit=1"))
                .header("accept", "application/json")
                .header("Authorization", API_KEY)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
            JsonArray results = jsonObject.getAsJsonArray("results");
            if (results != null && results.size() > 0) {
                JsonObject firstResult = results.get(0).getAsJsonObject();
                fsq_id = firstResult.get("fsq_id").getAsString();
                name = firstResult.get("name").getAsString();

                System.out.println("(FoursquareAPI) fsq_id: " + fsq_id);
                System.out.println("Company-name: " + name);

                ctx.result(fsq_id + ", " + name);
                getFoursquareTips(ctx, fsq_id, name, json);
            } else {
                System.out.println("NO RESULTS FOUND");
                ctx.result("Inga resultat hittades");
            }
        } else {
            System.out.println("GET: NOT WORKING");
            ctx.status(500).result("Internal Server Error");
        }
    }

    public static String formatName(String name) {
        String formattedName = name.replace(" ", "%20");
        return formattedName;
    }

}
