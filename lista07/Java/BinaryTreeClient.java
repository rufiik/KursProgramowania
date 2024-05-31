// Plik: BinaryTreeClient.java
// Autor: Rafał Wochna 279752
import java.io.*;
import java.net.*;
/**
 * Klient dla serwera drzewa binarnego.
 */
public class BinaryTreeClient {
    private static final String ADRES_SERWERA = "localhost"; /** Adres serwera. */
    private static final int PORT_SERWERA = 12345; /** Port serwera. */
    private Socket socket; /** Gniazdo do komunikacji z serwerem. */
    private ObjectOutputStream out; /** Strumień wyjściowy do wysyłania danych do serwera. */
    private ObjectInputStream in; /** Strumień wejściowy do odbierania danych od serwera. */

    /**
     * Tworzy nowego klienta i łączy się z serwerem.
     */
    public BinaryTreeClient() {
        try {
            socket = new Socket(ADRES_SERWERA, PORT_SERWERA); /** Tworzy nowe gniazdo. */
            out = new ObjectOutputStream(socket.getOutputStream()); /** Tworzy nowy strumień wyjściowy. */
            in = new ObjectInputStream(socket.getInputStream()); /** Tworzy nowy strumień wejściowy. */
        } catch (IOException e) { /** Obsługa wyjątku. */
            e.printStackTrace(); /** Wyświetla ślad stosu. */
            System.exit(1); /** Zamyka program. */
        }
    }

    /**
     * Wysyła polecenie do serwera i odbiera odpowiedź.
     * @param polecenie - polecenie do wysłania
     * @return odpowiedź od serwera
     * @throws IOException jeśli wystąpi błąd we/wy
     */
    public String sendCommand(String polecenie) {
        String odpowiedz = "";
        try {
            out.writeObject(polecenie);
            odpowiedz = (String) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return odpowiedz;
    }
    /**
     * Zamyka połączenie z serwerem.
     */
    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
