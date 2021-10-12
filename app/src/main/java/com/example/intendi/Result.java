package com.example.intendi;

public class Result {
    int score;
    String dateOfGame;
    public Result(int score, String dateOfGame){
        this.score = score;
        this.dateOfGame = dateOfGame;
    }
    public int getScore(){ return score; }
    public String getDateOfGame(){ return dateOfGame; }
}
