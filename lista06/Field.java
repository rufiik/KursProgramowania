import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
/**
 * Reprezentuje pojedyncze pole na planszy symulacji.
 * Każde pole jest odpowiedzialne za zmianę swojego koloru na podstawie kolorów swoich sąsiadów.
 * Kolor pola jest określany na podstawie średniego koloru jego sąsiadów.
 * Pole może również losowo zmienić swój kolor z prawdopodobieństwem p.
 * Pole jest reprezentowane przez osobny wątek.
 * Wątek może być zawieszony przez użytkownika poprzez kliknięcie na pole.
 * id - identyfikator pola
 * p - prawdopodobieństwo zmiany koloru
 * k - opóźnienie w milisekundach
 * color - aktualny kolor pola
 * board - tablica pól
 * row - indeks wiersza pola
 * col - indeks kolumny pola
 * random - generator liczb losowych
 * lock - reentrant lock
 * threadSuspended - flaga wskazująca, czy wątek jest zawieszony
 * condition - zmienna warunkowa
 * @see Field#run()
 * @see Field#changeThreadColor()
 * @see Field#calculateAverageNeighborColor()
 * @see Field#suspendThread()
 * @see Field#isThreadSuspended()
 * @see Field#isThreadSuspendedAt(int, int)
 * @see Field#getColor()
 * @see Field#Field(int, double, int, Field[][], int, int)
 */
public class Field implements Runnable {
    private final int id; 
    private final double p;
    private final int k;
    private int color;
    private final Field[][] board;
    private final int row;
    private final int col;
    private static final Random random = new Random();
    private static final ReentrantLock lock = new ReentrantLock();
    public volatile boolean threadSuspended;
    private final Condition condition = lock.newCondition();

    /**
     * Konstruktor klasy Field.
     *
     * @param id    identyfikator pola
     * @param p     prawdopodobieństwo zmiany koloru
     * @param k     opóźnienie w milisekundach
     * @param board tablica pól
     * @param row   indeks wiersza pola
     * @param col   indeks kolumny pola
     */
    public Field(int id, double p, int k, Field[][] board, int row, int col) {
        this.id = id;
        this.p = p;
        this.k = k;
        this.color = random.nextInt(256 * 256 * 256); // kolor RGB
        this.board = board;
        this.row = row;
        this.col = col;

        this.threadSuspended = false;
    }
        /**
     * Zawiesza wątek pola.
     * Wątek zawieszony nie zmienia koloru pola.
     * Wątek zawieszony nie wykonuje obliczeń.
     * Wątek zawieszony zostaje wznowiony po ponownym kliknięciu na pole.
     */
    public void suspendThread() {
        lock.lock();
        try{
        System.out.println("Suspend: " + id);
        threadSuspended = !threadSuspended;
        if (!threadSuspended) {
            condition.signal(); 
        }
    } finally {
        lock.unlock();
    }
}

    /**
     * Zwraca aktualny kolor pola.
     *
     * @return aktualny kolor pola
     */
    public int getColor() {
        return color;
    }

    /**
     * Sprawdza, czy wątek jest zawieszony.
     *
     * @return true, jeśli wątek jest zawieszony; false w przeciwnym razie
     */
    public boolean isThreadSuspended() {
        return threadSuspended;
    }

    /**
     * Sprawdza, czy wątek jest zawieszony na podanym polu.
     *
     * @param row indeks wiersza pola
     * @param col indeks kolumny pola
     * @return true, jeśli wątek jest zawieszony na podanym polu; false w przeciwnym razie
     */
    public boolean isThreadSuspendedAt(int row, int col) {
        return board[row][col].isThreadSuspended();
    }
/**
 * Metoda run wątku pola.
 * Wątek zmienia kolor pola na średni kolor sąsiadów lub losowy kolor z prawdopodobieństwem p.
 * Wątek może być zawieszony przez użytkownika poprzez kliknięcie na pole.
 * Wątek zawieszony zostaje wznowiony po ponownym kliknięciu na pole.
 * Wątek zawieszony nie zmienia koloru pola.
 * Wątek zawieszony nie wykonuje obliczeń.
 * 
 */
    @Override
    public void run() {
        while (true) {
            try {
                long delay = (long) ((random.nextDouble() + 0.5) * k);
                Thread.sleep(delay);
                synchronized (this) {
                    while (threadSuspended) {
                        wait();
                    }
                }
                if (!isThreadSuspended()) {
                    changeThreadColor();
                }
                Thread.yield();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
/**
 * Zmienia kolor pola na średni kolor sąsiadów lub losowy kolor z prawdopodobieństwem p.
 * Kolor pola jest określany na podstawie średniego koloru jego sąsiadów.
 * Kolor pola jest określany na podstawie losowego koloru z prawdopodobieństwem p.
 * 
 */
    private void changeThreadColor() {
        synchronized (this.board) {
            lock.lock();
            try {
                System.out.println("Rozpoczęcie: (" + this.row + "," + this.col + ")");
                if (random.nextDouble() < p) {
                    color = random.nextInt(256 * 256 * 256);
                } else {
                    calculateAverageNeighborColor();
                }
            } finally {
                System.out.println("Zakończenie: (" + this.row + "," + this.col + ")");
                lock.unlock();
                    }
        }
    }
/** 
 * Oblicza średni kolor sąsiadów pola, jeśli sąsiednie pola nie są zawieszone.
*/
    private void calculateAverageNeighborColor() {
        int[] neighbors = new int[4];
        int count = 0;
        if (!isThreadSuspendedAt((row - 1 + board.length) % board.length, col)) {
            neighbors[0] = board[(row - 1 + board.length) % board.length][col].getColor();
            count++;
        }
        if (!isThreadSuspendedAt((row + 1) % board.length, col)) {
            neighbors[1] = board[(row + 1) % board.length][col].getColor();
            count++;
        }
        if (!isThreadSuspendedAt(row, (col - 1 + board[0].length) % board[0].length)) {
            neighbors[2] = board[row][(col - 1 + board[0].length) % board[0].length].getColor();
            count++;
        }
        if (!isThreadSuspendedAt(row, (col + 1) % board[0].length)) {
            neighbors[3] = board[row][(col + 1) % board[0].length].getColor();
            count++;
        }
        int r = 0, g = 0, b = 0;
        for (int neighborColor : neighbors) {
            r += (neighborColor >> 16) & 0xFF;
            g += (neighborColor >> 8) & 0xFF;
            b += neighborColor & 0xFF;
        }
       
        r /= count;
        g /= count;
        b /= count;
        if(count!=0){
        this.color= (r << 16) | (g << 8) | b;
    }
    }

}