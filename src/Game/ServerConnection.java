package Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerConnection implements Runnable {

    private Player player;
    private String nextLine;
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

        out.print("username: " + player.getUsername());
            try {
                while (true) {
                    String input = in.readLine();
                    if (input.startsWith("nextLine:")){
                        nextLine = input.split(":",0)[1];
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }
    public String getNextLine(){
        return nextLine;
    }
}
