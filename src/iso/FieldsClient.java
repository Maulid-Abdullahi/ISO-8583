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
import java.util.ArrayList;

public class  FieldsClient extends JFrame implements ActionListener {
    JCheckBox[] checkBox;


    BufferedReader reader; // reads from server
    PrintWriter writer; // send data to server

    Socket sock;

    String host; // server location (ip address)

    int port;
    public static  void main(String[] args){
        new FieldsClient("127.0.0.1", 6000).run();
    }

    public  FieldsClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run () {

        ArrayList<String> data = new ArrayList<>();
        for(int i = 1; i <= 64; i++){
            data.add("" + i);
        }

        //JCheckBox[] checkBox;
        JButton submitButton;
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(5, 5));

        checkBox = new JCheckBox[data.size()];
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(0, 4, 5, 5));

        for (int i = 0; i < data.size(); i++) {
            checkBox[i] = new JCheckBox(data.get(i));
            centerPanel.add(checkBox[i]);
        }
        contentPane.add(centerPanel, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel();
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        footerPanel.add(submitButton);
        contentPane.add(footerPanel, BorderLayout.PAGE_END);

        frame.setContentPane(contentPane);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);


        connectToServer(host, port);

        Thread readFromServer = new Thread(new ServerHandler());
        readFromServer.start();

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
        String message = "";
        for(JCheckBox c:checkBox){
            if(c.isSelected()) {
                message += "1";
            } else {
                message += "0";
            }
        }

        for(JCheckBox c:checkBox){
            c.setSelected(false);
        }

        writer.println(message); // send to server.

        writer.flush();

    }

    public class ServerHandler implements Runnable {

        public synchronized void run () {
            String message;
            try {
                // Print response from server to console
                while ((message = reader.readLine()) != null) {
                    System.out.println("Fields: " + message);
                }
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
       }
        }
}




