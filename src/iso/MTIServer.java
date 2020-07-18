package iso;

import java.io.*;
import java.net.*;


public class MTIServer {
    MessageFormat msgFormat = new MessageFormat();

    public static void main (String[] args) {
        new MTIServer().run();
    }

    public void run () {
        System.out.println("Listening on port 5000...");

        try {
            // Create server port 5000
            ServerSocket serverSocket = new ServerSocket(5000);
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

    /**
     * Checks whether an MTI number is valid
     *
     * @param mti - a string of 4 characters representing an mti number
     * @return true/false
     */
    public void processMTI (String mti) throws NotISO8583 {
        // check that MTI number is well formed
        try {

            Integer.parseInt(mti);
        } catch (NumberFormatException ex) {
            throw new NotISO8583("Bad format: Not an ISO8583 number");
        }

        String [] mtiDigits = mti.split("");

        // Interpret
        int versionNo = Integer.parseInt(mtiDigits[0]);
        int classNo = Integer.parseInt(mtiDigits[1]);
        int functionNo = Integer.parseInt(mtiDigits[2]);
        int originNo = Integer.parseInt(mtiDigits[3]);

        String version = msgFormat.version.get(versionNo);
        String msgClass = msgFormat.messageClass.get(classNo);
        String msgFunction = msgFormat.messageFunction.get(functionNo);
        String msgOrigin = msgFormat.messageOrigin.get(originNo);

        System.out.println();
        System.out.println("=====================================================");
        System.out.format("version of ISO 8583: (%s = %s)%n", versionNo, version);
        System.out.format("class of the message: (%s = %s)%n", classNo, msgClass);
        System.out.format("function of the message: (%s = %s)%n", functionNo, msgFunction);
        System.out.format("who began the communication: (%s = %s)%n", originNo, msgOrigin);
        System.out.println("=====================================================\n");


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
                    // parse message - extract first 4 characters
                    String mtiMessage = message.substring(0,4);

                    try {
                        processMTI(mtiMessage);
                        writer.println("Accepted");
                        writer.flush();
                    } catch (NotISO8583 ex ) {
                        writer.println("Rejected - Please Try Again.");
                        writer.flush();
                        System.err.println(ex.getMessage());
                    }



                }
            } catch (IOException ex) {
                System.err.println("Error: " + ex.getMessage());
            }catch(StringIndexOutOfBoundsException r){
                writer.println("Rejected");
                writer.flush();
                System.out.println("IndexOutOfBoundException Occurs");
            }
        }
    }
}
