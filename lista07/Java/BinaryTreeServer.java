/**Plik: BinaryTreeServer
 * Autor: Rafał Wochna 279752
 */
import java.io.*;
import java.net.*;
import java.util.concurrent.*;

/**
 * Serwer dla drzewa binarnego.
 */
public class BinaryTreeServer {

    private static final int PORT = 12345; /** Port serwera. */
/**
 * Metoda główna.
 * @param args argumenty wiersza poleceń
 * @throws IOException jeśli wystąpi błąd we/wy
 * @throws ClassNotFoundException jeśli nie znaleziono klasy
 */
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(10); /** Utwórz pulę wątków. */
        try (ServerSocket serverSocket = new ServerSocket(PORT)) { /** Utwórz nowy socket serwera. */
            System.out.println("Serwer uruchomiony na porcie " + PORT); /** Wyświetl komunikat. */
            while (true) { /** Uruchom pętlę serwera. */
                Socket clientSocket = serverSocket.accept(); /** Zaakceptuj nowe połączenie klienta. */
                pool.execute(new ClientHandler(clientSocket)); /** Utwórz nowy obsługiwacz klienta i wykonaj go w puli wątków. */
            }
        } catch (IOException e) { /** Obsłuż wyjątek. */
            e.printStackTrace(); 
        }
    }
}
/**
 * Obsługiwacz klienta dla serwera drzewa binarnego.
 */
class ClientHandler implements Runnable {
    private Socket clientSocket; /** Socket klienta. */
    private ObjectOutputStream out; /** Strumień wyjściowy do wysyłania danych do klienta. */
    private ObjectInputStream in;   /** Strumień wejściowy do odbierania danych od klienta. */
    private BinaryTree<Integer> intTree = new BinaryTree<>(); /** Drzewo liczb całkowitych. */
    private BinaryTree<Double> doubleTree = new BinaryTree<>(); /** Drzewo liczb zmiennoprzecinkowych. */
    private BinaryTree<String> stringTree = new BinaryTree<>(); /** Drzewo ciągów znaków. */
    private String currentTreeType = "Integer";  /** Bieżący typ drzewa. */
    public ClientHandler(Socket socket) { /** Utwórz nowy obsługiwacz klienta. */
        this.clientSocket = socket; /** Ustaw socket klienta. */
    }
/**
 * Metoda run.
 * @see Runnable
 * @see ObjectOutputStream
 * @see ObjectInputStream
 * @see IOException
 */
    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(clientSocket.getOutputStream()); /** Utwórz nowy strumień wyjściowy. */
            in = new ObjectInputStream(clientSocket.getInputStream()); /** Utwórz nowy strumień wejściowy. */

            String command; /** Komenda od klienta. */
            while ((command = (String) in.readObject()) != null) { /** Odczytaj komendy od klienta. */
                handleCommand(command); /** Obsłuż komendę. */
            }
        } catch (EOFException e) { /** Obsłuż rozłączenie */
            System.out.println("Klient rolaczony"); 
        } catch (IOException | ClassNotFoundException e) { /** Obsłuż wyjątek ClassNotFoundException */
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close(); /** Zamknij gniazdko klienta. */
            } catch (IOException e) { /** Obsłuż wyjątek we/wy */
                e.printStackTrace();
            }
        }
    }
