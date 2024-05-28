import javax.swing.JFrame;
public class Main {
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Usage: java SimulationBoard <n> <m> <p> <k>");
            return;
        }
        int n = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);
        int k = Integer.parseInt(args[2]);
        double p = Double.parseDouble(args[3]);
        int CELL_SIZE=Math.min(1920 / m, 1080 / n);
        JFrame frame = new JFrame("Simulation Board");
        SimulationBoard simulation = new SimulationBoard(n, m, p, k, CELL_SIZE);
        frame.add(simulation);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        simulation.startSimulation();
    }
}
