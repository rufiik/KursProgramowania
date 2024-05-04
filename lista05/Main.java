import javax.print.attribute.standard.PageRanges;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.scene.Node;
import javafx.scene.shape.Shape;
import javafx.scene.input.ScrollEvent;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseButton;
public class Main extends Application {
    @Override
    public void start(Stage stage) {

        stage.setTitle("Menu");
        StackPane panel = new StackPane();
        Pane panel2 = new Pane();
       
        Circle circle = new Circle(300, 300, 100);
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(5);
        circle.setTranslateY(-275);

        Rectangle rectangle = new Rectangle(200, 420, 200, 200);
        rectangle.setFill(Color.WHITE);
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeWidth(5);
        rectangle.setTranslateY(-50);

        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(new Double[]{
            300.0, 13.4, 
            200.0, 186.6,   
            400.0, 186.6 
        });
        triangle.setFill(Color.WHITE);
        triangle.setStroke(Color.BLACK);
        triangle.setStrokeWidth(5);
        triangle.setTranslateY(175);

    
       
        Button info = new Button("Info");
        info.setOnAction(event -> {
            // Tworzenie okna dialogowego z informacjami
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informacje o programie");
            alert.setHeaderText("Nazwa: Prosty edytor graficzny \nPrzeznaczenie: Rysowanie figur \nAutor: Rafal Wochna 279752");
            alert.setContentText("Instrukcja uzywania:\nNaciskajac figure w glownym oknie mozesz ja narysowac na plotnie\nNaciskajac figure na plotnie mozesz ja aktywowac tj. edytowac:\nprzesunac, zmienic rozmiar za pomoca scrolla lub obrocic ja ... \nMozesz takze zapisac figure do pliku oraz wczytac ja z pliku \nPod prawym przyciskiem mozesz zmienic kolor aktywnej figury");
            alert.showAndWait();
        });
        info.setPrefWidth(200);
        info.setPrefHeight(100);
        info.setStyle("-fx-font-size: 30px;");
        info.setTranslateY(350);

        panel.getChildren().addAll(circle, rectangle, triangle, info);


        Scene scene = new Scene(panel, 600, 800, Color.WHITESMOKE);

        stage.centerOnScreen();
        stage.setScene(scene);
        stage.show();
      
        Stage newStage = new Stage();
        Scene newScene = new Scene(panel2, 1000, 1000);
        newStage.setScene(newScene);
        Canvas canvas = new Canvas();
        canvas.widthProperty().bind(panel2.widthProperty());
        canvas.heightProperty().bind(panel2.heightProperty());
        GraphicsContext gc = canvas.getGraphicsContext2D();
        panel2.getChildren().add(canvas);
        newStage.setTitle("Paint");

        circle.setOnMouseClicked(e -> plotno(canvas,panel2,newStage,'c'));
        rectangle.setOnMouseClicked(e -> plotno(canvas,panel2,newStage,'r'));
        triangle.setOnMouseClicked(e -> plotno(canvas,panel2,newStage,'t'));
    }
    

private void modify(Shape shape,Pane panel2) {
    shape.setOnMousePressed(e1 -> {
        // Obliczamy różnicę pomiędzy pozycją myszy a pozycją figury
        double deltaX = e1.getSceneX() - shape.getTranslateX();
        double deltaY = e1.getSceneY() - shape.getTranslateY();
        
        // Przesunięcie trójkąta o tę różnicę
        shape.setOnMouseDragged(ev -> {
            shape.setTranslateX(ev.getSceneX() - deltaX);
            shape.setTranslateY(ev.getSceneY() - deltaY);
        });
    });
    shape.setOnMouseClicked(event -> {
        if (event.getButton() == MouseButton.SECONDARY) {
            System.out.println("Prawy przycisk myszy");
            ColorPicker colorPicker = new ColorPicker();
            colorPicker.setTranslateX(event.getSceneX());
        colorPicker.setTranslateY(event.getSceneY());
            panel2.getChildren().add(colorPicker);
            colorPicker.setOnAction(e -> {
                shape.setFill(colorPicker.getValue());
                panel2.getChildren().remove(colorPicker);
            });
        }
    });
}
private void ScaleHandler(Shape shape, ScrollEvent event) {
    double scale = 1.1;
    double deltaY = event.getDeltaY();
    double minScale = 0.5;
    double maxScale = 3.0;
    if (deltaY < 0){ //zmniejszanie
        scale = 1/1.1;
        if(shape.getScaleX() > minScale && shape.getScaleY() > minScale){
            shape.setScaleX(shape.getScaleX() * scale);
            shape.setScaleY(shape.getScaleY() * scale);
        }
    }
    else{ //zwiekszanie
        if(shape.getScaleX() < maxScale && shape.getScaleY() < maxScale){
            shape.setScaleX(shape.getScaleX() * scale);
            shape.setScaleY(shape.getScaleY() * scale);
        }

  
}
}
    private void plotno(Canvas canvas,Pane panel2,Stage newStage,char figure) {
    

        newStage.show();
panel2.setOnMouseClicked(e -> {
    Node target = e.getPickResult().getIntersectedNode();
    if (target == canvas) {
            if(figure=='c'){
                Circle circle = new Circle(e.getX(), e.getY(), 100);
                circle.setFill(Color.WHITE);
                circle.setStroke(Color.BLACK);
                circle.setStrokeWidth(5);
                panel2.getChildren().add(circle);
                circle.setOnScroll(e1 -> ScaleHandler(circle, e1));
                modify(circle,panel2);
            }
            else if(figure=='r'){
                Rectangle rectangle = new Rectangle(e.getX(), e.getY(), 200, 200);
                rectangle.setFill(Color.WHITE);
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeWidth(5);
                panel2.getChildren().add(rectangle);
                rectangle.setOnScroll(e1 -> ScaleHandler(rectangle, e1));
                modify(rectangle,panel2);
            }
            else if(figure=='t'){
                Polygon triangle = new Polygon();
                triangle.getPoints().addAll(new Double[]{
                    e.getX(), e.getY(), 
                    e.getX()-100, e.getY()+173.2,   
                    e.getX()+100, e.getY()+173.2 
                });
                triangle.setFill(Color.WHITE);
                triangle.setStroke(Color.BLACK);
                triangle.setStrokeWidth(5);
                panel2.getChildren().add(triangle);
                triangle.setOnScroll(e1 -> ScaleHandler(triangle, e1));
                modify(triangle,panel2);
                

    }
    
}
}   
);  
}
    public static void main(String args[]){
        launch(args);
     }
}
