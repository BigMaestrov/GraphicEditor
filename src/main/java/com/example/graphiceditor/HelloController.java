package com.example.graphiceditor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

public class HelloController {
    // Переменная задающая главную сцену приложения
    @FXML
    private Stage primaryStage;
    // Блок переменных для сохранения графических элементов полотна
    private ArrayList<Point> tempPoints = new ArrayList<Point>();
    ArrayList<Figure> figures = new ArrayList<>();
    // Блок переменных графических элементов интерфейса
    @FXML
    public Canvas canvas1;
    public GraphicsContext context;
    @FXML
    public ChoiceBox typeChoiceBox;
    @FXML
    public ChoiceBox<String> colorChoiceBox;
    @FXML
    public ChoiceBox<String> figureChoiceBox;
    ObservableList<String> figuresNames = FXCollections.observableArrayList();
    @FXML
    Slider angleSlider;

    // Метод срабатывающий при старте программы
    @FXML
    protected void initialize() {
        // Заполнение полей typeChoiceBox
        ObservableList<String> types = FXCollections.observableArrayList("Прямая", "Куб Сплайн", "Треугольник", "Стрелка", "Удалить фигуру", "Перемещение", "Поворот", "Увеличение", "Уменьшение", "Отражение");
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
        tempPoints.clear();
        context = canvas1.getGraphicsContext2D();
        context.setFill(Color.WHITE);
        context.fillRect(0, 0, canvas1.getWidth(), canvas1.getHeight());
        figures.clear();
        figuresNames.clear();
        figureChoiceBox.setItems(figuresNames);
    }

