import java.io.Serializable;
import javafx.scene.paint.Color;

public class ShapeData implements Serializable {
    private String type;
    private double x;
    private double y;
    private double red; // Czerwony składnik koloru
    private double green; // Zielony składnik koloru
    private double blue; // Niebieski składnik koloru
    private double opacity; // Opcjonalny składnik koloru (alfa)
    private double width;
    private double height;
    private double radius;
    private double rotation;
    private double scaleX;
    private double scaleY;

    public ShapeData(String type, double x, double y, double red, double green, double blue, double opacity,double width, double height, double radius, double rotation, double scaleX, double scaleY) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.opacity = opacity;
        this.width = width;
        this.height = height;
        this.radius = radius;
        this.rotation = rotation;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    public String getType() {
        return type;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getRed() {
        return red;
    }

    public double getGreen() {
        return green;
    }

    public double getBlue() {
        return blue;
    }

    public double getOpacity() {
        return opacity;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getRadius() {
        return radius;
    }

    public double getRotation() {
        return rotation;
    }

    public double getScaleX() {
        return scaleX;
    }

    public double getScaleY() {
        return scaleY;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setRed(double red) {
        this.red = red;
    }

    public void setGreen(double green) {
        this.green = green;
    }

    public void setBlue(double blue) {
        this.blue = blue;
    }

    public void setOpacity(double opacity) {
        this.opacity = opacity;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public void setScaleX(double scaleX) {
        this.scaleX = scaleX;
    }

    public void setScaleY(double scaleY) {
        this.scaleY = scaleY;
    }
}