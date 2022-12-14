package com.example.graphiceditor;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Figure {
    ArrayList<Point> figurePoints;
    String name;
    Color color;
    Boolean mirrored;

    public Figure() {
    }

    public Figure(ArrayList<Point> figurePoints, String name, Color color) {
        this.figurePoints = figurePoints;
        this.name = name;
        this.color = color;
        //Проверка на то была ли отражена фигура
        mirrored = false;
    }

    public void print(GraphicsContext context, Canvas canvas1) {
        for (int i = 0; i < this.figurePoints.size() - 1; i++) {
            context.strokeLine(figurePoints.get(i).getX(), figurePoints.get(i).getY(), figurePoints.get(i + 1).getX(), figurePoints.get(i + 1).getY());
        }
        context.strokeLine(figurePoints.get(this.figurePoints.size() - 1).getX(), figurePoints.get(this.figurePoints.size() - 1).getY(), figurePoints.get(0).getX(), figurePoints.get(0).getY());
        fillByLine(context, canvas1);
    }

    public ArrayList<Point> getPoints() {
        return figurePoints;
    }

    public void setPoints(ArrayList<Point> points) {
        this.figurePoints = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void printFiguresData() {
        System.out.println(this.name + "Num points" + this.figurePoints.size());
    }

    private void fillByLine(GraphicsContext g, Canvas canvas) {
        // Определяем наивысшую и наинизшую по Y координате точки
        Point pmin = figurePoints.get(0);
        Point pmax = figurePoints.get(0);
        int ymin = 0;
        int ymax = (int) canvas.getHeight();
        for (Point p : figurePoints) {
            if (p.Y > pmax.Y) pmax = p;
            if (p.Y < pmin.Y) pmin = p;
        }
        ymin = pmin.Y < ymin ? ymin : (int) pmin.Y;
        ymax = pmax.Y > ymax ? ymax : (int) pmax.Y;
        List<Integer> xBoundaries = new ArrayList<>();
        for (int y = ymin; y <= ymax; y++) {
            xBoundaries.clear();
            for (int i = 0; i < figurePoints.size(); i++) {
                int k = i < figurePoints.size() - 1 ? i + 1 : 0;
                if (((figurePoints.get(i).Y < y) && (figurePoints.get(k).Y >= y)) || ((figurePoints.get(i).Y >= y) && (figurePoints.get(k).Y < y))) {
                    // формула определения x координаты пересечения двух отрезков, заданных вершинами
                    double x = figurePoints.get(i).X + ((figurePoints.get(k).X - figurePoints.get(i).X) * (y - figurePoints.get(i).Y)) / (figurePoints.get(k).Y - figurePoints.get(i).Y);
                    xBoundaries.add((int) x);
                }
            }
            //Сортировка
            //xBoundaries.sort((a, b) -> a.compareTo(b));
            for (int el = 0; el < xBoundaries.size() - 1; el += 2) {
                g.strokeLine(xBoundaries.get(el), y, xBoundaries.get(el + 1), y);
            }
        }
    }


    public void move(Point newPoint) {
        Point center = getCenter();
        double dx = newPoint.X - center.X;
        double dy = newPoint.Y - center.Y;
        double x;
        double y;
        for (int i = 0; i < figurePoints.size(); i++) {
            x = figurePoints.get(i).X;
            y = figurePoints.get(i).Y;
            figurePoints.get(i).setX(x + dx);
            figurePoints.get(i).setY(y + dy);
        }
    }

    /*
    Метод поворота фигуры
     */
    public void rotate(double df, double xc, double yc) {
        df = df / 180 * Math.PI;
        for (int i = 0; i < figurePoints.size(); i++) {
            Point temp = new Point(figurePoints.get(i).X - xc, figurePoints.get(i).Y - yc);
            double tempX = temp.X * Math.cos(df) - temp.Y * Math.sin(df);
            temp.Y = temp.X * Math.sin(df) + temp.Y * Math.cos(df);
            temp.X = tempX;
            temp.X += xc;
            temp.Y += yc;
            figurePoints.set(i, temp);
        }
    }

    public void downScaling(Point point) {
        for (int i = 0; i < figurePoints.size(); i++) {
            Point temp = new Point(figurePoints.get(i).X - point.X, figurePoints.get(i).Y - point.Y);
            temp.X *= 0.9;
            temp.Y *= 0.9;
            temp.X += point.X;
            temp.Y += point.Y;
            figurePoints.set(i, temp);
        }
    }

    public void upScaling(Point point) {
        for (int i = 0; i < figurePoints.size(); i++) {
            Point temp = new Point(figurePoints.get(i).X - point.X, figurePoints.get(i).Y - point.Y);
            temp.X *= 1.1;
            temp.Y *= 1.1;
            temp.X += point.X;
            temp.Y += point.Y;
            figurePoints.set(i, temp);
        }
    }

    public void mirrorY(double yc) {
        mirrored = !mirrored;
        System.out.println();
        for (int i = 0; i < figurePoints.size(); i++) {
            Point fP = new Point(0, 0);
            fP.Y = figurePoints.get(i).Y - yc;
            fP.X = figurePoints.get(i).X;
            fP.Y = -fP.Y;
            fP.Y += yc;
            figurePoints.set(i, fP);
        }

    }

    /*
    Метод возвращающий координаты центра фигуры
     */
    public Point getCenter() {
        double minX = figurePoints.get(0).X;
        double maxX = figurePoints.get(0).X;
        double minY = figurePoints.get(0).Y;
        double maxY = figurePoints.get(0).Y;
        for (int i = 0; i < figurePoints.size(); i++) {
            if (figurePoints.get(i).X > maxX) {
                maxX = figurePoints.get(i).X;
            }
            if (figurePoints.get(i).X < minX) {
                minX = figurePoints.get(i).X;
            }
            if (figurePoints.get(i).Y > maxY) {
                maxY = figurePoints.get(i).Y;
            }
            if (figurePoints.get(i).Y < minY) {
                minY = figurePoints.get(i).Y;
            }
        }
        return new Point((maxX + minX) / 2, (maxY + minY) / 2);
    }


    public List<List<Integer>> getPointsBySide(int Y) {
        List<List<Integer>> points = new ArrayList<List<Integer>>();
        List<Integer> leftPoints = new ArrayList<>();
        List<Integer> rightPoints = new ArrayList<Integer>();
        for (int i = 0; i < figurePoints.size(); i++) {
            int k = i < figurePoints.size() - 1 ? i + 1 : 0;
            // обработка граничных случаев
            if (((figurePoints.get(i).Y < Y) && (figurePoints.get(k).Y >= Y)) || ((figurePoints.get(i).Y >= Y) && (figurePoints.get(k).Y < Y))) {
                // формула определения x координаты пересечения двух отрезков, заданных вершинами
                double x = figurePoints.get(i).X + ((figurePoints.get(k).X - figurePoints.get(i).X) * (Y - figurePoints.get(i).Y)) / (figurePoints.get(k).Y - figurePoints.get(i).Y);
                if ((figurePoints.get((mirrored ? i : k)).Y - (figurePoints.get((mirrored ? k : i)).Y) < 0))
                    leftPoints.add((int) x);
                else rightPoints.add((int) x);
            }
        }
        points.add(leftPoints);
        points.add(rightPoints);
        return points;
    }
}
