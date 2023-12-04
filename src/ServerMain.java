import java.util.ArrayList;

import resources.service.Server;
import resources.service.ServerThread;
import resources.ui.ServerFrame;

public class ServerMain {

    private static final int PORT = 5000;

    private static boolean serverStatus = true;

    private static void startServer() {
        
    }
    public static void main(String[] args) {
        ServerFrame serverWindow = new ServerFrame();

        // serverWindow.logText("Hello World");
        Server server = new Server(PORT);
        server.run();
    }
}
