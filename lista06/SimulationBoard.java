import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class SimulationBoard extends JPanel {
    private final int n;
    private final int m;
    private final double p;
    private final int k;
    private final Field[][] board;
    private int CELL_SIZE;
    

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
    public void startSimulation() {
        new Timer(100, e -> repaint()).start();
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(m * CELL_SIZE, n * CELL_SIZE); // ustawienie preferowanego rozmiaru
    }
    private void initializeBoard() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                board[i][j] = new Field(i * m + j, p, k, board, i, j);
                new Thread(board[i][j]).start();
            }
        }
    }
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