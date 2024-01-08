package org.example.Controller;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.javalin.http.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class OpenAIController {

    public static void chatGPT(Context ctx) {
        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = ""; //Write API-Key here
        String model = "gpt-3.5-turbo";
        String jsonBody = ctx.body();
        JsonObject jsonObject = JsonParser.parseString(jsonBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + message.replace("\n", "") + "\"}]}"))
                .build();


        try{
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String responseBody = response.body();
            JsonObject object = JsonParser.parseString(responseBody).getAsJsonObject();

            System.out.println(object.getAsJsonObject().toString());

            String stringResponse = object.getAsJsonObject().get("choices").getAsJsonArray().get(0).getAsJsonObject().get("message").getAsJsonObject().get("content").getAsString();

            JsonObject json = new JsonObject();
            json.addProperty("response", stringResponse);

            ctx.json(json.toString());

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void test(Context ctx) {
        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = ""; //Write API-Key here
        String model = "gpt-3.5-turbo";
        String jsonBody = ctx.body();
        JsonObject jsonObject = JsonParser.parseString(jsonBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();

        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Bearer " + apiKey);
            con.setRequestProperty("Content-Type", "application/json");

            String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + message + "\"}]}";
            con.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JsonObject json = new JsonObject();
            json.addProperty("response", extractContentFromResponse(response.toString()));

            ctx.json(json.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String extractContentFromResponse(String response) {
        int startMarker = response.indexOf("content")+11; // Marker for where the content starts.
        int endMarker = response.indexOf("\"", startMarker); // Marker for where the content ends.
        return response.substring(startMarker, endMarker); // Returns the substring containing only the response.
    }
}