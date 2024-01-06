package org.example.Controller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class OpenAIController {

    /*
    public static void main(String[] args) {
        ArrayList<String> reviews = new ArrayList<>();
        reviews.add("Such an amazing experience! At one point I was looking through the drink book( I say book because it’s at least 20 pages!) and was struggling to find the by the glass wine list, an employee noticed and was by my side asking if I needed any help in seconds. Our server was super knowledgeable and was able to make several spot on recommendations. It was my 13 yr old daughter’s first experience at a restaurant like this and she was blown away. I told her before hand things like notice how when the servers reach in front of you, their face will always be facing you, in between each course they will clear out silverware and bring us the exact utensils we need for our next course. Etc. When we arrived, she was looking for all of those things and she kept saying dad look how they cleared out plates, they were always facing us and cleared our  plates all at the same time! Thanks for making her experience incredible. It’s the little things. Not to mention the food was off the charts. Such thoughtful pairing of flavor profiles and textures in each dish. Must try!");
        ArrayList<String> AIresponse = getAIReviews(reviews);

        System.out.println("Response 1");
        System.out.println(AIresponse.get(0));

        System.out.println("Response 2");
        System.out.println(AIresponse.get(1));

        System.out.println("Response 3");
        System.out.println(AIresponse.get(2));
    }

     */

    public static ArrayList<String> getAIReviews(ArrayList<String> reviews) {
        ArrayList<String> AIReviews = new ArrayList<>();

        String promptOne = "Based on the following reviews, write 5 strengths this business has. Write only the 5 points, with the format [number] + [.], nothing else: ";
        String promptTwo = "Based on the reviews, write 5 weaknesses this business has. Write only the 5 points, with the format [number] + [.], nothing else: ";
        String promptThree = "Based on the reviews, write 5 action points to improve the business. Write only the 5 points, with the format [number] + [.], nothing else: ";

        AIReviews.add(chatGPT(promptOne + reviews.toString()));
        AIReviews.add(chatGPT(promptTwo + reviews.toString()));
        AIReviews.add(chatGPT(promptThree + reviews.toString()));

        return AIReviews;
    }

    public static String chatGPT(String message) {
        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = ""; //Write API-Key here
        String model = "gpt-3.5-turbo";

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

            return extractContentFromResponse(response.toString());

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