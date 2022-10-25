package com.example.graphiceditor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private Button button1;

    private int n=0;

    @FXML
    protected void clearCanvas(ActionEvent actionEvent){
        System.out.println("Кнопка нажата");
        n++;
        button1.setText(""+n);
    }
}