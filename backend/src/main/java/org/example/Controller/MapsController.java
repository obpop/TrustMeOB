package org.example.Controller;

import io.javalin.http.Context;

public class MapsController {
    String APIKey = ""; //Skriv in API key nedan

    //Test metod nedan
    //TODO: Ta bort när resten av koden fungerar
    public void testMapCreation(Context ctx) {
        double[] testCoordinates = new double[]{55.61427531137008,12.989121348455722};
        handleMapCreation(testCoordinates, ctx);
    }

    // Tar emot en array med latitude och longitude
    public void handleMapCreation(double[] coordinates, Context ctx) {
        if (coordinates == null || coordinates.length != 2) {
            ctx.status(400).result("400: False coordinates");
            return;
        } else if (APIKey == null || APIKey.isEmpty()) {
            ctx.status(503).result("503: API Key is not configured correctly");
            return;
        } else {
            try {
                String mapUrl = generateStaticMapUrl(coordinates[0], coordinates[1]);
                ctx.result(mapUrl);
            } catch (Exception e) {
                ctx.status(500).result("500: Internal server problem: " + e.getMessage());
            }
        }
    }

    //Genererar en statisk bild
    private String generateStaticMapUrl(double latitude, double longitude) {
        //URL för Google Static Maps API
        //EX https://maps.googleapis.com/maps/api/staticmap?center=55.61427531137008,12.989121348455722&zoom=16&size=400x400&key=YOUR_API_KEY
        return "https://maps.googleapis.com/maps/api/staticmap?center="
                + latitude
                + ","
                + longitude
                + "&zoom=16&size=400x400&key="
                + APIKey;
    }

}