/** @author Jakub Musial 268442  */
/** Thread Simulation @version 2.0 */
/** Java @version jdk1.8.0_331 */

import javafx.application.Application;
import javafx.application.Platform;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;





/** Class implementing thread operations
 * @see Board
 * @see rowIndex
 * @see columnIndex
 * @see rows
 * @see columns
 * @see ColorBox
 * @see ThreadColor
 * @see RandomGenerator
 * @see latency
 * @see probability
 * @see Stopped
 * 
 * @see ColorField
 * @see generateRandomColors
 * @see generateColors
 * @see changeThreadColor
 * @see run
 * 
 * @see ThreadHandler
 */
class ColorField extends Thread {
    private Pane Board; /** Pane holding thread's color boxes */

    private int rowIndex; /** Thread's row index on the Simulation Board */
    private int columnIndex; /** Thread's column index on the Simulation Board */
    private int rows; /** Number of rows on ColorField.Board */
    private int columns; /** Number of columns on ColorField.Board */
    
    public Rectangle ColorBox; /** A figure used for displaying thread's current color */
    public Color ThreadColor; /** Thread's current color */

    private Random RandomGenerator; /** Thread's random number values generator (this generator is an alias for ThreadSimulation.RandomGenerator in ColorField class) */

    private int latency; /** Thread's operating latency */
    private double probability; /** Probability of a color change event */

    public volatile boolean Stopped; /** Determines if the thread is currently stopped (by mouse click) */

    
    /** SimulationThread class constructor */
    public ColorField (Pane Board, int rowIndex, int columnIndex, int rows, int columns, double width, double height, Random RandomGenerator, int latency, double probability) {
        super();
        this.setDaemon(true);

        this.Board = Board;

        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.rows = rows;
        this.columns = columns;

        this.RandomGenerator = RandomGenerator;
               
        this.latency = latency;
        this.probability = probability;

        this.generateRandomColors();
        this.ColorBox = new Rectangle(rowIndex * width, columnIndex * height, width, height);
        this.ColorBox.setFill(this.ThreadColor);
        this.ColorBox.setStrokeWidth(2.0);
        this.ColorBox.setOnMouseClicked(new ThreadHandler(rowIndex, columnIndex));
        
        this.Stopped = false;
    }

    /** Thread's random color generator */
    private void generateRandomColors () {
        this.ThreadColor = new Color(RandomGenerator.nextDouble(), RandomGenerator.nextDouble(), RandomGenerator.nextDouble(), 1.0); // The added 4th parameter is color's opacity (Color class constructor without the opacity parameter is private)
        
    }

    /** Thread's color generator (based on surrounding threads) */
    private void generateColors() {
        double tmpRed = 0.0;
        double tmpGreen = 0.0;
        double tmpBlue = 0.0;

        int notStoppedSurroundingThreads = 0; /** Determines if there are stopped threads surrounding currently operating thread */

        /** Generating color (red, green, blue) values based on surrounding threads */
        if (!ThreadSimulation.Threads[(this.rows + (this.rowIndex - 1)) % this.rows][this.columnIndex].Stopped) {
            notStoppedSurroundingThreads++;

            tmpRed += ThreadSimulation.Threads[(this.rows + (this.rowIndex - 1)) % this.rows][this.columnIndex].ThreadColor.getRed();
            tmpGreen += ThreadSimulation.Threads[(this.rows + (this.rowIndex - 1)) % this.rows][this.columnIndex].ThreadColor.getGreen();
            tmpBlue += ThreadSimulation.Threads[(this.rows + (this.rowIndex - 1)) % this.rows][this.columnIndex].ThreadColor.getBlue();
        }
        if (!ThreadSimulation.Threads[(this.rowIndex + 1) % this.rows][this.columnIndex].Stopped) {
            notStoppedSurroundingThreads++;

            tmpRed += ThreadSimulation.Threads[(this.rowIndex + 1) % this.rows][this.columnIndex].ThreadColor.getRed();
            tmpGreen += ThreadSimulation.Threads[(this.rowIndex + 1) % this.rows][this.columnIndex].ThreadColor.getGreen();
            tmpBlue += ThreadSimulation.Threads[(this.rowIndex + 1) % this.rows][this.columnIndex].ThreadColor.getBlue();
        }
        if (!ThreadSimulation.Threads[this.rowIndex][(this.columns + (this.columnIndex - 1)) % this.columns].Stopped) {
            notStoppedSurroundingThreads++;

            tmpRed += ThreadSimulation.Threads[this.rowIndex][(this.columns + (this.columnIndex - 1)) % this.columns].ThreadColor.getRed();
            tmpGreen += ThreadSimulation.Threads[this.rowIndex][(this.columns + (this.columnIndex - 1)) % this.columns].ThreadColor.getGreen();
            tmpBlue += ThreadSimulation.Threads[this.rowIndex][(this.columns + (this.columnIndex - 1)) % this.columns].ThreadColor.getBlue();
        }
        if (!ThreadSimulation.Threads[this.rowIndex][(this.columnIndex + 1) % this.columns].Stopped) {
            notStoppedSurroundingThreads++;

            tmpRed += ThreadSimulation.Threads[this.rowIndex][(this.columnIndex + 1) % this.columns].ThreadColor.getRed();
            tmpGreen += ThreadSimulation.Threads[this.rowIndex][(this.columnIndex + 1) % this.columns].ThreadColor.getGreen();
            tmpBlue += ThreadSimulation.Threads[this.rowIndex][(this.columnIndex + 1) % this.columns].ThreadColor.getBlue();
        }

        if (notStoppedSurroundingThreads > 0) {
            tmpRed /= notStoppedSurroundingThreads;
            tmpGreen /= notStoppedSurroundingThreads;
            tmpBlue /= notStoppedSurroundingThreads;

            this.ThreadColor = new Color(tmpRed, tmpGreen, tmpBlue, 1.0); // The added 4th parameter is color's opacity (Color class constructor without the opacity parameter is private)
        }
    }

