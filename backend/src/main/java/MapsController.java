import io.javalin.http.Context;

public class MapsController {
    //private static final String API_KEY = System.getenv("API_GOOGLE_KEY");
    private static final String API_KEY = "AIzaSyDM_eCDztfCtz3tEb1XdJF_vwQXavvFYEc";

    // Tar emot en array med latitude och longitude
    public String handleMapCreation(double[] coordinates) {
        if (coordinates == null || coordinates.length != 2) {
            return "Error: False coordinates";
        } if (API_KEY == null || API_KEY.isEmpty()) {
            return "Error: API Key is not configured correctly";
        } else {
            try {
                String mapUrl = generateStaticMapUrl(coordinates[0], coordinates[1]);
                return mapUrl;
            } catch (Exception e) {
                return "Error: Internal server problem: " + e.getMessage();
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
                + API_KEY;
    }

}