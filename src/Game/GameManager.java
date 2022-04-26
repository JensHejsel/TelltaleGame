package Game;

import javax.swing.*;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.io.*;
import java.net.*;

public class GameManager {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public static void main(String[] args) { GameManager manager = new GameManager(); }
    public GameManager() {

        //Create and set up the window.
        JFrame frame = new JFrame("Telltale Game");
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,300));
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
        frame.add(mainPanel);
        frame.pack();
        frame.setVisible(true);
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

        ServerSocket ss = null;
        try {
            int portNumber = 5000;
            ss = new ServerSocket(portNumber);
            while (true) {
                System.out.println("Waiting for connection request on port " + portNumber + "...");
                Socket con = ss.accept();
                System.out.println("Connection request received");
                InputStreamReader in = new InputStreamReader(con.getInputStream());
                StringBuffer msg = new StringBuffer();
                int c;
                while ((c = in.read()) != 0)
                    msg.append((char)c);
                PrintWriter out = new PrintWriter(con.getOutputStream());
                out.print("Simon says: " + msg);
                out.flush();
                con.close();
            }
        } catch (IOException e) {
            if (ss != null && ss.isBound() && !ss.isClosed())
                ss.close();
            System.err.println(e);
        }

    }
}
