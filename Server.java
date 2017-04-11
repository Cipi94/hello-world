package socket.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by aewwc on 10/04/2017.
 */
public class Server {

    public static void main(String[] arguments) {
        try {
            Server server = new Server(8080);
            server.start();
        } catch (IOException e) {
            System.out.println("Non posso avviare il server. Errore: " + e.getMessage());
        }

    }

    private final ServerSocket mServerSocket;

    private Server(int port) throws IOException {
        mServerSocket = new ServerSocket(port);
    }

    private void start() throws IOException {
        while (true) {
            Socket socket = mServerSocket.accept();
            ClientHandler handler = new ClientHandler(socket);
            handler.start();
        }
    }
}
