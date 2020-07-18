package sockets;

import java.io.*;
import java.net.*;

/*
This program connects to a web server and downloads the specified URL
from it. It uses the HTTP protocol directly.
 */

public class HttpClient {
    public static void main (String[] args) {
        try {
            // check arguments
            if ((args.length != 1) && (args.length != 2))
                throw new IllegalArgumentException("Wrong number of arguments");
            // Get an output stream to write the URL contents to
            OutputStream to_file;
            if (args.length == 2) to_file = new FileOutputStream(args[1]);
            else to_file = System.out;

            // Now use the URL class to parse the user-specified URL into
            // its various parts: protocol, host. port, filename.
            URL url =  new URL(args[0]);
            String protocol = url.getProtocol();
            if (!protocol.equals("http"))
                throw new IllegalArgumentException("URL must use 'http:' protocol");
            String host = url.getHost();
            int port  = url.getPort();
            if (port == -1) port = 80; // if no port, usethe default HTTP post
            String filename = url.getFile();
            // open a network socket connection to the specified host and port
            Socket socket = new Socket(host, port);
            // Gte input and output streams for the socket
            InputStream from_server = socket.getInputStream();
            PrintWriter to_server = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

            // send the http get command tp the web server, specifying the file
            System.out.println("File: '" +  filename + "'");
            to_server.println("GET /" + filename + " HTTP/1.0");
            // to_server.println("Connection: Keep-Alive");
            to_server.println("User-Agent: Javalinux Aalborg [en]");
            to_server.println("");
            to_server.flush();

            // Now read the serrver's respomnse
            byte[] buffer = new byte[4096];
            int byte_read;
            while ((byte_read = from_server.read(buffer)) != -1)
                to_file.write(buffer, 0, byte_read);

            // When the server closes the connection, we close our stuff
            socket.close();
            to_file.close();

        } catch (Exception e) {
            System.err.println(e);
            System.err.println("Usage: java HttpClient <URL> [<filename>]");
        }
    }
}
