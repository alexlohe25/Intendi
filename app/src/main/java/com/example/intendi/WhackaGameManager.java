package com.example.intendi;

import java.util.Random;

public class WhackaGameManager {
    public static String[] allColors = {"Rojo","Verde","Azul","Amarillo","Rosa"};
    public static int[] allBalls = {
            R.drawable.red_ball,R.drawable.green_ball,
            R.drawable.blue_ball,R.drawable.yellow_ball,
            R.drawable.pink_ball
    };
    private String currentColor;
    private int ballAmountLeft;
    private int round;
    private int score;
    private Random rand;

    public WhackaGameManager(){
        this.rand = new Random();
        this.round = 1;
        this.score = 0;
        ballAmountLeft = 0;
    }

    public void prepareColors(String color1){
        this.currentColor = color1;
    }

    public void checkBall(clickableDolphin D){
        if(currentColor.equals(D.getColor())){
            int newIndex = rand.nextInt(4);
            String newColor = allColors[newIndex];
            while(newColor == currentColor){
                newIndex = rand.nextInt(4);
                newColor = allColors[newIndex];
            }
            D.setBall(allBalls[newIndex],allColors[newIndex]);
            ballAmountLeft--;
            incrementScore();
        }

    }

    public int getBallAmountLeft() {
        return ballAmountLeft;
    }

    public void setBallAmountLeft(int ballAmountLeft) {
        this.ballAmountLeft = ballAmountLeft;
    }

    public int getRandomIndex(){
        return rand.nextInt(5);

    }

    public void incrementScore(){
        this.score += 10;
    }

    public String getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(String currentColor) {
        this.currentColor = currentColor;
    }

    public int getScore() {
        return score;
    }

    public int getRound() {
        return round;
    }
}
