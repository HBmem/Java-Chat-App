package resources.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread extends Thread {
    private Socket socket;
    private ArrayList<ServerThread> threadList;
    private PrintWriter out;

    public ServerThread(Socket socket, ArrayList<ServerThread> threads) {
        this.socket = socket;
        this.threadList = threads;
    }

    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader( new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(socket.getOutputStream(), true);

            while (true) {
                String outputString = input.readLine();

                if (outputString.equals(".exit")) {
                    break;
                }
                
                outputToAllClients(outputString);
                System.out.println("Server msg:" + outputString);
            }
        }
        catch (Exception e) {
            System.out.println();
        }
    }

    private void outputToAllClients(String outpuString) {
        for (ServerThread serverThread : threadList) {
            serverThread.out.println(outpuString);
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