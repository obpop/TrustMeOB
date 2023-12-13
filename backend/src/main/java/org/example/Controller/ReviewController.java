package org.example.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ReviewController {
    public static String getRequest() throws IOException {
        URL urlForGetRequest = new URL("https://jsonplaceholder.typicode.com/posts/1"); //change maybe
        String readLine = null;
        HttpURLConnection connection = (HttpURLConnection) urlForGetRequest.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("userId", "a1bcdef"); //add later
        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((readLine = in.readLine()) != null) {
                response.append(readLine);
            } in.close();
            //print out result
            return response.toString();
        } else {
            System.out.println("GET: NOT WORKING");
            return "Error: " + responseCode;
        }
    }
}
