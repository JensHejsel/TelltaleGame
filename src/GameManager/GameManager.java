package GameManager;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;

public class GameManager {
    private JButton button1;
    private JTextField TextField;
    private String name = "Jens";
    ArrayList<String> firstPart = new ArrayList<String>(Arrays.asList("Der var engang en dreng som hed @%#", 
    																  "Nu skal i hører historien om @%# som var en papegøje", 
    																  "Der var engang en moden kvinde kendt som @%#",
    																  "Der var engang en onkel som gik under aliaset @%#",
    																  "Der var engang en hemmelig agent kendt som @%#", 
    																  "Nu skal i hører om rotten @%#",
    																  "Nu skal i hører om nørden @%#",
    																  "Der var engang en bjørn den hed @%#"));
    ArrayList<String> SecondPart = new ArrayList<String>(Arrays.asList("NAME elskede at spille @%#",
    																   "@%# var NAME's ynglingsting og den skulle altid med på eventyr",
    																	"NAME kunne godt lide at have @%# med sig overalt det føltes betrykkende",
    																	"NAME havde jordens største @%#",
    																	"NAME tog altid sit sværd med sig fordi den ofte manglede @%#",
    																	"NAME glemte altid sin @%# når NAME skulle på tur",
    																	"NAME fik tit at vide NAME var god til at @%#"));
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
