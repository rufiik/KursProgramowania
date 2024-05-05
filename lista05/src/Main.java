import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseButton;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.geometry.Insets;
import javafx.scene.control.Slider;


/**
 * Prosty edytor graficzny
 * @author Rafal Wochna 279752
 
 */
public class Main extends Application {
    /**
     * selectedShape - zmienna przechowujaca aktualnie zaznaczona figure
     */
    private Shape selectedShape = null;
    /**
     * shapes - lista przechowujaca informacje o figurach
     */
    private List<ShapeData> shapes = new ArrayList<>(); 
    /**
     * rotateListener - Listener obrotu figury
     * obs - obiekt obserwujacy
     * oldVal - stara wartosc
     *  newVal - nowa wartosc
     */
    private ChangeListener<Number> rotateListener = (obs, oldVal, newVal) -> {
        if (selectedShape != null) {
            selectedShape.setRotate(newVal.doubleValue());
        }
    };
    /**
     * shapeDataMap - mapa przechowujaca informacje o figurach (przydante do wczytywania figur z pliku)
     */
    Map<Shape, ShapeData> shapeDataMap = new HashMap<>();
    /**
     * start - funkcja inicjalizujaca okno
     * @param stage - okno
     */
    @Override
    public void start(Stage stage) {
/**
 * Ustawienia okna
 * @param stage - okno
 * @param przyciski - panel przyciskow
 * @param button1 - przycisk do rysowania okregu
 * @param button2 - przycisk do rysowania prostokata
 * @param button3 - przycisk do rysowania trojkata
 * @param info - przycisk informacji
 * @param drawingPane - panel do rysowania
 * @param canvas - plotno do rysowania
 * @param root - panel glowny
 * @param scene - scena
 * @param slider - slider do obrotu
 * @param colorPicker - colorPicker do zmiany koloru
 * @param saveButton - przycisk zapisu
 * @param loadButton - przycisk wczytania
 * @param shape - figura
 * @param shapeData - informacje o figurze
 */
        stage.setTitle("Menu");
        HBox przyciski = new HBox();
        Button button1 = new Button("Okrag");
        Button button2 = new Button("Prostokat");
        Button button3 = new Button("Trojkat");
        Button info = new Button("Info");
        info.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informacje o programie");
            alert.setHeaderText("Nazwa: Prosty edytor graficzny \nPrzeznaczenie: Rysowanie figur \nAutor: Rafal Wochna 279752");
            alert.setContentText("Instrukcja uzywania:\nNaciskajac figure w glownym oknie mozesz ja narysowac na plotnie\nNaciskajac figure na plotnie mozesz ja aktywowac tj. edytowac:\nprzesunac, zmienic rozmiar za pomoca scrolla lub obrocic ja ... \nMozesz takze zapisac figure do pliku oraz wczytac ja z pliku \nPod prawym przyciskiem mozesz zmienic kolor aktywnej figury");
            alert.showAndWait();
        });
        przyciski.setPrefHeight(200);
        button1.setMinWidth(100);
        button2.setMinWidth(100);
        button3.setMinWidth(100);
        info.setMinWidth(100);
        przyciski.getChildren().addAll(button1, button2, button3, info);
        Pane drawingPane = new Pane();
        Canvas canvas = new Canvas(1000,800);
        drawingPane.getChildren().add(canvas);
        VBox root = new VBox(przyciski, drawingPane);
        Scene scene = new Scene(root, 1000, 800);
/**
 * Listener do zmiany rozmiaru okna
 * @param observable - obiekt obserwujacy
 * @param oldValue - stara wartosc
 * @param newValue - nowa wartosc
 */
        scene.widthProperty().addListener((observable, oldValue, newValue) -> {
            canvas.setWidth(newValue.doubleValue());
        });
        
        scene.heightProperty().addListener((observable, oldValue, newValue) -> {
            canvas.setHeight(newValue.doubleValue());
        });
/**
 * Slider do obrotu figur od 0 do 360 stopni
 */
        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(360);
        slider.setValue(0);
        przyciski.getChildren().add(slider);
/**
 * 
 * ColorPicker do zmiany koloru figur
 *  
 */
        ColorPicker colorPicker = new ColorPicker();
        przyciski.getChildren().add(colorPicker);
        stage.setScene(scene);
        stage.setTitle("Paint");
        stage.show();


