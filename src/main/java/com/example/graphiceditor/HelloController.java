package com.example.graphiceditor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class HelloController {
    //Переменная задающая главную сцену приложения
    @FXML private Stage primaryStage;
    //Блок переменных для сохранения графических элементов полотна
    private ArrayList<Point2D> points = new ArrayList<Point2D>();
    private int indexLastPoint =-1;
    private boolean isPoint = false;

    //Блок переменных графических элементов интерфейса


    @FXML
    public Canvas canvas1;
    @FXML
    public ChoiceBox typeChoiceBox;
    @FXML
    public ChoiceBox colorChoiceBox;
    //Метод срабатывающий при старте программы
    @FXML
    protected void initialize(){
        //Заполнение полей typeChoiceBox
        ObservableList<String> types = FXCollections.observableArrayList("Прямая", "Куб Сплайн", "Треугольник", "Стрелка");
        typeChoiceBox.setItems(types);
        typeChoiceBox.setValue("Прямая");
        //Заполнение полей colorComboBox
        ObservableList<String> colors = FXCollections.observableArrayList("Черный", "Красный", "Синий", "Зеленый");
        colorChoiceBox.setItems(colors);
        colorChoiceBox.setValue("Черный");
    }

    //Метод срабатывающий при нажатии на кнопку "очистить поле"
    @FXML
    protected void clearCanvas(ActionEvent actionEvent) {
        isPoint = false;
        GraphicsContext context = canvas1.getGraphicsContext2D();
        context.setFill(Color.WHITE);
        context.fillRect(0, 0, canvas1.getWidth(), canvas1.getHeight());
    }

    //Метод обрабатывающий нажатия на полотно
    @FXML
    private void addPoint(MouseEvent event) {
        Point2D newPoint = new Point2D(event.getX(), event.getY());
        GraphicsContext context = canvas1.getGraphicsContext2D();
        PixelWriter pixelWriter = context.getPixelWriter();
        Point2D newPoint2D = new Point2D(event.getX(),event.getY());
        if(event.getButton()== MouseButton.PRIMARY){
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    pixelWriter.setColor((int) newPoint2D.getX() + i, (int) newPoint2D.getY() + j, Color.BLACK);
                }
            }
            isPoint = true;
            points.add(newPoint);
            indexLastPoint++;
        } else{
            connectPoints(points.get(indexLastPoint),newPoint2D, pixelWriter);
        }

    }


    private void connectPoints(Point2D point,Point2D newPoint, PixelWriter writer) {
        DecimalFormat dc = new DecimalFormat("#.#");
        int maxX = returnMax(newPoint.getX(), point.getX());
        int minX = returnMin(newPoint.getX(), point.getX());
        int maxY = returnMax(newPoint.getY(), point.getY());
        int minY = returnMin(newPoint.getY(), point.getY());
        System.out.println(newPoint.getX() + " " + point.getX() + " " + newPoint.getY() + " " + point.getY());
        for (int i = minX; i < maxX; i++) {
            for (int j = minY; j < maxY; j++) {
                //((i - point.getX()) / (point.getX() - newPoint.getX()) == (((j - point.getY()) / (newPoint.getY() - point.getY())))
                //(dc.format(((i - point.getX()) / (point.getX() - newPoint.getX()))) == dc.format(((j - point.getY()) / (point.getY() - newPoint.getY()))))
                String r1 = dc.format((i - point.getX()) / (newPoint.getX() - point.getX()));
                String r2 = dc.format((j - point.getY()) / (newPoint.getY() - point.getY()));
                if (r1.equals(r2)) {
                    for (int k = 0; k < 3; k++) {
                        for (int o = 0; o < 3; o++) {
                            writer.setColor(i + k, j + o, Color.RED);
                        }
                    }

                }
            }
        }
    }

    private static int returnMax(double num1, double num2) {
        double max = num1;
        if (num1 < num2) max = num2;
        return (int) max;
    }

    private static int returnMin(double num1, double num2) {
        double min = num1;
        if (num1 > num2) min = num2;
        return (int) min;
    }

    public static void drawCubeSplain(Point2D point1,Point2D point2){

    }

    public void keyPressed(KeyEvent keyEvent) {
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage=primaryStage;
        this.primaryStage.show();
    }
}