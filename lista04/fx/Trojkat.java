import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Trojkat extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Trojkat Pascala");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        TextField textField = new TextField();
        textField.setPromptText("Podaj liczbę całkowitą nieujemną");
        GridPane.setConstraints(textField, 0, 0);

        Button button = new Button("Stworz");
        GridPane.setConstraints(button, 1, 0);

        Label messageLabel = new Label();
        GridPane.setConstraints(messageLabel, 0, 1, 2, 1);

        gridPane.getChildren().addAll(textField, button, messageLabel);

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    int n = Integer.parseInt(textField.getText().trim());
                    if (n < 0) {
                        throw new NumberFormatException();
                    }
                    gridPane.getChildren().remove(messageLabel);
                    gridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node) > 1);

                    for (int i = 0; i <= n; i++) {
                        TrojkatPascala trojkat = new TrojkatPascala(i);
                        trojkat.stworz(i);
                        int[] tablica = trojkat.tab;
                        for (int j = 0; j <= i; j++) {
                            Label label = new Label(String.valueOf(tablica[j]));
                            GridPane.setConstraints(label, (j * 2 + (n - i)), i + 2);
                            label.setStyle("-fx-text-fill: blue;");
                            gridPane.getChildren().add(label);
                        }
                    }

                    Button backButton = new Button("Cofnij");
                    GridPane.setConstraints(backButton, n, n + 3);
                    gridPane.getChildren().add(backButton);

                    backButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            textField.clear();
                            gridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node) > 1);
                            gridPane.getChildren().add(messageLabel);
                        }
                    });

                } catch (NumberFormatException ex) {
                    messageLabel.setText("Podaj liczbę całkowitą nieujemną");
                }
            }
        });

        Scene scene = new Scene(gridPane, 640, 480);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}