/**
 * Przyciski do zapisu i wczytania figur z pliku
 */
        Button saveButton = new Button("Zapisz");
        saveButton.setOnAction(event -> {

            /**
             * Zapisywanie figur do pliku
             * filename - nazwa pliku
             * IOException - wyjatek w przypadku bledu zapisu
             * 
             */
            try {
                shapes.clear();
                shapes.addAll(shapeDataMap.values());
                ShapeIO.saveShapes(shapes, "shapes.dat");
            } catch (IOException e) {
                System.err.println("Nie udalo sie zapisac figur: " + e);
            }
        });
        Button loadButton = new Button("Wczytaj");
        loadButton.setOnAction(event -> {
            /**
             * Wczytywanie figur z pliku
             * shapes - lista figur
             * filename - nazwa pliku
             * IOException - wyjatek w przypadku bledu wczytania
             * ClassNotFoundException - wyjatek w przypadku bledu wczytania
             * @param shape - figura
             */
            try {
                shapes = ShapeIO.loadShapes("shapes.dat");
                 drawingPane.getChildren().clear();
                 if (!drawingPane.getChildren().contains(canvas)) {
                    drawingPane.getChildren().add(canvas);
                }
                for (ShapeData shapeData : shapes) {
                    Shape shape = null;
                    if (shapeData.getType().equals("Circle")) {
                        shape = new Circle(shapeData.getX(), shapeData.getY(), shapeData.getRadius());
                    } else if (shapeData.getType().equals("Rectangle")) {
                        shape = new Rectangle(shapeData.getX(), shapeData.getY(), shapeData.getWidth(), shapeData.getHeight());
                    } else if (shapeData.getType().equals("Polygon")) {
                        // Tworzenie trójkąta na podstawie wczytanych danych
                        Polygon triangle = new Polygon();
                        triangle.getPoints().addAll(new Double[]{
                                shapeData.getX(), shapeData.getY(),
                                shapeData.getX() - shapeData.getRadius(), shapeData.getY() + 86.6,
                                shapeData.getX() + shapeData.getRadius(), shapeData.getY() + 86.6
                        });
                        shape = triangle;
                    }
                    /**
                     * Ustawianie koloru, obrotu, pozostalych wlasciwosci i dodanie do panelu
                     * @param color - kolor figury
                     * @param shapeData - informacje o figurze
                     * @param shape - figura
                     * @param drawingPane - panel do rysowania
                     * @param slider - slider do obrotu
                     * @param colorPicker - colorPicker do zmiany koloru
                     * @param shapeDataMap - mapa przechowujaca informacje o figurach
                     * @param shapes - lista przechowujaca informacje o figurach
                     * 
                     */
                    if (shape != null) {
                        Color color = Color.color(shapeData.getRed(), shapeData.getGreen(), shapeData.getBlue(), shapeData.getOpacity());
                        shape.setFill(color);
                        shape.setRotate(shapeData.getRotation());
                        shape.setStroke(Color.BLACK);
                        shape.setStrokeWidth(5);
                        if (!drawingPane.getChildren().contains(shape)) {
                            drawingPane.getChildren().add(shape);
                        }
                        shape.setScaleX(shapeData.getScaleX());
                        shape.setScaleY(shapeData.getScaleY());
                        modify(shape, slider, colorPicker);
                    }
                }
               
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Nie udało się odczytać figur: " + e);
            }
        });
        przyciski.getChildren().addAll(saveButton, loadButton);
