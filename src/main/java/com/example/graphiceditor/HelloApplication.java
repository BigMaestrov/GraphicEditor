package com.example.graphiceditor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(HelloApplication.class.getResource("hello-view.fxml"));
        AnchorPane root = fxmlLoader.load();
        HelloController helloController = fxmlLoader.getController();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("GraphicEditor");
        stage.setScene(scene);
        helloController.setPrimaryStage(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}