package sockets;


import java.io.*;
import java.net.*;

public class HttpMirror {
    public static void main (String[] args) {
        int i;

        try {
            i = 0;
            // get the port to listen on
            int port = Integer.parseInt(args[0]);
            // Create a server socket to listen on that port
            ServerSocket ss = new ServerSocket(port);
            // Now enter an infinite loop, waiting for connections and handling them

            for (;;) {
                // wait for client to connect. the method will block, and when it
                // returns the socket will be already connected to the client
                Socket client = ss.accept();
                // get input and ouput streams to talk to the client from the socket
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));

                // start sending our reply, using HTTP 1.0 protocol
                out.println("HTTP/1.0 " + (i++) + " "); // Version & status code
                out.println("Content-Type: text/plain"); // The type of data we send
                out.println();
                out.flush();

                // Now read the HTTP request from the client and send it right back
                // to the client as part of the body of our response.
                // The client does not disconnect so we never get an EOF.
                // It does send an empty line at the end of the hreaders, though.
                // So when we see the empty line, we stop reading. This meand we dont
                // mirrir the contents of POST requests.
                String line;
                while ((line =in.readLine()) != null) {
                    if (line.length() == 0) break;
                    out.println(line);
                }
                out.println("i = " + i);
                out.flush();

                // close the streams and socket, breaking the connection to the client
                out.close();
                in.close();
                client.close();
            }

        } catch (Exception e) {
            System.err.println(e);
            System.err.println("Usage: java HTTPMirror <port>");
        }
    }
}
