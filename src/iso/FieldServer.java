package iso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class FieldServer {

    public static void main (String[] args) {
        new FieldServer().run();
//        String  F = new FieldServer().getFields("0010000010001000011001");
//
//        System.out.println(F);
    }

    public void run () {
        System.out.println("Listening on port 6000...");

        try {
            // Create server port 6000
            ServerSocket serverSocket = new ServerSocket(6000);
            //Socket sd = new Socket();

            // Continuously listen to client requests
            while (true) {
                // get socket connection for current client
                Socket clientSocket = serverSocket.accept();

                Thread t = new Thread(new ClientHandler(clientSocket));
                t.start();

                System.out.println("Got connection...");
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public String getFields(String bitmap){
        String fields = "";
        int counter = 1;
        for(int i =0; i<bitmap.length(); i++){
            if(bitmap.charAt(i) == '1'){
                fields = fields + ("," + counter);
            }
            counter = counter + 1;
        }
        return fields.substring(1);
    }

    public class ClientHandler implements Runnable {
        BufferedReader reader;
        PrintWriter writer;
        Socket sock;

        // Constructor - sets up reader and sock fields needed for reading data sent by client.
        public ClientHandler (Socket clientSocket) {
            try {
                // set up client socket
                sock = clientSocket;

                // Create a BufferedReader object for reading messages from client
                InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(isReader);

                writer = new PrintWriter(sock.getOutputStream());

            }  catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }

        @Override
        public void run() {

            String bitmap;
            try {
                while ((bitmap = reader.readLine()) != null) {
                   String fields = getFields(bitmap);
                    System.out.println(fields);

                    writer.println(fields);
                    writer.flush();

                }
            } catch (IOException ex) {
                System.err.println("Error: " + ex.getMessage());
            }
        }



    }
}



