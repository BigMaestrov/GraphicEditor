package com.example.graphiceditor;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Figure {
    ArrayList<Point2D> figurePoints;
    String name;

    public Figure() {
    }

    public Figure(ArrayList<Point2D> figurePoints, String name) {
        this.figurePoints = figurePoints;
        this.name = name;
    }

    public void print(GraphicsContext context){
        for (int i = 0; i < this.figurePoints.size()-1; i++) {
            context.strokeLine(figurePoints.get(i).getX(),figurePoints.get(i).getY(), figurePoints.get(i+1).getX(),figurePoints.get(i+1).getY());
        }
        context.strokeLine(figurePoints.get(this.figurePoints.size()-1).getX(),figurePoints.get(this.figurePoints.size()-1).getY(), figurePoints.get(0).getX(),figurePoints.get(0).getY());
    }

    public ArrayList<Point2D> getPoints() {
        return figurePoints;
    }

    public void setPoints(ArrayList<Point2D> points) {
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
}
