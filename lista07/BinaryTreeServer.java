/**File: BinaryTreeServer
 * Author: Rafał Wochna 279752
 */
import java.io.*;
import java.net.*;
import java.util.concurrent.*;
/**
 * A server for the binary tree.
 */
public class BinaryTreeServer {

    private static final int PORT = 12345; /** The port of the server. */
/**
 * The main method.
 * @param args the command-line arguments
 * @throws IOException if an I/O error occurs
 * @throws ClassNotFoundException if a class is not found 
 */
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(10); /** Create a thread pool. */
        try (ServerSocket serverSocket = new ServerSocket(PORT)) { /** Create a new server socket. */
            System.out.println("Server started on port " + PORT); /** Print a message. */
            while (true) { /** Run the server loop. */
                Socket clientSocket = serverSocket.accept(); /** Accept a new client connection. */
                pool.execute(new ClientHandler(clientSocket)); /** Create a new client handler and execute it in the thread pool. */
            }
        } catch (IOException e) { /** Handle the exception. */
            e.printStackTrace(); /** Print the stack trace. */
        }
    }
}
/**
 * A client handler for the binary tree server.
 */
class ClientHandler implements Runnable {
    private Socket clientSocket; /** The client socket. */
    private ObjectOutputStream out; /** The output stream for sending data to the client. */
    private ObjectInputStream in;   /** The input stream for receiving data from the client. */
    private BinaryTree<Integer> intTree = new BinaryTree<>(); /** The integer tree. */
    private BinaryTree<Double> doubleTree = new BinaryTree<>(); /** The double tree. */
    private BinaryTree<String> stringTree = new BinaryTree<>(); /** The string tree. */
    private String currentTreeType = "Integer";  /** The current tree type. */
    public ClientHandler(Socket socket) { /** Create a new client handler. */
        this.clientSocket = socket; /** Set the client socket. */
    }
/**
 * The run method.
 * @see Runnable
 * @see ObjectOutputStream
 * @see ObjectInputStream
 * @see IOException
 */
    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(clientSocket.getOutputStream()); /** Create a new output stream. */
            in = new ObjectInputStream(clientSocket.getInputStream()); /** Create a new input stream. */

            String command; /** The command from the client. */
            while ((command = (String) in.readObject()) != null) { /** Read commands from the client. */
                handleCommand(command); /** Handle the command. */
            }
        } catch (EOFException e) { /** Handle the disconnection */
            System.out.println("Client disconnected"); 
        } catch (IOException | ClassNotFoundException e) { /** Handle the classnotfoundexecption */
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close(); /** Close the client socket. */
            } catch (IOException e) { /** Handle the I/O exception */
                e.printStackTrace();
            }
        }
    }
/**
 * Handles a command from the client.
 * @param command - the command to handle
 * @throws IOException if an I/O error occurs
 */
    private void handleCommand(String command) throws IOException {
        String[] parts = command.split(" "); /** Split the command into parts. */
        String action = parts[0]; /** Get the action from the command. */
        String value = null; /** The value from the command. */
    
        if (parts.length > 1) { /** Check if the command has a value. */
            value = parts[1]; /** Get the value from the command. */
        }
    
        switch (action) { /** Handle the action. */
            case "setType": /** Set the tree type. */
                if (value != null) {
                    currentTreeType = value;
                    out.writeObject("Tree type set to " + currentTreeType);
                } else {
                    out.writeObject("setType command requires a value");
                }
                break;
            case "insert": /** Insert a value into the tree. */
                if (value != null) {
                    handleInsert(value);
                } else {
                    out.writeObject("insert command requires a value");
                }
                break;
            case "delete": /** Delete a value from the tree. */
                if (value != null) {
                    handleDelete(value);
                } else {
                    out.writeObject("delete command requires a value");
                }
                break;
            case "search": /** Search for a value in the tree. */
                if (value != null) {
                    handleSearch(value);
                } else {
                    out.writeObject("search command requires a value");
                }
                break;
            case "draw": /** Draw the tree. */
                handleDraw();
                break;
            default:
                out.writeObject("Invalid command");
                break;
        }
    }
    /**
     * Handles the insert command.
     * @param value - the value to insert
     * @throws IOException if an I/O error occurs
     */
    private void handleInsert(String value) throws IOException {
        switch (currentTreeType) { /** Insert the value into the tree. */
            case "Integer": /** Insert an integer value. */
                intTree.insert(Integer.parseInt(value));
                break;
            case "Double": /** Insert a double value. */
                doubleTree.insert(Double.parseDouble(value));
                break;
            case "String": /** Insert a string value. */
                stringTree.insert(value);
                break;
        }
        out.writeObject("Inserted " + value + " into " + currentTreeType + " tree"); /** Send the result to the client. */
    }
/**
 * Handles the delete command.
 * @param value - the value to delete
 * @throws IOException if an I/O error occurs
 */
    private void handleDelete(String value) throws IOException {
        boolean found = false; /** Delete the value from the tree. */
        switch (currentTreeType) { /** Check the tree type. */
            case "Integer": /** Delete an integer value. */
                found = intTree.search(Integer.parseInt(value));
                if (found) {
                    intTree.delete(Integer.parseInt(value));
                }
                break;
            case "Double": /** Delete a double value. */
                found = doubleTree.search(Double.parseDouble(value));
                if (found) {
                    doubleTree.delete(Double.parseDouble(value));
                }
                break;
            case "String": /** Delete a string value. */
                found = stringTree.search(value);
                if (found) {
                    stringTree.delete(value);
                }
                break;
        }
        if (found) { /** Check if the value was found. */
            out.writeObject("Deleted " + value + " from " + currentTreeType + " tree"); /** Send the result to the client. */
        } else {
            out.writeObject(value + " not found in " + currentTreeType + " tree");  /** Send the result to the client. */
        }
    }
/**
 * Handles the search command.
 * @param value - the value to search for
 * @throws IOException if an I/O error occurs
 */
    private void handleSearch(String value) throws IOException {
        boolean found = false; /** Search for the value in the tree. */
        switch (currentTreeType) { /** Check the tree type. */
            case "Integer": /** Search for an integer value. */
                found = intTree.search(Integer.parseInt(value));
                break;
            case "Double": /** Search for a double value. */
                found = doubleTree.search(Double.parseDouble(value));
                break;
            case "String": /** Search for a string value. */
                found = stringTree.search(value);
                break;
        }
        out.writeObject(found ? value + " found in " + currentTreeType + " tree" : value + " not found in " + currentTreeType + " tree"); /** Send the result to the client. */
    }
/**
 * Handles the draw command.
 * @throws IOException if an I/O error occurs
 */
    private void handleDraw() throws IOException {
        String treeStructure = ""; /** Draw the tree. */
        switch (currentTreeType) { /** Check the tree type. */
            case "Integer": /** Draw the integer tree. */
                treeStructure = intTree.draw();
                break;
            case "Double": /** Draw the double tree. */
                treeStructure = doubleTree.draw();
                break;
            case "String": /** Draw the string tree. */
                treeStructure = stringTree.draw();
                break;
        }
        out.writeObject(treeStructure); /** Send the tree structure to the client. */
    }
    
}
