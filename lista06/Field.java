import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Field implements Runnable {
    private final int id;
    private final double p;
    private final int k;
    private int color;
    private final Field[][] board;
    private final int row;
    private final int col;
    private static final Random random = new Random();
    private static final Lock lock = new ReentrantLock();
    public volatile boolean threadSuspended;

    public Field(int id, double p, int k, Field[][] board, int row, int col) {
        this.id = id;
        this.p = p;
        this.k = k;
        this.color = random.nextInt(256 * 256 * 256); // RGB color
        this.board = board;
        this.row = row;
        this.col = col;
        this.threadSuspended = false;
    }
    public int getColor() {
        return color;
    }
    public boolean isThreadSuspended() {
        return threadSuspended;
    }
    public boolean isThreadSuspendedAt(int row, int col) {
        return board[row][col].isThreadSuspended();
    }
    @Override
    public void run() {
        try {
            while (true) {
                int delay = (int) (0.5 * k + random.nextDouble() * k);
                Thread.sleep(delay);
                
                synchronized (this) {
                    while (threadSuspended) {
                        wait(); // czekaj, dopóki wątek nie zostanie wznowiony
                    }
    
                    if (random.nextDouble() < p) {
                        color = random.nextInt(256 * 256 * 256);
                    } else {
                        int newColor = calculateAverageNeighborColor();
                        color = newColor;
                    }
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } 
    }
    private int calculateAverageNeighborColor() {
        int[] neighbors = new int[4];
        int ilosc=0;
        if(!isThreadSuspendedAt((row - 1 + board.length) % board.length, col)){
            neighbors[0] = board[(row - 1 + board.length) % board.length][col].getColor();
            ilosc++;
        }
        if(!isThreadSuspendedAt((row + 1) % board.length, col)){
            neighbors[1] = board[(row + 1) % board.length][col].getColor();
            ilosc++;
        }
        if(!isThreadSuspendedAt(row, (col - 1 + board[0].length) % board[0].length)){
            neighbors[2] = board[row][(col - 1 + board[0].length) % board[0].length].getColor();
            ilosc++;
        }
        if(!isThreadSuspendedAt(row,(col + 1) % board[0].length)){
            neighbors[3] = board[row][(col + 1) % board[0].length].getColor();
            ilosc++;
        }
        int r = 0, g = 0, b = 0;
        for (int neighborColor : neighbors) {
            r += (neighborColor >> 16) & 0xFF;
            g += (neighborColor >> 8) & 0xFF;
            b += neighborColor & 0xFF;
        }
        r /= ilosc;
        g /= ilosc;
        b /= ilosc;
          if(ilosc==0){
            System.out.println("Error: ilosc=0");
        }
        return (r << 16) | (g << 8) | b;
      
    }
    public synchronized void suspendThread() {
        System.out.println("Suspend: " + id);
        threadSuspended = !threadSuspended;
        if (!threadSuspended)
            notify();
    }
}