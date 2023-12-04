package resources.service;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private int port;
    private boolean serverStatus;
    private static ArrayList<ServerThread> threadList = new ArrayList<>();

    public Server (int port) {
        this.port = port;
    }

    public void run() {
        System.out.println("fstart");
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Tried!");
            while (true) {
                System.out.println("T1");
                Socket socket = serverSocket.accept();
                System.out.println("T2");
                ServerThread serverThread = new ServerThread(socket, threadList);

                threadList.add(serverThread);
                serverThread.start();

                if (!serverStatus) {
                    break;
                }
                System.out.println("runs!");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
