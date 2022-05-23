package com.keviron.cmms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/fxml/Main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1100, 650);

        scene.getStylesheets().add(getClass().getResource("/css/Main.css").toExternalForm());
        scene.setFill(Color.TRANSPARENT);

        stage.setResizable(false);
        stage.setTitle("CMMS - 2022 LTS!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}