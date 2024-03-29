package Game;

import javax.swing.*;
import static javax.swing.BoxLayout.Y_AXIS;

import java.lang.Object;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.io.*;
import java.net.*;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameManager {

    private static ExecutorService pool = Executors.newFixedThreadPool(8);
    Player hostPlayer = null;

    String combinedPlayerAnswers;

    String hostCurrentVote;
    ServerConnection serverConn;
    StoryController storyController = new StoryController();
    JTextField iP = new JTextField();
    JTextField port = new JTextField();
    JTextField username = new JTextField();
    JLabel connectedUsersLabel = new JLabel();
    JFrame joinFrame = new JFrame("Story Teller");
    JFrame hostFrame = new JFrame();
    JTextField userInput = new JTextField("Indsæt dit ord her");
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    boolean gameHasStarted = false;
    Player grandWinner;

    private static ArrayList<PlayerHandler> players = new ArrayList<PlayerHandler>();

    public static void main(String[] args) { GameManager manager = new GameManager();
    }
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
        connectedUsersLabel = new JLabel("Forbindelser:\n");

        String finalIpAddress = ipAddress;
        copyToClipboardButton.addActionListener(e -> copyToClipboard(finalIpAddress));

        hostFrame = new JFrame();
        hostFrame.setBackground(Color.BLACK);
        hostFrame.setMinimumSize(new Dimension(400, 400));
        hostFrame.setLocationRelativeTo(null);

        JButton startServerButton = new JButton("Igangsæt spil");
        startServerButton.addActionListener(listener -> {
            hostPlayer = new Player(hostUsernameField.getText());
            hostGameWindow();
            for (PlayerHandler playerHandler : players) {
                playerHandler.getOut().println("startgame");
            }
            hostPlayer = new Player(hostUsernameField.getText());
        });
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
                        System.out.println(ss.getInetAddress() + " connected");
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
        connectedUsersLabel.setText(connectedUsersLabel.getText() + ", " + username);
    }

    private void joinLobby() {
        try {
            //int portNumber = Integer.parseInt(port.getText());
            //String iPAddress = iP.getText();
            Socket con = new Socket(iP.getText(), 5000);
            serverConn = new ServerConnection(con, username.getText(), this);

            new Thread(serverConn).start();

        } catch (IOException o) {
            System.out.println("caught");
        }
    }
    private void hostGameWindow(){
        String nextLine = storyController.getNextLine();
        System.out.println("hostGameWindow siger at nextline er " + nextLine);
        System.out.println("hostGameWindow siger at currentline er " + storyController.getCurrentLine());
        if(nextLine.equals("endOfStory")){
            hostEndGameWindow();
        }else {
            for (PlayerHandler playerHandler : players)
                playerHandler.getOut().println("nextLine:" + nextLine);

            hostFrame.getContentPane().removeAll();
            JPanel gamePanel = new JPanel();
            hostFrame.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 300));
            hostFrame.setMinimumSize(new Dimension(1000, 1000));
            JButton sendSentence = new JButton("Indsæt ord");
            sendSentence.addActionListener(e -> hostInput());
            JButton startVoting = new JButton("Start afstemning");
            startVoting.addActionListener(e -> {
                hostVotingWindow();
                combinedPlayerAnswers = "";
                combinedPlayerAnswers += hostPlayer.getCurrentAnswer();
                for (PlayerHandler player : players)
                    combinedPlayerAnswers += ":" + player.getPlayer().getCurrentAnswer();
                for (PlayerHandler player : players)
                    player.getOut().println("startvoting:" + combinedPlayerAnswers);
            });
            JLabel unfinishedSentence = new JLabel(nextLine);
            gamePanel.add(unfinishedSentence);
            gamePanel.add(userInput);
            gamePanel.add(sendSentence);
            gamePanel.add(startVoting);
            hostFrame.add(gamePanel);
            hostFrame.revalidate();
            hostFrame.repaint();
        }
    }
    public void joinGameWindow(){
        joinFrame.getContentPane().removeAll();
        JPanel gamePanel = new JPanel();
        joinFrame.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 300));
        joinFrame.setMinimumSize(new Dimension(1000, 1000));
        JButton sendSentence = new JButton("Indsæt ord");
        sendSentence.addActionListener(listener -> userInput());
        JLabel unfinishedSentence = new JLabel(serverConn.getNextLine());
        sendSentence.addActionListener(e -> userInput());
        gamePanel.add(unfinishedSentence);
        gamePanel.add(userInput);
        gamePanel.add(sendSentence);
        joinFrame.add(gamePanel);
        joinFrame.revalidate();
        joinFrame.repaint();
    }
    private void hostVotingWindow() {
        hostFrame.getContentPane().removeAll();
        JPanel gamePanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(gamePanel, Y_AXIS);
        gamePanel.setLayout(boxLayout);
        hostFrame.setMinimumSize(new Dimension(1000, 1000));
        JButton hostInput = new JButton(hostPlayer.getCurrentAnswer());
        hostInput.addActionListener(e-> hostVoteInput(hostInput));
        gamePanel.add(hostInput);
        for (PlayerHandler player : players) {
            JButton x = new JButton(player.getPlayer().getCurrentAnswer());
            x.addActionListener(e-> hostVoteInput(x) );
            gamePanel.add(x);
        }
        JButton seeWinnerOfThisRound = new JButton("Se vinder");
        seeWinnerOfThisRound.addActionListener(e -> {
            Player winner = findWinner();
            hostRoundWinnerWindow(winner);
            for (PlayerHandler player : players)
                player.getOut().println("winnerround:"+winner.getUsername()+":"+winner.getCurrentAnswer());
            });
        hostFrame.add(seeWinnerOfThisRound);
        hostFrame.add(gamePanel);
        hostFrame.revalidate();
        hostFrame.repaint();
    }

    public void joinVotingWindow(String answers) {
        joinFrame.getContentPane().removeAll();
        JPanel gamePanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(gamePanel, Y_AXIS);
        gamePanel.setLayout(boxLayout);
        joinFrame.setMinimumSize(new Dimension(1000, 1000));
        for (String x : answers.split(":")) {
            JButton button = new JButton(x.replace(":", ""));
            gamePanel.add(button);
            button.addActionListener(e->  joinVoteInput(button) );
        }
        joinFrame.add(gamePanel);
        joinFrame.revalidate();
        joinFrame.repaint();
    }

    private void hostRoundWinnerWindow(Player winner){
        hostFrame.getContentPane().removeAll();
        JPanel gamePanel = new JPanel();
        hostFrame.setMinimumSize(new Dimension(1000, 1000));
        JLabel winnerAnnouncer = new JLabel("Winner of the round is: " +winner.getUsername()+" with the sentence: " + winner.currentAnswer);
        gamePanel.add(winnerAnnouncer);
        JButton startNextRound = new JButton("Start næste runde");
        startNextRound.addActionListener(e -> {
            hostGameWindow();
            System.out.println("hostRoundWinnerWindow siger at currentLine er " + storyController.getCurrentLine());
            if (!Objects.equals(storyController.getCurrentLine(), "endOfStory"))
                for(PlayerHandler player : players)
                    player.getOut().println("startgame:");
        });
        hostFrame.add(startNextRound);
        hostFrame.add(gamePanel);
        hostFrame.revalidate();
        hostFrame.repaint();
    }
    public void joinRoundWinnerWindow(String winnerUsername, String winnerAnswer){
        joinFrame.getContentPane().removeAll();
        JPanel gamePanel = new JPanel();
        joinFrame.setMinimumSize(new Dimension(1000, 1000));
        JLabel winnerAnnouncer = new JLabel("Winner of the round is: " +winnerUsername+" with the sentence: " + winnerAnswer);
        gamePanel.add(winnerAnnouncer);
        joinFrame.add(gamePanel);
        joinFrame.revalidate();
        joinFrame.repaint();
    }

    public void hostEndGameWindow(){
        grandWinner = hostPlayer;
        for(PlayerHandler player : players){
            if(player.getPlayer().getPoints() > grandWinner.getPoints()){
                grandWinner = player.getPlayer();
            }
        }
        for (PlayerHandler playerHandler : players){
            playerHandler.getOut().println("endgame:"+grandWinner.getUsername() + ":"+ storyController.getFullStory());
        }

        hostFrame.getContentPane().removeAll();
        JPanel gamePanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(gamePanel, Y_AXIS);
        gamePanel.setLayout(boxLayout);
        JLabel winnerAnnouncer = new JLabel("The Winner is:");
        JLabel winner = new JLabel(grandWinner.getUsername());
        JLabel storyAnnouncer = new JLabel("The full story:");
        JLabel fullStory = new JLabel(storyController.getFullStory());
        gamePanel.add(winnerAnnouncer);
        gamePanel.add(winner);
        gamePanel.add(storyAnnouncer);
        gamePanel.add(fullStory);
        hostFrame.add(gamePanel);
        hostFrame.revalidate();
        hostFrame.repaint();
    }

    public void joinEndGameWindow(String winnerUsername, String fullStory) {
        joinFrame.getContentPane().removeAll();
        JPanel gamePanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(gamePanel, Y_AXIS);
        gamePanel.setLayout(boxLayout);
        JLabel winnerAnnouncer = new JLabel("The Winner is:");
        JLabel winner = new JLabel(winnerUsername);
        JLabel storyAnnouncer = new JLabel("The full story:");
        JLabel fullStoryLabel = new JLabel(fullStory);
        gamePanel.add(winnerAnnouncer);
        gamePanel.add(winner);
        gamePanel.add(storyAnnouncer);
        gamePanel.add(fullStoryLabel);
        joinFrame.add(gamePanel);
        joinFrame.revalidate();
        joinFrame.repaint();
    }


    private void hostInput() {
        hostPlayer.setCurrentAnswer(storyController.setVotedAnswer(userInput.getText()));
    }

    private void userInput(){
        serverConn.getOut().println("answer:"+ serverConn.getUsername()+ ":" + storyController.setVotedAnswer(userInput.getText()));
    }

    public ArrayList<PlayerHandler> getPlayers() {
        return players;
    }

    private void hostVoteInput(JButton thisButton){
        hostPlayer.setCurrentVote(thisButton.getText());
    }
    private void joinVoteInput(JButton thisButton){
        serverConn.getOut().println("vote:"+ serverConn.getUsername()+ ":" + thisButton.getText());
    }
    private Player findWinner(){
        Player winner = null;
        String combinnedVotes = hostPlayer.getCurrentVote();
        for(PlayerHandler player : players){
            combinnedVotes += player.getPlayer().getCurrentVote();
        }
        int votesForRightAnswer = 0;
        for (PlayerHandler player : players){
            int index = 0;
            int currentVotes =0;
            while((index = combinnedVotes.indexOf(player.getPlayer().getCurrentAnswer(),index)) != -1){
                currentVotes++;
                index+=player.getPlayer().getCurrentAnswer().length();
            }
            System.out.println(currentVotes);
            if (currentVotes > votesForRightAnswer){
                winner = player.getPlayer();
                votesForRightAnswer = currentVotes;
            }
        }
        int index = 0;
        int currentVotes =0;
        while((index = combinnedVotes.indexOf(hostPlayer.getCurrentAnswer(),index)) != -1){
            currentVotes++;
            index+=hostPlayer.getCurrentAnswer().length();
        }
        System.out.println(currentVotes);
        if(currentVotes > votesForRightAnswer){
            winner = hostPlayer;
        }

        storyController.setWinningAnswer(winner.currentAnswer);
        for(PlayerHandler player : players)
            player.getOut().println("winningAnswer:"+ winner.getCurrentAnswer());

        winner.awardWinner();
        return winner;

    }
}