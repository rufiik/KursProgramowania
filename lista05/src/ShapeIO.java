import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
        Object obj = in.readObject();
        if (obj instanceof List<?>) {
            List<?> list = (List<?>) obj;
            List<ShapeData> shapeDataList = new ArrayList<>();
            for (Object item : list) {
                if (item instanceof ShapeData) {
                    shapeDataList.add((ShapeData) item);
                } else {
                    throw new InvalidObjectException("Expected List<ShapeData>");
                }
            }
            return shapeDataList;
        }
    }
    throw new InvalidObjectException("Expected List<ShapeData>");
}
}