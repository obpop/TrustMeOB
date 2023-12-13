package org.example.Controller;

import io.javalin.http.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ReviewController {
    public static void getReviewForPlace(Context ctx) throws IOException, InterruptedException {

        String url = "https://api.yelp.com/v3/businesses/manhatta-new-york/reviews?limit=20&sort_by=yelp_sort";

        HttpResponse<String> response;
        try (HttpClient client = HttpClient.newHttpClient()) {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Bearer " + apiKey)
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }

        if (response.statusCode() == 200) {
            ctx.json(response.body());
        } else {
            System.out.println("GET: NOT WORKING");
        }
    }
}
