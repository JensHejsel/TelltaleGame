package Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PlayerHandler implements Runnable{
    private Socket player;
    private BufferedReader in;
    private PrintWriter out;

    public PlayerHandler(Socket playerSocket)throws IOException {
        this.player = playerSocket;
        in = new BufferedReader(new InputStreamReader(player.getInputStream()));
        out = new PrintWriter(player.getOutputStream(), true);
    }
    @Override
    public void run() {

    }
}
