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


public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Trojkat Pascala");
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        TextField textField = new TextField();
        textField.setPromptText("Podaj liczbe calkowita nieujemna");
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
                    gridPane.getChildren().clear();


                    gridPane.getChildren().remove(messageLabel);
                    gridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node) > 1);

                    for (int i = 0; i <= n; i++) {
                        int t = 1;
                        for (int j = 0; j <= i; j++) {
                            Label label = new Label(String.valueOf(t));
                            GridPane.setConstraints(label, n-i+j*2, i + 2);
                            label.setStyle("-fx-text-fill: blue;");
                            gridPane.getChildren().add(label);
                            t = t * (i - j) / (j + 1);
                        }

                    }
                   
                    Button backButton = new Button("Cofnij");
                    GridPane.setConstraints(backButton, 2*n, n);
                    gridPane.getChildren().add(backButton);
                   
                    primaryStage.sizeToScene();
                    primaryStage.centerOnScreen();
                    backButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            gridPane.getChildren().clear();
                            GridPane.setConstraints(textField, 0, 0);
                            GridPane.setConstraints(button, 1, 0);
                            gridPane.getChildren().addAll(textField, button);
                            
                        }
                    });
                    
                } catch (NumberFormatException ex) {
                    messageLabel.setText("Podaj liczbe calkowita nieujemna");
                }
            }
        });

        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}