    /** Thread's color changer */
    private void changeThreadColor() {
        try {
            synchronized (this.Board) {
                System.out.println("Starting thread: (" + this.rowIndex + "," + this.columnIndex + ")");

                if (this.RandomGenerator.nextDouble() <= this.probability) { this.generateColors(); }
                else { this.generateRandomColors(); }

                Platform.runLater(() -> { this.ColorBox.setFill(this.ThreadColor); });

                System.out.println("Stopping thread: (" + this.rowIndex + "," + this.columnIndex + ")");
            }
        }
        catch (Exception exception) { System.out.println("Failed to perform a thread color change event"); }
    }

    /** Thread operating method */
    @Override
    public void run() {
        while (true) {
            try {
                if (!this.Stopped) {
                    this.changeThreadColor();
                    
                    Thread.sleep(this.RandomGenerator.nextInt(this.latency) + (this.latency / 2));
                    
                    if (!this.Stopped) {
                        synchronized(this) {
                            while (this.Stopped) {
                                wait();
                            }
                        }
                    }
    
                    Thread.yield();
                }
            }
            catch (InterruptedException exception) {}
        }
    }



    /** Class implementing thread stopping and resuming
     * @see ColorBox
     * @see rowIndex
     * @see columnIndex
     * 
     * @see ThreadHandler
     * @see handle
     */
    class ThreadHandler implements EventHandler <MouseEvent> {
        Rectangle ColorBox; /** Rectangle object for thread's color visualization */

        private int rowIndex; /** ColorBox's row index on the Board */
        private int columnIndex; /** ColorBox's column index on the Board */

        /** ThreadHandler class constructor */
        public ThreadHandler (int rowIndex, int columnIndex) {
            this.rowIndex = rowIndex;
            this.columnIndex = columnIndex;
        }

        /** Thread's stop and resume operations handler */
        @Override
        public void handle (MouseEvent event) {
            if (event.getButton() == MouseButton.PRIMARY) {
                ColorBox = (Rectangle) event.getSource();

                // Stopping or reactivating the thread
                ThreadSimulation.Threads[this.rowIndex][this.columnIndex].Stopped = !ThreadSimulation.Threads[this.rowIndex][this.columnIndex].Stopped;
                if (ThreadSimulation.Threads[this.rowIndex][this.columnIndex].Stopped) { ThreadSimulation.Threads[this.rowIndex][this.columnIndex].ColorBox.setStroke(Color.BLACK); }
                else { ThreadSimulation.Threads[this.rowIndex][this.columnIndex].ColorBox.setStroke(null); }
                ThreadSimulation.Threads[this.rowIndex][this.columnIndex].notify();
            }
        }
    }
}





/** Program's main class - operating application launch
 * @see rows
 * @see columns
 * @see latency
 * @see probability
 * @see Threads
 * @see RandomGenerator
 * 
 * @see start
 * @see main
 * 
 * @see ColorField
 */
public class ThreadSimulation extends Application {
    private static int rows; /** Simulation board's vertical size */
    private static int columns; /** Simulation board's horizontal size */
    private static int latency; /** Simulation operating latency */
    private static double probability; /** Probability of a color change event */

    public static ColorField[][] Threads; /** Simulation's thread board */

    private static Random RandomGenerator; /** Simulation's random number generator */

    /** Application starting method */
    @Override
    public void start (Stage PrimaryStage) {
        RandomGenerator = new Random();

        BorderPane MainPane = new BorderPane();
        MainPane.setPrefSize(1024, 628);
        
        /** Simulation board */
        Pane SimulationBoard = new Pane();

        try {

            // Simulation threads
            Threads = new ColorField[rows][columns];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    Threads[i][j] = new ColorField(SimulationBoard, i, j, rows, columns, 1024 / rows, 628 / columns, RandomGenerator, latency, probability);

                    SimulationBoard.getChildren().add(Threads[i][j].ColorBox);

                    Threads[i][j].start();
                }
            }

            MainPane.setBackground(null);
            MainPane.setCenter(SimulationBoard);
        }
        catch (Exception exception) {
            System.out.println(exception.getMessage());

            Label ExceptionLabel = new Label("Something went wrong\nPlease try again");
            ExceptionLabel.setAlignment(Pos.CENTER);
            ExceptionLabel.setFont(new Font("Courier New", 36));
            
            MainPane.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
            MainPane.setCenter(ExceptionLabel);
        }

        PrimaryStage.setTitle("Thread simulation");
        PrimaryStage.setScene(new Scene(MainPane));
        PrimaryStage.show();
    }

    /** Program's main method */
    public static void main (String[] args) {
        try {
            // Getting the m, n, k, p values form their TextFields
            rows = Integer.parseInt(args[0]);
            columns = Integer.parseInt(args[1]);            
            latency = Integer.parseInt(args[2]);
            probability = Double.parseDouble(args[3]);

            if (rows > 0 && columns > 0 && latency > 0 && probability >= 0.0 && probability <= 1.0) { Application.launch(args); }
            else { throw new NumberFormatException(); }
        }
        catch (NumberFormatException exception) {
            System.out.println("Invalid parameters\nCouldn't launch the simulation");
            System.exit(0);
        }
    }
}