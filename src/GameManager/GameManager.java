package GameManager;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;

public class GameManager {
    private JButton button1;
    private JTextField TextField;
    private String name = "Jens";
    ArrayList<String> firstPart = new ArrayList<String>(Arrays.asList("Der var engang en dreng som hed @%#", 
    																  "Nu skal i h�rer historien om @%# som var en papeg�je", 
    																  "Der var engang en moden kvinde kendt som @%#",
    																  "Der var engang en onkel som gik under aliaset @%#",
    																  "Der var engang en hemmelig agent kendt som @%#", 
    																  "Nu skal i h�rer om rotten @%#",
    																  "Nu skal i h�rer om n�rden @%#",
    																  "Der var engang en bj�rn den hed @%#"));
    ArrayList<String> SecondPart = new ArrayList<String>(Arrays.asList("NAME elskede at spille @%#",
    																   "@%# var NAME's ynglingsting og den skulle altid med p� eventyr",
    																	"NAME kunne godt lide at have @%# med sig overalt det f�ltes betrykkende",
    																	"NAME havde jordens st�rste @%#",
    																	"NAME tog altid sit sv�rd med sig fordi den ofte manglede @%#",
    																	"NAME glemte altid sin @%# n�r NAME skulle p� tur",
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
