package SingleThreaded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public void run() throws IOException {
        int port = 8010;
        ServerSocket socket = new ServerSocket(port);

        // Set timeout to 10 seconds (10000 ms)
        socket.setSoTimeout(10000);

        while (true) {
            try {
                System.out.println("Server is listening on port " + port);

                Socket acceptedConnection = socket.accept();
                System.out.println("Connection accepted from client: " + acceptedConnection.getRemoteSocketAddress());

                // Enable autoFlush
                PrintWriter toClient = new PrintWriter(acceptedConnection.getOutputStream(), true);
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(acceptedConnection.getInputStream()));

                // Send message to client
                toClient.println("Hello from the server!");

                // Read something from client (optional)
                String clientMessage = fromClient.readLine();
                System.out.println("Client says: " + clientMessage);

                // Close connection
                acceptedConnection.close();

            } catch (IOException ex) {
                System.out.println("No client connected within timeout.");
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
