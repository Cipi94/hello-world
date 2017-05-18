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
            InputStream inputStream = socket.getInputStream(); //Per prendere ciò che mi arriva dal server
            OutputStream outputStream = socket.getOutputStream(); //Prr mandare al server
            System.out.println("Socket aperto");
            // creao wrappers
            ObjectOutputStream writer = new ObjectOutputStream(outputStream);
            writer.flush();
            System.out.println("obj output stream open");
            ObjectInputStream scanner = new ObjectInputStream(inputStream);
            System.out.println("obj input stream open");
            // creo wrappers tastiera e video
            Scanner tastiera = new Scanner(System.in); //Dico che l'input è la tastiera
            PrintWriter video = new PrintWriter(System.out); //e che l'output è il monitor
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
            //IMPORTANTE è NON CHIUDERE TASTIERA E VIDEO SENNò DOPO NON VA PIU NIENTE... provare per credere
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