    // Метод обрабатывающий нажатия на полотно
    @FXML
    private void addPoint(MouseEvent event) {
        context = canvas1.getGraphicsContext2D();
        // Построение прямой
        if (typeChoiceBox.getValue() == "Прямая") {
            Point newPoint = new Point(event.getX(), event.getY());
            PixelWriter pixelWriter = context.getPixelWriter();
            tempPoints.add(newPoint);
            if (event.getButton() == MouseButton.PRIMARY) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        pixelWriter.setColor((int) newPoint.getX() + i, (int) newPoint.getY() + j, Color.BLACK);
                    }
                }
            }
            if (tempPoints.size() == 2) {
                //Добавление фигуры в список
                ArrayList<Point> figurePoints = new ArrayList<>();
                for (int i = 0; i < tempPoints.size(); i++) {
                    figurePoints.add(tempPoints.get(i));
                }
                Line line = new Line(figurePoints, "Прямая" + figures.size(), decodeColor(colorChoiceBox.getValue()));
                figures.add(line);
                //Рисование фигуры
                context.setStroke(decodeColor(colorChoiceBox.getValue()));

                /*context.strokeLine(tempPoints.get(0).getX(), tempPoints.get(0).getY(), tempPoints.get(1).getX(),
                        tempPoints.get(1).getY());*/
                line.DrawLine(context);
                figuresNames.add(line.getName());
                tempPoints.clear();
            }
        }
        // Построение кубического сплайна
        if (typeChoiceBox.getValue() == "Куб Сплайн") {
            Point newPoint = new Point(event.getX(), event.getY());
            PixelWriter pixelWriter = context.getPixelWriter();
            Point2D newPoint2D = new Point2D(event.getX(), event.getY());
            if (event.getButton() == MouseButton.PRIMARY) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        pixelWriter.setColor((int) newPoint2D.getX() + i, (int) newPoint2D.getY() + j, Color.BLACK);
                    }
                }
                tempPoints.add(newPoint);
            }
            if (tempPoints.size() == 4) {
                //Добавление фигуры в список
                ArrayList<Point> figurePoints = new ArrayList<>();
                for (int i = 0; i < tempPoints.size(); i++) {
                    figurePoints.add(tempPoints.get(i));
                }
                CubeSpline cubeSpline = new CubeSpline(figurePoints, "Куб сплайн" + figures.size(), decodeColor(colorChoiceBox.getValue()));
                figures.add(cubeSpline);
                //Рисование фигуры
                context.setStroke(decodeColor(colorChoiceBox.getValue()));
                Point[] points = new Point[4];
                for (int i = 0; i < tempPoints.size(); i++) {
                    points[i] = new Point(tempPoints.get(i).getX(), tempPoints.get(i).getY());
                }
                cubeSpline.DrawCubeSpline(context, points);
                figuresNames.add(cubeSpline.getName());
                tempPoints.clear();
            }
        }
        // Построение треугольника
        if (typeChoiceBox.getValue() == "Треугольник") {
            Point newPoint = new Point(event.getX(), event.getY());
            PixelWriter pixelWriter = context.getPixelWriter();
            Point2D newPoint2D = new Point2D(event.getX(), event.getY());
            if (event.getButton() == MouseButton.PRIMARY) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        pixelWriter.setColor((int) newPoint2D.getX() + i, (int) newPoint2D.getY() + j, Color.BLACK);
                    }
                }
                tempPoints.add(newPoint);
            }
            if (tempPoints.size() == 2) {
                //Добавление фигуры в список
                ArrayList<Point> figurePoints = new ArrayList<>();
                figurePoints.add(new Point(tempPoints.get(0).getX(), tempPoints.get(0).getY()));
                figurePoints.add(new Point(tempPoints.get(1).getX(), tempPoints.get(1).getY()));
                figurePoints.add(new Point(tempPoints.get(1).getX() + tempPoints.get(1).getX() - (int) tempPoints.get(0).getX(), tempPoints.get(0).getY()));
                Figure triangle = new Figure(figurePoints, "Треугольник" + figures.size(), decodeColor(colorChoiceBox.getValue()));
                figures.add(triangle);
                //Рисование фигуры
                context.setStroke(decodeColor(colorChoiceBox.getValue()));
                triangle.print(context, canvas1);
                figuresNames.add(triangle.getName());
                tempPoints.clear();
            }
        }
        // Построение стрелки
        if (typeChoiceBox.getValue() == "Стрелка") {
            Point newPoint = new Point(event.getX(), event.getY());
            PixelWriter pixelWriter = context.getPixelWriter();
            Point2D newPoint2D = new Point2D(event.getX(), event.getY());
            if (event.getButton() == MouseButton.PRIMARY) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        pixelWriter.setColor((int) newPoint2D.getX() + i, (int) newPoint2D.getY() + j, Color.BLACK);
                    }
                }
                tempPoints.add(newPoint);
            }
            if (tempPoints.size() == 3) {
                //Переменные для хранения введенных строчек
                double x0 = tempPoints.get(0).getX();
                double xm = tempPoints.get(1).getX();
                double y0 = tempPoints.get(0).getY();
                double ym = tempPoints.get(2).getY();
                //Добавление фигуры в список
                ArrayList<Point> figurePoints = new ArrayList<>();
                figurePoints.add(new Point(x0, y0 + (ym - y0) * 0.666));
                figurePoints.add(new Point(x0 + (xm - x0) * 0.666, y0 + (ym - y0) * 0.666));
                figurePoints.add(new Point(x0 + (xm - x0) * 0.666, ym));
                figurePoints.add(new Point(xm, y0 + (ym - y0) * 0.5));
                figurePoints.add(new Point(x0 + (xm - x0) * 0.666, y0));
                figurePoints.add(new Point(x0 + (xm - x0) * 0.666, y0 + (ym - y0) * 0.333));
                figurePoints.add(new Point(x0, y0 + (ym - y0) * 0.333));
                Figure arrow = new Figure(figurePoints, "Стрелка" + figures.size(), decodeColor(colorChoiceBox.getValue()));
                figures.add(arrow);
                //Установление цвета фигуры
                context.setStroke(decodeColor(colorChoiceBox.getValue()));
                arrow.print(context, canvas1);
                figuresNames.add(arrow.getName());
                tempPoints.clear();
            }
        }
        //Удаление фигуры
        if (typeChoiceBox.getValue() == "Удалить фигуру") {
            for (int i = 0; i < figuresNames.size(); i++) {
                if (figuresNames.get(i) == figureChoiceBox.getValue()) {
                    figures.remove(i);
                    figuresNames.remove(i);
                }
            }
            context.setFill(Color.WHITE);
            context.fillRect(0, 0, canvas1.getWidth(), canvas1.getHeight());
            for (int i = 0; i < figuresNames.size(); i++) {
                //Установление цвета фигуры
                context.setStroke(figures.get(i).color);
                figures.get(i).print(context, canvas1);
            }
        }
        //Геометрические преобразования

        //Перемещение
        if (typeChoiceBox.getValue() == "Перемещение") {
            Point newPoint = new Point(event.getX(), event.getY());
            PixelWriter pixelWriter = context.getPixelWriter();
            if (event.getButton() == MouseButton.PRIMARY) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        pixelWriter.setColor((int) newPoint.getX() + i, (int) newPoint.getY() + j, Color.BLACK);
                    }
                }
            }
            //Перемещение фигуры
            for (int i = 0; i < figuresNames.size(); i++) {
                //Поиск выбранной фигуры
                if (figuresNames.get(i) == figureChoiceBox.getValue()) {
                    //Смещение точек
                    figures.get(i).move(newPoint);
                }
            }
            context.setFill(Color.WHITE);
            context.fillRect(0, 0, canvas1.getWidth(), canvas1.getHeight());
            for (int i = 0; i < figuresNames.size(); i++) {
                //Установление цвета фигуры
                context.setStroke(figures.get(i).color);
                figures.get(i).print(context, canvas1);
            }
        }
        //Поворот
        if (typeChoiceBox.getValue() == "Поворот") {
            Point newPoint = new Point(event.getX(), event.getY());
            PixelWriter pixelWriter = context.getPixelWriter();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    pixelWriter.setColor((int) newPoint.getX() + i, (int) newPoint.getY() + j, Color.BLACK);
                }
            }
            //Поворот фигуры
            for (int i = 0; i < figuresNames.size(); i++) {
                //Поиск выбранной фигуры
                if (figuresNames.get(i) == figureChoiceBox.getValue()) {
                    //Смещение точек
                    figures.get(i).rotate(angleSlider.getValue(), newPoint.X, newPoint.Y);
                }
            }
            context.setFill(Color.WHITE);
            context.fillRect(0, 0, canvas1.getWidth(), canvas1.getHeight());
            for (int i = 0; i < figuresNames.size(); i++) {
                //Установление цвета фигуры
                context.setStroke(figures.get(i).color);
                figures.get(i).print(context, canvas1);
            }
        }
        if (typeChoiceBox.getValue() == "Увеличение") {
            Point newPoint = new Point(event.getX(), event.getY());
            PixelWriter pixelWriter = context.getPixelWriter();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    pixelWriter.setColor((int) newPoint.getX() + i, (int) newPoint.getY() + j, Color.BLACK);
                }
            }
            //Масштабирование
            for (int i = 0; i < figuresNames.size(); i++) {
                //Поиск выбранной фигуры
                if (figuresNames.get(i) == figureChoiceBox.getValue()) {
                    //Смещение точек
                    figures.get(i).upScaling(newPoint);
                }
            }
            context.setFill(Color.WHITE);
            context.fillRect(0, 0, canvas1.getWidth(), canvas1.getHeight());
            for (int i = 0; i < figuresNames.size(); i++) {
                //Установление цвета фигуры
                context.setStroke(figures.get(i).color);
                figures.get(i).print(context, canvas1);
            }
        }
        if (typeChoiceBox.getValue() == "Уменьшение") {
            Point newPoint = new Point(event.getX(), event.getY());
            PixelWriter pixelWriter = context.getPixelWriter();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    pixelWriter.setColor((int) newPoint.getX() + i, (int) newPoint.getY() + j, Color.BLACK);
                }
            }
            //Масштабирование
            for (int i = 0; i < figuresNames.size(); i++) {
                //Поиск выбранной фигуры
                if (figuresNames.get(i) == figureChoiceBox.getValue()) {
                    //Смещение точек
                    figures.get(i).downScaling(newPoint);
                }
            }
            context.setFill(Color.WHITE);
            context.fillRect(0, 0, canvas1.getWidth(), canvas1.getHeight());
            for (int i = 0; i < figuresNames.size(); i++) {
                //Установление цвета фигуры
                context.setStroke(figures.get(i).color);
                figures.get(i).print(context, canvas1);
            }
        }
        if (typeChoiceBox.getValue() == "Отражение") {
            Point newPoint = new Point(event.getX(), event.getY());
            PixelWriter pixelWriter = context.getPixelWriter();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    pixelWriter.setColor((int) newPoint.getX() + i, (int) newPoint.getY() + j, Color.BLACK);
                }
            }
            //Масштабирование
            for (int i = 0; i < figuresNames.size(); i++) {
                //Поиск выбранной фигуры
                if (figuresNames.get(i) == figureChoiceBox.getValue()) {
                    //Смещение точек
                    figures.get(i).mirrorY(newPoint.Y);
                }
            }
            context.setFill(Color.WHITE);
            context.fillRect(0, 0, canvas1.getWidth(), canvas1.getHeight());
            for (int i = 0; i < figures.size(); i++) {
                //Установление цвета фигуры
                context.setStroke(figures.get(i).color);
                figures.get(i).print(context, canvas1);
            }
        }
        figureChoiceBox.setItems(figuresNames);
    }

    //ТМО Кастум фигур кс код
        /*
// списки краевых x координат для каждой фигуры
        List<int> Xra = new List<int>();
        List<int> Xla = new List<int>();
        List<int> Xrb = new List<int>();
        List<int> Xlb = new List<int>();

        // Очищаем экран перед отрисовкой
        g.Clear(Color.White);

        for (int y = ymin; y <= ymax; y++)
        {
            // очищаем списки координат
            Xra.Clear();
            Xla.Clear();
            Xlb.Clear();
            Xrb.Clear();

            // заполнение границ фигуры А
            for (int i = 0; i < VertexListA.Count; i++)
            {
                int k = i < VertexListA.Count - 1 ? i + 1 : 0;
                // обработка граничных случаев
                if (((VertexListA[i].Y < y) && (VertexListA[k].Y >= y)) || ((VertexListA[i].Y >= y) && (VertexListA[k].Y < y)))
                {
                    // формула определения x координаты пересечения двух отрезков, заданных вершинами
                    double x = VertexListA[i].X + ((VertexListA[k].X - VertexListA[i].X) * (y - VertexListA[i].Y)) / (VertexListA[k].Y - VertexListA[i].Y);
                    if (VertexListA[k].Y - VertexListA[i].Y < 0)
                    {
                        Xra.Add((int)x);
                    }
                    else
                    {
                        Xla.Add((int)x);
                    }
                }
            }

            // заполнение границ фигуры В
            for (int i = 0; i < VertexListB.Count; i++)
            {
                int k = i < VertexListB.Count - 1 ? i + 1 : 0;
                // обработка граничных случаев
                if (((VertexListB[i].Y < y) && (VertexListB[k].Y >= y)) || ((VertexListB[i].Y >= y) && (VertexListB[k].Y < y)))
                {
                    // формула определения x координаты пересечения двух отрезков, заданных вершинами
                    double x = VertexListB[i].X + ((VertexListB[k].X - VertexListB[i].X) * (y - VertexListB[i].Y)) / (VertexListB[k].Y - VertexListB[i].Y);
                    if (VertexListB[k].Y - VertexListB[i].Y < 0)
                    {
                        Xrb.Add((int)x);
                    }
                    else
                    {
                        Xlb.Add((int)x);
                    }
                }
            }

            List<PointInfo> M = new List<PointInfo>(); // рабочии массив

            // Заполнение рабочего массива:
            int n = Xla.Count;

            for (int i = 0; i < n; i++)
            {
                PointInfo Buff = new PointInfo();
                Buff.x = Xla[i];
                Buff.dQ = 2;
                M.Add(Buff);
            }

            n = Xra.Count;

            for (int i = 0; i < n; i++)
            {
                PointInfo Buff = new PointInfo();
                Buff.x = Xra[i];
                Buff.dQ = -2;
                M.Add(Buff);
            }

            n = Xlb.Count;

            for (int i = 0; i < n; i++)
            {
                PointInfo Buff = new PointInfo();
                Buff.x = Xlb[i];
                Buff.dQ = 1;
                M.Add(Buff);
            }

            n = Xrb.Count;

            for (int i = 0; i < n; i++)
            {
                PointInfo Buff = new PointInfo();
                Buff.x = Xrb[i];
                Buff.dQ = -1;
                M.Add(Buff);
            }

            // сортируем M по возрастанию X координаты
            M.Sort((a, b) => a.x.CompareTo(b.x));

            int Q = 0;
            int xemin = 0;
            int xemax = pictureBox1.Width;

            // Конечные списки x границ
            List<int> Xlr = new List<int>();
            List<int> Xrr= new List<int>();

            if (M.Count == 0) continue;

            if ((M[0].x >= xemin) && (M[0].dQ < 0))
            {
                Xlr.Add(xemin);
                Q = -M[0].dQ;
            }
            // Выполнение ТМО
            for (int i = 0; i < M.Count; i++)
            {
                int x = M[i].x;
                int Qnew = Q + M[i].dQ;
                if (
                        (Q < SetQ[0] || Q > SetQ[1]) &&
                                (Qnew >= SetQ[0] && Qnew <= SetQ[1])
                )
                {
                    Xlr.Add(x);
                }
                if (
                        (Q >= SetQ[0] && Q <= SetQ[1]) &&
                                (Qnew < SetQ[0] || Qnew > SetQ[1])
                )
                {
                    Xrr.Add(x);
                }
                Q = Qnew;
            }
            if (Q >= SetQ[0] && Q <= SetQ[1])
            {
                Xrr.Add(xemax);
            }
            for (int id = 0; id < Xlr.Count && id < Xrr.Count; id++)
            {
                if (Xlr[id] < Xrr[id])
                {
                    g.DrawLine(DrawPen, new Point(Xlr[id], y), new Point(Xrr[id], y));
                }
            }
        }
    }
    */


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
}