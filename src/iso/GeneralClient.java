package iso;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GeneralClient extends Frame implements ActionListener {
    TextField outgoing;
    Button sendButton;

    BufferedReader reader; // reads from server
    PrintWriter writer; // send data to server

    Socket sock;

    String host; // server location (ip address)

    int port;

    public GeneralClient (String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run () {
        outgoing = new TextField();
        outgoing.setSize(800, 100);
        outgoing.setBounds(100, 120, 300, 30);
        Label outgoingLabel = new Label();
        outgoingLabel.setText("ISO 8583 Message");
        outgoingLabel.setBounds(100, 100, 170, 20);


        sendButton = new Button("Send");
        sendButton.setBounds(100, 160, 170, 30);

        sendButton.addActionListener(this);
        add(sendButton);

        add(outgoing);
        add(outgoingLabel);

        setSize(1000, 300);
        setLayout(null);
        setVisible(true); // setting frame visibility

        connectToServer(host, port);

//        Thread readFromServer = new Thread(new ServerHandler());
//        readFromServer.start();

    }

    public void connectToServer (String host, int port) {
        // make a socket, then make a PrintWriter
        // assign the PrintWriter to writer instance variable
        try {
            sock = new Socket(host, port); // connect to server

            InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
            reader = new BufferedReader(streamReader);

            writer = new PrintWriter(sock.getOutputStream());

            System.out.println("Connection established...");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void actionPerformed (ActionEvent ev) {
        // get text from text field
        // send it to server using writer

        writer.println(outgoing.getText()); // send to server.

        writer.flush();

        outgoing.setText("");
        outgoing.requestFocus();

    }

//    public class ServerHandler implements Runnable {
//
//        public synchronized void run () {
//            String message;
//            try {
//                while ((message = reader.readLine()) != null) {
//                    incoming.setText(message);
//                }
//            } catch (IOException ex) {
//                System.err.println(ex.getMessage());;
//            }
//        }
//    }
}




