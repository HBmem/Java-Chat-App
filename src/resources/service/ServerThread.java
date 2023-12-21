package resources.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

public class ServerThread extends Thread {

    Logger logger = Logger.getLogger(getClass().getName());

    private Socket socket;
    private List<PrintWriter> clientWriters;

    public ServerThread(Socket socket, List<PrintWriter> clientWriters) {
        this.socket = socket;
        this.clientWriters = new CopyOnWriteArrayList<>(clientWriters);
    }

    public void addClientWriter(PrintWriter writer) {
        clientWriters.add(writer);
    }

    public void updateServerThreads(PrintWriter newWriter) {
        for (PrintWriter writer : clientWriters) {
            if (writer != newWriter) {
                writer.flush();
            }
        }
    }

    @Override
    public void run() {
        try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);) {

            out.println("Welcome to the Server!");

            String inputString;
            String clientName = input.readLine();
            broadcast(clientName + " has connected to the server!");

            while ((inputString = input.readLine()) != null) {
                if (inputString.equals(".exit")) {
                    broadcast(clientName + " has left the server");
                    break;
                }

                broadcast(inputString);
                logger.info("Server msg: " + inputString);
            }
        } catch (IOException e) {
            // Handles unexpected disconnection
            logger.info("Client disconnected unexpectedly: " + e.getMessage());
        } finally {
            removeClientWriter();
            try {
                socket.close();
            } catch (IOException e) {
                logger.info("Error closing socket: " + e.getMessage());
            }
        }
    }

    private void broadcast(String message) {
        for (PrintWriter writer : clientWriters) {
            try {
                writer.println(message);
                writer.flush();
            } catch (Exception e) {
                logger.info("Error broadcasting message: " + e.getMessage());
            }
        }
    }

    private void removeClientWriter() {
        clientWriters.removeIf(writer -> writer.toString().contains(socket.getRemoteSocketAddress().toString()));
    }
}