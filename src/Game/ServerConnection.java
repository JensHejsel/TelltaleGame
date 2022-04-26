package Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerConnection implements Runnable {

    Player player;
    private Socket server;
    private BufferedReader in;
    private PrintWriter out;

    public ServerConnection(Socket server, Player player) throws IOException {
        this.server = server;
        this.player = player;
        in = new BufferedReader(new InputStreamReader(server.getInputStream()));
        out = new PrintWriter(server.getOutputStream(), true);
    }

    @Override
    public void run() {

        out.println("username: " + player.getUsername());

        while (true) {



        }
    }
}
