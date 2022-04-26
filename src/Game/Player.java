package Game;

import java.util.ArrayList;

public class Player {

    String currentVote;
    String currentAnswer;
    String username;

    public Player(String username) {
        this.username = username;
    }
    public String getUsername(){
        return username;
    }

    public void setCurrentAnswer(String answer) {
        currentAnswer = answer;
    }

    public String getCurrentAnswer() {
        return currentAnswer;
    }

    public void setCurrentVote(String currentVote) {
        this.currentVote = currentVote;
    }
    public String getCurrentVote(){
        return currentVote;
    }
}
