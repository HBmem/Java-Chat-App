package resources.service;

import java.net.ServerSocket;

public class Server {
    private ServerSocket serverSocket;

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);

        while (true) {
            // serverSocket.
        }

        } catch (Exception e) {
            System.out.println("Error occurred during connection: " + e );
        }
        
    }

    public void stop() {
        try {
            serverSocket.close();
        } catch (Exception e) {
            System.out.println("Error occurred during closure: " + e );
        }
    }
}