package resources.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServerThread extends Thread {
    private Socket socket;
    private List<ServerThread> threadList;
    private PrintWriter out;

    public ServerThread(Socket socket, List<ServerThread> threads) {
        this.socket = socket;
        this.threadList = new CopyOnWriteArrayList<>(threads);
    }

    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader( new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println("Welcome to the Server!");

            String inputString;
            while((inputString = input.readLine()) != null) {
                if (inputString.equals(".exit")) {
                    break;
                }
                outputToAllClients(inputString);
                System.out.println("Server msg: " + inputString);
            }
        } catch (IOException e) {
            // Handles unexpected disconnection
            System.err.println("Client disconnected unexpectedly: " + e.getMessage());
        } finally {
            // Cleanup: close resources and remove thread from the list
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Error closing socket: " + e.getMessage());
            }
            threadList.remove(this);
        }
    }

    private void outputToAllClients(String outpuString) {
        for (ServerThread serverThread : threadList) {
            try {
                serverThread.out.println(outpuString);
            } catch (Exception e) {
                // Handle exception while sending messages to clients if any
            }
        }
    }
    // private ServerSocket serverSocket;

    // public void start(int port) {
    //     try {
    //         serverSocket = new ServerSocket(port);

    //         while (true) {
    //             // serverSocket.
    //         }

    //     } catch (Exception e) {
    //         System.out.println("Error occurred during connection: " + e);
    //     }

    // }

    // public void stop() {
    //     try {
    //         serverSocket.close();
    //     } catch (Exception e) {
    //         System.out.println("Error occurred during closure: " + e);
    //     }
    // }

    // private static class ClientHandler extends Thread {
    //     private Socket clientSocket;
    //     private PrintWriter out;
    //     private BufferedReader in;

    //     public ClientHandler(Socket socket) {
    //         this.clientSocket = socket;
    //     }

    //     public void run() {
    //         try {
    //             out = new PrintWriter(clientSocket.getOutputStream(), true);
    //             in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

    //             String inputLine;
    //             while ((inputLine = in.readLine()) != null) {
    //                 if (".".equals(inputLine)) {
    //                     out.println("bye");
    //                     break;
    //                 }
    //                 out.println(inputLine);
    //             }

    //             in.close();
    //             out.close();
    //             clientSocket.close();
    //         } catch (Exception e) {
    //             System.out.println("Error occurred with client connection: " + e);
    //         }

    //     }
    // }
}