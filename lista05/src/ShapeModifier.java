import javafx.scene.shape.Shape;
import javafx.scene.control.Slider;
import javafx.scene.control.ColorPicker;
import java.util.List;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * ShapeModifier - klasa modyfikujaca figury
 */
public class ShapeModifier {

    private List<ShapeData> shapes;
    private Shape selectedShape = null;
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
    public ShapeModifier(List<ShapeData> shapes, Shape selectedShape) {
        this.shapes = shapes;
        this.selectedShape = selectedShape;
    }
    /**
     * Funkcja modyfikujaca figury
     * @param shape - figura
     * @param slider - slider do obrotu
     * @param colorPicker - colorPicker do zmiany koloru
     */
    public void modify(Shape shape, Slider slider, ColorPicker colorPicker) {
        

        /**
         * Przesuwanie figury
         * @param deltaX - przesuniecie w osi X
         * @param deltaY - przesuniecie w osi Y
         * @param shapeData - informacje o figurze
         * shapes.get(i) - informacje o figurze
         */
       
        shape.setOnMousePressed(e1 -> {
            int j[]=new int[1];
        for(int i=0;i<shapes.size();i++){
            // System.out.println(shapes.get(i).getX()+ " "+shapes.get(i).getY() + " "+shapes.get(i).getWidth() + " "+shapes.get(i).getHeight());
            // System.out.println(shape.getBoundsInParent().getWidth()+ " "+shape.getBoundsInParent().getHeight());
            if((shape.getBoundsInParent().getMinX()<=shapes.get(i).getX() && shapes.get(i).getX()<=shape.getBoundsInParent().getMaxX()) && shapes.get(i).getWidth()==shape.getBoundsInParent().getWidth() && shapes.get(i).getHeight()==shape.getBoundsInParent().getHeight()){
                     j[0]=i;
            }
        }
            double deltaX = e1.getSceneX() - shape.getTranslateX();
            double deltaY = e1.getSceneY() - shape.getTranslateY();
            shape.setOnMouseDragged(ev -> {
                shapes.get(j[0]).setX(ev.getSceneX() - deltaX);
                shapes.get(j[0]).setY(ev.getSceneY() - deltaY);
                shapes.get(j[0]).setMinX(shape.getBoundsInParent().getMinX());
                shapes.get(j[0]).setMaxX(shape.getBoundsInParent().getMaxX());
                shapes.get(j[0]).setMinY(shape.getBoundsInParent().getMinY());
                shapes.get(j[0]).setMaxY(shape.getBoundsInParent().getMaxY());
                shape.setTranslateX(ev.getSceneX() - deltaX);
                shape.setTranslateY(ev.getSceneY() - deltaY);
   
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
         * 
         */
       
        shape.setOnMouseClicked(event -> {
            
            if (event.getButton() == MouseButton.SECONDARY) {
                int j[]=new int[1];
            for(int i=0;i<shapes.size();i++){
                if((shape.getBoundsInParent().getMinX()<=shapes.get(i).getX() && shapes.get(i).getX()<=shape.getBoundsInParent().getMaxX()) && shapes.get(i).getWidth()==shape.getBoundsInParent().getWidth() && shapes.get(i).getHeight()==shape.getBoundsInParent().getHeight()){
                         j[0]=i;

                }
            }
                if (rotateListener != null) {
                    slider.valueProperty().removeListener(rotateListener);
                }
                rotateListener = (obs, oldVal, newVal) -> {
                    shape.setRotate(newVal.doubleValue());
                    shapes.get(j[0]).setRotation(newVal.doubleValue());
                };
                slider.valueProperty().addListener(rotateListener);
                colorPicker.setOnAction(e -> {
                        Color color = colorPicker.getValue();
                        shape.setFill(color);
                        shapes.get(j[0]).setRed(color.getRed());
                        shapes.get(j[0]).setGreen(color.getGreen());
                        shapes.get(j[0]).setBlue(color.getBlue());
                        shapes.get(j[0]).setOpacity(color.getOpacity());
                    
                });
/**
 * 
 * Zmiana rozmiaru figury za pomoca scrolla
 * @param shape - figura
 * @param e1 - zdarzenie
 * @param shapeData - informacje o figurze
 * 
 */
        shape.setOnScroll(e1 -> {
            ScaleHandler(shape, e1);
            shapes.get(j[0]).setScaleX(shape.getScaleX());
            shapes.get(j[0]).setScaleY(shape.getScaleY());
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
        if (deltaY < 0 && deltaY!=0) { 
            scale = 1 / 1.1;
            if (shape.getScaleX() > minScale && shape.getScaleY() > minScale) {
                shape.setScaleX(shape.getScaleX() * scale);
                shape.setScaleY(shape.getScaleY() * scale);
            }
        } else if(deltaY > 0 && deltaY!=0){
            if (shape.getScaleX() < maxScale && shape.getScaleY() < maxScale) {
                shape.setScaleX(shape.getScaleX() * scale);
                shape.setScaleY(shape.getScaleY() * scale);
            }
        }
    }
    }
