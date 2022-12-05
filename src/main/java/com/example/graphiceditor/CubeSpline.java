package com.example.graphiceditor;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.CubicCurve;

import java.util.ArrayList;

public class CubeSpline extends Figure{

    public CubeSpline(ArrayList<Point2D> figurePoints, String name) {
        this.figurePoints = figurePoints;
        this.name = name;
    }

    public void print(GraphicsContext context){
        CubicCurve cubicCurve = new CubicCurve();
                context.beginPath();
                context.moveTo(figurePoints.get(0).getX(), figurePoints.get(0).getY());
                context.bezierCurveTo(figurePoints.get(1).getX(), figurePoints.get(1).getY(), figurePoints.get(2).getX(),
                        figurePoints.get(2).getY(), figurePoints.get(3).getX(), figurePoints.get(3).getY());
                context.stroke();

    }

    public void DrawCubeSpline(GraphicsContext g, Point[] P)
    {
        Point[] L = new Point[4]; // Матрица вещественных коэффициентов
        for(int i =0;i<P.length;i++){
            L[i]= new Point(P[i]);
        }
        Point Pv1 = new Point(P[0]);
        Point Pv2 = new Point(P[0]);
        double dt = 0.0001; //Частота точек?
        double t = 0;
        double xt, yt;
        Point Ppred = new Point(P[0]);
        Point Pt = new Point(P[0]);
        //DrawPen.CustomEndCap = new AdjustableArrowCap(0, 0);
        // Касательные векторы
        Pv1.X = 4 * (P[1].X - P[0].X);
        Pv1.Y = 4 * (P[1].Y - P[0].Y);
        Pv2.X = 4 * (P[3].X - P[2].X);
        Pv2.Y = 4 * (P[3].Y - P[2].Y);
        // Коэффициенты полинома
        L[0].X = 2 * P[0].X - 2 * P[2].X + Pv1.X + Pv2.X; // Ax
        L[0].Y = 2 * P[0].Y - 2 * P[2].Y + Pv1.Y + Pv2.Y; // Ay
        L[1].X = -3 * P[0].X + 3 * P[2].X - 2 * Pv1.X - Pv2.X; // Bx
        L[1].Y = -3 * P[0].Y + 3 * P[2].Y - 2 * Pv1.Y - Pv2.Y; // By
        L[2].X = Pv1.X; // Cx
        L[2].Y = Pv1.Y; // Cy
        L[3].X = P[0].X; // Dx
        L[3].Y = P[0].Y; // Dy
        while (t < 1 + dt / 2)
        {
            xt = ((L[0].X * t + L[1].X) * t + L[2].X) * t + L[3].X;
            yt = ((L[0].Y * t + L[1].Y) * t + L[2].Y) * t + L[3].Y;
            Pt.X = (int)Math.round(xt);
            Pt.Y = (int)Math.round(yt);
            g.strokeLine(Ppred.getX(), Ppred.getY(), Pt.getX(), Pt.getY());
            Ppred = Pt;
            t = t + dt;
        }
    }
}
