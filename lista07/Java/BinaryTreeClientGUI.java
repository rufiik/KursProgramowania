// Plik: BinaryTreeClientGUI.java
// Autor: Rafał Wochna 279752
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * GUI dla klienta drzewa binarnego.
 */
public class BinaryTreeClientGUI extends Application {
    private BinaryTreeClient client; /** Obiekt klienta, który komunikuje się z serwerem. */
    @Override
    public void start(Stage primaryStage) {
        client = new BinaryTreeClient(); // Utwórz obiekt klienta.
        ComboBox<String> treeTypeComboBox = new ComboBox<>(); // Utwórz ComboBox do wyboru typu drzewa.
        treeTypeComboBox.getItems().addAll("Integer", "Double", "String"); // Dodaj typy drzewa do ComboBox.
        treeTypeComboBox.setValue("Integer"); // Ustaw domyślną wartość na Integer.
        TextField valueField = new TextField(); // Utwórz pole tekstowe dla wartości.
        valueField.setPromptText("Wartosc"); // Ustaw tekst podpowiedzi dla pola wartości.
        Button insertButton = new Button("Wstaw"); // Utwórz przycisk do wstawiania wartości.
        Button deleteButton = new Button("Usun"); // Utwórz przycisk do usuwania wartości.
        Button searchButton = new Button("Szukaj"); // Utwórz przycisk do wyszukiwania wartości.
        Button drawButton = new Button("Rysuj"); // Utwórz przycisk do rysowania drzewa.
        TextArea outputArea = new TextArea(); // Utwórz obszar tekstowy dla wyników.
        
        outputArea.setPrefHeight(600);
        outputArea.setEditable(false); // Ustaw obszar tekstowy jako tylko do odczytu.
        // Ustaw akcję dla przycisku wstawiania.
        insertButton.setOnAction(e -> {
            String value = valueField.getText();
            String response = client.sendCommand("insert " + value);
            valueField.clear();
            outputArea.setText(response);
             outputArea.setStyle("-fx-font-size: 15px;");
        });
        // Ustaw akcję dla przycisku usuwania.
        deleteButton.setOnAction(e -> {
            String value = valueField.getText();
            String response = client.sendCommand("delete " + value);
            valueField.clear();
            outputArea.setText(response);
            outputArea.setStyle("-fx-font-size: 15px;");
        });
        // Ustaw akcję dla przycisku wyszukiwania.
        searchButton.setOnAction(e -> {
            String value = valueField.getText();
            String response = client.sendCommand("search " + value);
            valueField.clear();
            outputArea.setText(response);
            outputArea.setStyle("-fx-font-size: 15px;");
        });
        // Ustaw akcję dla przycisku rysowania.
        drawButton.setOnAction(e -> {
            String treeStructure = client.sendCommand("draw");
            outputArea.setText(treeStructure);
            outputArea.setStyle("-fx-font-size: 30px;");
        });
        // Ustaw akcję dla ComboBoxa typu drzewa.
        treeTypeComboBox.setOnAction(e -> {
            String treeType = treeTypeComboBox.getValue();
            client.sendCommand("setType " + treeType);
        });
        // Utwórz pionową skrzynkę dla komponentów GUI.
        VBox vbox = new VBox(10, treeTypeComboBox, valueField, insertButton, deleteButton, searchButton, drawButton, outputArea);
        vbox.setPadding(new Insets(10));
        Scene scene = new Scene(vbox, 400, 800);
        
        // Ustaw scenę dla głównego etapu.
        primaryStage.setScene(scene);
        primaryStage.setTitle("Klient Drzewa Binarnego");
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        // Zamknij połączenie klienta po zakończeniu aplikacji.
        client.close();
        super.stop();
    }
    // Uruchom aplikację.
    public static void main(String[] args) {
        launch(args);
    }
}
