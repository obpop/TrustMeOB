package org.example.Controller;

import io.javalin.http.Context;

public class MapsController {
    //TODO skriv in API key nedan
    String APIKey = "";

    //Test metod nedan
    //TODO: Ta bort när resten av koden fungerar
    public void testMapCreation(Context ctx) {
        double[] testCoordinates = new double[]{55.61427531137008,12.989121348455722};
        handleMapCreation(testCoordinates, ctx);
    }

    // Tar emot en array med latitude och longitude
    public void handleMapCreation(double[] coordinates, Context ctx) {
        if (coordinates != null && coordinates.length == 2) {
            String mapUrl = generateStaticMapUrl(coordinates[0], coordinates[1]);
            ctx.result(mapUrl);
            // TODO: Skicka mapURL till front-end
        } else {
            System.out.println("OBS! Did not enter coordinates correctly");
        }
    }

    //Genererar en statisk bild
    private String generateStaticMapUrl(double latitude, double longitude) {
        // URL för Google Static Maps API
        return "https://maps.googleapis.com/maps/api/staticmap?center="
                + latitude
                + ","
                + longitude
                + "&zoom=16&size=400x400&key="
                + APIKey;

        //ex https://maps.googleapis.com/maps/api/staticmap?center=55.61427531137008,12.989121348455722&zoom=16&size=400x400&key=AIzaSyBScQfyRncKpYdtwSIuNFAumA30mGOIdpc
    }

}