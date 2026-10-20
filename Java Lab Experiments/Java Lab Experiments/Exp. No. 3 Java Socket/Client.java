import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/** Sends one message to the server and prints its reply. */
public class Client {
    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost";
        int port = 5000;

        try (Socket socket = new Socket(host, port);
             BufferedReader input = new BufferedReader(
                     new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true,
                     StandardCharsets.UTF_8)) {

            System.out.println("Connected to server at " + host + ":" + port);
            output.println("Hello Server");

            String response = input.readLine();
            if (response != null) {
                System.out.println("Server says: " + response);
            } else {
                System.out.println("Server closed the connection without replying.");
            }

        } catch (IOException e) {
            System.out.println("Could not connect to the server: " + e.getMessage());
            System.out.println("Start Server.java first and make sure port 5000 is available.");
        }
    }
}
