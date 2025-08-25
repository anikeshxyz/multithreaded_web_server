package Multithreaded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    // This method returns a Runnable task
    public Runnable getRunnable() {
        return () -> {
            try {
                int port = 8010;
                InetAddress address = InetAddress.getByName("localhost");

                Socket socket = new Socket(address, port);

                PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // Send message to server
                toServer.println("Hello from the client (Thread: " + Thread.currentThread().getName() + ")");

                // Read response
                String line = fromServer.readLine();
                System.out.println("Response from server: " + line + " (Thread: " + Thread.currentThread().getName() + ")");

                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    public static void main(String[] args) {
        Client client = new Client();

        // Create 100 client threads
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(client.getRunnable(), "Client-" + i);
            thread.start();
        }
    }
}
