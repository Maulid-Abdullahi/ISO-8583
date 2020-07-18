package iso;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ClientWindow extends Frame implements WindowListener, ActionListener {
    TextField outgoing;
    TextField incoming;
    Button sendButton;

    public static void main (String[] args) {
        new ClientWindow();
    }


    public ClientWindow () {
        outgoing = new TextField();
        outgoing.setBounds(100, 120, 170, 30);
        Label outgoingLabel = new Label();
        outgoingLabel.setText("Transaction Message");
        outgoingLabel.setBounds(100, 100, 170, 20);


        sendButton = new Button("Send");
        sendButton.setBounds(100, 160, 170, 30);

        sendButton.addActionListener(this);
        add(sendButton);

        incoming = new TextField();
        incoming.setBounds(100, 250, 170, 30);
        Label incomingLabel = new Label();
        incomingLabel.setText("Response");
        incomingLabel.setBounds(100, 230, 170, 20);

        add(outgoing);
        add(incoming);

        add(incomingLabel);
        add(outgoingLabel);

        setSize(400, 400);
        setLayout(null);
        setVisible(true); // setting frame visibility
    }

    public void actionPerformed (ActionEvent ev) {
        System.out.println(outgoing.getText());
    }

    public void windowClosing (WindowEvent e) {
        dispose();
        System.exit(0);
    }

    public void windowOpened (WindowEvent e) {};
    public void windowClosed (WindowEvent e) {};
    public void windowIconified (WindowEvent e) {};
    public void windowDeiconified (WindowEvent e) {};
    public void windowActivated (WindowEvent e) {};
    public void windowDeactivated (WindowEvent e) {};
}



