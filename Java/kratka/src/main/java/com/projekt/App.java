package com.projekt;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        new GUI(stage);
    }

    public static void main(String[] args) {
        launch();
    }

}