package Game;

import javax.swing.*;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.io.*;
import java.net.*;
import java.util.concurrent.CompletableFuture;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameManager {

    private static ExecutorService pool = Executors.newFixedThreadPool(8);

    JTextField iP = new JTextField();
    JTextField port = new JTextField();
    JTextField username = new JTextField();
    JLabel connectedUsersLabel;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    boolean gameHasStarted = false;

    private static ArrayList<PlayerHandler> players = new ArrayList<PlayerHandler>();

    public static void main(String[] args) { GameManager manager = new GameManager(); }
    public GameManager() {

        //Create and set up the window.
        JFrame frame = new JFrame("Telltale Game");
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 300));
        mainPanel.setBackground(Color.black);

        frame.setMinimumSize(new Dimension(1000, 1000));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton hostButton = new JButton("Host Spil");
        hostButton.addActionListener(e -> {
            try {
                onHostButtonClicked();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        JButton joinButton = new JButton("Join Spil");

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

    private void onHostButtonClicked() throws IOException {
        String ipAddress = null;
        try {
            ipAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        JLabel ipAddressLabel = new JLabel("Din IP adresse er: " + ipAddress);
        JButton copyToClipboardButton = new JButton("Kopiér adresse");
        JTextField hostUsernameField = new JTextField("Indtast brugernavn");
        hostUsernameField.setMaximumSize(new Dimension(200,30));
        hostUsernameField.setMargin(new Insets(30, 0, 0, 0));
        JLabel connectedUsersLabel = new JLabel("Forbindelser:\n");

        String finalIpAddress = ipAddress;
        copyToClipboardButton.addActionListener(e -> copyToClipboard(finalIpAddress));

        JFrame hostFrame = new JFrame();
        hostFrame.setBackground(Color.BLACK);
        hostFrame.setMinimumSize(new Dimension(400, 400));
        hostFrame.setLocationRelativeTo(null);

        JButton startServerButton = new JButton("Igangsæt spil");
        JPanel hostPanel = new JPanel();

        GroupLayout layout = new GroupLayout(hostPanel);
        hostPanel.setLayout(layout);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(ipAddressLabel)
                            .addComponent(copyToClipboardButton)
                            .addComponent(hostUsernameField))
                        .addComponent(connectedUsersLabel)
                        .addComponent(startServerButton)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(ipAddressLabel)
                            .addComponent(hostUsernameField)
                            .addComponent(connectedUsersLabel))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(copyToClipboardButton)
                            .addComponent(startServerButton))
        );

        hostFrame.add(hostPanel);
        hostFrame.pack();
        hostFrame.setVisible(true);
        listenForClients();
    }

    private void copyToClipboard(String ipAddress) {
        StringSelection selection = new StringSelection(ipAddress);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }

    private void listenForClients() throws IOException {
        GameManager x = this;
        new Thread() {

            @Override
            public void run() {

                ServerSocket ss = null;
                try {
                    int portNumber = 5000;
                    ss = new ServerSocket(portNumber);
                    while (gameHasStarted == false) {
                        System.out.println("Waiting for connection request on port " + portNumber + "...");

                        Socket con = ss.accept();
                        PlayerHandler player = new PlayerHandler(con, x);
                        players.add(player);
                        pool.execute(player);
                    }
                } catch (IOException e) {
                    if (ss != null && ss.isBound() && !ss.isClosed()) {
                        try {
                            ss.close();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    System.err.println(e);
                }
                finally {
                    interrupt();
                }
            }
        }.start();
    }

    public void displayConnectedUsers(String username) {
        connectedUsersLabel.setText(connectedUsersLabel.getText() + "\n" + username);
    }

    private void joinLobby() {
        try {
            //int portNumber = Integer.parseInt(port.getText());
            //String iPAddress = iP.getText();
            Socket con = new Socket(iP.getText(), 5000);
            ServerConnection serverConn = new ServerConnection(con, new Player(username.getText()));
            new Thread(serverConn).start();

        } catch (IOException o) {
            System.out.println("caught");
        }
    }
}