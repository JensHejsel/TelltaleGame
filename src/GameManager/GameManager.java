package GameManager;

import javax.swing.*;

public class GameManager {
    private JButton button1;
    private JTextField TextField;

    public static void main(String[] args){
        createAndShow();
    }
    private static void createAndShow() {
        JFrame mainFrame = new JFrame("Telltale Game");

        mainFrame.setSize(500, 400);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
    }
}
