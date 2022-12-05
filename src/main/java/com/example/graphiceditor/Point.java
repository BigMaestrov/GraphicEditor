package com.example.graphiceditor;

public class Point {
    public double X;
    public double Y;

    public double getX() {
        return X;
    }

    public void setX(double x) {
        X = x;
    }

    public double getY() {
        return Y;
    }

    public void setY(double y) {
        Y = y;
    }

    public Point(double x, double y) {
        X = x;
        Y = y;
    }

    public Point(Point p) {
        X = p.X;
        Y = p.Y;
    }
}
