package Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerConnection implements Runnable {

    GameManager gameManager;
    String username;
    private String nextLine = "";
    private Socket server;
    private BufferedReader in;
    private PrintWriter out;

    public ServerConnection(Socket server, String username, GameManager gameManager) throws IOException {
        this.gameManager = gameManager;
        this.server = server;
        this.username = username;
        in = new BufferedReader(new InputStreamReader(server.getInputStream()));
        out = new PrintWriter(server.getOutputStream(), true);
    }

    @Override
    public void run() {

        out.println("username:" + username);
            try {
                while (true) {
                    String input = in.readLine();

                    if (input.startsWith("nextLine:")){
                        nextLine = input.split(":",0)[1];
                        gameManager.storyController.updateCurrentLine(nextLine);
                    }
                    else if (input.startsWith("startgame")) {
                        gameManager.joinGameWindow();
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }
    public String getNextLine(){
        return nextLine;
    }
    public PrintWriter getOut(){ return out; }
    public String getUsername(){return username;}
}
