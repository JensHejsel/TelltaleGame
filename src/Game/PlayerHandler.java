package Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PlayerHandler implements Runnable {

    GameManager gameManager;
    private Socket player;
    private BufferedReader in;
    private PrintWriter out;

    public PlayerHandler(Socket playerSocket, GameManager gameManager)throws IOException {
        this.player = playerSocket;
        this.gameManager = gameManager;
        in = new BufferedReader(new InputStreamReader(player.getInputStream()));
        out = new PrintWriter(player.getOutputStream(), true);
    }
    @Override
    public void run() {

        try {
            while (true) {
                String input = in.readLine();

                if (input.startsWith("username: ")) {
                    gameManager.displayConnectedUsers(input.split(": ", 0)[1]);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
