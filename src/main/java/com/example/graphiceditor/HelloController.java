package com.example.graphiceditor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class HelloController {
    // Переменная задающая главную сцену приложения
    @FXML
    private Stage primaryStage;
    // Блок переменных для сохранения графических элементов полотна
    private ArrayList<Point2D> points = new ArrayList<Point2D>();
    private boolean isPoint = false;

    // Блок переменных графических элементов интерфейса
    @FXML
    public Canvas canvas1;
    public GraphicsContext context;
    @FXML
    public ChoiceBox typeChoiceBox;
    @FXML
    public ChoiceBox<String> colorChoiceBox;

    // Метод срабатывающий при старте программы
    @FXML
    protected void initialize() {
        // Заполнение полей typeChoiceBox
        ObservableList<String> types = FXCollections.observableArrayList("Прямая", "Куб Сплайн", "Треугольник",
                "Стрелка");
        typeChoiceBox.setItems(types);
        typeChoiceBox.setValue("Прямая");
        // Заполнение полей colorComboBox
        ObservableList<String> colors = FXCollections.observableArrayList("Черный", "Красный", "Синий", "Зеленый");
        colorChoiceBox.setItems(colors);
        colorChoiceBox.setValue("Черный");
        context = canvas1.getGraphicsContext2D();
    }

    // Метод срабатывающий при нажатии на кнопку "очистить поле"
    @FXML
    protected void clearCanvas(ActionEvent actionEvent) {
        isPoint = false;
        points.clear();
        context = canvas1.getGraphicsContext2D();
        context.setFill(Color.WHITE);
        context.fillRect(0, 0, canvas1.getWidth(), canvas1.getHeight());
    }

    // Метод обрабатывающий нажатия на полотно
    @FXML
    private void addPoint(MouseEvent event) {
        Point2D newPoint = new Point2D(event.getX(), event.getY());
        context = canvas1.getGraphicsContext2D();
        // Построение прямой
        if (typeChoiceBox.getValue() == "Прямая") {
            PixelWriter pixelWriter = context.getPixelWriter();
            Point2D newPoint2D = new Point2D(event.getX(), event.getY());
            if (event.getButton() == MouseButton.PRIMARY) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        pixelWriter.setColor((int) newPoint2D.getX() + i, (int) newPoint2D.getY() + j, Color.BLACK);
                    }
                }
                isPoint = true;
                points.add(newPoint);
            } else {
                for (int i = 0; i < points.size(); i++) {
                    context.setStroke(decodeColor(colorChoiceBox.getValue()));
                    context.strokeLine(points.get(i).getX(), points.get(i).getY(), points.get(i + 1).getX(),
                            points.get(i + 1).getY());
                }
            }
        }
        // Построение кубического сплайна
        if (typeChoiceBox.getValue() == "Куб Сплайн") {
            PixelWriter pixelWriter = context.getPixelWriter();
            Point2D newPoint2D = new Point2D(event.getX(), event.getY());
            if (event.getButton() == MouseButton.PRIMARY) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        pixelWriter.setColor((int) newPoint2D.getX() + i, (int) newPoint2D.getY() + j, Color.BLACK);
                    }
                }
                isPoint = true;
                points.add(newPoint);
            }
            if (points.size() == 4)
                context.setStroke(decodeColor(colorChoiceBox.getValue()));
            context.beginPath();
            context.moveTo(points.get(0).getX(), points.get(0).getY());
            context.bezierCurveTo(points.get(1).getX(), points.get(1).getY(), points.get(2).getX(),
                    points.get(2).getY(), points.get(3).getX(), points.get(3).getY());
            context.stroke();
            points.clear();
        }
        // Построение треугольника
        if (typeChoiceBox.getValue() == "Треугольник") {
            PixelWriter pixelWriter = context.getPixelWriter();
            Point2D newPoint2D = new Point2D(event.getX(), event.getY());
            if (event.getButton() == MouseButton.PRIMARY) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        pixelWriter.setColor((int) newPoint2D.getX() + i, (int) newPoint2D.getY() + j, Color.BLACK);
                    }
                }
                isPoint = true;
                points.add(newPoint);
            }
            if (points.size() == 2)
                context.setStroke(decodeColor(colorChoiceBox.getValue()));

            context.strokeLine(points.get(0).getX(), points.get(0).getY(), points.get(1).getX(), points.get(1).getY());

            context.strokeLine(points.get(1).getX(), points.get(1).getY(),
                    points.get(1).getX() + points.get(1).getX() - (int) points.get(0).getX(), points.get(0).getY());

            context.strokeLine(points.get(1).getX() + points.get(1).getX() - (int) points.get(0).getX(),
                    points.get(0).getY(), points.get(0).getX(), points.get(0).getY());
            points.clear();
        }
        // Построение стрелки
        if (typeChoiceBox.getValue() == "Стрелка") {
            PixelWriter pixelWriter = context.getPixelWriter();
            Point2D newPoint2D = new Point2D(event.getX(), event.getY());
            if (event.getButton() == MouseButton.PRIMARY) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        pixelWriter.setColor((int) newPoint2D.getX() + i, (int) newPoint2D.getY() + j, Color.BLACK);
                    }
                }
                isPoint = true;
                points.add(newPoint);
            }
            if (points.size() == 3) {
                double x0 = points.get(0).getX();
                double xm = points.get(1).getX();
                double y0 = points.get(0).getY();
                double ym = points.get(2).getY();
                context.setStroke(decodeColor(colorChoiceBox.getValue()));

                //1->4
                context.strokeLine(x0, y0+(ym-y0)*0.6, x0+(xm-x0)*0.6, y0+(ym-y0)*0.6);
                context.strokeLine(x0+(xm-x0)*0.6, y0+(ym-y0)*0.6, x0+(xm-x0)*0.6, ym);
                context.strokeLine(x0+(xm-x0)*0.6, ym, xm, y0+(ym-y0)*0.5);
                //4->7
                context.strokeLine(xm, y0+(ym-y0)*0.5, x0+(xm-x0)*0.6, y0);
                context.strokeLine(x0+(xm-x0)*0.6, y0, x0+(xm-x0)*0.6, y0+(ym-y0)*0.3);
                context.strokeLine(x0+(xm-x0)*0.6, y0+(ym-y0)*0.3, x0, y0+(ym-y0)*0.3);
                context.strokeLine(x0, y0+(ym-y0)*0.3,x0, y0+(ym-y0)*0.6);

                points.clear();
            }
        }
    }

    @Deprecated
    private void connectPointsOLD(Point2D point, Point2D newPoint, PixelWriter writer) {
        DecimalFormat dc = new DecimalFormat("#.##");
        int maxX = returnMax(newPoint.getX(), point.getX());
        int minX = returnMin(newPoint.getX(), point.getX());
        int maxY = returnMax(newPoint.getY(), point.getY());
        int minY = returnMin(newPoint.getY(), point.getY());
        System.out.println(newPoint.getX() + " " + point.getX() + " " + newPoint.getY() + " " + point.getY());
        for (int i = minX; i < maxX; i++) {
            for (int j = minY; j < maxY; j++) {
                // ((i - point.getX()) / (point.getX() - newPoint.getX()) == (((j -
                // point.getY()) / (newPoint.getY() - point.getY())))
                // (dc.format(((i - point.getX()) / (point.getX() - newPoint.getX()))) ==
                // dc.format(((j - point.getY()) / (point.getY() - newPoint.getY()))))
                String r1 = dc.format((i - point.getX()) / (newPoint.getX() - point.getX()));
                String r2 = dc.format((j - point.getY()) / (newPoint.getY() - point.getY()));
                if (r1.equals(r2)) {
                    for (int k = 0; k < 3; k++) {
                        for (int o = 0; o < 3; o++) {
                            writer.setColor(i + k, j + o, decodeColor(colorChoiceBox.getValue()));
                        }
                    }

                }
            }
        }
    }

    private static int returnMax(double num1, double num2) {
        double max = num1;
        if (num1 < num2)
            max = num2;
        return (int) max;
    }

    private static int returnMin(double num1, double num2) {
        double min = num1;
        if (num1 > num2)
            min = num2;
        return (int) min;
    }

    public void keyPressed(KeyEvent keyEvent) {
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.show();
    }

    public static Color decodeColor(String colorName) {
        Color color = Color.BLACK;
        switch (colorName) {
            case ("Черный"):
                color = Color.BLACK;
                break;
            case ("Красный"):
                color = Color.RED;
                break;
            case ("Синий"):
                color = Color.BLUE;
                break;
            case ("Зеленый"):
                color = Color.GREEN;
                break;
            default:
                color = Color.BLACK;
        }
        return color;
    }

    public static void drawArrow() {

    }

    public static int calculateLength(int x1, int x2) {
        int result = 0;
        /*
         * if (x1 > x2) {
         * result = x1-x2;
         * }else{
         * result = x2-x1;
         * }
         */
        return x1 - x2;
    }
}