package com.example.graphiceditor;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class CubeSpline extends Figure{

    public CubeSpline(ArrayList<Point2D> figurePoints, String name) {
        this.figurePoints = figurePoints;
        this.name = name;
    }

    public void print(GraphicsContext context){
                context.beginPath();
                context.moveTo(figurePoints.get(0).getX(), figurePoints.get(0).getY());
                context.bezierCurveTo(figurePoints.get(1).getX(), figurePoints.get(1).getY(), figurePoints.get(2).getX(),
                        figurePoints.get(2).getY(), figurePoints.get(3).getX(), figurePoints.get(3).getY());
                context.stroke();

    }
}
