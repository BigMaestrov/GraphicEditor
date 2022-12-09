package com.example.graphiceditor;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Line extends Figure {

    public Line(ArrayList<Point> figurePoints, String name, Color color) {
        this.figurePoints = figurePoints;
        this.name = name;
        this.color = color;
    }

    public void print(GraphicsContext context, Canvas canvas) {
        Point[] points = new Point[4];
        for (int i = 0; i < figurePoints.size(); i++) {
            points[i] = new Point(figurePoints.get(i).getX(), figurePoints.get(i).getY());
        }
        this.DrawLine(context);
    }

    //Отрисовывает линию
    public void DrawLine(GraphicsContext g) {
        {
            PixelWriter pixelWriter = g.getPixelWriter();
            int x0 = (int)figurePoints.get(0).X;
            int y0 = (int)figurePoints.get(0).Y;
            int x1 = (int)figurePoints.get(1).X;
            int y1 = (int)figurePoints.get(1).Y;
            boolean steep = Math.abs(y1 - y0) > Math.abs(x1 - x0); // Проверяем рост отрезка по оси икс и по оси игрек
            // Отражаем линию по диагонали, если угол наклона слишком большой
            int temp;
            if (steep)
            {
                temp = x0; x0 = y0; y0 = temp;
                temp = x1; x1 = y1; y1 = temp;
            }
            // Если линия растёт не слева направо, то меняем начало и конец отрезка местами
            if (x0 > x1)
            {
                temp = x0; x0 = x1; x1 = temp;
                temp = y0; y0 = y1; y1 = temp;
            }
            int dx = x1 - x0;
            int dy = Math.abs(y1 - y0);
            int error = dx / 2; // Здесь используется оптимизация с умножением на dx, чтобы избавиться от лишних дробей
            int ystep = (y0 < y1) ? 1 : -1; // Выбираем направление роста координаты y
            int y = y0;
            for (int x = x0; x <= x1; x++)
            {
                if (steep)
                {
                    pixelWriter.setColor(y,x, this.color);
                }
                else
                {
                    pixelWriter.setColor(x,y, this.color);
                }
                error -= dy;
                if (error < 0)
                {
                    y += ystep;
                    error += dx;
                }
            }
        }

    }

    public Point getCenter() {
        return new Point((figurePoints.get(0).X + figurePoints.get(1).X) / 2, (figurePoints.get(0).Y + figurePoints.get(1).Y) / 2);
    }
}
