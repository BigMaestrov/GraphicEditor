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
/*
    private void FillByLine()
    {
        // Определяем наивысшую и наинизшую по Y координате точки
        Point pmin = VertexList[0];
        Point pmax = VertexList[0];
        int ymin = 0;
        int ymax = pictureBox1.Height;
        foreach (Point p in VertexList)
        {
            if (p.Y > pmax.Y) pmax = p;
            if (p.Y < pmin.Y) pmin = p;
        }
        ymin = pmin.Y < ymin ? ymin : pmin.Y;
        ymax = pmax.Y > ymax ? ymax : pmax.Y;

        List<int> xBoundaries = new List<int>();

        for (int y = ymin; y <= ymax; y++)
        {
            xBoundaries.Clear();
            for (int i = 0; i < VertexList.Count ; i++)
            {
                int k = i < VertexList.Count - 1 ? i + 1 : 0;
                if (((VertexList[i].Y < y) && (VertexList[k].Y >= y)) || ((VertexList[i].Y >= y) && (VertexList[k].Y < y)))
                {
                    // формула определения x координаты пересечения двух отрезков, заданных вершинами
                    double x = VertexList[i].X + ((VertexList[k].X - VertexList[i].X) * (y - VertexList[i].Y)) / (VertexList[k].Y - VertexList[i].Y);
                    xBoundaries.Add((int)x);
                }
            }
            xBoundaries.Sort((a, b) => a.CompareTo(b)); // сортировка по возростанию
            for (int el = 0; el < xBoundaries.Count - 1; el += 2)
            {
                g.DrawLine(DrawPen, new Point(xBoundaries[el], y), new Point(xBoundaries[el + 1], y));
            }
        }
    }*/
}
