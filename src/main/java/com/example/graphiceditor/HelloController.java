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
    public ChoiceBox<String> figureChoiceBox1;
    ObservableList<String> figuresNames1 = FXCollections.observableArrayList();
    @FXML
    public ChoiceBox<String> figureChoiceBox2;
    ObservableList<String> figuresNames2 = FXCollections.observableArrayList();
    @FXML
    Slider angleSlider;

    // Метод срабатывающий при старте программы
    @FXML
    protected void initialize() {
        // Заполнение полей typeChoiceBox
        ObservableList<String> types = FXCollections.observableArrayList("Прямая", "Куб Сплайн", "Треугольник", "Стрелка", "Удалить фигуру", "Перемещение", "Поворот", "Увеличение", "Уменьшение", "Отражение", "Разность", "Сим. Разность");
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
        figuresNames1.clear();
        figureChoiceBox1.setItems(figuresNames1);
        figureChoiceBox2.setItems(figuresNames2);
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
                figuresNames1.add(line.getName());
                figuresNames2.add(line.getName());
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
                figuresNames1.add(cubeSpline.getName());
                figuresNames2.add(cubeSpline.getName());
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
                figuresNames1.add(triangle.getName());
                figuresNames2.add(triangle.getName());
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
                figuresNames1.add(arrow.getName());
                figuresNames2.add(arrow.getName());
                tempPoints.clear();
            }
        }
        //Удаление фигуры
        if (typeChoiceBox.getValue() == "Удалить фигуру") {
            for (int i = 0; i < figuresNames1.size(); i++) {
                if (figuresNames1.get(i) == figureChoiceBox1.getValue()) {
                    figures.remove(i);
                    figuresNames1.remove(i);
                    figuresNames2.remove(i);
                }
            }
            context.setFill(Color.WHITE);
            context.fillRect(0, 0, canvas1.getWidth(), canvas1.getHeight());
            for (int i = 0; i < figuresNames1.size(); i++) {
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
            for (int i = 0; i < figuresNames1.size(); i++) {
                //Поиск выбранной фигуры
                if (figuresNames1.get(i) == figureChoiceBox1.getValue()) {
                    //Смещение точек
                    figures.get(i).move(newPoint);
                }
            }
            context.setFill(Color.WHITE);
            context.fillRect(0, 0, canvas1.getWidth(), canvas1.getHeight());
            for (int i = 0; i < figuresNames1.size(); i++) {
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
            for (int i = 0; i < figuresNames1.size(); i++) {
                //Поиск выбранной фигуры
                if (figuresNames1.get(i) == figureChoiceBox1.getValue()) {
                    //Смещение точек
                    figures.get(i).rotate(angleSlider.getValue(), newPoint.X, newPoint.Y);
                }
            }
            context.setFill(Color.WHITE);
            context.fillRect(0, 0, canvas1.getWidth(), canvas1.getHeight());
            for (int i = 0; i < figuresNames1.size(); i++) {
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
            for (int i = 0; i < figuresNames1.size(); i++) {
                //Поиск выбранной фигуры
                if (figuresNames1.get(i) == figureChoiceBox1.getValue()) {
                    //Смещение точек
                    figures.get(i).upScaling(newPoint);
                }
            }
            context.setFill(Color.WHITE);
            context.fillRect(0, 0, canvas1.getWidth(), canvas1.getHeight());
            for (int i = 0; i < figuresNames1.size(); i++) {
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
            for (int i = 0; i < figuresNames1.size(); i++) {
                //Поиск выбранной фигуры
                if (figuresNames1.get(i) == figureChoiceBox1.getValue()) {
                    //Смещение точек
                    figures.get(i).downScaling(newPoint);
                }
            }
            context.setFill(Color.WHITE);
            context.fillRect(0, 0, canvas1.getWidth(), canvas1.getHeight());
            for (int i = 0; i < figuresNames1.size(); i++) {
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
            for (int i = 0; i < figuresNames1.size(); i++) {
                //Поиск выбранной фигуры
                if (figuresNames1.get(i) == figureChoiceBox1.getValue()) {
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
        if (typeChoiceBox.getValue() == "Разность") {
            int figure1Index =-1; //Индекс 1 фигуры
            int figure2Index =-1; //Индекс 2 фигуры
            Point newPoint = new Point(event.getX(), event.getY());
            PixelWriter pixelWriter = context.getPixelWriter();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    pixelWriter.setColor((int) newPoint.getX() + i, (int) newPoint.getY() + j, Color.BLACK);
                }
            }
            //Поиск фигуры 1
            for (int i = 0; i < figuresNames1.size(); i++) {
                if (figuresNames1.get(i) == figureChoiceBox1.getValue()) {
                    //Смещение точек
                    figure1Index = i;
                }
            }
            //Поиск фигуры 2
            for (int i = 0; i < figuresNames1.size(); i++) {
                if (figuresNames2.get(i) == figureChoiceBox2.getValue()) {
                    figure2Index = i;
                }
            }
            //Закрашивание экрана
            context.setFill(Color.WHITE);
            context.fillRect(0, 0, canvas1.getWidth(), canvas1.getHeight());
            //ТМО                      figurePoints, "Стрелка" + figures.size(), decodeColor(colorChoiceBox.getValue())
            figures.add(new CustomFigure()); //figures.add(new CustomFigure("ТМО" + lastIndex, colorDialog.Color, (ITMOable)FigureList[TMOFirstSelect], (ITMOable)FigureList[TMOLastSelect], TMOtype));
            //"Сим. разность"==1
            //"Разность"==2
            //Удаление данных о фигурах
            figures.remove(figure1Index);
            figures.remove(figure2Index);
            figuresNames1.remove(figure1Index);
            figuresNames1.remove(figure2Index);
            figuresNames2.remove(figure1Index);
            figuresNames2.remove(figure2Index);
            //Отрисовка фигур
            for (int i = 0; i < figures.size(); i++) {
                //Установление цвета фигуры
                context.setStroke(figures.get(i).color);
                figures.get(i).print(context, canvas1);
            }
        }
        figureChoiceBox1.setItems(figuresNames1);
        figureChoiceBox2.setItems(figuresNames2);
    }

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