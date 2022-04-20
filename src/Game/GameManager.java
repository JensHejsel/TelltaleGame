package Game;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class GameManager {
    JTextField iP = new JTextField();
    JTextField port = new JTextField();
    JTextField username = new JTextField();

    public static void main(String[] args) {
        GameManager manager = new GameManager();
    }

    public GameManager() {

        //Create and set up the window.
        JFrame frame = new JFrame("HelloWorldSwing");
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 300));
        mainPanel.setBackground(Color.black);

        frame.setMinimumSize(new Dimension(1000, 1000));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton hostButton = new JButton("Host Game");
        JButton joinButton = new JButton("Join Game");
        mainPanel.add(hostButton);
        mainPanel.add(joinButton);

        joinButton.addActionListener(e -> onJoinButtonClicked());

        frame.add(mainPanel);
        frame.pack();
        frame.setVisible(true);
    }

    private void onJoinButtonClicked() {

        JFrame joinFrame = new JFrame("Story Teller");
        JPanel joinPanel = new JPanel();
        joinFrame.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 300));
        joinFrame.setMinimumSize(new Dimension(1000, 1000));
        joinFrame.setBackground(Color.black);
        joinFrame.setLocationRelativeTo(null);
        iP.setText("Indsæt IP adresse");
        iP.setPreferredSize(new Dimension(200, 50));
        port.setPreferredSize(new Dimension(200, 50));
        port.setText("Indsæt port");
        username.setPreferredSize(new Dimension(200, 50));
        username.setText("Indtast brugernavn");
        JButton startButton = new JButton("Join lobby");
        startButton.addActionListener(e -> joinLobby());
        joinPanel.add(iP);
        joinPanel.add(port);
        joinPanel.add(username);
        joinPanel.add(startButton);
        joinFrame.add(joinPanel);
        joinFrame.pack();
        joinFrame.setVisible(true);
        joinPanel.setVisible(true);

    }

    private void joinLobby() {
        try {
            //int portNumber = Integer.parseInt(port.getText());
            //String iPAddress = iP.getText();
            Socket con = new Socket("172.31.147.101", 5000);
            PrintWriter out = new PrintWriter(con.getOutputStream());
            out.print("Hello Server!");
            out.write(0);
            out.flush();
            InputStreamReader in = new InputStreamReader(con.getInputStream());
        } catch (IOException o) {
            System.out.println("caught");
        }
    }
}