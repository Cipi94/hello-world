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
        mInputStream = socket.getInputStream(); //serve per aprire il canale di comunicazione in ingresso
        mOutputStream = socket.getOutputStream(); //serve per aprire il canale di comunicazione in uscita
    }

    @Override
    public void start() {
        try {
            // creiamo i wrapper... COSA SONO???? Sono i cosi per serializzare gl ioggetti, involucri che contengono roba serializzata
            ObjectInputStream scanner = new ObjectInputStream(mInputStream); //Serve per de-serializzare la stringa di byte che gli arriva; ossia ricostruisce l'oggetto
            ObjectOutputStream printer = new ObjectOutputStream(mOutputStream); //Serve per serializzare l'oggetto trasformandolo in una sequenza di byte
            printer.flush(); //serve per pulire quello che è eventualmente rimasto in uscita
            // implementiamo la logica
            boolean exit = false;
            while (!exit) {
                // leggiamo cosa ci dice il client (ipotizziamo che il protocollo stabilisca che il comando sia una stringa)
                String command = null;
                try {
                    command = (String) scanner.readObject(); //scanner è il wrapper di object, e quindo leggo quello che mi arriva
                } catch (ClassNotFoundException e) { //Errore in caso non arrivi una stringa
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
                        printer.writeInt(c); //Scrivo un intero, quindi serializzali come un intero
                        printer.flush(); //sempre per pulire
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
            //Qui è importante che il socket lo chiudi per ultimo!!
            scanner.close(); //Chiudo input
            printer.close(); //chiudo output
            mSocket.close(); //chiudo connessione
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
