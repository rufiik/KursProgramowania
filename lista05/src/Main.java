import java.io.*;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
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
    ShapeModifier shapeModifier = new ShapeModifier(shapes, selectedShape);
    private Drawing drawing = new Drawing(shapes, selectedShape);
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
            alert.setContentText("Instrukcja uzywania:\nNaciskajac figure w glownym oknie mozesz ja narysowac na plotnie\nNaciskajac figure na plotnie mozesz ja aktywowac tj. edytowac:\nprzesunac, zmienic rozmiar za pomoca scrolla lub obrocic ja aktywujac i uzywajac scrolla \nMozesz takze zapisac figure do pliku oraz wczytac ja z pliku \nPod prawym przyciskiem mozesz zmienic kolor aktywnej figury");
            alert.showAndWait();
        });
        
        przyciski.setPrefHeight(25);
        button1.setMinWidth(100);
        button2.setMinWidth(100);
        button3.setMinWidth(100);
        info.setMinWidth(100);
        przyciski.getChildren().addAll(button1, button2, button3, info);
        Pane drawingPane = new Pane();
        Rectangle clip = new Rectangle(1000, 800);
        Canvas canvas = new Canvas(1000,800);
        clip.widthProperty().bind(canvas.widthProperty());
clip.heightProperty().bind(canvas.heightProperty());
// Set the clip on the drawingPane
drawingPane.setClip(clip);
        drawingPane.getChildren().add(canvas);
        BorderPane root = new BorderPane();
        root.setTop(przyciski);
        root.setCenter(drawingPane);
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
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Zapisz jako");
    File file = fileChooser.showSaveDialog(saveButton.getScene().getWindow());
    if (file != null) {
    try {
        ShapeIO.saveShapes(shapes, file.getAbsolutePath());
    } catch (IOException e) {
        System.err.println("Nie udalo sie zapisac figur: " + e);
    }
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
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Wybierz plik");
    File file = fileChooser.showOpenDialog(loadButton.getScene().getWindow());
    if (file != null) {
        try {
            shapes.clear();
            shapes = ShapeIO.loadShapes(file.getAbsolutePath());
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
                        shapeData.getX() - shapeData.getRadius(), shapeData.getY() + 86.0,
                        shapeData.getX() + shapeData.getRadius(), shapeData.getY() + 86.0
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
                
                shapeModifier.modify(shape, slider, colorPicker);
                
            }
           
        }
       
    } catch (IOException | ClassNotFoundException e) {
        System.err.println("Nie udało się odczytać figur: " + e);
    }
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
            drawing.plotno(canvas, drawingPane, 'c',slider,colorPicker,shapes);
        });
        button2.setOnAction(event -> {
            selectedShape = null;
            drawing.plotno(canvas, drawingPane, 'r',slider,colorPicker,shapes);
        });
        button3.setOnAction(event -> {
            selectedShape = null;
            drawing.plotno(canvas, drawingPane, 't',slider,colorPicker,shapes);
        });
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