/**
 * Przyciski do rysowania figur
 * @param canvas - plotno do rysowania
 * @param drawingPane - panel do rysowania
 * @param figure - typ figury
 * @param slider - slider do obrotu
 * @param colorPicker - colorPicker do zmiany koloru
 * 
 */
        button1.setOnAction(event -> {
            selectedShape = null;
            plotno(canvas, drawingPane, 'c',slider,colorPicker);
        });
        button2.setOnAction(event -> {
            selectedShape = null;
            plotno(canvas, drawingPane, 'r',slider,colorPicker);
        });
        button3.setOnAction(event -> {
            selectedShape = null;
            plotno(canvas, drawingPane, 't',slider,colorPicker);
        });
    }
    /**
     * Funkcja rysujaca figury na plotnie
     * @param canvas - plotno do rysowania
     * @param panel2 - panel do rysowania
     * @param figure - typ figury
     * @param slider - slider do obrotu
     * @param colorPicker - colorPicker do zmiany koloru
     * Node target - Gdzie naciska uzytkownik
     */
    private void plotno(Canvas canvas, Pane panel2, char figure, Slider slider, ColorPicker colorPicker) {
        panel2.setOnMouseClicked(e -> {
            Node target = e.getPickResult().getIntersectedNode();
            if (target == canvas) {
                if (figure == 'c') {
                    Circle circle = new Circle(0,0, 50);
                    circle.setTranslateX(e.getX());
                    circle.setTranslateY(e.getY());
                    circle.setFill(colorPicker.getValue());
                    circle.setStroke(Color.BLACK);
                    circle.setStrokeWidth(5);
                    circle.setRadius(50);
                    panel2.getChildren().add(circle);
                    modify(circle, slider, colorPicker);
                    //informacje o kształcie dodane do listy shapes
                    ShapeData shapeData=new ShapeData("Circle", circle.getTranslateX(), circle.getTranslateY(), colorPicker.getValue().getRed(), colorPicker.getValue().getGreen(), colorPicker.getValue().getBlue(), colorPicker.getValue().getOpacity(), 100, 100, 50, 0, 1, 1);
                    shapes.add(shapeData);
                    shapeDataMap.put(circle,shapeData);

                } else if (figure == 'r') {
                    Rectangle rectangle = new Rectangle(0,0, 150, 100);
                    rectangle.setTranslateX(e.getX());
                    rectangle.setTranslateY(e.getY());
                    rectangle.setFill(colorPicker.getValue());
                    rectangle.setStroke(Color.BLACK);
                    rectangle.setStrokeWidth(5);
                    panel2.getChildren().add(rectangle);
                    modify(rectangle, slider, colorPicker);
                    // informacje o kształcie dodane do listy shapes
                    ShapeData shapeData=new ShapeData("Rectangle", rectangle.getTranslateX(),rectangle.getTranslateY(), colorPicker.getValue().getRed(), colorPicker.getValue().getGreen(), colorPicker.getValue().getBlue(), colorPicker.getValue().getOpacity(), 150, 100, 0, 0, 1, 1);
                    shapes.add(shapeData);
                    shapeDataMap.put(rectangle,shapeData);
                } else if (figure == 't') {
                    Polygon triangle = new Polygon();
                    triangle.getPoints().addAll(new Double[]{
                            0.0,  0.0,
                            -50.0,  86.6,
                             50.0,  86.6
                    });
                    triangle.setTranslateX(e.getX());
                    triangle.setTranslateY(e.getY());
                    triangle.setFill(colorPicker.getValue());
                    triangle.setStroke(Color.BLACK);
                    triangle.setStrokeWidth(5);
                    panel2.getChildren().add(triangle);
                    modify(triangle, slider, colorPicker);
                    //informacje o kształcie dodane do listy shapes
                    ShapeData shapeData=new ShapeData("Polygon", triangle.getTranslateX(), triangle.getTranslateY(), colorPicker.getValue().getRed(), colorPicker.getValue().getGreen(), colorPicker.getValue().getBlue(), colorPicker.getValue().getOpacity(), 100, 100, 50, 0, 1, 1);
                    shapes.add(shapeData);
                    shapeDataMap.put(triangle,shapeData);

                }
            }
    });
    }
    /**
     * Funkcja modyfikujaca figury
     * @param shape - figura
     * @param slider - slider do obrotu
     * @param colorPicker - colorPicker do zmiany koloru
     */
    private void modify(Shape shape, Slider slider, ColorPicker colorPicker) {
        /**
         * Przesuwanie figury
         * @param deltaX - przesuniecie w osi X
         * @param deltaY - przesuniecie w osi Y
         * @param shapeData - informacje o figurze
         * @param shapeDataMap - mapa przechowujaca informacje o figurach
         * 
         */
        shape.setOnMousePressed(e1 -> {
            double deltaX = e1.getSceneX() - shape.getTranslateX();
            double deltaY = e1.getSceneY() - shape.getTranslateY();
            shape.setOnMouseDragged(ev -> {
                shape.setTranslateX(ev.getSceneX() - deltaX);
                shape.setTranslateY(ev.getSceneY() - deltaY);
                ShapeData shapeData = shapeDataMap.get(shape);
                if (shapeData != null) {
                shapeData.setX(ev.getSceneX() - deltaX);
                shapeData.setY(ev.getSceneY() - deltaY);
                }
              
            });
        });
        /**
         * 
         * Obracanie figury
         * @param shape - figura
         * @param slider - slider do obrotu
         * @param rotateListener - Listener obrotu figury
         * @param selectedShape - zmienna przechowujaca aktualnie zaznaczona figure
         * @param event,e - zdarzenia
         * @param shapeData - informacje o figurze
         * @param shapeDataMap - mapa przechowujaca informacje o figurach
         * 
         */
        shape.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                selectedShape = shape;
                slider.valueProperty().addListener((obs, oldVal, newVal) -> {
                    if (selectedShape != null) {
                        selectedShape.setRotate(newVal.doubleValue());
                        ShapeData shapeData = shapeDataMap.get(selectedShape);
                        if (shapeData != null) {
                            shapeData.setRotation(newVal.doubleValue());
                        }
                    }
                });
                slider.valueProperty().addListener(rotateListener); // Dodaj nowe nasłuchiwanie
                slider.valueProperty().setValue(0);
                colorPicker.setOnAction(e -> {
                    if (selectedShape != null) {

                        Color color = colorPicker.getValue();
                        selectedShape.setFill(color);
                        ShapeData shapeData = shapeDataMap.get(shape);
                        if (shapeData != null) {
                            shapeData.setRed(color.getRed());
                            shapeData.setGreen(color.getGreen());
                            shapeData.setBlue(color.getBlue());
                            shapeData.setOpacity(color.getOpacity());
                        }
                    }
                });
/**
 * 
 * Zmiana rozmiaru figury za pomoca scrolla
 * @param shape - figura
 * @param e1 - zdarzenie
 * @param shapeData - informacje o figurze
 * @param shapeDataMap - mapa przechowujaca informacje o figurach
 * 
 */
        shape.setOnScroll(e1 -> {
            ScaleHandler(shape, e1);
            ShapeData shapeData = shapeDataMap.get(shape);
            if (shapeData != null) {
                shapeData.setScaleX(shape.getScaleX());
                shapeData.setScaleY(shape.getScaleY());
            }
        });
        
    }
        });

    }

    /**
     * Zmiana rozmiaru figury za pomoca scrolla
     * @param shape - figura
     * @param event - zdarzenie
     *ScrollEvent - zdarzenie scrolla
     * 
     */
    private void ScaleHandler(Shape shape, ScrollEvent event) {
        /**
         * scale - zmienna przechowujaca skale
         * deltaY - zmienna przechowujaca przesuniecie
         * minScale - minimalna skala
         * maxScale - maksymalna skala
         * 
         */
        double scale = 1.1;
        double deltaY = event.getDeltaY();
        double minScale = 0.5;
        double maxScale = 3.0;
        /**
         * zmniejszanie i zwiekszanie figury
         */
        if (deltaY < 0) { 
            scale = 1 / 1.1;
            if (shape.getScaleX() > minScale && shape.getScaleY() > minScale) {
                shape.setScaleX(shape.getScaleX() * scale);
                shape.setScaleY(shape.getScaleY() * scale);
            }
        } else {
            if (shape.getScaleX() < maxScale && shape.getScaleY() < maxScale) {
                shape.setScaleX(shape.getScaleX() * scale);
                shape.setScaleY(shape.getScaleY() * scale);
            }
        }
    }

/**
 * ShapeIO - klasa do zapisywania i wczytywania figur
 */
public class ShapeIO {
/**
 * saveShapes - zapisywanie figur do pliku
 * @param filename - nazwa pliku
 * @throws IOException - wyjatek w przypadku bledu zapisu
 */
    public static void saveShapes(List<ShapeData> shapes, String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(shapes);
        }
    }
/**
 * loadShapes - wczytywanie figur z pliku
 * @param filename
 * @return lista figur
 * @throws IOException - wyjatek w przypadku bledu wczytania     
 * @throws ClassNotFoundException - wyjatek w przypadku bledu wczytania  
 */
    public static List<ShapeData> loadShapes(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<ShapeData>) in.readObject();
        }
    }
}

/**
 * Klasa main startujaca aplikacje
 * @param args argumenty
 * 
 */
    public static void main(String args[]) {
        launch(args);
    }
}
