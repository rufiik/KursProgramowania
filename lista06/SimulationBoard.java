import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/**
 * Reprezentuje planszę symulacji.
 * Plansza składa się z n x m pól, które są odpowiedzialne za zmianę swojego koloru na podstawie kolorów swoich sąsiadów.
 * Kolor pola jest określany na podstawie średniego koloru jego sąsiadów.
 * Pole może również losowo zmienić swój kolor z prawdopodobieństwem p.
 * Pole jest reprezentowane przez osobny wątek.
 * Wątek może być zawieszony przez użytkownika poprzez kliknięcie na pole.
 * n - liczba wierszy
 * m - liczba kolumn
 * p - prawdopodobieństwo zmiany koloru
 * k - opóźnienie w milisekundach
 * board - tablica pól
 * CELL_SIZE - rozmiar komórki planszy
 * @see SimulationBoard#startSimulation()
 * @see SimulationBoard#getPreferredSize()
 * @see SimulationBoard#initializeBoard()
 * @see SimulationBoard#paintComponent(Graphics)
 * @see SimulationBoard#SimulationBoard(int, int, double, int, int)
 * @see Field
 */
public class SimulationBoard extends JPanel {
    private final int n;
    private final int m;
    private final double p;
    private final int k;
    private final Field[][] board;
    private int CELL_SIZE;
    
/**
 * Konstruktor klasy SimulationBoard.
 * @param n liczba wierszy
 * @param m liczba kolumn
 * @param p prawdopodobieństwo zmiany koloru
 * @param k opóźnienie w milisekundach
 * @param CELL_SIZE rozmiar komórki planszy
 */
    public SimulationBoard(int n, int m, double p, int k, int CELL_SIZE) {
        this.n = n;
        this.m = m;
        this.p = p;
        this.k = k;
        this.board = new Field[n][m];
        this.CELL_SIZE = CELL_SIZE;
        initializeBoard();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX() / CELL_SIZE;
                int y = e.getY() / CELL_SIZE;
                if (x < m && y < n) {
                    board[y][x].suspendThread();
                }
            }
        });
    }
    /**
     * Rozpoczyna symulację.
     * Wątek odświeżający planszę jest uruchamiany co 100 milisekund.
     */
    public void startSimulation() {
        new Timer(100, e -> repaint()).start();
    }
    /**
     * @return - preferowany rozmiar planszy.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(m * CELL_SIZE, n * CELL_SIZE); // ustawienie preferowanego rozmiaru
    }
    /**
     * Inicjalizuje planszę.
     * Tworzy obiekty klasy Field i uruchamia dla nich osobne wątki.
     */
    private void initializeBoard() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                board[i][j] = new Field(i * m + j, p, k, board, i, j);
                new Thread(board[i][j]).start();
            }
        }
    }
    /**
     * Rysuje planszę.
     * @param g obiekt klasy Graphics
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                g.setColor(new Color(board[i][j].getColor()));
                g.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                if (board[i][j].threadSuspended) {
                    g.setColor(Color.BLACK); 
                    g.drawRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE-1, CELL_SIZE-1); // rysowanie obrysu
                }
            }
        }
    }
}