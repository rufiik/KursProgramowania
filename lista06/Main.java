import javax.swing.JFrame;
/**
 * Klasa główna programu.
 * Program tworzy okno z planszą symulacji.
 * Program przyjmuje 4 argumenty:
 * n - liczba wierszy
 * m - liczba kolumn
 * p - prawdopodobieństwo zmiany koloru
 * k - opóźnienie w milisekundach
 * frame - okno programu
 * CELL_SIZE - rozmiar komórki planszy
 * @see SimulationBoard
 * @see Field
 */
public class Main {
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Usage: java SimulationBoard <n> <m> <p> <k>");
            return;
        }
        JFrame frame = new JFrame("Simulation Board");
        try{
            int n = Integer.parseInt(args[0]);
            int m = Integer.parseInt(args[1]);
            int k = Integer.parseInt(args[2]);
            double p = Double.parseDouble(args[3]);
            int CELL_SIZE=Math.min(1920 / m, 1080 / n);
            SimulationBoard simulation = new SimulationBoard(n, m, p, k, CELL_SIZE);
            frame.add(simulation);
            simulation.startSimulation();
        } catch (NumberFormatException e) {
            System.out.println("Invalid arguments");
            return;
        }
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

    }
}
