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
    public static void getFoursquareTips(Context ctx, String fsq_id, String name) throws IOException, InterruptedException {
        String API_KEY = "fsq3ye3chqxy2q+YePOCTGY5FGCdRtvVcWcjSb4oWUXe0t0="; //TODO OBS! fsq3ye3chqxy2q+YePOCTGY5FGCdRtvVcWcjSb4oWUXe0t0=
        // fsq_id = "4b6289b9f964a520bc4a2ae3"; //TODO OBS! 5a187743ccad6b307315e6fe
        String text = "";

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

                System.out.println("Review: " + text);
            }

            JsonObject json = new JsonObject();
            json.addProperty("fsq_id", fsq_id);
            json.addProperty("name", name);
            json.add("texts", textArray);

            String jsonString = json.toString();
            System.out.println(jsonString);
            ctx.result(jsonString);
        } else {
            System.out.println("GET: NOT WORKING");
        }

    }

    public static void getFoursquarePlaces(Context ctx) throws IOException, InterruptedException {
        String API_KEY = "fsq3ye3chqxy2q+YePOCTGY5FGCdRtvVcWcjSb4oWUXe0t0="; //TODO OBS! fsq3ye3chqxy2q+YePOCTGY5FGCdRtvVcWcjSb4oWUXe0t0=

        //Get the info from ReviewController json


        String name = ""; //TODO OBS! Grand Hotel Lund
      //  String address = ""; //TODO OBS! Bantorget 1
      //  String city = ""; //TODO OBS! Lund
      //  String cc = ""; //TODO OBS! SE
        double northEastLAT = 00; //55.70521002989272
        double northEastLNG = 00; //13.19029362989272

        double southWestLAT = 00; //55.70251037010727
        double southWestLNG = 00; //13.18759397010728

        String fsq_id = "";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                       // "https://api.foursquare.com/v3/places/search?query=grand%20hotel%20lund&" +
                       //         "ne=" + northEastLAT +  "%2C" + northEastLNG + "&sw=" + southWestLAT +"%2C" + southWestLNG +"&limit=1"


                        "https://api.foursquare.com/v3/places/search?query=grand%20hotel%20lund&" +
                        "ne=55.70521002989272%2C13.19029362989272&sw=55.7025103701072%2C13.18759397010728&limit=1"))
                        //TODO fixa att det går att söka på ett ställe + ne och sw parameterar från google places

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

                System.out.println("fsq_id: " + fsq_id);
                System.out.println("name: " + name);

                ctx.result(fsq_id + ", " + name);
                getFoursquareTips(ctx, fsq_id, name);
            } else {
                System.out.println("NO RESULTS FOUND");
                ctx.result("Inga resultat hittades");
            }
        } else {
            System.out.println("GET: NOT WORKING");
            ctx.status(500).result("Internal Server Error");
        }
    }

}
