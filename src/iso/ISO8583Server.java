package iso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Reads an ISO 8583 message and splits it into
 * separate pieces of data:
 *  - header
 *  - MTI
 *  - Bitmap
 *  - Data element
 *
 *  (header) -> MTI -> (Primary bitmap) -> (secondary bitmap - optional) -> (message data fields - optional)
 */
public class ISO8583Server {

    public static void main (String[] args) {
        new ISO8583Server().run();
    }

    public void run () {
        System.out.println("Listening on port 5050...");

        try {
            // Create server port 5050
            ServerSocket serverSocket = new ServerSocket(5050);
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

            String message;
            try {
                while ((message = reader.readLine()) != null) {
                    //System.out.println(message);
                    // Get MTI message - extract first 4 characters
                    String header = message.substring(0,4);

                    String mti = message.substring(4, 8);


                    // Used to check if there is a secondary bitmap
                    String bitmapTag = message.substring(8, 9);

                    // assumes only primary bitmap present
                    String bitmap = message.substring(8, 72);
                    String messageData = message.substring(72);
                    
                    // update if secondary bitmap present
                    if (hasSecondaryBitmap(bitmapTag)) {
                        // primary + secondary bitmap if bitmapTag specifies that there be one
                        bitmap = message.substring(8,136);

                        messageData = message.substring(136);
                    }


                    writer.println(header);


                    writer.println(mti);
                    writer.flush();
                    writer.println(bitmap);
                    writer.flush();
                    writer.println(messageData);
                    writer.flush();
                    System.out.println("Header: "+ header);
                    System.out.println("Message Type Indicator: " + mti);
                    System.out.println("Bitmap: " +bitmap);
                    System.out.println("Data Elements: " +messageData);

                }
            } catch (IOException ex) {
                System.err.println("Error: " + ex.getMessage());
            }
        }

        /**
         * Takes a single hex value and checks the 2nd bit to determine if the
         * ISO 8583 message has a secondary bitmap. A secondary bitmap exists if
         * this bit is 1, otherwise it does not.
         * A hex value will have a 1 as the 2nd bit iff its one of: 4,5,6,7,c,d,e,f
         *
         *
         * @param bitmapTag
         * @return true or false
         */
        private boolean hasSecondaryBitmap(String bitmapTag) {
            String hexVals = "4567cdef";

            return hexVals.indexOf(bitmapTag) >= 0;
        }
    }
}


