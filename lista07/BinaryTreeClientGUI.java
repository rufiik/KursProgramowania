// File: BinaryTreeClientGUI.java
// Author : Rafał Wochna 279752
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * GUI for the binary tree client.
 */
public class BinaryTreeClientGUI extends Application {
    private BinaryTreeClient client; /** The client object that communicates with the server. */
    @Override
    public void start(Stage primaryStage) {
        client = new BinaryTreeClient(); // Create the client object.
        ComboBox<String> treeTypeComboBox = new ComboBox<>(); // Create a combo box for selecting the tree type.
        treeTypeComboBox.getItems().addAll("Integer", "Double", "String"); // Add the tree types to the combo box.
        treeTypeComboBox.setValue("Integer"); // Set the default value to Integer.

        TextField valueField = new TextField(); // Create a text field for the value.
        valueField.setPromptText("Value"); // Set the prompt text for the value field.

        Button insertButton = new Button("Insert"); // Create a button for inserting a value.
        Button deleteButton = new Button("Delete"); // Create a button for deleting a value.
        Button searchButton = new Button("Search"); // Create a button for searching a value.
        Button drawButton = new Button("Draw"); // Create a button for drawing the tree.

        TextArea outputArea = new TextArea(); // Create a text area for the output.
        outputArea.setEditable(false); // Set the text area to read-only.
// Set the action for the insert button.
        insertButton.setOnAction(e -> {
            String value = valueField.getText();
            String response = client.sendCommand("insert " + value);
            valueField.clear();
            outputArea.setText(response);
        });
// Set the action for the delete button.
        deleteButton.setOnAction(e -> {
            String value = valueField.getText();
            String response = client.sendCommand("delete " + value);
            valueField.clear();
            outputArea.setText(response);
        });
// Set the action for the search button.
        searchButton.setOnAction(e -> {
            String value = valueField.getText();
            String response = client.sendCommand("search " + value);
            valueField.clear();
            outputArea.setText(response);
        });
// Set the action for the draw button.
        drawButton.setOnAction(e -> {
            String treeStructure = client.sendCommand("draw");
            outputArea.setText(treeStructure);
        });
// Set the action for the tree type combo box.
        treeTypeComboBox.setOnAction(e -> {
            String treeType = treeTypeComboBox.getValue();
            client.sendCommand("setType " + treeType);
        });
// Create a vertical box for the GUI components.
        VBox vbox = new VBox(10, treeTypeComboBox, valueField, insertButton, deleteButton, searchButton, drawButton, outputArea);
        vbox.setPadding(new Insets(10));
        Scene scene = new Scene(vbox, 400, 400);
// Set the scene for the primary stage.
        primaryStage.setScene(scene);
        primaryStage.setTitle("Binary Tree Client");
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        // Close the client connection when the application stops.
        client.close();
        super.stop();
    }
// Launch the application.
    public static void main(String[] args) {
        launch(args);
    }
}