/**
 * Obsługuje komendę od klienta.
 * @param command - komenda do obsłużenia
 * @throws IOException jeśli wystąpi błąd we/wy
 */
    private void handleCommand(String command) throws IOException {
        String[] parts = command.split(" "); /** Podziel komendę na części. */
        String action = parts[0]; /** Pobierz akcję z komendy. */
        String value = null; /** Wartość z komendy. */
    
        if (parts.length > 1) { /** Sprawdź, czy komenda ma wartość. */
            value = parts[1]; /** Pobierz wartość z komendy. */
        }
    
        switch (action) { /** Obsłuż akcję. */
            case "setType": /** Ustaw typ drzewa. */
                if (value != null) {
                    currentTreeType = value;
                    out.writeObject("Typ drzewa ustawiony na " + currentTreeType);
                } else {
                    out.writeObject("Komenda setType wymaga wartosci");
                }
                break;
            case "insert": /** Wstaw wartość do drzewa. */
                if (value != null) {
                    handleInsert(value);
                } else {
                    out.writeObject("Komenda wstaw wymaga wartosci");
                }
                break;
            case "delete": /** Usuń wartość z drzewa. */
                if (value != null) {
                    handleDelete(value);
                } else {
                    out.writeObject("Komenda usun wymaga wartości");
                }
                break;
            case "search": /** Wyszukaj wartość w drzewie. */
                if (value != null) {
                    handleSearch(value);
                } else {
                    out.writeObject("Komenda szukaj wymaga wartosci");
                }
                break;
            case "draw": /** Narysuj drzewo. */
                handleDraw();
                break;
            default:
                out.writeObject("Nieprawidlowa komenda");
                break;
        }
    }
    /**
     * Obsługuje komendę insert.
     * @param value - wartość do wstawienia
     * @throws IOException jeśli wystąpi błąd we/wy
     */
    private void handleInsert(String value) throws IOException {
        switch (currentTreeType) { /** Wstaw wartość do drzewa. */
            case "Integer": /** Wstaw wartość całkowitą. */
                intTree.insert(Integer.parseInt(value));
                break;
            case "Double": /** Wstaw wartość zmiennoprzecinkową. */
                doubleTree.insert(Double.parseDouble(value));
                break;
            case "String": /** Wstaw wartość ciągu znaków. */
                stringTree.insert(value);
                break;
        }
        out.writeObject("Wstawiono " + value + " do drzewa " + currentTreeType); /** Wyślij wynik do klienta. */
    }
/**
 * Obsługuje komendę delete.
 * @param value - wartość do usunięcia
 * @throws IOException jeśli wystąpi błąd we/wy
 */
    private void handleDelete(String value) throws IOException {
        boolean found = false; /** Usuń wartość z drzewa. */
        switch (currentTreeType) { /** Sprawdź typ drzewa. */
            case "Integer": /** Usuń wartość całkowitą. */
                found = intTree.search(Integer.parseInt(value));
                if (found) {
                    intTree.delete(Integer.parseInt(value));
                }
                break;
            case "Double": /** Usuń wartość zmiennoprzecinkową. */
                found = doubleTree.search(Double.parseDouble(value));
                if (found) {
                    doubleTree.delete(Double.parseDouble(value));
                }
                break;
            case "String": /** Usuń wartość ciągu znaków. */
                found = stringTree.search(value);
                if (found) {
                    stringTree.delete(value);
                }
                break;
        }
        if (found) { /** Sprawdź, czy wartość została znaleziona. */
            out.writeObject("Usunieto " + value + " z drzewa " + currentTreeType); /** Wyślij wynik do klienta. */
        } else {
            out.writeObject(value + " nie znaleziono w drzewie " + currentTreeType);  /** Wyślij wynik do klienta. */
        }
    }
/**
 * Obsługuje komendę search.
 * @param value - wartość do wyszukania
 * @throws IOException jeśli wystąpi błąd we/wy
 */
    private void handleSearch(String value) throws IOException {
        boolean found = false; /** Wyszukaj wartość w drzewie. */
        switch (currentTreeType) { /** Sprawdź typ drzewa. */
            case "Integer": /** Wyszukaj wartość całkowitą. */
                found = intTree.search(Integer.parseInt(value));
                break;
            case "Double": /** Wyszukaj wartość zmiennoprzecinkową. */
                found = doubleTree.search(Double.parseDouble(value));
                break;
            case "String": /** Wyszukaj wartość ciągu znaków. */
                found = stringTree.search(value);
                break;
        }
        out.writeObject(found ? value + " znaleziono w drzewie " + currentTreeType : value + " nie znaleziono w drzewie " + currentTreeType); /** Wyślij wynik do klienta. */
    }
/**
 * Obsługuje komendę draw.
 * @throws IOException jeśli wystąpi błąd we/wy
 */
    private void handleDraw() throws IOException {
        String treeStructure = ""; /** Narysuj drzewo. */
        switch (currentTreeType) { /** Sprawdź typ drzewa. */
            case "Integer": /** Narysuj drzewo całkowite. */
                treeStructure = intTree.draw();
                break;
            case "Double": /** Narysuj drzewo zmiennoprzecinkowe. */
                treeStructure = doubleTree.draw();
                break;
            case "String": /** Narysuj drzewo ciągów znaków. */
                treeStructure = stringTree.draw();
                break;
        }
        out.writeObject(treeStructure); /** Wyślij strukturę drzewa do klienta. */
    }
    
}

