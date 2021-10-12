package com.example.intendi;
import static java.lang.Math.pow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class SendaManager {

    private int boardSize;
    private int round;
    private int score;
    private int lifes;

    private int indexCompared;
    private Random rand;

    //Numbers inside cards in board
    private int[] boardNumbers;

    //Pattern of cards to be replicated
    private ArrayList<Integer> pattern;

    public SendaManager(){
        this.boardSize = 9;
        this.boardNumbers = new int[50];
        this.rand = new Random();
        this.indexCompared = 0;
        this.pattern = new ArrayList<Integer>();
        this.lifes = 3;
        this.round = 1;
        this.score = 0;

        randomizePattern();
    }

    public int[] getBoardNumbers(){
        return this.boardNumbers;
    }

    public ArrayList<Integer> getPattern(){ return this.pattern; }

    public int getScore(){
        return this.score;
    }

    public void expandBoard(){
        this.boardSize = (int) pow(this.getBoardLen()+1, 2);
    }

    // 1 -> correct, 0 -> nextRound, -1 -> error, 2 changeGrid
    public int compareCurrentCell(int cellId){
        if(cellId != this.pattern.get(indexCompared)){ //Error
            this.lifes--;
            this.indexCompared = 0;
            if(this.lifes == 0){
                return -2; //Game over
            }else{
                return -1; //Player loses 1 life and repeats round
            }
        }else{
            this.indexCompared++;
            if(indexCompared > this.round + 1){ //Check if round is completed
                this.round++;
                this.indexCompared = 0;
                randomizePattern();
                if(this.round+2 > ((this.boardSize*50)/100)){ //Check if board needs to be expanded, 70% cells are being used
                    this.score += 100;
                    return 2;
                }else{ //Next round, no need to expand board
                    this.score += 100;
                    return 0;
                }
            }else{ //Round is not finished
                return 1;
            }
        }
    }

    public int getRound(){
        return this.round;
    }

    public int getBoardSize() {return this.boardSize; }

    public void randomizePattern(){
        //Cards with -1 will not have text
        Arrays.fill(this.boardNumbers, -1);

        //Generate round+2 random numbers between 0 and boardSize, and use that values as indexes to assign order
        this.pattern.clear();

        for (int i=0; i<this.boardSize; i++) {
            this.pattern.add(i);
        }

        Collections.shuffle(pattern);
        for (int i=0; i < this.round+2; i++) {
            this.boardNumbers[pattern.get(i)] =  i;
        }
    }

    public int getBoardLen(){
        return (int) Math.sqrt(this.boardSize);
    }
    public int getLifes() { return this.lifes; }
}
