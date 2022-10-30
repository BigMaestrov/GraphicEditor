package com.example.graphiceditor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class HelloController {

    private Point2D point;
    private boolean isPoint = false;

    @FXML
    public Canvas canvas1;

    @FXML
    protected void clearCanvas(ActionEvent actionEvent) {
        isPoint = false;
        GraphicsContext context = canvas1.getGraphicsContext2D();
        context.setFill(Color.WHITE);
        context.fillRect(0, 0, canvas1.getWidth(), canvas1.getHeight());
    }

    @FXML
    private void addPoint(MouseEvent event) {
        Point2D newPoint = new Point2D(event.getX(), event.getY());
        GraphicsContext context = canvas1.getGraphicsContext2D();
        PixelWriter pixelWriter = context.getPixelWriter();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                pixelWriter.setColor((int) event.getX() + i, (int) event.getY() + j, Color.BLACK);
            }
        }

        if (isPoint) {
            connectPoints(newPoint, pixelWriter);
        }
        isPoint = true;
        point = newPoint;
    }

    private void connectPoints(Point2D newPoint, PixelWriter writer) {
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
}