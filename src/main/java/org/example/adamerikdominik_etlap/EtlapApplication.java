package org.example.adamerikdominik_etlap;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EtlapApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EtlapApplication.class.getResource("etlapList-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 650);
        stage.setTitle("Étlap!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}