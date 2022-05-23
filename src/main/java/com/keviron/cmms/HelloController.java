package com.keviron.cmms;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    private int am = 1;

    @FXML
    private AnchorPane MenuPanel;

    @FXML
    private Button clientsButton;
    @FXML
    private Button homeButton;

    @FXML
    private Button repairsButton;

    @FXML
    private StackPane ContentPanel;

    @Override
    public void initialize(URL url, ResourceBundle rb)  {

    }


    @FXML
    public void switchToRepairs(ActionEvent evt) throws IOException {
        if (am == 2) {return;}
        am = 2;

        FadeTransition FadeOut = new FadeTransition(Duration.millis(500), ContentPanel);
        makeFadeTransition(FadeOut, 1.0, 0.0);

        FadeOut.setOnFinished((e) -> {
            try {
                ContentPanel.getChildren().clear();
                ContentPanel.getChildren().add(FXMLLoader.load(getClass().getResource("/fxml/Repairs.fxml")));

                FadeTransition FadeIn = new FadeTransition(Duration.millis(500), ContentPanel);
                makeFadeTransition(FadeIn, 0.0, 1.0);

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

    }

    @FXML
    public void switchToClients(ActionEvent evt) throws IOException {
        if (am == 2) {return;}
        am = 2;

        FadeTransition FadeOut = new FadeTransition(Duration.millis(500), ContentPanel);
        makeFadeTransition(FadeOut, 1.0, 0.0);

        FadeOut.setOnFinished((e) -> {
            try {
                ContentPanel.getChildren().clear();
                ContentPanel.getChildren().add(FXMLLoader.load(getClass().getResource("/fxml/Clients.fxml")));

                FadeTransition FadeIn = new FadeTransition(Duration.millis(500), ContentPanel);
                makeFadeTransition(FadeIn, 0.0, 1.0);

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

    }

    private void makeFadeTransition(FadeTransition fade, double StartValue , double EndValue){
        fade.setFromValue(StartValue);
        fade.setToValue(EndValue);
        fade.play();
    }
}