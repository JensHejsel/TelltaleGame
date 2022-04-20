package GameManager;

import javax.swing.*;
import java.awt.*;

public class GameManager {
    public static void main(String[] args) { createAndShow(); }
    private static void createAndShow() {

        //Create and set up the window.
        JFrame frame = new JFrame("HelloWorldSwing");
        JPanel panel = new JPanel();
        BoxLayout layout = new BoxLayout(panel, BoxLayout.X_AXIS);
        panel.setLayout(layout);

        frame.setMinimumSize(new Dimension(400, 400));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add the ubiquitous "Hello World" label.
        JButton hostButton = new JButton("Host Game");
        JButton joinButton = new JButton("Join Game");
        panel.add(hostButton);
        panel.add(joinButton);

        //Display the window.
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        //Hej jens
    }
}
