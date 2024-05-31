// File: BinaryTreeClient.java
// Author : Rafał Wochna 279752
import java.io.*;
import java.net.*;
/**
 * A client for the binary tree server.
 */
public class BinaryTreeClient {
    private static final String SERVER_ADDRESS = "localhost"; /** The address of the server. */
    private static final int SERVER_PORT = 12345; /** The port of the server. */
    private Socket socket; /** The socket for communication with the server. */
    private ObjectOutputStream out; /** The output stream for sending data to the server. */
    private ObjectInputStream in; /** The input stream for receiving data from the server. */
/**
 * Creates a new client and connects to the server.
 */
    public BinaryTreeClient() {
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT); /** Create a new socket. */
            out = new ObjectOutputStream(socket.getOutputStream()); /** Create a new output stream. */
            in = new ObjectInputStream(socket.getInputStream()); /** Create a new input stream. */
        } catch (IOException e) { /** Handle the exception. */
            e.printStackTrace(); /** Print the stack trace. */
            System.exit(1); /** Exit the program. */
        }
    }
    /**
     * Sends a command to the server and receives a response.
     * @param command - the command to send
     * @return the response from the server
     * @throws IOException if an I/O error occurs
     */
    public String sendCommand(String command) {
        String response = "";
        try {
            out.writeObject(command);
            response = (String) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return response;
    }
    /**
     * Receives a response from the server.
     * @return the response from the server
     * @throws IOException if an I/O error occurs
     */
    public String receiveResponse() throws IOException {
        try {
            String response = (String) in.readObject();
            return response;
        } catch (ClassNotFoundException e) {
            System.out.println("here");
            e.printStackTrace();
            return null;
        }
        
    }
    /**
     * Closes the connection to the server.
     */
    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
