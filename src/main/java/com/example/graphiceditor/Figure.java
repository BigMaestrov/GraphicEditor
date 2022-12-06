package com.example.graphiceditor;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class Figure {
    ArrayList<Point> figurePoints;
    String name;

    public Figure() {
    }

    public Figure(ArrayList<Point> figurePoints, String name, GraphicsContext context, Canvas canvas) {
        this.figurePoints = figurePoints;
        this.name = name;
        FillByLine(context, canvas);
    }

    public void print(GraphicsContext context){
        for (int i = 0; i < this.figurePoints.size()-1; i++) {
            context.strokeLine(figurePoints.get(i).getX(),figurePoints.get(i).getY(), figurePoints.get(i+1).getX(),figurePoints.get(i+1).getY());
        }
        context.strokeLine(figurePoints.get(this.figurePoints.size()-1).getX(),figurePoints.get(this.figurePoints.size()-1).getY(), figurePoints.get(0).getX(),figurePoints.get(0).getY());
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

    public void printFiguresData(){
        System.out.println( this.name +"Num points"+ this.figurePoints.size());
    }

    private void FillByLine(GraphicsContext g, Canvas canvas) {
        // Определяем наивысшую и наинизшую по Y координате точки
        Point pmin = figurePoints.get(0);
        Point pmax = figurePoints.get(0);
        int ymin = 0;
        int ymax = (int) canvas.getHeight();
        Point p = new Point(0, 0);
        for (int i = 0; i < figurePoints.size(); i++) {
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
            xBoundaries.sort((a, b) -> a.compareTo(b));

            for(int i=0; i<xBoundaries.size();i++){
                System.out.println(xBoundaries.get(i));
            }

            for (int el = 0; el < xBoundaries.size() - 1; el += 2) {
                g.strokeLine(xBoundaries.get(el), y, xBoundaries.get(el+1), y);
            }
        }
    }
}
