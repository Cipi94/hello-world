package socket.server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by aewwc on 10/04/2017.
 */
public class ClientHandler extends Thread {

    private Socket mSocket;
    private InputStream mInputStream;
    private OutputStream mOutputStream;

    public ClientHandler(Socket socket) throws IOException {
        mSocket = socket;
        mInputStream = socket.getInputStream();
        mOutputStream = socket.getOutputStream();
    }

    @Override
    public void run() {
        try {
            // creiamo i wrapper
            ObjectInputStream scanner = new ObjectInputStream(mInputStream);
            ObjectOutputStream printer = new ObjectOutputStream(mOutputStream);
            printer.flush();
            // implementiamo la logica
            boolean exit = false;
            while (!exit) {
                // leggiamo cosa ci dice il client (ipotizziamo che il protocollo stabilisca che il comando sia una stringa)
                String command = null;
                try {
                    command = (String) scanner.readObject();
                } catch (ClassNotFoundException e) {
                    command = "wtf";
                }
                System.out.println("Ho letto: " + command);
                switch (command) {
                    case "sum":
                        // leggo due interi
                        int a = scanner.readInt();
                        int b = scanner.readInt();
                        int c = a + b;
                        System.out.println("Somma: a = " + a + ", b = " + b + ", c = " + c);
                        // invio la somma al client
                        printer.writeInt(c);
                        printer.flush();
                        break;
                    case "multiply":
                        // leggo due interi
                        a = scanner.readInt();
                        b = scanner.readInt();
                        c = a * b;
                        System.out.println("Somma: a = " + a + ", b = " + b + ", c = " + c);
                        // invio la somma al client
                        printer.writeInt(c);
                        printer.flush();
                        break;
                    case "exit":
                        exit = true;
                        break;
                    default:
                        printer.writeObject("Fuck");
                        printer.flush();
                        break;
                }
            }
            // chiudo tutto
            scanner.close();
            printer.close();
            mSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
