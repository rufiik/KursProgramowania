import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;    
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import java.util.ArrayList;
import java.util.List;
/**
 * Klasa rysujaca figury na plotnie
 */
public class Drawing{ 
    private List<ShapeData> shapes = new ArrayList<>();
    private Shape selectedShape = null;
    private ShapeModifier shapeModifier;

    public Drawing(List <ShapeData> shapes, Shape selectedShape) {
        this.shapes = shapes;
        this.selectedShape = selectedShape;
        this.shapeModifier = new ShapeModifier(shapes, selectedShape);
    }
    /**
     * Funkcja rysujaca figury na plotnie
     * @param canvas - plotno do rysowania
     * @param drawingPane - panel do rysowania
     * @param figure - typ figury
     * @param slider - slider do obrotu
     * @param colorPicker - colorPicker do zmiany koloru
     * Node target - Gdzie naciska uzytkownik
     */
    public void plotno(Canvas canvas, Pane drawingPane, char figure, Slider slider, ColorPicker colorPicker, List<ShapeData> shapes) {
        drawingPane.setOnMouseClicked(e -> {
            Node target = e.getPickResult().getIntersectedNode();
            if (target == canvas) {
                if (figure == 'c') {
                    Circle circle = new Circle(0,0, 50);
                    circle.setRadius(50);
                    stroke(circle);
                    circle.setTranslateX(e.getX());
                    circle.setTranslateY(e.getY());
                    circle.setFill(colorPicker.getValue());
                    
                    
                    //informacje o kształcie dodane do listy shapes
                    ShapeData shapeData=new ShapeData("Circle",circle.getTranslateX(), circle.getTranslateY(), circle.getBoundsInParent().getMinX(), circle.getBoundsInParent().getMaxX(),circle.getBoundsInParent().getMinY(),circle.getBoundsInParent().getMaxY(), colorPicker.getValue().getRed(), colorPicker.getValue().getGreen(), colorPicker.getValue().getBlue(), colorPicker.getValue().getOpacity(), 100, 100, 50, 0, 1, 1);
                    shapeData.setWidth(circle.getBoundsInParent().getWidth());
                    shapeData.setHeight(circle.getBoundsInParent().getHeight());
                    shapes.add(shapeData);
                    drawingPane.getChildren().add(circle);
                    // System.out.println(shapes+" "+shapeData);
                    // System.out.println(shapeData==shapes.get(0)); 
                    shapeModifier.modify(circle, slider, colorPicker);

                } else if (figure == 'r') {
                    Rectangle rectangle = new Rectangle(0,0, 150, 100);
                    stroke(rectangle);
                    rectangle.setTranslateX(e.getX());
                    rectangle.setTranslateY(e.getY());
                    rectangle.setFill(colorPicker.getValue());
                
                    // informacje o kształcie dodane do listy shapes
                    ShapeData shapeData=new ShapeData("Rectangle",rectangle.getTranslateX(),rectangle.getTranslateY(), rectangle.getBoundsInParent().getMinX(), rectangle.getBoundsInParent().getMaxX(),rectangle.getBoundsInParent().getMinY(),rectangle.getBoundsInParent().getMaxY(), colorPicker.getValue().getRed(), colorPicker.getValue().getGreen(), colorPicker.getValue().getBlue(), colorPicker.getValue().getOpacity(), 100, 150, 50, 0, 1, 1); 
                    shapeData.setWidth(rectangle.getBoundsInParent().getWidth());
                    shapeData.setHeight(rectangle.getBoundsInParent().getHeight());
                    shapes.add(shapeData);
                    drawingPane.getChildren().add(rectangle);
                    shapeModifier.modify(rectangle, slider, colorPicker);
                } else if (figure == 't') {
                    Polygon triangle = new Polygon();
                    triangle.getPoints().addAll(new Double[]{
                            0.0,  0.0,
                            -50.0,  86.0,
                             50.0,  86.0
                    });
                    stroke(triangle);
                    triangle.setTranslateX(e.getX());
                    triangle.setTranslateY(e.getY());
                    triangle.setFill(colorPicker.getValue());
                
                    //informacje o kształcie dodane do listy shapes
                    ShapeData shapeData=new ShapeData("Polygon",triangle.getTranslateX(), triangle.getTranslateY(), triangle.getBoundsInParent().getMinX(), triangle.getBoundsInParent().getMaxX(),triangle.getBoundsInParent().getMinY(),triangle.getBoundsInParent().getMaxY(), colorPicker.getValue().getRed(), colorPicker.getValue().getGreen(), colorPicker.getValue().getBlue(), colorPicker.getValue().getOpacity(), 104, 89, 50, 0, 1, 1);
                    shapeData.setWidth(triangle.getBoundsInParent().getWidth());
                    shapeData.setHeight(triangle.getBoundsInParent().getHeight());
                    shapes.add(shapeData);
                    drawingPane.getChildren().add(triangle);
                    shapeModifier.modify(triangle, slider, colorPicker);

                }
            }
    });
    }
    /**
     * Funkcja ustawiajaca obramowke figury
     * @param shape - figura
     */

     private void stroke(Shape shape){
        shape.setStroke(Color.BLACK);
        shape.setStrokeWidth(5);
   }    
}