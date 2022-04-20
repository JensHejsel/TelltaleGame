package Game;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameManager {
    public static void main(String[] args) { GameManager manager = new GameManager(); }
    public GameManager() {

        //Create and set up the window.
        JFrame frame = new JFrame("HelloWorldSwing");
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,300));
        mainPanel.setBackground(Color.black);

        frame.setMinimumSize(new Dimension(1000, 1000));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton hostButton = new JButton("Host Game");
        JButton joinButton = new JButton("Join Game");
        mainPanel.add(hostButton);
        mainPanel.add(joinButton);
        frame.add(mainPanel);
        frame.pack();
        frame.setVisible(true);
        // dillermand
    }
}
