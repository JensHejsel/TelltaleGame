package Game;

import java.util.ArrayList;

public class Player {

    String currentVote;
    String currentAnswer;
    String username;

    int points = 0;

    public Player(String username) {
        this.username = username;
        this.points = 0;
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
    public void awardWinner(){
        this.points++;
    }
    public int getPoints(){ return points;
    }
}
