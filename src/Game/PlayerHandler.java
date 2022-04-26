package Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class PlayerHandler implements Runnable {

    Player player;
    GameManager gameManager;
    private Socket playerSocket;
    private BufferedReader in;
    private PrintWriter out;

    public PlayerHandler(Socket playerSocket, GameManager gameManager)throws IOException {
        this.playerSocket = playerSocket;
        this.gameManager = gameManager;
        in = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()));
        out = new PrintWriter(playerSocket.getOutputStream(), true);
    }
    @Override
    public void run() {

        try {
            while (true) {
                String input = in.readLine();
                if (input.startsWith("username:")) {
                    player = new Player(input.split(":", 0)[1]);
                    gameManager.displayConnectedUsers(input.split(":", 0)[1]);
                } else if(input.startsWith("answer:")) {
                    for (PlayerHandler x : gameManager.getPlayers()) {
                        //System.out.println(input.split(":", 0)[1].replace(":", "") + " | " + x.getPlayer().getUsername());
                        if (input.split(":", 0)[1].replace(":", "").equals(x.getPlayer().getUsername())) {
                            x.getPlayer().setCurrentAnswer(input.split(":", 0)[2]);
                            System.out.println(x.getPlayer().getCurrentAnswer());
                            break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public PrintWriter getOut() {
        return out;
    }

    public Player getPlayer() {
        return player;
    }
}
