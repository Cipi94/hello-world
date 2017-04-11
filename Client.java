package socket.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by aewwc on 10/04/2017.
 */
public class Client {

    public static void main(String[] arguments) {
        try {
            // creo socket
            System.out.println("Apro socket");
            Socket socket = new Socket("localhost", 8080);
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            System.out.println("Socket aperto");
            // creao wrappers
            ObjectOutputStream writer = new ObjectOutputStream(outputStream);
            writer.flush();
            System.out.println("obj output stream open");
            ObjectInputStream scanner = new ObjectInputStream(inputStream);
            System.out.println("obj input stream open");
            // creo wrappers tastiera e video
            Scanner tastiera = new Scanner(System.in);
            PrintWriter video = new PrintWriter(System.out);
            // logica
            boolean exit = false;
            while (!exit) {
                video.println("Cosa vuoi fare?\n1) somma\n2) moltiplicazione\n3) esci\n");
                video.flush();
                int scelta = tastiera.nextInt();
                switch (scelta) {
                    case 1:
                        writer.writeObject("sum");
                        writer.flush();
                        int a = tastiera.nextInt();
                        writer.writeInt(a);
                        writer.flush();
                        int b = tastiera.nextInt();
                        writer.writeInt(b);
                        writer.flush();
                        // attendo risultato dal server
                        int c = scanner.readInt();
                        video.println("Ho ricevuto: " + c);
                        video.flush();
                        break;
                    case 2:
                        writer.writeObject("multiply");
                        writer.flush();
                        a = tastiera.nextInt();
                        writer.writeInt(a);
                        writer.flush();
                        b = tastiera.nextInt();
                        writer.writeInt(b);
                        writer.flush();
                        // attendo risultato dal server
                        c = scanner.readInt();
                        video.println("Ho ricevuto: " + c);
                        video.flush();
                        break;
                    case 3:
                        writer.writeObject("exit");
                        writer.flush();
                        exit = true;
                        break;
                }
            }
            // chiudo tutto
            scanner.close();
            writer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
