package com.example.intendi;

import java.util.Random;


/*
  Class that manages game function, variables, and transaction of the Whack-A-Ball game.
  Stores vital data for the game, including static lists for the ball images, strings, and hex values.
*/
public class WhackaGameManager {
    public static String[] allColors = {"Rojo","Verde","Azul","Amarillo","Rosa"};
    public static String[] allHex = {"#ff0000","#11e303","#001eff","#ffe100","#e28dec"};
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

    /*
    Constructor for empty game manager
     */
    public WhackaGameManager(){
        this.rand = new Random();
        this.round = 1;
        this.score = 0;
        ballAmountLeft = 0;
    }

    /*
    Changes the games current string of the balls to tap.
    */
    public void prepareColors(String color1){
        this.currentColor = color1;
    }

    /*
    Changes a clickable dolphin's current color property and string
    to a new different random one from the static lists.
    */
    public void checkBall(clickableDolphin D){
        if(currentColor.equals(D.getColor())){
            int newIndex = rand.nextInt(5);
            String newColor = allColors[newIndex];
            while(newColor == currentColor){ //while it is the same, select new index
                newIndex = rand.nextInt(5);
                newColor = allColors[newIndex];
            }
            D.setBall(allBalls[newIndex],allColors[newIndex]);
            ballAmountLeft--;
            incrementScore();
        }

    }
    //returns a random hexCode from the static list.
    public String getRandomHex(){
        return allHex[getRandomIndex()];
    }

    //returns the number of balls left of the current color objective.
    public int getBallAmountLeft() {
        return ballAmountLeft;
    }

    //sets the number of balls left of the current color objective.
    public void setBallAmountLeft(int ballAmountLeft) {
        this.ballAmountLeft = ballAmountLeft;
    }

    //returns a random number form 0 to 4 (array limit)
    public int getRandomIndex(){
        return rand.nextInt(5);

    }

    //add the default 10 points to the game score
    public void incrementScore(){
        this.score += 10;
    }

    //returns the current string of the color objective
    public String getCurrentColor() {
        return currentColor;
    }

    //sets the current string of the color objective
    public void setCurrentColor(String currentColor) {
        this.currentColor = currentColor;
    }

    //returns the current score
    public int getScore() {
        return score;
    }

}
