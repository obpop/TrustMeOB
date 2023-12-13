package org.example;

import io.javalin.Javalin;
import org.example.Controller.ReviewController;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Javalin app = Javalin.create().start(7000);
        app.get("/", ReviewController::getReviewForPlace);

    }
}
