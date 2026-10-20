import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/** Accepts one client, reads one message, replies, and then stops. */
public class Server {
    public static void main(String[] args) {
        int port = 5000;

        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Server started on port " + port + ".");
            System.out.println("Waiting for a client...");

            try (Socket socket = server.accept();
                 BufferedReader input = new BufferedReader(
                         new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                 PrintWriter output = new PrintWriter(socket.getOutputStream(), true,
                         StandardCharsets.UTF_8)) {

                System.out.println("Client connected: " + socket.getInetAddress());
                String message = input.readLine();

                if (message != null) {
                    System.out.println("Client says: " + message);
                    output.println("Hello Client!");
                } else {
                    System.out.println("Client disconnected before sending a message.");
                }
            }

            System.out.println("Server stopped.");
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
            System.out.println("Check whether another program is already using port " + port + ".");
        }
    }
}
