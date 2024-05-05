import java.io.Serializable;
import javafx.scene.paint.Color;
import java.io.Serializable;
/**
 * Klasa przechowująca dane o kształcie do rysowania.
   * Klasa przechowuje informacje o kształcie do narysowania, takie jak: typ kształtu, współrzędne x i y, składowe koloru (czerwony, zielony, niebieski, opcjonalnie alfa), szerokość, wysokość, promień, rotacja, skala w osi x i skala w osi y.
   * Klasa implementuje interfejs Serializable, co pozwala na zapisywanie obiektów tej klasy do pliku. 
 */

public class ShapeData implements Serializable {
    private String type;
    private double x; // X współrzędna kształtu
    private double y; // Y  współrzędna kształtu
    private double red; // czerwony kolor componentu
    private double green; // zielony kolor componentu     
    private double blue; // niebieski kolor componentu
    private double opacity; // przezroczystość kształtu
    private double width; // szerokość kształtu
    private double height; // wysokość kształtu
    private double radius; // promień kształtu
    private double rotation;    // obrót kształtu
    private double scaleX; // Skala w osi x
    private double scaleY; // Skala w osi y

    /**
     * Constructs a ShapeData object with the specified parameters.
     *
     * @param type - typ kształtu
     * @param x - współrzędna kształtu
     * @param y - współrzędna kształtu
     * @param red - składowa czerwona koloru kształtu
     * @param green - składowa zielona koloru kształtu
     * @param blue - składowa niebieska koloru kształtu
     * @param opacity - wartość przezroczystości kształtu
     * @param width - szerokość kształtu
     * @param height - wysokość kształtu
     * @param radius - promień kształtu
     * @param rotation - wartość rotacji kształtu
     * @param scaleX - skala w osi x kształtu
     * @param scaleY - skala w osi y kształtu
     */
    public ShapeData(String type, double x, double y, double red, double green, double blue, double opacity, double width, double height, double radius, double rotation, double scaleX, double scaleY) {
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
//
/**
 * Zwraca typ kształtu.
 *
 * @return typ kształtu
 */
public String getType() {
    return type;
}

/**
 * Zwraca współrzędną x kształtu.
 *
 * @return współrzędna x kształtu
 */
public double getX() {
    return x;
}

/**
 * Zwraca współrzędną y kształtu.
 *
 * @return współrzędna y kształtu
 */
public double getY() {
    return y;
}

/**
 * Zwraca składową czerwoną koloru kształtu.
 *
 * @return składowa czerwona koloru kształtu
 */
public double getRed() {
    return red;
}

/**
 * Zwraca składową zieloną koloru kształtu.
 *
 * @return składowa zielona koloru kształtu
 */
public double getGreen() {
    return green;
}

/**
 * Zwraca składową niebieską koloru kształtu.
 *
 * @return składowa niebieska koloru kształtu
 */
public double getBlue() {
    return blue;
}

/**
 * Zwraca wartość przezroczystości kształtu.
 *
 * @return wartość przezroczystości kształtu
 */
public double getOpacity() {
    return opacity;
}

/**
 * Zwraca szerokość kształtu.
 *
 * @return szerokość kształtu
 */
public double getWidth() {
    return width;
}

/**
 * Zwraca wysokość kształtu.
 *
 * @return wysokość kształtu
 */
public double getHeight() {
    return height;
}

/**
 * Zwraca promień kształtu.
 *
 * @return promień kształtu
 */
public double getRadius() {
    return radius;
}

/**
 * Zwraca wartość rotacji kształtu.
 *
 * @return wartość rotacji kształtu
 */
public double getRotation() {
    return rotation;
}

/**
 * Zwraca skalę w osi x kształtu.
 *
 * @return skala w osi x kształtu
 */
public double getScaleX() {
    return scaleX;
}

/**
 * Zwraca skalę w osi y kształtu.
 *
 * @return skala w osi y kształtu
 */
public double getScaleY() {
    return scaleY;
}

/**
 * Ustawia typ kształtu.
 *
 * @param type typ kształtu
 */
public void setType(String type) {
    this.type = type;
}

/**
 * Ustawia współrzędną x kształtu.
 *
 * @param x współrzędna x kształtu
 */
public void setX(double x) {
    this.x = x;
}

/**
 * Ustawia współrzędną y kształtu.
 *
 * @param y współrzędna y kształtu
 */
public void setY(double y) {
    this.y = y;
}

/**
 * Ustawia składową czerwoną koloru kształtu.
 *
 * @param red składowa czerwona koloru kształtu
 */
public void setRed(double red) {
    this.red = red;
}

/**
 * Ustawia składową zieloną koloru kształtu.
 *
 * @param green składowa zielona koloru kształtu
 */
public void setGreen(double green) {
    this.green = green;
}

/**
 * Ustawia składową niebieską koloru kształtu.
 *
 * @param blue składowa niebieska koloru kształtu
 */
public void setBlue(double blue) {
    this.blue = blue;
}

/**
 * Ustawia wartość przezroczystości kształtu.
 *
 * @param opacity wartość przezroczystości kształtu
 */
public void setOpacity(double opacity) {
    this.opacity = opacity;
}

/**
 * Ustawia szerokość kształtu.
 *
 * @param width szerokość kształtu
 */
public void setWidth(double width) {
    this.width = width;
}

/**
 * Ustawia wysokość kształtu.
 *
 * @param height wysokość kształtu
 */
public void setHeight(double height) {
    this.height = height;
}

/**
 * Ustawia promień kształtu.
 *
 * @param radius promień kształtu
 */
public void setRadius(double radius) {
    this.radius = radius;
}

/**
 * Ustawia wartość rotacji kształtu.
 *
 * @param rotation wartość rotacji kształtu
 */
public void setRotation(double rotation) {
    this.rotation = rotation;
}

/**
 * Ustawia skalę w osi x kształtu.
 *
 * @param scaleX skala w osi x kształtu
 */
public void setScaleX(double scaleX) {
    this.scaleX = scaleX;
}

/**
 * Ustawia skalę w osi y kształtu.
 *
 * @param scaleY skala w osi y kształtu
 */
public void setScaleY(double scaleY) {
    this.scaleY = scaleY;
}
}
