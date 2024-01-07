package org.example;

import io.javalin.Javalin;
import org.example.Controller.FoursquareAPI;
import org.example.Controller.MapsController;
import org.example.Controller.OpenAIController;
import org.example.Controller.ReviewController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    public static void main(String[] args) throws IOException {
        Javalin app = Javalin.create(config -> {
            config.plugins.enableCors(cors -> {
                cors.add(it -> {
                    it.anyHost();
                });
            });
        }).start(8080);

        app.get("/places", ReviewController::getReviewForPlace);
    }
}
